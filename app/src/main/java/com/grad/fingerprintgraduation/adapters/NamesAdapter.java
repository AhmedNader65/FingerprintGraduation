package com.grad.fingerprintgraduation.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grad.fingerprintgraduation.EmployeeModel;
import com.grad.fingerprintgraduation.R;
import com.grad.fingerprintgraduation.activities.AdminViewEmployee;

import java.util.ArrayList;


public class NamesAdapter extends RecyclerView.Adapter<NamesAdapter.MyViewHolder> {
    ArrayList<EmployeeModel> employeeModels;
    Context context;
    public NamesAdapter(ArrayList<EmployeeModel> employeeModels) {
        this.employeeModels = employeeModels;
    }

    @Override
    public NamesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_names, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EmployeeModel employeeModel = employeeModels.get(position);
        holder.name.setText(employeeModel.getName());
        holder.bd.setText(employeeModel.getBd());
        holder.address.setText(employeeModel.getAddress());
    }


    @Override
    public int getItemCount() {
        return employeeModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name , bd,address;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            bd = (TextView) itemView.findViewById(R.id.bd);
            address = (TextView) itemView.findViewById(R.id.address);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context,AdminViewEmployee.class).putExtra("uid",employeeModels.get(getAdapterPosition()).getUid()));
        }
    }
}
