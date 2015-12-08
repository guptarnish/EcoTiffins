package es.hol.ecotiffins.ecotiffins.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;

import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.ecotiffins.adapter.ListViewAdapter;
import es.hol.ecotiffins.ecotiffins.model.Order;

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