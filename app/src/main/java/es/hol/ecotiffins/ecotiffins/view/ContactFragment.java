package es.hol.ecotiffins.ecotiffins.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.hol.ecotiffins.ecotiffins.R;

public class ContactFragment extends Fragment {
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        }
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}