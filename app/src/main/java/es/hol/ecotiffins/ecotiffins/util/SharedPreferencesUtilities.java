package es.hol.ecotiffins.ecotiffins.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.NavigableMap;

import es.hol.ecotiffins.ecotiffins.R;

/**
 * Author       :   Mohsin Khan
 * Designation  :   Software Developer
 * E-mail       :   khan.square@gmail.com
 * Date         :   9/27/2015
 * Purpose      :   To store an integer value in shared preferences
 * Description  :   Application will save edited images in file system (SD CARD),
 *                  So it is necessary to provide a unique identity to each image.
 *                  To fulfill this requirement, This class has been created.
 */

public class SharedPreferencesUtilities {
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedpreferences;

    public static final String USER = "user";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";

    public SharedPreferencesUtilities(Context context) {
        sharedpreferences = context.getSharedPreferences(context.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public String getUser() {
            return sharedpreferences.getString(USER, "");
    }

    public String getEmail() {
        return sharedpreferences.getString(EMAIL, "");
    }

    public String getPassword() {
        return sharedpreferences.getString(PASSWORD, "");
    }

    public String getAddress() {
        return sharedpreferences.getString(ADDRESS, "");
    }

    public String getPhone() {
        return sharedpreferences.getString(PHONE, "");
    }

    public void setUser(String user) {
        editor = sharedpreferences.edit();
        editor.putString(USER, user);
        editor.apply();
    }

    public void setEmail(String email) {
        editor = sharedpreferences.edit();
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public void setPassword(String password) {
        editor = sharedpreferences.edit();
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    public void setPhone(String phone) {
        editor = sharedpreferences.edit();
        editor.putString(PHONE, phone);
        editor.apply();
    }

    public void setAddress(String address) {
        editor = sharedpreferences.edit();
        editor.putString(ADDRESS, address);
        editor.apply();
    }
}