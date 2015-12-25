package es.hol.ecotiffins.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import es.hol.ecotiffins.ecotiffins.R;

public class ServiceAreasFragment extends Fragment {
    private View rootView;
    public MapView mapView;
    public GoogleMap map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_service_areas, container, false);
            prepareWebViewIntro(savedInstanceState);
        }
        return rootView;
    }

    private void prepareWebViewIntro(Bundle savedInstanceState) {
        mapView = (MapView) rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        MapsInitializer.initialize(getActivity());
        map = mapView.getMap();
        if(map!=null)
            map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    map.getUiSettings().setMyLocationButtonEnabled(true);

                }
            });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}