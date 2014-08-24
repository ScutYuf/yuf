package com.yuf.app.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
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

import android.R.bool;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
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
	private int currentPage=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.tab3_mywork);
	mjsonArray=new JSONArray();
	Log.d("mywork", mjsonArray.toString());
	mImageLoader = new ImageLoader(MyApplication.requestQueue, new BitmapCache()); 
	listView=(PullToRefreshListView)findViewById(R.id.tab3_mywork_listview);
	
	listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
					DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
			// Update the LastUpdatedLabel
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

			// Do work to refresh the list here.
			refreshListView();

			
		}
	});
	listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

		@Override
		public void onLastItemVisible() {
		
				Toast.makeText(Tab3MyWorkActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
			getDate(++currentPage);
				
		}
	});
	madaAdapter=new MyListAdapter();
	listView.setAdapter(madaAdapter);
	listView.setMode(Mode.BOTH);

	
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
	
	refreshListView();
}
	
	

		
		
		void getDate(int _pageindex)
		{
		
		JsonObjectRequest request=new JsonObjectRequest(Method.GET,String.format("http://110.84.129.130:8080/Yuf/post/getPost/%s/%d", UserInfo.getInstance().getUserid(),_pageindex), null, new com.android.volley.Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, response.toString());
				JSONArray jsonArray;
				try {
					jsonArray = response.getJSONArray("postData");
					mjsonArray=MyApplication.joinJSONArray(mjsonArray, jsonArray);
					madaAdapter.notifyDataSetChanged();
					Log.d("mywork", mjsonArray.toString());
					Log.d("mywork",String.valueOf( mjsonArray.length()));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				try {
					if (response.getInt("currentPage")>=response.getInt("postMaxPage")) {
						listView.setMode(Mode.PULL_FROM_START);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// Call onRefreshComplete when the list has been refreshed.
				
				// TODO Auto-generated method stub
				listView.onRefreshComplete();
				
				
			}
		}, new com.android.volley.Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
			
	
		} );
		MyApplication.requestQueue.add(request);
		MyApplication.requestQueue.start();
		}
	









		class MyListAdapter extends BaseAdapter
		{

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mjsonArray.length();
			
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

			@SuppressLint("SimpleDateFormat") @Override
			public View getView(int position, View convertView, ViewGroup parent) {
				Log.d("mywork", "get");
				if (convertView==null) {
					convertView=getLayoutInflater().inflate(R.layout.tab3_mywork_mycollection_item,null);
					
				}
				NetworkImageView imageView=(NetworkImageView)convertView.findViewById(R.id.tab3_mywork_item_img);
				try {
					imageView.setImageUrl("http://110.84.129.130:8080/Yuf"+mjsonArray.getJSONObject(position).getString("postpicurl"),mImageLoader);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				TextView posttitle=(TextView)convertView.findViewById(R.id.tab3_mywork_item_name);
				try {
					posttitle.setText(mjsonArray.getJSONObject(position).getString("posttitle"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				TextView posttime=(TextView)convertView.findViewById(R.id.tab3_mywork_item_time);
				try {
					long currentTime=mjsonArray.getJSONObject(position).getLong("posttime");
				
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date(currentTime);
					posttime.setText(formatter.format(date));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				return convertView;
			}
		
		
		
			
			
		}
		private void refreshListView()
		{
			currentPage=0;
			mjsonArray=new JSONArray();
			getDate(++currentPage);
			listView.setMode(Mode.BOTH);
		}
}