package es.hol.ecotiffins.ecotiffins.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class HomeFragment extends Fragment {
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            startSlider();
            populateListView();
        }
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void populateListView() {
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order("Single Pack", "Only 1 tiffin", 1, 50, R.mipmap.ic_launcher));
        orders.add(new Order("Combo Pack", "You'll get 3 tiffins", 3, 45, R.mipmap.ic_launcher));
        orders.add(new Order("Monthly Booking", "To get Daily service", 1, 40, R.mipmap.ic_launcher));
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(new ListViewAdapter(getActivity(), R.layout.layout_listitem, orders));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Call API here
            }
        });
    }

    private void startSlider() {
        SliderLayout sliderLayout = (SliderLayout) rootView.findViewById(R.id.slider);
        HashMap<String,Integer> images = new HashMap<>();
        images.put("Hannibal", R.drawable.img_slider);
        images.put("Big Bang Theory", R.drawable.img_slider_one);
        images.put("House of Cards", R.drawable.img_slider_two);
        images.put("Game of Thrones", R.drawable.img_slider_three);

        for(String name : images.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .description(name)
                    .image(images.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
    }
}