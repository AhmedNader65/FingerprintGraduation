package com.grad.fingerprintgraduation.Fragment;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grad.fingerprintgraduation.R;
import com.grad.fingerprintgraduation.Utils;

import java.util.Calendar;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class EmployerCheck_in_out extends Fragment {
    Button inBtn, outBtn;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    boolean checkedToday = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_checkinandout, container, false);
        inBtn = (Button) v.findViewById(R.id.check_in);
        outBtn = (Button) v.findViewById(R.id.check_out);
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sp.edit();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day != sp.getInt("day", 0)) {
            editor.putBoolean("in", false);
            editor.commit();
        } else {
            checkedToday = true;
        }
        if (sp.getBoolean("in", false)) {
            inBtn.setVisibility(View.GONE);
            outBtn.setVisibility(View.VISIBLE);
        } else {
            inBtn.setVisibility(View.VISIBLE);
            outBtn.setVisibility(View.GONE);
        }

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        inBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SmartLocation.with(getContext()).location().state().isGpsAvailable()) {
                    SmartLocation.with(getContext()).location()
                            .oneFix()
                            .start(new OnLocationUpdatedListener() {
                                @Override
                                public void onLocationUpdated(Location location) {


                                    Utils.isInLocation(location.getLatitude(), location.getLongitude(), new Utils.OnDataFetchComplete() {
                                        @Override
                                        public void OnDataFetchCompleted(boolean inLoc) {
                                            if (inLoc) {
                                                Calendar calendar = Calendar.getInstance();
                                                int day = calendar.get(Calendar.DAY_OF_WEEK);
                                                if (day != sp.getInt("day", 0)) {
                                                    editor.putBoolean("in", false);
                                                    editor.commit();
                                                } else {
                                                    checkedToday = true;
                                                }
                                                // Write a message to the database
                                                if (checkedToday) {
                                                    Toast.makeText(getContext(), R.string.already_checked, Toast.LENGTH_SHORT).show();
                                                    return;
                                                }


                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference myRef = database.getReference("company").child("MonthAttendence")
                                                        .child(Utils.getCurrentDayDate()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("in-time");

                                                DatabaseReference myRef2 = database.getReference("company").child("MonthAttendenceForUser").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .child(Utils.getCurrentDayDate2()).child("in-time");
//
//                DatabaseReference myRef = database.getReference("company").child("MonthAttendence")
//                        .child(Utils.getCurrentYear()).child(Utils.getCurrentMonth())
//                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Utils.getCurrentDay()).child("in-time");

                                                myRef.setValue(Utils.getCurrentClockDate());
                                                myRef2.setValue(Utils.getCurrentClockDate());
                                                editor.putBoolean("in", true);
                                                editor.putInt("day", day);
                                                editor.commit();
                                                inBtn.setVisibility(View.GONE);
                                                outBtn.setVisibility(View.VISIBLE);
                                            } else {
                                                // Out of location
                                                Toast.makeText(getContext(), R.string.out_location, Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                                }
                            });

                } else {
                    Toast.makeText(getContext(), R.string.turn_gps_on, Toast.LENGTH_SHORT).show();
                }
            }
        });

        outBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SmartLocation.with(getContext()).location().state().isGpsAvailable()) {
                    SmartLocation.with(getContext()).location()
                            .oneFix()
                            .start(new OnLocationUpdatedListener() {
                                @Override
                                public void onLocationUpdated(Location location) {
                                    Utils.isInLocation(location.getLatitude(), location.getLongitude(), new Utils.OnDataFetchComplete() {
                                        @Override
                                        public void OnDataFetchCompleted(boolean inLoc) {
                                            if (inLoc) {

                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference myRef = database.getReference("company").child("MonthAttendence")
                                                        .child(Utils.getCurrentDayDate()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("out-time");

                                                DatabaseReference myRef2 = database.getReference("company").child("MonthAttendenceForUser").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .child(Utils.getCurrentDayDate2()).child("out-time");
                                                myRef.setValue(Utils.getCurrentClockDate());
                                                myRef2.setValue(Utils.getCurrentClockDate());
                                                outBtn.setVisibility(View.GONE);
                                                inBtn.setVisibility(View.VISIBLE);
                                                editor.putBoolean("in", false);
                                                editor.commit();
                                            } else {
                                                // Out of location
                                                Toast.makeText(getContext(), R.string.out_location, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });

                } else {
                    Toast.makeText(getContext(), R.string.turn_gps_on, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
