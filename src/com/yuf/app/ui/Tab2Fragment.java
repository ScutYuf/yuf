package com.yuf.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.yuf.app.mywidget.NoScrollListview;

public class Tab2Fragment extends Fragment {

	private NoScrollListview cartListView;
//修改收货地址	
	private Button changeButton;
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
		changeButton = (Button)view.findViewById(R.id.changeButton);
		
		changeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), Tab2AddressEditActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		
	}
	
	
}
