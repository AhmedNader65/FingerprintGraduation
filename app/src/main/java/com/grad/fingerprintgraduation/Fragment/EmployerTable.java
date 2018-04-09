package com.grad.fingerprintgraduation.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grad.fingerprintgraduation.R;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class EmployerTable extends Fragment {
    private static final String TAG ="Employee Table" ;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private TableLayout ll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_table, container, false);
        ll = (TableLayout) v.findViewById(R.id.displayLinear);
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sp.edit();
        // Read from the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("company").child("MonthAttendenceForUser")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap<String,String> times = (HashMap<String, String>) dataSnapshot.getValue();
                String date = dataSnapshot.getKey();
                String checkIn = times.get("in-time");
                String checkOut = times.get("out-time");
                if(checkOut==null){
                    checkOut = "";
                }
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
        return v;

    }

    public void init(String date, String checkIn, String checkOut){
        Context context = getContext();
        int res = R.layout.table_row;
            try {
                LayoutInflater inflater = LayoutInflater.from(context);
                TableRow row = (TableRow) inflater.inflate(res, null);
                TextView dateTV = (TextView) row.findViewById(R.id.date);
                dateTV.setText(date);
                TextView in = (TextView) row.findViewById(R.id.check_in);
                in.setText(checkIn);
                TextView out = (TextView) row.findViewById(R.id.check_out);
                out.setText(checkOut);
                ll.addView(row);
            }catch (Exception e){};
        }

}
