package es.hol.ecotiffins.ecotiffins.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.ecotiffins.adapter.HistoryAdapter;
import es.hol.ecotiffins.ecotiffins.adapter.ListViewAdapter;
import es.hol.ecotiffins.ecotiffins.controller.WebServiceHandler;
import es.hol.ecotiffins.ecotiffins.controller.WebServiceListener;
import es.hol.ecotiffins.ecotiffins.model.History;
import es.hol.ecotiffins.ecotiffins.model.Order;
import es.hol.ecotiffins.ecotiffins.model.TiffinPack;
import es.hol.ecotiffins.ecotiffins.model.WebService;
import es.hol.ecotiffins.ecotiffins.util.GeneralUtilities;
import es.hol.ecotiffins.ecotiffins.util.SharedPreferencesUtilities;

public class HistoryFragment extends Fragment implements WebServiceListener {
    private View rootView;
    private GeneralUtilities generalUtilities;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_history, container, false);
            generalUtilities = new GeneralUtilities(getActivity());
            WebServiceHandler webServiceHandler = new WebServiceHandler(getActivity());
            webServiceHandler.webServiceListener = this;
            HashMap<String, String> formData = new HashMap<>();
            formData.put("email", new SharedPreferencesUtilities(getActivity()).getEmail());
            webServiceHandler.requestToServer(
                    (getResources().getString(R.string.api_end_point)) + "history.php",
                    WebService.HISTORY,
                    formData,
                    true
            );
        }
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void populateListView(ArrayList<History> histories) {
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setEmptyView(rootView.findViewById(R.id.txtEmpty));
        listView.setAdapter(new HistoryAdapter(getActivity(), R.layout.layout_history, histories));
    }

    @Override
    public void onRequestCompleted(String response, int api) {
        try {
            Log.e("History", response);
            final ArrayList<History> histories = new ArrayList<>();
            final JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("error").equals("false")) {
                JSONArray jsonArray = jsonObject.getJSONArray("history");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectHistory = jsonArray.getJSONObject(i);
                    histories.add(new History(
                            jsonObjectHistory.getString("type") + " Pack",
                            "Order Id : ECO_ORDER_" + jsonObjectHistory.getString("order_id"),
                            jsonObjectHistory.getString("price"),
                            jsonObjectHistory.getString("created")
                    ));
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        populateListView(histories);
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