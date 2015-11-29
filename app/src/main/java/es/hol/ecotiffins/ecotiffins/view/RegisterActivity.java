package es.hol.ecotiffins.ecotiffins.view;

import android.content.Context;

import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Patterns;
import android.view.View;

import es.hol.ecotiffins.ecotiffins.controller.WebServiceHandler;
import es.hol.ecotiffins.ecotiffins.controller.WebServiceListener;
import es.hol.ecotiffins.ecotiffins.model.WebService;
import es.hol.ecotiffins.ecotiffins.util.GeneralUtilities;

import android.widget.ViewFlipper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.ecotiffins.util.SharedPreferencesUtilities;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private ViewFlipper viewFlipper;
    private AppCompatEditText editEmail;
    private AppCompatEditText editName;
    private AppCompatEditText editPhone;
    private AppCompatEditText editAddress;
    private AppCompatEditText editPassword;
    private AppCompatEditText editConfirm;
    private WebServiceHandler webServiceHandler;
    private GeneralUtilities generalUtilities;
    private SharedPreferencesUtilities sharedPreferencesUtilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        generalUtilities = new GeneralUtilities(this);
        webServiceHandler = new WebServiceHandler(RegisterActivity.this);
        webServiceHandler.webServiceListener = this;
        sharedPreferencesUtilities = new SharedPreferencesUtilities(this);

        initializeComponents();
        setOnClickListeners();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextToSecondScreen:
                //Validating user to fill the required field & email validations
                //If user satisfies the conditions, we will move to the next screen
                //Otherwise set error to TextInputLayout
                if (editName.getText().toString().trim().length() > 0) {
                    clearValidationError(findViewById(R.id.txtInputLayoutName));
                    if (Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString().trim()).matches()) {
                        clearValidationError(findViewById(R.id.txtInputLayoutEmail));
                        //Setting up animation to view flipper and moving to the next state
                        viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                        viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);
                        viewFlipper.showNext();
                    } else {
                        setValidationError(findViewById(R.id.txtInputLayoutEmail), getResources().getString(R.string.error_email));
                    }
                } else {
                    setValidationError(findViewById(R.id.txtInputLayoutName), getResources().getString(R.string.name_empty));
                }
                break;

            case R.id.btnNextToThirdScreen:
                //Validating user to fill the required field
                //If user satisfies the conditions, we will move to the next screen
                //Otherwise set error to TextInputLayout
                if (editPhone.getText().toString().trim().length() == 10) {
                    if (!(String.valueOf(editPhone.getText().toString().charAt(0)).equals("7") || String.valueOf(editPhone.getText().toString().charAt(0)).equals("8") || String.valueOf(editPhone.getText().toString().charAt(0)).equals("9"))) {
                        setValidationError(findViewById(R.id.txtInputLayoutPhone), getResources().getString(R.string.contact_validate));
                    } else {
                        clearValidationError(findViewById(R.id.txtInputLayoutPhone));
                        if (editAddress.getText().toString().trim().length() > 10) {
                            clearValidationError(findViewById(R.id.txtInputLayoutAddress));
                            //Setting up animation to view flipper and moving to the next state
                            viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                            viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);
                            viewFlipper.showNext();
                        } else {
                            setValidationError(findViewById(R.id.txtInputLayoutAddress), getResources().getString(R.string.address_empty));
                        }
                    }
                } else {
                    setValidationError(findViewById(R.id.txtInputLayoutPhone), getResources().getString(R.string.contact_empty));
                }
                break;

            case R.id.btnRegister:
                if (editPassword.getText().toString().length() < 6) {
                    setValidationError(findViewById(R.id.txtInputLayoutPassword), getResources().getString(R.string.password_empty));
                } else if (!(editPassword.getText().toString().equals(editConfirm.getText().toString()))) {
                    clearValidationError(findViewById(R.id.txtInputLayoutPassword));
                    setValidationError(findViewById(R.id.txtInputLayoutConfirmPassword), getResources().getString(R.string.password_not_confirm));
                } else {
                    clearValidationError(findViewById(R.id.txtInputLayoutConfirmPassword));
                    registerUser();
                }
                break;
            case R.id.imgBack:
                //Setting up animation to view flipper
                viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
                viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);
                viewFlipper.showPrevious();
                break;
        }
    }

    /**
     * Method will setup all click listeners to UI controls
     */
    private void setOnClickListeners() {
        findViewById(R.id.btnNextToSecondScreen).setOnClickListener(this);
        findViewById(R.id.btnNextToThirdScreen).setOnClickListener(this);
        findViewById(R.id.btnRegister).setOnClickListener(this);
        findViewById(R.id.imgBack).setOnClickListener(this);
    }

    /**
     * Method will initialize all AppCompatEditTexts and ViewFlipper used in UI
     */
    private void initializeComponents() {
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        editEmail = (AppCompatEditText) findViewById(R.id.editEmail);
        editName = (AppCompatEditText) findViewById(R.id.editName);
        editPhone = (AppCompatEditText) findViewById(R.id.editPhone);
        editAddress = (AppCompatEditText) findViewById(R.id.editAddress);
        editPassword = (AppCompatEditText) findViewById(R.id.editPassword);
        editConfirm = (AppCompatEditText) findViewById(R.id.editConfirmPassword);
    }

    /**
     * Method is useful to apply error message on TextInputLayouts
     *
     * @param view         on which error will be shown e.g findViewById(R.id.textInputLayout)
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

    /**
     * Method is useful to disable error message on TextInputLayouts
     *
     * @param view on which error will be shown e.g findViewById(R.id.textInputLayout)
     */
    private void clearValidationError(View view) {
        //Here I am handling typecasting so that there is no need to cast before method is called
        TextInputLayout textInputLayout = (TextInputLayout) view;
        textInputLayout.setErrorEnabled(false);
    }

    /**
     * Method will call the API to register user with license number.
     * Here I'm making a HashMap to carry form fields for the Post Request. This HashMap contains Registration Number,
     * Device Id (IMEI) and GCM Id. After collecting the sufficient information, I've already prepared an instance of WebServiceHandler
     * and its listener which listen when Http Request brings results from the server
     */
    private void registerUser() {
        //Preparing form fields for Http Post Request Body
        HashMap<String, String> formData = new HashMap<>();
        formData.put("name", editName.getText().toString().trim());
        formData.put("email", editEmail.getText().toString().trim());
        formData.put("password", editPassword.getText().toString().trim());
        formData.put("contact", editPhone.getText().toString().trim());
        formData.put("address", editAddress.getText().toString().trim());
        //Checking internet connectivity and then requesting to the server
        if (generalUtilities.isConnected()) {
            webServiceHandler.requestToServer((getResources().getString(R.string.api_end_point)) + "register.php", WebService.REGISTER, formData, true);
        } else {
            generalUtilities.showAlertDialog("Error", getResources().getString(R.string.internet_error), "OK");
        }
    }

    @Override
    public void onRequestCompleted(String response, int api) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("error").equals("false")) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            } else {
                generalUtilities.showAlertDialog("Request Cancelled", new JSONObject(response).getString("error_msg"), "OK");
                startActivity(new Intent(getApplication(), RegisterActivity.class));
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestFailure(IOException e, int api) {
        e.printStackTrace();
        generalUtilities.showAlertDialog("Error", getResources().getString(R.string.request_failure), "OK");
        startActivity(new Intent(getApplication(), RegisterActivity.class));
        finish();
    }
}