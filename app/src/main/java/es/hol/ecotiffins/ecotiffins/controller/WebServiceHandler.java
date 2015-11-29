package es.hol.ecotiffins.ecotiffins.controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.ecotiffins.model.WebService;
import okio.Buffer;

public class WebServiceHandler {
    /**
     * Context is used to show the progressbar
     * in the popup window
     */
    private Context context;

    /**
     * I'm showing progress bar when a device request to the server
     * the progressbar will be inflated in a popup window
     * As we received any response the popup will be dismissed automatically
     */
    private PopupWindow popupWindow;

    /**
     * OkHttp is a third party library to make http calls to the server
     * It is fast and and a convenient way to call APIs
     */
    private OkHttpClient client = new OkHttpClient();

    /**
     * It is an interface contains a onRequestCompleted() method
     * when we get any response from the server this method will be invoked
     * So you have to implement this interface & override its method when you make any API call
     */
    public WebServiceListener webServiceListener;

    /**
     * Timeout is in seconds for socket and connection both
     * This constant will effect both socket and server
     */
    private final int REQUEST_TIMEOUT = 20;

    public WebServiceHandler(Context context) {
        this.context = context;
        client.setConnectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
        client.setReadTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
    }

    public void requestToServer(String url, final int api, HashMap<String, String> formData, final boolean progress) {
        if (progress)
            startProgressBar();
        Request request = new Request.Builder()
                .url(url)
                .post(getRequestBody(formData))
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (progress)
                    stopProgressBar();
                e.printStackTrace();
                //Passing the data to the interface
                webServiceListener.onRequestFailure(e, api);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (progress)
                    stopProgressBar();

                //Passing the data to the interface
                webServiceListener.onRequestCompleted(response.body().string(), api);
            }
        });
        printRequest(request);
    }

    /**
     * This method is responsible to make API call to the server
     * Results of the request will be available in WebServiceListener.onRequestCompleted()
     *
     * @param url server address on which request is to be sent
     * @param api pass the constant of WebService,class
     * @see WebService
     * @see WebServiceListener
     */
    public void requestToServer(String url, final int api, HashMap<String, String> formData) {
        startProgressBar();
        Request request = new Request.Builder()
                .url(url)
                .post(getRequestBody(formData))
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                stopProgressBar();
                e.printStackTrace();
                //Passing the data to the interface
                webServiceListener.onRequestFailure(e, api);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                stopProgressBar();
                //Passing the data to the interface
                webServiceListener.onRequestCompleted(response.body().string(), api);
            }
        });
        printRequest(request);
    }

    /**
     * Method will manage form data for all apis and prepare a RequestBody
     * which is needed to call the api
     *
     * @param formData a HashMap that consist of all data to be send to the server
     * @return RequestBody with proper key-value configuration
     */
    private RequestBody getRequestBody(HashMap<String, String> formData) {
        //We can get a Set of all keys mapped in HashMap
        Set<String> keySet = formData.keySet();
        //We can't directly get the keys so I'm getting a List using this Set
        ArrayList<String> keyList = new ArrayList<>(keySet);

        //Adding all keys and their values to FormEncodingBuilder
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        for (String key : keyList) {
            formEncodingBuilder.add(key, formData.get(key));
        }

        //Now returning a RequestBody
        return formEncodingBuilder.build();
    }

    /**
     * This method will create a popup window and inflate the layout
     * R.layout.layout_progressbar.
     * Here you can also define the colors of progressbar.
     */
    public void startProgressBar() {
        View dialogLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_progressbar, null);
        popupWindow = new PopupWindow(dialogLayout, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);

        //It is necessary to initialize CircleProgressBar to start working with it.
        CircleProgressBar circleProgressBar = (CircleProgressBar) dialogLayout.findViewById(R.id.progressBar);
        circleProgressBar.setVisibility(View.VISIBLE);
        //Changing the color combination with an VARARGS (Variable Length Argument) of type int
        circleProgressBar.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        //Setting up the position of PopUp Window
        popupWindow.showAtLocation(dialogLayout, Gravity.CENTER, 0, 0);
    }

    /**
     * This method will hide the progressbar by dismissing the popup window
     * Just because we cant touch the UI from another thread, that's why I'm using
     * runOnUiThread()
     */
    public void stopProgressBar() {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * Method will print Http Request body to the LogCat Error
     *
     * @param request pass the request to print its body
     */
    private void printRequest(Request request) {
        try {
            Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            Log.e("WEB_SERVICE", buffer.readUtf8());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
