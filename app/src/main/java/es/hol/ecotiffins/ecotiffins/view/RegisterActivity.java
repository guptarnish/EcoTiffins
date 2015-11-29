package es.hol.ecotiffins.ecotiffins.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import es.hol.ecotiffins.ecotiffins.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewFlipper viewFlipper;
    private AppCompatEditText editEmail;
    private AppCompatEditText editName;
    private AppCompatEditText editPhone;
    private AppCompatEditText editAddress;
    private AppCompatEditText editPassword;
    private AppCompatEditText editConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeComponents();
        setOnClickListeners();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextToSecondScreen :
                //Validating user to fill the required field & email validations
                //If user satisfies the conditions, we will move to the next screen
                //Otherwise set error to TextInputLayout
                if (editName.getText().toString().trim().length() > 0) {
                    if (Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString().trim()).matches()) {
                        //Setting up animation to view flipper and moving to the next state
                        viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                        viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);
                        viewFlipper.showNext();
                    } else {
                        setValidationError(findViewById(R.id.txtInputLayoutEmail), getResources().getString(R.string.error_email));
                    }
                } else {
                    setValidationError(findViewById(R.id.txtInputLayoutName), "Please enter your name. It can't be empty");
                }
                break;

            case R.id.btnNextToThirdScreen :
                //Validating user to fill the required field
                //If user satisfies the conditions, we will move to the next screen
                //Otherwise set error to TextInputLayout
                if (editPhone.getText().toString().trim().length() == 10) {
                    if (editAddress.getText().toString().trim().length() >= 10) {
                        //Setting up animation to view flipper and moving to the next state
                        viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                        viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);
                        viewFlipper.showNext();
                    } else {
                        setValidationError(findViewById(R.id.txtInputLayoutAddress), "Please enter a valid address");
                    }
                } else {
                    setValidationError(findViewById(R.id.txtInputLayoutPhone), "Please enter your mobile number");
                }
                break;

            case R.id.btnRegister :

                break;
            case R.id.imgBack :
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
}