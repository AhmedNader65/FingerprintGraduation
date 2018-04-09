package com.grad.fingerprintgraduation.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.grad.fingerprintgraduation.Fragment.EmployerCheck_in_out;
import com.grad.fingerprintgraduation.Fragment.EmployerTable;


public class MyPagerAdapter extends FragmentPagerAdapter {
	SharedPreferences sp;
	public MyPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		sp = PreferenceManager.getDefaultSharedPreferences(context);
	}
	@Override
	public CharSequence getPageTitle(int position) {
		switch (position){
			case 0:
				return "";
			case 1:
				return "";
			default:
				return null;
		}
	}
	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0:
				EmployerCheck_in_out p = new EmployerCheck_in_out();
				return p;

			case 1:
				EmployerTable p2 = new EmployerTable();
				return p2;
		}
		return null;
		}
	@Override
	public int getCount() {
		return 2;
	}

}
