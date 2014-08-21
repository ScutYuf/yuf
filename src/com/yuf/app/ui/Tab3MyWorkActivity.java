package com.yuf.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.http.extend.BitmapCache;
import com.yuf.app.mywidget.XListView;
import com.yuf.app.mywidget.XListView.IXListViewListener;

import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Tab3MyWorkActivity extends Activity {
	private ImageView backImageView;
	private PullToRefreshListView listView; 

	private ImageView addImageView;
	private ImageLoader mImageLoader;
	private JSONArray mjsonArray;
	private String TAG="Tab3MyWorkActivity";
	private MyListAdapter madaAdapter;
	private Boolean isEnd=false;
	private Boolean isLoading=false;
	private int pageindex=0;
	private int max_page_index;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.tab3_mywork);
	mjsonArray=new JSONArray();
	mImageLoader = new ImageLoader(MyApplication.requestQueue, new BitmapCache()); 
	listView=(PullToRefreshListView)findViewById(R.id.tab3_mywork_listview);
	listView.setAdapter(new MyListAdapter());
	listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
					DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

			// Update the LastUpdatedLabel
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

			// Do work to refresh the list here.
			getDate(++pageindex);
			if (pageindex>=max_page_index) {
				listView.setMode(listView.getMode() == Mode.BOTH ? Mode.PULL_FROM_START
						: Mode.BOTH);
			}
		}
	});
	listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

		@Override
		public void onLastItemVisible() {
		
				Toast.makeText(Tab3MyWorkActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
			
		}
	});
	madaAdapter=new MyListAdapter();
	listView.setAdapter(madaAdapter);
	listView.setMode(listView.getMode() == Mode.BOTH ? Mode.PULL_FROM_START
			: Mode.BOTH);

	
	backImageView=(ImageView)findViewById(R.id.tab3_mywork_back_imageview);
	backImageView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			onBackPressed();
			// TODO Auto-generated method stub
			
		}
	});
	
	
	addImageView=(ImageView)findViewById(R.id.tab3_mywork_add_imageview);
	addImageView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(Tab3MyWorkActivity.this,Tab3AddWorkActivity.class);
			startActivity(intent);
			// TODO Auto-generated method stub
			
		}
	});
}
	
	

		
		
		void getDate(int pageindex)
		{
		
		JsonObjectRequest request=new JsonObjectRequest(Method.GET,String.format("http://110.84.129.130:8080/Yuf/post/getPost/%s/%d", MyApplication.userid,1), null, new com.android.volley.Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, response.toString());
			
					JSONArray jsonArray=response.names();
				mjsonArray=MyApplication.joinJSONArray(mjsonArray, jsonArray);
				madaAdapter.notifyDataSetChanged();
				// Call onRefreshComplete when the list has been refreshed.
				
				// TODO Auto-generated method stub
				
			}
		}, new com.android.volley.Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
			
	
		} );
		}
	









		class MyListAdapter extends BaseAdapter
		{

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
//				return mjsonArray.length();
				return 3;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				try {
					return mjsonArray.get(position);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView==null) {
					convertView=getLayoutInflater().inflate(R.layout.tab3_mywork_mycollection_item,null);
					
				}
				NetworkImageView imageView=(NetworkImageView)convertView.findViewById(R.id.tab3_mywork_item_img);
//				imageView.setImageURI(mjsonArray.getString(""+"postpicurl"))
				TextView posttitle=(TextView)convertView.findViewById(R.id.tab3_mywork_item_name);
				TextView posttime=(TextView)convertView.findViewById(R.id.tab3_mywork_item_time);
				
				// TODO Auto-generated method stub
				return convertView;
			}
		
		
		
			
			
		}
}