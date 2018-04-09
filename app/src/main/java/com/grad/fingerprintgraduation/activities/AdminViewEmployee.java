package com.grad.fingerprintgraduation.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grad.fingerprintgraduation.R;

import java.util.HashMap;

public class AdminViewEmployee extends AppCompatActivity {

    private static final String TAG ="Employee Table" ;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private TableLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_admin_view_employee);
        String Uid = getIntent().getStringExtra("uid");
        ll = (TableLayout) findViewById(R.id.displayLinear);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();
        // Read from the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.e("hiii","hiiiii "+Uid);
        DatabaseReference myRef = database.getReference("company").child("MonthAttendenceForUser")
                .child(String.valueOf(Uid));
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("details",dataSnapshot.getValue().toString());
                HashMap<String,String> times = (HashMap<String, String>) dataSnapshot.getValue();
                String date = dataSnapshot.getKey();
                String checkIn = times.get("in-time");
                String checkOut = times.get("out-time");
                init(date,checkIn,checkOut);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void init(String date, String checkIn, String checkOut){
        TableRow row = (TableRow)LayoutInflater.from(this).inflate(R.layout.table_row, null);
        TextView dateTV = (TextView) row.findViewById(R.id.date);
        dateTV.setText(date);
        TextView in = (TextView) row.findViewById(R.id.check_in);
        in.setText(checkIn);
        TextView out = (TextView) row.findViewById(R.id.check_out);
        out.setText(checkOut);
        ll.addView(row);
        Log.e("data",date+" "+checkIn + " " + checkOut);
    }

}
