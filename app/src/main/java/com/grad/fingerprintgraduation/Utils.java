package com.grad.fingerprintgraduation;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class Utils {
    private static final String TAG = "LOCATION";

    public static boolean isInLocation(final double lat, double lng){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("company");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                HashMap<String,Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
                double comLat = (double) value.get("lat");
                double comLng = (double) value.get("lng");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return true;
    }
    public static String getCurrentDayDate(){
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }
    public static String getCurrentDayDate2(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }
    public static String getCurrentYear(){
        DateFormat df = new SimpleDateFormat("yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public static String getCurrentMonth(){
        DateFormat df = new SimpleDateFormat("MM");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public static String getCurrentDay(){
        DateFormat df = new SimpleDateFormat("dd");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public static String getCurrentClockDate(){
        DateFormat df = new SimpleDateFormat("h:mm a");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }
}
