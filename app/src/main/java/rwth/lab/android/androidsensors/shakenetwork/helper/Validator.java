package rwth.lab.android.androidsensors.shakenetwork.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by evgenijavstein on 16/05/15.
 */


public class Validator implements TextWatcher {
    private View enable;
    private EditText ipField;
    private EditText portField;

    public Validator(EditText ipField, EditText portField, View enable) {
        this.enable = enable;
        this.ipField = ipField;
        this.portField = portField;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Pattern ipPattern = Patterns.IP_ADDRESS;
        Pattern portPattern = Pattern.compile("^[0-9]+$");

        Matcher ipMatcher = ipPattern.matcher(ipField.getText().toString());
        Matcher portMatcher = portPattern.matcher(portField.getText().toString());
        if (ipMatcher.matches() && portMatcher.matches())
            enable.setEnabled(true);
        else
            enable.setEnabled(false);
    }
}
