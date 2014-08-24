package com.yuf.app.ui;

import com.yuf.app.MyApplication;
import com.yuf.app.mywidget.MyGridView;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yuf.app.adapter.FoodMaterialGVAdapter;




@SuppressLint("ValidFragment")
public class Tab0FoodMaterialFragment extends Fragment{

	private MyGridView gridView1;
	private MyGridView gridView2;
	private MyGridView gridView3;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		 super.onCreateView(inflater, container, savedInstanceState);
		View view=inflater.inflate(R.layout.tab0_food_material,container,false);
		gridView1=(MyGridView)view.findViewById(R.id.food_material_gridview1);
		gridView2=(MyGridView)view.findViewById(R.id.food_material_gridview2);
		gridView3=(MyGridView)view.findViewById(R.id.food_material_gridview3);
		
		gridView1.setAdapter(new FoodMaterialGVAdapter(getActivity()));
		gridView2.setAdapter(new FoodMaterialGVAdapter(getActivity()));
		gridView3.setAdapter(new FoodMaterialGVAdapter(getActivity()));
		
		
		
		return  view;
	}
}
