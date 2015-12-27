package es.hol.ecotiffins.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.controller.WebServiceHandler;
import es.hol.ecotiffins.controller.WebServiceListener;
import es.hol.ecotiffins.model.Order;
import es.hol.ecotiffins.model.TiffinPack;
import es.hol.ecotiffins.model.WebService;
import es.hol.ecotiffins.util.GeneralUtilities;
import es.hol.ecotiffins.util.SharedPreferencesUtilities;

public class OrderFragment extends Fragment implements WebServiceListener{
    private View rootView;
    private AppCompatEditText editQty;
    private AppCompatEditText editAddress;
    private AppCompatEditText editPromoCode;
    private FloatingActionButton fabOrder;
    private Order order;

    private GeneralUtilities generalUtilities;
    private SharedPreferencesUtilities sharedPreferencesUtilities;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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
        generalUtilities = new GeneralUtilities(getActivity());
        sharedPreferencesUtilities = new SharedPreferencesUtilities(getActivity());

        final TextView txtTotal = (TextView) rootView.findViewById(R.id.txtTotal);
        final TextView txtRate = (TextView) rootView.findViewById(R.id.txtRate);
        editQty = (AppCompatEditText) rootView.findViewById(R.id.editQty);
        editAddress = (AppCompatEditText) rootView.findViewById(R.id.editAddress);
        editPromoCode = (AppCompatEditText) rootView.findViewById(R.id.editPromoCode);
        editAddress.setText(sharedPreferencesUtilities.getAddress());

        editQty.setText(String.valueOf(order.getQuantity()));
        txtRate.setText("Rs. " + order.getPrice());
        txtTotal.setText("Rs. " + order.getQuantity() * Integer.parseInt(order.getPrice()));

        TextView txtDescription = (TextView) rootView.findViewById(R.id.txtDescription);
        if (order.getTitle().equals(TiffinPack.SINGLE)) {
            txtDescription.setText(getActivity().getResources().getString(R.string.single_pack_contains));
            editQty.setEnabled(false);
        } else if (order.getTitle().equals(TiffinPack.COMBO)) {
            txtDescription.setText(getActivity().getResources().getString(R.string.combo_pack_contains));
            editQty.setEnabled(true);
        } else if (order.getTitle().equals(TiffinPack.MONTHLY)) {
            txtDescription.setText(getActivity().getResources().getString(R.string.monthly_pack_contains));
            editQty.setEnabled(false);
        }

        editQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().equals(""))
                    txtTotal.setText("Rs. " + Integer.parseInt(s.toString()) * Integer.parseInt(order.getPrice()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     *
     */
    private void setOnClickListener() {
        fabOrder = (FloatingActionButton) rootView.findViewById(R.id.fabOpen);
        fabOrder.setColorFilter(Color.WHITE);
        fabOrder.setVisibility(View.GONE);
        fabOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAnOrder();
            }
        });
    }

    private void requestAnOrder() {
        WebServiceHandler webServiceHandler = new WebServiceHandler(getActivity());
        webServiceHandler.webServiceListener = OrderFragment.this;
        HashMap<String, String> formData = new HashMap<>();
        formData.put("email", sharedPreferencesUtilities.getEmail());
        formData.put("address", editAddress.getText().toString());
        formData.put("quantity", editQty.getText().toString().trim());
        formData.put("quantity_type", (order.getTitle().equals(TiffinPack.MONTHLY) ? "3" : (order.getTitle().equals(TiffinPack.COMBO) ? "2" : "1")));
        if (!editPromoCode.getText().toString().trim().equals(""))
            formData.put("promo_code", editPromoCode.getText().toString().trim());

        /*if(order.getTitle().equals(TiffinPack.COMBO)) {

        } else {

        }*/
        if (!editAddress.getText().toString().trim().equals("")) {
            if (!editQty.getText().toString().trim().equals("") && !editQty.getText().toString().trim().equals("0")) {
                if(order.getTitle().equals(TiffinPack.COMBO)){
                    if (!editQty.getText().toString().trim().equals("1")) {
                        webServiceHandler.requestToServer(
                                (getResources().getString(R.string.api_end_point)) + "order.php",
                                WebService.ORDER,
                                formData,
                                true
                        );
                    } else {
                        generalUtilities.showAlertDialog("Message","You will have to order more than one tiffin for Combo Pack.","OK");
                    }

                } else {
                    webServiceHandler.requestToServer(
                            (getResources().getString(R.string.api_end_point)) + "order.php",
                            WebService.ORDER,
                            formData,
                            true
                    );
                }
            } else {
                generalUtilities.showAlertDialog("Message","Please enter quantity of tiffins.","OK");
            }
        } else {
            setValidationError(rootView.findViewById(R.id.txtInputLayoutAddress), "Please enter a valid address");
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
        ((Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE)).vibrate(50);
        //Here I am handling typecasting so that there is no need to cast before method is called
        TextInputLayout textInputLayout = (TextInputLayout) view;
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(errorMessage);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.order, menu);
        //super.onCreateOptionsMenu(menu, inflater);
        menu.getItem(0).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order:
                requestAnOrder();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}