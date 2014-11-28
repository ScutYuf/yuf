package com.yuf.app.adapter;

import java.util.ArrayList;

import com.yuf.app.ui.Tab2Fragment;

import android.R.array;
import android.R.integer;
import android.R.string;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

	private ArrayList<Fragment>fragments;
	private ArrayList<String>titles;
	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		fragments=new ArrayList<Fragment>();
		titles=new ArrayList<String>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}
	public Fragment getFragment(int i){
		return fragments.get(i);
	}
	public boolean addFragment(Fragment fragment)
	{
		return fragments.add(fragment);
	}
	public boolean addTitle(String title)
	{
		return titles.add(title);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return titles.get(position);
	}

	
	

}
