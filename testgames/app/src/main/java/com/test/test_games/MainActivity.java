package com.test.test_games;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.games.Game1Fragment;
import com.test.games.Game2Fragment;

import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.KITKAT)
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends FragmentActivity implements SNavigationDrawer.DrawerListener, SNavigationDrawer.OnMenuItemClickListener {

    private SNavigationDrawer sNavigationDrawer;
    private Class fragmentClass;
    private static Fragment fragment;
    private FragmentManager fragmentManager;

    private RelativeLayout appBarRL;
    private TextView appBarTitleTV;
    private ImageView menuIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        sNavigationDrawer = findViewById(R.id.navigationDrawer);

        appBarRL = findViewById(R.id.appBarRL);
        appBarTitleTV = findViewById(R.id.appBarTitleTV);
        menuIV = findViewById(R.id.menuIV);

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(getString(R.string.main), R.drawable.main_pic));
        menuItems.add(new MenuItem(getString(R.string.game1), R.drawable.game1_pic));
        menuItems.add(new MenuItem(getString(R.string.game2), R.drawable.game2_pic));

        sNavigationDrawer.setMenuItemList(menuItems);
        sNavigationDrawer.setOnMenuItemClickListener(this);
        sNavigationDrawer.setDrawerListener(this);

        setFragment(MainFragment.class);
        setToolBar();
    }

    @Override
    public void onMenuItemClicked(int position) {
        switch (position) {
            case 0: {
                setToolBar();
                unlockOrientationLandscape(this);
                fragmentClass = MainFragment.class;
                break;
            }
            case 1: {
                removeToolBar();
                lockOrientationLandscape(this);
                fragmentClass = Game1Fragment.class;
                break;
            }
            case 2: {
                removeToolBar();
                lockOrientationLandscape(this);
                fragmentClass = Game2Fragment.class;
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDrawerOpening() {

    }

    @Override
    public void onDrawerClosing() {
        if (fragment.getClass() != fragmentClass)
            setFragment(fragmentClass);
    }

    @Override
    public void onDrawerOpened() {

    }

    @Override
    public void onDrawerClosed() {

    }

    @Override
    public void onDrawerStateChanged(int i) {

    }

    private void setFragment(Class fragmentClass) {
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }
    }

    private static void lockOrientationLandscape(Activity a) {
        a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
    }

    private static void unlockOrientationLandscape(Activity a) {
        a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @SuppressLint("ResourceAsColor")
    private void setToolBar() {
        appBarTitleTV.setVisibility(View.VISIBLE);
        appBarRL.setBackgroundResource(R.drawable.shadow);
        menuIV.setBackgroundResource(0);
        menuIV.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
            showNavigationBar();
    }

    @SuppressLint("ResourceAsColor")
    private void removeToolBar() {
        appBarTitleTV.setVisibility(View.GONE);
        appBarRL.setBackgroundResource(0);
        menuIV.setBackgroundResource(R.drawable.rounded_background);
        menuIV.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        hideNavigationBar();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (appBarRL.getBackground() != null) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                hideNavigationBar();
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                showNavigationBar();
            }
        }
    }

    private void hideNavigationBar() {
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        getWindow().getDecorView().setSystemUiVisibility(flags);

        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(flags);
                }
            }
        });
    }

    private void showNavigationBar() {
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        getWindow().getDecorView().setSystemUiVisibility(flags);

        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(flags);
                }
            }
        });
    }
}