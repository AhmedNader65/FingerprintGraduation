package com.grad.fingerprintgraduation.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grad.fingerprintgraduation.EmployeeModel;
import com.grad.fingerprintgraduation.adapters.NamesAdapter;
import com.grad.fingerprintgraduation.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ByNameFragment extends Fragment {
    private static final String TAG ="Name Table" ;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private TableLayout ll;
    private RecyclerView namesRV;
    private ArrayList<EmployeeModel> namesArray;
    private NamesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.admin_by_name_table, container, false);
        namesRV= (RecyclerView) v.findViewById(R.id.namesRV);
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sp.edit();
        // Read from the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("company").child("employees");
        namesArray = new ArrayList<>();
        adapter = new NamesAdapter(namesArray);
        namesRV.setLayoutManager(new LinearLayoutManager(getContext()));
        namesRV.setAdapter(adapter);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String Uid = dataSnapshot.getKey().toString();
                HashMap<String,String> data = (HashMap<String, String>) dataSnapshot.getValue();
                String bd = data.get("birthday");
                String address = data.get("address");
                String name = data.get("name");
                EmployeeModel emp = new EmployeeModel(Uid,name,address,bd);
                namesArray.add(emp);
                adapter.notifyDataSetChanged();
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
}
