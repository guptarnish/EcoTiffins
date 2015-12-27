package es.hol.ecotiffins.util;

import android.content.Context;
import android.content.SharedPreferences;

import es.hol.ecotiffins.ecotiffins.R;

/**
 * Author       :   Mohsin Khan
 * Designation  :   Software Developer
 * E-mail       :   khan.square@gmail.com
 * Date         :   9/27/2015
 * Purpose      :   To store an integer value in shared preferences
 * Description  :   Application will save edited images in file system (SD CARD),
 * So it is necessary to provide a unique identity to each image.
 * To fulfill this requirement, This class has been created.
 */

public class SharedPreferencesUtilities {
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedpreferences;

    public static final String USER = "user";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";

    public static final String SINGLE = "single";
    public static final String COMBO = "combo";
    public static final String MONTHLY = "monthly";

    public static final String PROMO_CODE = "promo";
    public static final String DISCOUNT = "discount";

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

    public String getSingle() {
        return sharedpreferences.getString(SINGLE, "Price Not Available");
    }

    public void setSingle(String price) {
        editor = sharedpreferences.edit();
        editor.putString(SINGLE, price);
        editor.apply();
    }

    public String getCombo() {
        return sharedpreferences.getString(COMBO, "Price Not Available");
    }

    public void setCombo(String price) {
        editor = sharedpreferences.edit();
        editor.putString(COMBO, price);
        editor.apply();
    }

    public String getMonthly() {
        return sharedpreferences.getString(MONTHLY, "Price Not Available");
    }

    public void setMonthly(String price) {
        editor = sharedpreferences.edit();
        editor.putString(MONTHLY, price);
        editor.apply();
    }

    public String getPromoCode() {
        return sharedpreferences.getString(PROMO_CODE, "NO_PROMO");
    }

    public void setPromoCode(String code) {
        editor = sharedpreferences.edit();
        editor.putString(PROMO_CODE, code);
        editor.apply();
    }

    public String getDiscount() {
        return sharedpreferences.getString(DISCOUNT, "0");
    }

    public void setDiscount(String discount) {
        editor = sharedpreferences.edit();
        editor.putString(DISCOUNT, discount);
        editor.apply();
    }

    public void flushPreferences() {
        editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }
}