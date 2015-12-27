package es.hol.ecotiffins.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import es.hol.ecotiffins.controller.WebServiceHandler;
import es.hol.ecotiffins.controller.WebServiceListener;
import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.model.WebService;
import es.hol.ecotiffins.util.GeneralUtilities;
import es.hol.ecotiffins.util.SharedPreferencesUtilities;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, WebServiceListener, FragmentManager.OnBackStackChangedListener {
    private NavigationView navigationView;
    private static final String HOME_FRAGMENT_TAG = "HOME";
    public static final String MAIN_FRAGMENT_STACK = "MAIN";

    private SharedPreferencesUtilities sharedPreferencesUtilities;
    private GeneralUtilities generalUtilities;
    private WebServiceHandler webServiceHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        webServiceHandler = new WebServiceHandler(this);
        sharedPreferencesUtilities = new SharedPreferencesUtilities(this);
        generalUtilities = new GeneralUtilities(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView txtUserName = (TextView) header.findViewById(R.id.txtUserName);
        TextView txtEmail = (TextView) header.findViewById(R.id.txtEmail);
        txtUserName.setText(sharedPreferencesUtilities.getUser());
        txtEmail.setText(sharedPreferencesUtilities.getEmail());
        ImageView imageView = (ImageView) header.findViewById(R.id.imageView);
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        webServiceHandler.webServiceListener = this;
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        loadHomeFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        webServiceHandler.requestToServer(
                (getResources().getString(R.string.api_end_point)) + "prices.php",
                WebService.PRICES,
                new HashMap<String, String>(),
                false
        );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_offers) {
            webServiceHandler.requestToServer(
                    (getResources().getString(R.string.api_end_point)) + "offers.php",
                    WebService.PROMO_CODE,
                    new HashMap<String, String>(),
                    true
            );
            return false;
        }

        //return super.onOptionsItemSelected(item);
        return false;
    }

    public void showPromoCode() {
        View dialogLayout = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_offer, null);
        final PopupWindow popupWindow = new PopupWindow(dialogLayout, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        if (!sharedPreferencesUtilities.getDiscount().equals("0")) {
            ((TextView)dialogLayout.findViewById(R.id.txtDialogMessage)).setText(
                    "We take lots of care of our customers, use below promo code to grab " +
                            sharedPreferencesUtilities.getDiscount() + "%" +
                            " discount."
            );
            ((TextView)dialogLayout.findViewById(R.id.txtPromoCode)).setText(sharedPreferencesUtilities.getPromoCode());
        } else {
            ((TextView)dialogLayout.findViewById(R.id.txtPromoCode)).setVisibility(View.GONE);
        }

        dialogLayout.findViewById(R.id.btnDialogOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //Setting up the position of PopUp Window
        popupWindow.showAtLocation(dialogLayout, Gravity.CENTER, 0, 0);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_home) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            uncheckRemainingMenus();
            navigationView.getMenu().getItem(0).setChecked(true);
        } else if (id == R.id.nav_history) {
            fragment = new HistoryFragment();
            uncheckRemainingMenus();
            navigationView.getMenu().getItem(1).setChecked(true);
        } else if (id == R.id.nav_about) {
            fragment = new AboutFragment();
            uncheckRemainingMenus();
            navigationView.getMenu().getItem(2).setChecked(true);
        } else if (id == R.id.nav_contact) {
            fragment = new ContactFragment();
            uncheckRemainingMenus();
            navigationView.getMenu().getItem(3).setChecked(true);
        } else if (id == R.id.nav_service) {
            fragment = new ServiceAreasFragment();
            uncheckRemainingMenus();
            navigationView.getMenu().getItem(4).setChecked(true);
        } else if (id == R.id.nav_share) {
            Intent i=new Intent(android.content.Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            i.putExtra(android.content.Intent.EXTRA_TEXT, "Hay there! I am using this app to order tiffin. Check out https://play.google.com/store/apps/details?id=es.hol.ecotiffins&hl=en");
            startActivity(Intent.createChooser(i, "Share via"));
        } else if (id == R.id.nav_logout) {
            new SharedPreferencesUtilities(this).flushPreferences();
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(MAIN_FRAGMENT_STACK)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadHomeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new HomeFragment(), HOME_FRAGMENT_TAG)
                .commit();
    }

    private void uncheckRemainingMenus() {
        navigationView.getMenu().getItem(0).setChecked(false);
        navigationView.getMenu().getItem(1).setChecked(false);
        navigationView.getMenu().getItem(2).setChecked(false);
        navigationView.getMenu().getItem(3).setChecked(false);
    }

    @Override
    public void onRequestCompleted(String response, int api) {
        try {
            Log.e("MainActivity", response);
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("error").equals("false")) {
                if (api == WebService.PRICES) {
                    JSONArray jsonArray = jsonObject.getJSONArray("prices");
                    sharedPreferencesUtilities.setSingle(jsonArray.getJSONObject(0).getString("price"));
                    sharedPreferencesUtilities.setCombo(jsonArray.getJSONObject(1).getString("price"));
                    sharedPreferencesUtilities.setMonthly(jsonArray.getJSONObject(2).getString("price"));
                } else if (api == WebService.PROMO_CODE) {
                    JSONArray jsonArray = jsonObject.getJSONArray("offers");
                    sharedPreferencesUtilities.setPromoCode(jsonArray.getJSONObject(0).getString("promo_code"));
                    sharedPreferencesUtilities.setDiscount(jsonArray.getJSONObject(0).getString("discount"));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showPromoCode();
                        }
                    });
                }
            } else {
                generalUtilities.showAlertDialog("Request Cancelled", "Server error, Please contact to the vendor.", "OK");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestFailure(IOException e, int api) {
        generalUtilities.showAlertDialog("Error", getResources().getString(R.string.request_failure), "OK");
    }

    @Override
    public void onBackStackChanged() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof HomeFragment) {
            uncheckRemainingMenus();
            navigationView.getMenu().getItem(0).setChecked(true);
        } else if (fragment instanceof HistoryFragment) {
            uncheckRemainingMenus();
            navigationView.getMenu().getItem(1).setChecked(true);
        } else if (fragment instanceof AboutFragment) {
            uncheckRemainingMenus();
            navigationView.getMenu().getItem(2).setChecked(true);
        } else if (fragment instanceof ContactFragment) {
            uncheckRemainingMenus();
            navigationView.getMenu().getItem(3).setChecked(true);
        } else if (fragment instanceof ServiceAreasFragment) {
            uncheckRemainingMenus();
            navigationView.getMenu().getItem(4).setChecked(true);
        } else if (fragment instanceof OrderFragment) {
            uncheckRemainingMenus();
            navigationView.getMenu().getItem(0).setChecked(true);
        } else {
            uncheckRemainingMenus();
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }
}