package es.hol.ecotiffins.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.model.AreaPolygons;

public class ServiceAreasFragment extends Fragment {
    private View rootView;
    private MapView mapView;
    private GoogleMap map;
    private AreaPolygons polygons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_service_areas, container, false);
            polygons = new AreaPolygons();
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
                    drawPolygon(polygons.getAreaLatLngs(AreaPolygons.VAISHALI), ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark), ContextCompat.getColor(getActivity(), R.color.colorPrimaryAlpha));
                    drawPolygon(polygons.getAreaLatLngs(AreaPolygons.SODALA),ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark),ContextCompat.getColor(getActivity(), R.color.colorPrimaryAlpha));
                    drawPolygon(polygons.getAreaLatLngs(AreaPolygons.CHITRAKOOT),ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark),ContextCompat.getColor(getActivity(), R.color.colorPrimaryAlpha));
                    drawPolygon(polygons.getAreaLatLngs(AreaPolygons.DCM),ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark),ContextCompat.getColor(getActivity(), R.color.colorPrimaryAlpha));
                    drawPolygon(polygons.getAreaLatLngs(AreaPolygons.SINDHI_CAMP), ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark), ContextCompat.getColor(getActivity(), R.color.colorPrimaryAlpha));
                    drawPolygon(polygons.getAreaLatLngs(AreaPolygons.GOPALPURA), ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark), ContextCompat.getColor(getActivity(), R.color.colorPrimaryAlpha));
                    adjustCamera();

                    final Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_to_top);
                    animation.setFillAfter(true);
                    new CountDownTimer(5000, 1000) {
                        @Override
                        public void onFinish() {
                            rootView.findViewById(R.id.txtMapMsg).startAnimation(animation);
                        }

                        @Override
                        public void onTick(long millisUntilFinished) {

                        }
                    }.start();
                }
            });
    }

    private void drawPolygon(ArrayList<LatLng> latLngs, int strokeColor, int fillColor) {
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.strokeWidth(3);
        polygonOptions.strokeColor(strokeColor);
        polygonOptions.fillColor(fillColor);
        for (LatLng latLng : latLngs) {
            polygonOptions.add(latLng);
        }
        map.addPolygon(polygonOptions);
    }

    private void adjustCamera() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(26.948051, 75.717762));
        builder.include(new LatLng(26.933972, 75.812862));
        builder.include(new LatLng(26.855894, 75.725658));
        builder.include(new LatLng(26.858651, 75.822475));
        LatLngBounds bounds = builder.build();
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}