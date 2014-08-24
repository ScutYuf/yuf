package com.yuf.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Tab1SocietyForceFragment extends Fragment {
	private ListView listView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.tab1_myfocus,container,false);
		listView=(ListView)view.findViewById(R.id.tab1_myfocus_listview);
		SimpleAdapter adapter = new SimpleAdapter(getActivity(),getData(),R.layout.tab1_myfocus_list_item,
				new String[]{"name","head_img","account"},
				new int[]{R.id.myfocus_name_text,R.id.myfocus_head_img,R.id.myfocus_account_text});
		 listView.setAdapter(adapter);
		return  view;
	}
	
	
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "房祖名");
		map.put("account", "账号：123456");
		map.put("head_img", R.drawable.ic_launcher);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("name", "Merry");
		map.put("account", "账号：123456");
		map.put("head_img", R.drawable.ic_launcher);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("name", "用户3");
		map.put("account", "账号：123456");
		map.put("head_img", R.drawable.ic_launcher);
		list.add(map);
		
		return list;
	}
}
