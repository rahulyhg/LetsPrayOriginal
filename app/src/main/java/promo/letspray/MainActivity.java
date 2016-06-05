package promo.letspray;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import promo.letspray.Utility.ApplicationUtils;
import promo.letspray.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout dlMain;
    ActionBarDrawerToggle mDrawerToggle;
    Toolbar toolbar;
    NavigationView nvDrawer;
    RelativeLayout rlNavHeaderContent;
    TextView tvHeaderProfileName;

    int day_state = 0;

    FragmentManager fragmentManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        day_state = ApplicationUtils.getDayState();
        super.onCreate(savedInstanceState);
        setStatusbarBackground();
        setContentView(R.layout.activity_main);
        context = this;

        initUI();
        initActionBar();
        setActionbarBackground();
        initDrawer();
        initFragmentManager();
        setupDrawerContent();
        setDrawerHeaderContent();
        //setTheme(R.style.MorningTheme);


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

    private void setStatusbarBackground(){
        switch (day_state) {
            case ApplicationUtils.MORNING:
                setTheme(R.style.MorningTheme);
                break;
            case ApplicationUtils.NOON:
                setTheme(R.style.AfterNoonTheme);
                break;
            case ApplicationUtils.EVENING:
                setTheme(R.style.EveningTheme);
                break;
            case ApplicationUtils.NIGHT:
                setTheme(R.style.NightTheme);
                break;
        }
    }

    private void setActionbarBackground(){
        switch (day_state) {
            case ApplicationUtils.MORNING:
                toolbar.setBackgroundColor(getResources().getColor(R.color.morningActionbar));
                break;
            case ApplicationUtils.NOON:
                toolbar.setBackgroundColor(getResources().getColor(R.color.afternoonActionbar));
                break;
            case ApplicationUtils.EVENING:
                toolbar.setBackgroundColor(getResources().getColor(R.color.eveningActionbar));
                break;
            case ApplicationUtils.NIGHT:
                toolbar.setBackgroundColor(getResources().getColor(R.color.nightActionbar));
                break;
        }
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


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_rateMyapp) {
            Toast.makeText(this,"RATE MY APP PLEASE",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_calendar) {
            showPopup(MainActivity.this);
            Toast.makeText(this,"THIS IS CALENDAR",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPopup(Activity context) {

        // Inflate the popup_layout.xml
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.calendar_main, null,false);
        // Creating the PopupWindow
        final PopupWindow popupWindow = new PopupWindow(
                layout,800,800);

        popupWindow.setContentView(layout);
        popupWindow.setHeight(1000);
        popupWindow.setOutsideTouchable(false);
        // Clear the default translucent background
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);

        CalendarView cv = (CalendarView) layout.findViewById(R.id.calendarView1);
        cv.setBackgroundColor(Color.BLUE);

        popupWindow.showAtLocation(layout, Gravity.TOP,5,170);
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
//            case R.id.nav_prayer_tracker:
////                isCompanion = true;
////                fragment = CompanionsFragment.newInstance(user_id, menuItem.getTitle().toString());
////                fabMain.setVisibility(View.GONE);
////                UserFragment.isCompanion = true;
//                break;
            case R.id.nav_islamicEvents:
//                fragmentClass = ThirdFragment.class;
                break;

            case R.id.nav_shareApp:
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
