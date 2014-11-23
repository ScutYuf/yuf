package com.yuf.app.ui;

import com.yuf.app.mywidget.NoScrollListview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Tab2Fragment extends Fragment {
	private NoScrollListview cartListView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=(View)inflater.inflate(R.layout.main_tab_2_v, null);
		initMember(view);
		
		return view;
	}

	private void initMember(View view) {
		// TODO Auto-generated method stub
		
		cartListView=(NoScrollListview)view.findViewById(R.id.cart_listview);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		
	}
	
	
}
