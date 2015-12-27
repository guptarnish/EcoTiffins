package es.hol.ecotiffins.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Patterns;
import android.view.View;
import android.view.Window;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import es.hol.ecotiffins.controller.WebServiceHandler;
import es.hol.ecotiffins.controller.WebServiceListener;
import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.model.WebService;
import es.hol.ecotiffins.util.GeneralUtilities;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private AppCompatEditText editEmail;

    private WebServiceHandler webServiceHandler;
    private GeneralUtilities generalUtilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        generalUtilities = new GeneralUtilities(this);
        webServiceHandler = new WebServiceHandler(ForgetPasswordActivity.this);
        webServiceHandler.webServiceListener = this;

        editEmail = (AppCompatEditText) findViewById(R.id.editEmail);
        editEmail.setText(getIntent().getStringExtra("EMAIL"));
        findViewById(R.id.btnSubmit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                //Validating user to fill the required field
                //Otherwise set error to TextInputLayout
                if (Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString().trim()).matches()) {
                    requestResetPassword();
                } else {
                    setValidationError(findViewById(R.id.txtInputLayoutEmail), getResources().getString(R.string.error_email));
                }
                break;
        }
    }

    private void requestResetPassword() {
        //Preparing form fields for Http Post Request Body
        HashMap<String, String> formData = new HashMap<>();
        formData.put("email", editEmail.getText().toString().trim());
        //Checking internet connectivity and then requesting to the server
        if (generalUtilities.isConnected()) {
            webServiceHandler.requestToServer((getResources().getString(R.string.api_end_point)) + "forgotPassword.php", WebService.FORGET_PASSWORD, formData, true);
        } else {
            generalUtilities.showAlertDialog("Error", getResources().getString(R.string.internet_error), "OK");
        }
    }


    /**
     * Method is useful to apply error message on TextInputLayouts
     *
     * @param view         on which error will be shown e.g findViewById(R.id.textInputLayout)
     * @param errorMessage what you want to display
     */
    private void setValidationError(View view, String errorMessage) {
        //Vibrate device on error
        ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(50);
        //Here I am handling typecasting so that there is no need to cast before method is called
        TextInputLayout textInputLayout = (TextInputLayout) view;
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(errorMessage);
    }

    @Override
    public void onRequestCompleted(String response, int api) {
        try {
            String title;
            String message;
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("error").equals("false")) {
                title = "Message";
                message = jsonObject.getString("error_msg");
                generalUtilities.showAlertDialog(title, message, this);
            } else {
                title = "Request Failure";
                message = jsonObject.getString("error_msg");
                generalUtilities.showAlertDialog(title, message, "OK");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            generalUtilities.showAlertDialog("Request Cancelled", "Request is not completed due to server fault. Please try again.", "OK");
        }
    }

    @Override
    public void onRequestFailure(IOException e, int api) {
        e.printStackTrace();
        generalUtilities.showAlertDialog("Error", getResources().getString(R.string.request_failure), "OK");
    }
}