package com.grad.fingerprintgraduation.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grad.fingerprintgraduation.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;

import static android.R.attr.startYear;
import static com.grad.fingerprintgraduation.R.id.date;

public class ByDateFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    private Calendar calendar;
    private int year, month, day;
    private Button button1;
    private TableLayout ll;
    TextView noAttTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.admin_by_date_table, container, false);

        button1 = (Button) v.findViewById(R.id.button1);
        noAttTV  = (TextView) v.findViewById(R.id.no_attendance);
        ll = (TableLayout) v.findViewById(R.id.displayLinear);
        ll.setVisibility(View.GONE);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        getDate(year, String.format("%02d",month+1), String.format("%02d",day));
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),this,year,month,day);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sp.edit();
        return v;
    }

    private void getDate(int year, String month, String day) {
        month = arabicToDecimal(month);
        day = arabicToDecimal(day);
        Log.e("date"," year"+ year + "month "+month + "day "+day);
        for(int i = 1; i < ll.getChildCount();i++){
            ll.removeViewAt(i);
        }
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("company").child("MonthAttendence")
                .child(String.valueOf(year)).child(String.valueOf(month)).child(String.valueOf(day));
            noAttTV.setVisibility(View.VISIBLE);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ll.setVisibility(View.VISIBLE);
                noAttTV.setVisibility(View.GONE);

                HashMap<String,String> times = (HashMap<String, String>) dataSnapshot.getValue();
                String userUid = dataSnapshot.getKey();
                Log.e("hiii","got first data "+ userUid);
                final String checkIn = times.get("in-time");
                final String checkOut = times.get("out-time");
                DatabaseReference myRef = database.getReference("company").child("employees").child(userUid);
                myRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        if(dataSnapshot.getKey().toString().equals("name"))
                            init(dataSnapshot.getValue().toString(),checkIn,checkOut);
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
        TableRow row = (TableRow)LayoutInflater.from(getContext()).inflate(R.layout.table_row, null);
        TextView dateTV = (TextView) row.findViewById(R.id.date);
        dateTV.setText(date);
        TextView in = (TextView) row.findViewById(R.id.check_in);
        in.setText(checkIn);
        TextView out = (TextView) row.findViewById(R.id.check_out);
        out.setText(checkOut);
        ll.addView(row);
        Log.e("data",date+" "+checkIn + " " + checkOut);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        ll.setVisibility(View.GONE);
        getDate(year, String.format("%02d",month+1), String.format("%02d",dayOfMonth));
    }
    private static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for(int i=0;i<number.length();i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }
}

