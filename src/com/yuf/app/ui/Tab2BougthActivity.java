package com.yuf.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Tab2BougthActivity extends Activity {
	private ImageView okImageView;
	private ImageView backImageView;
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2_bought);
		
		listView=(ListView)findViewById(R.id.tab2_bougth_listview);
		SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.tab2_bougth_item,
				new String[]{"name","head_img","account"},
				new int[]{R.id.myfocus_name_text,R.id.myfocus_head_img,R.id.myfocus_account_text});//that is no right ,just for view
		 listView.setAdapter(adapter);
		
		backImageView=(ImageView)findViewById(R.id.tab2_bougth_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "G1");
		map.put("account", "google 1");
		map.put("head_img", R.drawable.ic_launcher);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("name", "G2");
		map.put("account", "google 2");
		map.put("head_img", R.drawable.ic_launcher);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("name", "G3");
		map.put("account", "google 3");
		map.put("head_img", R.drawable.ic_launcher);
		list.add(map);
		
		return list;
	}
}
