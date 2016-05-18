package promo.letspray;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import promo.letspray.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity{

    DrawerLayout dlMain;
    ActionBarDrawerToggle mDrawerToggle;
    Toolbar toolbar;
    NavigationView nvDrawer;
    RelativeLayout rlNavHeaderContent;
    TextView tvHeaderProfileName;

    FragmentManager fragmentManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        initUI();
        initActionBar();
        initDrawer();
        initFragmentManager();
        setupDrawerContent();
        setDrawerHeaderContent();
        setTheme(R.style.MorningTheme);

    }

    private void initUI() {
        dlMain = (DrawerLayout) findViewById(R.id.dlMain);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nvDrawer = (NavigationView) findViewById(R.id.nvDrawer);

    }


    private void initActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void initDrawer() {
        View headerView = nvDrawer.getHeaderView(0);
        tvHeaderProfileName = (TextView) headerView.findViewById(R.id.tvNavHeaderProfileName);
        rlNavHeaderContent = (RelativeLayout) headerView.findViewById(R.id.rlNavHeaderContent);
        mDrawerToggle = setupDrawerToggle();
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        //dlMain.setDrawerListener(mDrawerToggle);
        dlMain.addDrawerListener(mDrawerToggle);
        // Drawer icon changed
        mDrawerToggle.syncState();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, dlMain, toolbar, R.string.txt_nav_open, R.string.txt_nav_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
    }

    private void setDrawerHeaderContent() {
    }

    private void initFragmentManager() {
        fragmentManager = getSupportFragmentManager();
        openHomeFragment();
    }

    private void openHomeFragment() {
        fragmentManager.beginTransaction().replace(R.id.flContent, HomeFragment.newInstance()).commit();
    }

    private void setupDrawerContent() {
        nvDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        boolean isLogOut = true;
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                isLogOut = false;
                fragment = HomeFragment.newInstance();
//                fabMain.setVisibility(View.VISIBLE);
//                isCompanion = false;
                break;
            case R.id.nav_prayer_tracker:
//                isCompanion = true;
//                fragment = CompanionsFragment.newInstance(user_id, menuItem.getTitle().toString());
//                fabMain.setVisibility(View.GONE);
//                UserFragment.isCompanion = true;
                break;
            case R.id.nav_my_profile:
//                fragmentClass = ThirdFragment.class;
                break;
            case R.id.nav_settings:
//                fragmentClass = ThirdFragment.class;
                break;
            case R.id.nav_logout:
//                isCompanion = false;
//                logout();
//                isLogOut = true;
                break;
            default:
                fragment = HomeFragment.newInstance();
                break;
        }

        if (!isLogOut) {
        // Insert the fragment by replacing any existing fragment
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }
        // Highlight the selected item, update the title, and close the drawer
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        dlMain.closeDrawers();
    }

}
