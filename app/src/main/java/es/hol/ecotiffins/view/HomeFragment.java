package es.hol.ecotiffins.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import es.hol.ecotiffins.adapter.ListViewAdapter;
import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.model.Order;
import es.hol.ecotiffins.model.TiffinPack;
import es.hol.ecotiffins.util.GeneralUtilities;
import es.hol.ecotiffins.util.SharedPreferencesUtilities;

public class HomeFragment extends Fragment {
    private View rootView;
    private GeneralUtilities generalUtilities;
    private SharedPreferencesUtilities sharedPreferencesUtilities;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            sharedPreferencesUtilities = new SharedPreferencesUtilities(getActivity());
            generalUtilities = new GeneralUtilities(getActivity());
            startSlider();
        }
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        populateListView();
    }

    private void populateListView() {
        final ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order(TiffinPack.SINGLE, "Only 1 tiffin", 1, sharedPreferencesUtilities.getSingle(), R.drawable.img_single_pack));
        orders.add(new Order(TiffinPack.COMBO, "You'll get 3 tiffins", 3, sharedPreferencesUtilities.getCombo(), R.drawable.img_combo_pack));
        orders.add(new Order(TiffinPack.MONTHLY, "To get Daily service", 60, sharedPreferencesUtilities.getMonthly(), R.drawable.img_monthly));
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(new ListViewAdapter(getActivity(), R.layout.layout_listitem, orders));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!orders.get(position).getPrice().equals("Price Not Available")) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ORDER", orders.get(position));
                    Fragment fragment = new OrderFragment();
                    fragment.setArguments(bundle);
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, fragment)
                            .addToBackStack(MainActivity.MAIN_FRAGMENT_STACK)
                            .commit();
                } else {
                    Snackbar.make(view, "Please connect to the internet and restart the app to update rate list.", Snackbar.LENGTH_SHORT).show();
                    populateListView();
                }

            }
        });
    }

    private void startSlider() {
        SliderLayout sliderLayout = (SliderLayout) rootView.findViewById(R.id.slider);
        HashMap<String,Integer> images = new HashMap<>();
        images.put("To accomplish your daily need", R.drawable.img_slider);
        images.put("Special dishes everyday", R.drawable.img_slider_one);
        images.put("Always different varieties", R.drawable.img_slider_two);
        images.put("Available with great taste", R.drawable.img_slider_three);
        images.put("No compromisation with health", R.drawable.img_slider_four);

        for(String name : images.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .description(name)
                    .image(images.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
    }
}