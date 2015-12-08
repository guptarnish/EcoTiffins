package es.hol.ecotiffins.ecotiffins.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.ecotiffins.adapter.ListViewAdapter;
import es.hol.ecotiffins.ecotiffins.controller.WebServiceHandler;
import es.hol.ecotiffins.ecotiffins.controller.WebServiceListener;
import es.hol.ecotiffins.ecotiffins.model.Order;
import es.hol.ecotiffins.ecotiffins.model.TiffinPack;
import es.hol.ecotiffins.ecotiffins.model.WebService;
import es.hol.ecotiffins.ecotiffins.util.GeneralUtilities;
import es.hol.ecotiffins.ecotiffins.util.SharedPreferencesUtilities;

public class OrderFragment extends Fragment implements WebServiceListener{
    private View rootView;
    private AppCompatEditText editPromoCode;
    private Order order;

    private GeneralUtilities generalUtilities;
    private SharedPreferencesUtilities sharedPreferencesUtilities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_order, container, false);
            order = (Order) getArguments().getSerializable("ORDER");
            Log.e("ORDER", order.toString());
            initializeAllElements();
            setOnClickListener();
        }
        return rootView;
    }

    private void initializeAllElements() {
        int orderId;
        TextView txtQty = (TextView) rootView.findViewById(R.id.txtQty);
        TextView txtTotal = (TextView) rootView.findViewById(R.id.txtTotal);
        TextView txtRate = (TextView) rootView.findViewById(R.id.txtRate);
        editPromoCode = (AppCompatEditText) rootView.findViewById(R.id.editPromoCode);

        txtQty.setText(String.valueOf(order.getQuantity()));
        txtRate.setText("Rs. " + order.getPrice());
        txtTotal.setText("Rs. " + order.getQuantity() * Integer.parseInt(order.getPrice()));

        TextView txtDescription = (TextView) rootView.findViewById(R.id.txtDescription);
        if (order.getTitle().equals(TiffinPack.SINGLE)) {
            orderId = 1;
            txtDescription.setText("Please review once before placing an order. Here we are offering a tiffin with free home delivery. Tiffin contains 5 chapatees, two types of dishes, and rice.");
        } else if (order.getTitle().equals(TiffinPack.COMBO)) {
            orderId = 2;
            txtDescription.setText("Please review once before placing an order. Here we are offering three tiffins with free home delivery. Each tiffin contains 5 chapatees, two types of dishes, and rice.");
        } else if (order.getTitle().equals(TiffinPack.MONTHLY)) {
            orderId = 3;
            txtDescription.setText("Please review once before placing an order. Here you are requesting for daily tiffin service. Each Tiffin contains 5 chapatees, two types of dishes, and rice.");
        }

        generalUtilities = new GeneralUtilities(getActivity());
        sharedPreferencesUtilities = new SharedPreferencesUtilities(getActivity());
    }

    private void setOnClickListener() {
        rootView.findViewById(R.id.fabOpen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServiceHandler webServiceHandler = new WebServiceHandler(getActivity());
                webServiceHandler.webServiceListener = OrderFragment.this;
                HashMap<String, String> formData = new HashMap<>();
                formData.put("email", sharedPreferencesUtilities.getEmail());
                formData.put("quantity", (order.getTitle().equals(TiffinPack.MONTHLY) ? "3" : (order.getTitle().equals(TiffinPack.COMBO) ? "2" : "1")));
                if (!editPromoCode.getText().toString().trim().equals(""))
                    formData.put("promo_code", editPromoCode.getText().toString().trim());
                webServiceHandler.requestToServer(
                        (getResources().getString(R.string.api_end_point)) + "order.php",
                        WebService.ORDER,
                        formData,
                        true
                );
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRequestCompleted(String response, int api) {
        try {
            Log.e("Order", response);
            final JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("error").equals("false")) {
                final String orderId = jsonObject.getString("order_id");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(
                                getActivity(), R.style.AlertDialogCustom)
                                .setTitle("Thank You")
                                .setCancelable(false)
                                .setMessage("Your order has been placed. Your order id is "+ orderId +". We will reach to you as soon as possible.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    }
                                }).show();
                    }
                });
            } else {
                generalUtilities.showAlertDialog("Request Cancelled", jsonObject.getString("error_msg") + ". Please try again..", "OK");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestFailure(IOException e, int api) {
        generalUtilities.showAlertDialog("Error", getResources().getString(R.string.request_failure), "OK");
    }
}