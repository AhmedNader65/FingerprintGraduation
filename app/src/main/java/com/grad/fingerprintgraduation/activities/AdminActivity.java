package com.grad.fingerprintgraduation.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.grad.fingerprintgraduation.R;
import com.grad.fingerprintgraduation.adapters.AdminPagerAdapter;

import me.relex.circleindicator.CircleIndicator;

public class AdminActivity extends AppCompatActivity  {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS =16 ;
    private AdminPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String TAG= "ADMIN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.grad.fingerprintgraduation.R.layout.activity_admin);

        mSectionsPagerAdapter = new AdminPagerAdapter(getSupportFragmentManager(),this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(AdminActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.logout) {
                    Log.e("hiiii","logout");
                    editor.putBoolean("logged", false);
                    editor.commit();
                    startActivity(new Intent(AdminActivity.this, LoginActivity.class));
                    AdminActivity.this.finish();
                }else{
                    Log.e("hiiii","maps");
                    startActivity(new Intent(AdminActivity.this, MapsActivity.class));
                }
                return true;
            }
        });
        popup.show();
    }

}
