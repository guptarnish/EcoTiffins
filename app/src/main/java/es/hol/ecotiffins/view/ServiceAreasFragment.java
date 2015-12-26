package es.hol.ecotiffins.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

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
                    drawPolygon(getPoints());
                }
            });
    }

    private void drawPolygon(ArrayList<LatLng> latLngs) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.strokeWidth(5);
        polygonOptions.strokeColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        polygonOptions.fillColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryAlpha));
        for (LatLng latLng : latLngs) {
            polygonOptions.add(latLng);
            builder.include(latLng);
        }
        map.addPolygon(polygonOptions);
        LatLngBounds bounds = builder.build();
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    private ArrayList<LatLng> getPoints() {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(26.920935, 75.730367));
        latLngs.add(new LatLng(26.920055, 75.740152));
        latLngs.add(new LatLng(26.922351, 75.743371));
        latLngs.add(new LatLng(26.915042, 75.743628));
        latLngs.add(new LatLng(26.915348, 75.745988));
        latLngs.add(new LatLng(26.911713, 75.746074));
        latLngs.add(new LatLng(26.911445, 75.753026));
        latLngs.add(new LatLng(26.905972, 75.752812));
        latLngs.add(new LatLng(26.905657, 75.750709));
        latLngs.add(new LatLng(26.902327, 75.750602));
        latLngs.add(new LatLng(26.901763, 75.747566));
        latLngs.add(new LatLng(26.905197, 75.747383));
        latLngs.add(new LatLng(26.905159, 75.745087));
        latLngs.add(new LatLng(26.905848, 75.740345));
        latLngs.add(new LatLng(26.901887, 75.740581));
        latLngs.add(new LatLng(26.901868, 75.743499));
        latLngs.add(new LatLng(26.898270, 75.743907));
        latLngs.add(new LatLng(26.895295, 75.733543));
        latLngs.add(new LatLng(26.894414, 75.733800));
        latLngs.add(new LatLng(26.893649, 75.731526));
        latLngs.add(new LatLng(26.892194, 75.730024));
        latLngs.add(new LatLng(26.892654, 75.729595));
        latLngs.add(new LatLng(26.892041, 75.728822));
        latLngs.add(new LatLng(26.892032, 75.727513));
        latLngs.add(new LatLng(26.891247, 75.725174));
        latLngs.add(new LatLng(26.890941, 75.725132));
        latLngs.add(new LatLng(26.890903, 75.723994));
        latLngs.add(new LatLng(26.892089, 75.723994));
        latLngs.add(new LatLng(26.892089, 75.724445));
        latLngs.add(new LatLng(26.894424, 75.724316));
        latLngs.add(new LatLng(26.897677, 75.724874));
        latLngs.add(new LatLng(26.897849, 75.721891));
        latLngs.add(new LatLng(26.898155, 75.721956));
        latLngs.add(new LatLng(26.898921, 75.722857));
        latLngs.add(new LatLng(26.898634, 75.725153));
        latLngs.add(new LatLng(26.898366, 75.728887));
        latLngs.add(new LatLng(26.898672, 75.731719));
        latLngs.add(new LatLng(26.903513, 75.728586));
        latLngs.add(new LatLng(26.909675, 75.729144));
        latLngs.add(new LatLng(26.909751, 75.726719));
        latLngs.add(new LatLng(26.915396, 75.727084));
        latLngs.add(new LatLng(26.915224, 75.729766));
        latLngs.add(new LatLng(26.920925, 75.730346));
        return latLngs;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}