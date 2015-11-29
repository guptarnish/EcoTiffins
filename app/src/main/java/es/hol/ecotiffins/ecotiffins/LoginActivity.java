package es.hol.ecotiffins.ecotiffins;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, WebServiceListener{

    private ViewFlipper viewFlipper;
    private AppCompatEditText editLicenseNum;
    private AppCompatEditText editPinTypeNum;

    private WebServiceHandler webServiceHandler;
    private GeneralUtilities generalUtilities;
    private SharedPreferencesUtilities sharedPreferencesUtilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        generalUtilities = new GeneralUtilities(this);
        webServiceHandler = new WebServiceHandler(LoginActivity.this);
        webServiceHandler.webServiceListener = this;
        sharedPreferencesUtilities = new SharedPreferencesUtilities(this);

        //If user has been logged in already then this method will redirect to
        //MainActivity otherwise program flow will be as usual
        logInIfUserExist();

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        editLicenseNum = (AppCompatEditText) findViewById(R.id.editEmail);
        editPinTypeNum = (AppCompatEditText) findViewById(R.id.editPassword);

        setOnClickListeners();
    }

    /**
     * Method will setup all click listeners to UI controls
     */
    private void setOnClickListeners() {
        findViewById(R.id.btnNext).setOnClickListener(this);
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.imgBack).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext :
                //Validating user to fill the required field & entered number should be in four digits
                //If user satisfies the conditions, we will register the license number
                //Otherwise set error to TextInputLayout
                if (!editLicenseNum.getText().toString().matches ("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                    //Setting up animation to view flipper and moving to the next state
                    viewFlipper.setInAnimation(LoginActivity.this, R.anim.slide_in_from_right);
                    viewFlipper.setOutAnimation(LoginActivity.this, R.anim.slide_out_to_left);
                    viewFlipper.showNext();
                    ((TextView) findViewById(R.id.txtEmailAddress)).setText(editLicenseNum.getText().toString());
                } else {
                    setValidationError(findViewById(R.id.txtInputLayoutEmail), getResources().getString(R.string.error_email));
                }
                break;

            case R.id.btnLogin :
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
                //logInIfUserExist();
                break;
            case R.id.imgBack :
                //Setting up animation to view flipper
                viewFlipper.setInAnimation(LoginActivity.this, R.anim.slide_in_from_left);
                viewFlipper.setOutAnimation(LoginActivity.this, R.anim.slide_out_to_right);
                viewFlipper.showPrevious();
                break;
        }
    }

    /**
     * Method will call the API to register user with license number.
     * Here I'm making a HashMap to carry form fields for the Post Request. This HashMap contains Registration Number,
     * Device Id (IMEI) and GCM Id. After collecting the sufficient information, I've already prepared an instance of WebServiceHandler
     * and its listener which listen when Http Request brings results from the server
     */
    private void registerLicenseNumber() {
        //Preparing form fields for Http Post Request Body
        HashMap<String, String> formData = new HashMap<>();
        formData.put("RegistrationNo", editLicenseNum.getText().toString());
        formData.put("DeviceGcmId", "asd123");
        //Checking internet connectivity and then requesting to the server
        if (generalUtilities.isConnected()) {
            webServiceHandler.requestToServer(getResources().getString(R.string.api_end_point), WebService.LOGIN, formData, true);
        }

    }


    /**
     * Method is useful to apply error message on TextInputLayouts
     * @param view on which error will be shown e.g findViewById(R.id.textInputLayout)
     * @param errorMessage what you want to display
     */
    private void setValidationError(View view, String errorMessage) {
        //Vibrate device on error
        ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(100);
        //Here I am handling typecasting so that there is no need to cast before method is called
        TextInputLayout textInputLayout = (TextInputLayout) view;
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(errorMessage);
    }

    @Override
    public void onRequestCompleted(final String response, int api) {
        if (api == WebService.LOGIN) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equals("1")) {
                    sharedPreferencesUtilities.setUser("New User");
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            //Setting up animation to view flipper and moving to the next state
                            viewFlipper.setInAnimation(LoginActivity.this, R.anim.slide_in_from_right);
                            viewFlipper.setOutAnimation(LoginActivity.this, R.anim.slide_out_to_left);
                            viewFlipper.showNext();
                            ((TextView) findViewById(R.id.txtEmailAddress)).setText(editLicenseNum.getText().toString());
                        }
                    });
                } else {
                    generalUtilities.showAlertDialog("Request Cancelled", new JSONObject(response).getString("message"), "OK");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestFailure(IOException e, int api) {
        e.printStackTrace();
        generalUtilities.showAlertDialog("Error", getResources().getString(R.string.request_failure), "OK");
    }

    /**
     * Method will start MainActivity if it will found any user in shared preferences
     * The method has been called in the onCreate() of LoginActivity and
     * after API call.
     */
    private void logInIfUserExist() {
        if (!sharedPreferencesUtilities.getUser().equals("")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            LoginActivity.this.finish();
        }
    }
}