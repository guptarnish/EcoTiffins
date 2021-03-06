package es.hol.ecotiffins.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import es.hol.ecotiffins.adapter.HistoryAdapter;
import es.hol.ecotiffins.controller.WebServiceHandler;
import es.hol.ecotiffins.controller.WebServiceListener;
import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.model.History;
import es.hol.ecotiffins.model.WebService;
import es.hol.ecotiffins.sqlite.DatabaseHandler;
import es.hol.ecotiffins.util.GeneralUtilities;
import es.hol.ecotiffins.util.SharedPreferencesUtilities;

public class HistoryFragment extends Fragment implements WebServiceListener {
    private View rootView;
    private GeneralUtilities generalUtilities;
    private DatabaseHandler databaseHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_history, container, false);
            generalUtilities = new GeneralUtilities(getActivity());
            databaseHandler = new DatabaseHandler(getActivity());
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
            final ArrayList<History> histories = new ArrayList<>();
            final JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("error").equals("false")) {
                JSONArray jsonArray = jsonObject.getJSONArray("history");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectHistory = jsonArray.getJSONObject(i);
                    histories.add(new History(
                            jsonObjectHistory.getString("type") + " Pack",
                            jsonObjectHistory.getString("order_id"),
                            jsonObjectHistory.getString("price"),
                            jsonObjectHistory.getString("created")
                    ));
                }
                databaseHandler.insertData(histories);
            } else {
                generalUtilities.showAlertDialog("Request Cancelled", jsonObject.getString("error_msg") + ". Please try again..", "OK");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    populateListView(databaseHandler.getHistory());
                }
            });
        }
    }

    @Override
    public void onRequestFailure(IOException e, int api) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                populateListView(databaseHandler.getHistory());
            }
        });
    }
}