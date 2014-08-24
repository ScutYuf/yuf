package com.yuf.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class Tab1SocietyShareFragment extends Fragment {
	private PullToRefreshListView listView;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.tab1_share,container,false);
		listView=(PullToRefreshListView)view.findViewById(R.id.tab1_share_listview);
		//
		//
		//listview adddate
		SimpleAdapter adapter = new SimpleAdapter(getActivity(),getData(),R.layout.tab1_share_list_item,
				new String[]{"name","head_img","share_text"},
				new int[]{R.id.name_text,R.id.head_img,R.id.share_text});
		 listView.setAdapter(adapter);
		
		
		 
		 //
		 return  view;
	}
	
	
	
	
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "G1");
		map.put("share_text", "google 1");
		map.put("head_img", R.drawable.ic_launcher);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("name", "G2");
		map.put("share_text", "google 2");
		map.put("head_img", R.drawable.ic_launcher);
		list.add(map);

		
		
		return list;
	}
}
