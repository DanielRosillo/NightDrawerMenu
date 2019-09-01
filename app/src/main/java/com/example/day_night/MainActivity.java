package com.example.day_night;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity
{
    private Switch theme;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences (this);
        boolean status = (shared.getBoolean ("dark_mode", false));
        if(status) AppCompatDelegate.setDefaultNightMode (AppCompatDelegate.MODE_NIGHT_YES);
        else  AppCompatDelegate.setDefaultNightMode (AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
        FloatingActionButton fab = findViewById (R.id.fab);
        fab.setOnClickListener (view -> Snackbar.make (view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction ("Action", null).show ());
        DrawerLayout drawer = findViewById (R.id.drawer_layout);
        NavigationView navigationView = findViewById (R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder (
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout (drawer)
                .build ();
        NavController navController = Navigation.findNavController (this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController (this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController (navigationView, navController);

        View headerView = navigationView.getHeaderView (0);
        theme = headerView.findViewById (R.id.theme_switch);

        loadSettings();
        events();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater ().inflate (R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController (this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp (navController, mAppBarConfiguration)
                || super.onSupportNavigateUp ();
    }

    private void loadSettings()
    {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences (this);
        boolean dark = (shared.getBoolean ("dark_mode", false));
        theme.setChecked (dark);
    }

    private void events()
    {
        theme.setOnCheckedChangeListener ((buttonView, isChecked) ->
        {
            SharedPreferences mDefaultPreferences = PreferenceManager.getDefaultSharedPreferences (this);
            if (isChecked)
            {
                mDefaultPreferences.edit ().putBoolean ("dark_mode", true).apply ();
                AppCompatDelegate.setDefaultNightMode (AppCompatDelegate.MODE_NIGHT_YES);
            }

            else
            {
                mDefaultPreferences.edit ().putBoolean ("dark_mode", false).apply ();
                AppCompatDelegate.setDefaultNightMode (AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }

}
