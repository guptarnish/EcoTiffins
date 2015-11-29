package es.hol.ecotiffins.ecotiffins;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;


/**
 * Author       :   Mohsin Khan
 * Designation  :   Software Developer
 * E-mail       :   khan.square@gmail.com
 * Date         :   9/27/2015
 * Purpose      :   General utilities for the app
 * Description  :   Some basic operations like checking internet connectivity, setting default fonts, making toolbar etc.
 */

@SuppressWarnings({"ConstantConditions", "deprecation"})
public class GeneralUtilities {
    private Context context;

    public GeneralUtilities(Context context) {
        this.context = context;
    }

    public Typeface getRegularTypeFace () {
        return Typeface.createFromAsset(context.getAssets(), "fonts/regular_font.otf");
    }

    public Boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if(info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getScreenWidth() {
        int width;
        Point point = new Point();
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT < 13) {
            width=display.getWidth();
        } else {
            display.getSize(point);
            width=point.x;
        }
        return width;
    }
    public int getScreenHeight() {
        Point point = new Point();
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT < 13) {
            return display.getHeight();
        } else {
            display.getSize(point);
            return point.y;
        }
    }
    public static Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null) return null;
        try {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException e) {
            return null;
        }
        Rect position = new Rect();
        position.left = loc_int[0];
        position.top = loc_int[1];
        position.right = position.left + v.getWidth();
        position.bottom = position.top + v.getHeight();
        return position;
    }

    public void showAlertDialog(String title, String message, String buttonName) {
        new AlertDialog.Builder(
                context, R.style.AlertDialogCustom)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}