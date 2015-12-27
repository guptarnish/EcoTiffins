package es.hol.ecotiffins.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import es.hol.ecotiffins.controller.WebServiceHandler;
import es.hol.ecotiffins.controller.WebServiceListener;
import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.model.WebService;
import es.hol.ecotiffins.util.GeneralUtilities;
import es.hol.ecotiffins.util.SharedPreferencesUtilities;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, WebServiceListener {

    private ViewFlipper viewFlipper;
    private AppCompatEditText editEmail;
    private AppCompatEditText editPassword;
    private TextView textRegister;

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
        editEmail = (AppCompatEditText) findViewById(R.id.editEmail);
        editPassword = (AppCompatEditText) findViewById(R.id.editPassword);
        textRegister = (TextView) findViewById(R.id.txtRegister);

        setOnClickListeners();
    }

    /**
     * Method will setup all click listeners to UI controls
     */
    private void setOnClickListeners() {
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setColorFilter(Color.WHITE);
        findViewById(R.id.btnNext).setOnClickListener(this);
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.imgBack).setOnClickListener(this);
        findViewById(R.id.txtRegister).setOnClickListener(this);
        findViewById(R.id.txtForget).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                //Validating user to fill the required field & entered number should be in four digits
                //If user satisfies the conditions, we will register the license number
                //Otherwise set error to TextInputLayout
                if (Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString().trim()).matches()) {
                    //Setting up animation to view flipper and moving to the next state
                    viewFlipper.setInAnimation(LoginActivity.this, R.anim.slide_in_from_right);
                    viewFlipper.setOutAnimation(LoginActivity.this, R.anim.slide_out_to_left);
                    viewFlipper.showNext();
                    ((TextView) findViewById(R.id.txtEmailAddress)).setText(editEmail.getText().toString());
                } else {
                    setValidationError(findViewById(R.id.txtInputLayoutEmail), getResources().getString(R.string.error_email));
                }
                break;

            case R.id.btnLogin:
                if (editPassword.getText().toString().trim().equals("")) {
                    setValidationError(findViewById(R.id.txtInputLayoutPassword), getResources().getString(R.string.error_password));
                } else if (editPassword.getText().toString().trim().length() < 6) {
                    setValidationError(findViewById(R.id.txtInputLayoutPassword), getResources().getString(R.string.error_password_min_lenth));
                } else {
                    loginUser();
                }
                break;
            case R.id.imgBack:
                //Setting up animation to view flipper
                viewFlipper.setInAnimation(LoginActivity.this, R.anim.slide_in_from_left);
                viewFlipper.setOutAnimation(LoginActivity.this, R.anim.slide_out_to_right);
                viewFlipper.showPrevious();
                break;

            case R.id.txtRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.txtForget:
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                intent.putExtra("EMAIL", editEmail.getText().toString());
                startActivity(intent);
                break;
        }
    }

    /**
     * Method will call the API to register user with license number.
     * Here I'm making a HashMap to carry form fields for the Post Request. This HashMap contains Registration Number,
     * Device Id (IMEI) and GCM Id. After collecting the sufficient information, I've already prepared an instance of WebServiceHandler
     * and its listener which listen when Http Request brings results from the server
     */
    private void loginUser() {
        //Preparing form fields for Http Post Request Body
        HashMap<String, String> formData = new HashMap<>();
        formData.put("email", editEmail.getText().toString().trim());
        formData.put("password", editPassword.getText().toString().trim());
        //Checking internet connectivity and then requesting to the server
        if (generalUtilities.isConnected()) {
            webServiceHandler.requestToServer((getResources().getString(R.string.api_end_point)) + "login.php", WebService.LOGIN, formData, true);
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
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("error").equals("false")) {
                JSONObject jsonUserObject = jsonObject.getJSONObject("user");
                sharedPreferencesUtilities.setUser(jsonUserObject.getString("name"));
                sharedPreferencesUtilities.setEmail(jsonUserObject.getString("email"));
                sharedPreferencesUtilities.setPhone(jsonUserObject.getString("contact"));
                sharedPreferencesUtilities.setAddress(jsonUserObject.getString("address"));
                logInIfUserExist();
            } else {
                generalUtilities.showAlertDialog("Request Cancelled", new JSONObject(response).getString("error_msg"), "OK");
            }
        } catch (JSONException e) {
            e.printStackTrace();
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