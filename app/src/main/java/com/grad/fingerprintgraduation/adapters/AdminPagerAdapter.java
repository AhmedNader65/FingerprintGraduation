package com.grad.fingerprintgraduation.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.grad.fingerprintgraduation.Fragment.ByDateFragment;
import com.grad.fingerprintgraduation.Fragment.ByNameFragment;


public class AdminPagerAdapter extends FragmentPagerAdapter {
	SharedPreferences sp;
	public AdminPagerAdapter(FragmentManager fm, Context context) {
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
				ByDateFragment p = new ByDateFragment();
				return p;

			case 1:
				ByNameFragment p2 = new ByNameFragment();
				return p2;
		}
		return null;
		}

	@Override
	public int getCount() {
		return 2;
	}

}
