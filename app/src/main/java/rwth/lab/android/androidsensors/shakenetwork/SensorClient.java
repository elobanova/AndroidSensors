package rwth.lab.android.androidsensors.shakenetwork;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import rwth.lab.android.androidsensors.shakenetwork.packet.*;


/**
 * Created by evgenijavstein on 10/05/15.
 */
public class SensorClient {

    public interface OnShakeFromNetworkListener {


        void onShakeReceived(Shake shake);

        void onError(String message);

    }

    public static final int SO_TIMEOUT = 15000;//keep alive is sent every time this timeout occurs



    private static final String SHAKE_DATA = "shake";
    private static final String ERROR_DATA = "error";
    private static final int SUCESS_SHAKE_RECV = 100;
    private static final int ERROR_SHAKE_RECV = 100;
    public static final byte TYPE_REGISTER = 1;
    public static final byte TYPE_UNREGISTER = 2;
    public static final byte TYPE_KEEPALIVE = 3;
    public static final byte TYPE_EVENT = 4;
    public static final byte TYPE_SHAKE = 5;
    public static final int RECEIVE_BUFFER_MAX = 256;


    private boolean isRegistered=false;
    private OnShakeFromNetworkListener onShakeListener;
    private String sensorServerIp;
    private int sensorServerPort;

    private Thread periodic;
    private Handler uiCallback;
    ReceiverWorker receiverWorker;

    private DatagramSocket udpSocket = null;


    public static SensorClient newInstance() {
        SensorClient sensorClient = new SensorClient();
        try {
            sensorClient.udpSocket = new DatagramSocket();
        } catch (SocketException e) {
            return null;
        }
        return sensorClient;
    }


    public void sendEvent() {

        new UDPSendTask(TYPE_EVENT).execute(new MEvent());
    }

    public void register(String name) {
        if ((periodic != null && !periodic.isAlive()) || (periodic == null))
            new UDPSendTask(TYPE_REGISTER).execute(new Register(name));
    }

    /**
     * Method send keep alive and tries to receive a Shake periodically
     */
    private void sendKeepAlivePeriodically() {

        //only once else there is a mess
        receiverWorker = new ReceiverWorker();
        periodic = new Thread(receiverWorker);


        uiCallback = new Handler() {
            public void handleMessage(Message msg) {
                Shake shake = null;
                // BACK on UI THREAD do STUFF WITH UI
                if (msg.arg1 == SUCESS_SHAKE_RECV) {
                    shake = (Shake) msg.getData().getSerializable(SHAKE_DATA);
                    onShakeListener.onShakeReceived(shake);
                } else if (msg.arg1 == ERROR_SHAKE_RECV) {
                    IOException e = (IOException) msg.getData().getSerializable(ERROR_DATA);
                    onShakeListener.onError(e.getMessage());
                }


            }
        };
        periodic.start();


    }



    public void unregister() {
        uiCallback.removeCallbacksAndMessages(null);
        receiverWorker.terminate();
        try {
            periodic.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //send unregister header
        isRegistered=false;
        new UDPSendTask(TYPE_UNREGISTER).execute(new Unregister());
    }

    public void setOnShakeListener(OnShakeFromNetworkListener onShakeListener) {
        this.onShakeListener = onShakeListener;
    }


    public void setSensorServerIp(String sensorServerIp) {
        this.sensorServerIp = sensorServerIp;
    }

    public void setSensorServerPort(int sensorServerPort) {
        this.sensorServerPort = sensorServerPort;
    }
    public boolean isRegistered() {
        return isRegistered;
    }

    private void sendAsDatagram(Packet packet1) throws IOException {
        InetAddress address;
        Packet packet = packet1;
        byte[] buffer = packet.getBytes();


        address = InetAddress.getByName(sensorServerIp);//localhost as to be accesssed from emulator
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length,
                address, sensorServerPort);
        udpSocket.send(datagramPacket);
    }


    /**
     * Periodic worker running keep-alive request after each SO_TIMEOUT seconds.
     * keep-alive -> 15 seconds receive blocking -> keep alive -> 15 seconds receive blocking etc.ﬂ
     */
    class ReceiverWorker implements Runnable {
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Message msg = new Message();
                Bundle bundle = new Bundle();
                DatagramPacket packet;

                //send keep alive
                try {
                    sendAsDatagram(new KeepAlive());


                    //try receive something for SO_TIMEOUT seconds
                    byte[] recBuf = new byte[RECEIVE_BUFFER_MAX];

                    udpSocket.setSoTimeout(SO_TIMEOUT);//trying to read only for 15 seconds, then keep send keep alive again

                    packet = new DatagramPacket(recBuf, recBuf.length);
                    udpSocket.receive(packet);
                } catch (InterruptedIOException e) {
                    //timed out
                    continue;//go back to beginning send keep alive
                } catch (IOException e) {
                    //error occured while receiving the packet/ while sending keep alive
                    msg.arg1 = ERROR_SHAKE_RECV;
                    bundle.putSerializable(ERROR_DATA, e);
                    continue;
                }

                //SUCCESS receiving packet
                Shake shake = null;
                if (packet.getData()[0] == SensorClient.TYPE_SHAKE)
                    shake = new Shake(packet.getData(), packet.getLength());

                msg.arg1 = SUCESS_SHAKE_RECV;
                bundle.putSerializable(SHAKE_DATA, shake);
                msg.setData(bundle);
                uiCallback.sendMessage(msg);

            }
        }

        public void terminate() {
            running = false;
        }


    }


    /**
     * Simple task for sending, no response reading
     */
    class UDPSendTask extends AsyncTask<Packet, Void, Void> {
        byte type;

        public UDPSendTask(byte type) {
            this.type = type;
        }


        @Override
        protected Void doInBackground(Packet... packets) {

            try {
                sendAsDatagram(packets[0]);

                return null; //shake;
            } catch (UnknownHostException e) {
                onShakeListener.onError(e.getMessage());
            } catch (SocketException e) {
                onShakeListener.onError(e.getMessage());
            } catch (IOException e) {
                onShakeListener.onError(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (type == TYPE_REGISTER) {
                isRegistered=true;
                sendKeepAlivePeriodically();
            }
        }
    }

}
