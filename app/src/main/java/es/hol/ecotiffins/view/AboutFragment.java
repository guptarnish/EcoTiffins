package es.hol.ecotiffins.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import es.hol.ecotiffins.ecotiffins.R;

public class AboutFragment extends Fragment {
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_about, container, false);
            prepareWebViewIntro();
        }
        return rootView;
    }

    private void prepareWebViewIntro() {
        WebView webViewIntro = (WebView) rootView.findViewById(R.id.webViewIntro);
        webViewIntro.setBackgroundColor(Color.TRANSPARENT);
        webViewIntro.loadData(getString(R.string.app_intro), "text/html", "utf-8");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}