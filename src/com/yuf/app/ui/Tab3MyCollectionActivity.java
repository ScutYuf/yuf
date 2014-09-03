package com.yuf.app.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.http.extend.BitmapCache;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Tab3MyCollectionActivity extends Activity {
	//
	private ImageView backImageView;
	private PullToRefreshListView listView;
	private ImageLoader mImageLoader;
	private JSONArray jsonArray;
	private int currentPage;
	private MylistAdapter mAdaAdapter;
	private boolean isEnd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	jsonArray=new JSONArray();
	isEnd=false;
	currentPage=0;
	mImageLoader= new ImageLoader(MyApplication.requestQueue, new BitmapCache());
	mAdaAdapter=new  MylistAdapter();
	
		setContentView(R.layout.tab3_mycollection);
	listView=(PullToRefreshListView)findViewById(R.id.tab3_mycollection_listView);
	listView.setMode(Mode.BOTH);
	listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
	@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
					DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
			// Update the LastUpdatedLabel
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			// Do work to refresh the list here.
			jsonArray=new JSONArray();
			currentPage=0;
			isEnd=false;
			listView.setMode(Mode.BOTH);
			getNextPage();
		}
	});
	listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

		@Override
		public void onLastItemVisible() {
			
			// TODO Auto-generated method stub
			if (!isEnd) {
				getNextPage();
			}
		}
	});
	listView.setAdapter(mAdaAdapter);
	
	backImageView=(ImageView)findViewById(R.id.tab3_mycollection_back_imageview);
	backImageView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			onBackPressed();
			// TODO Auto-generated method stub
			
		}
	});
}
	private	void getNextPage()
		{
		
	
			JsonObjectRequest request=new JsonObjectRequest(Method.GET, String.format("http://110.84.129.130:8080/Yuf/relation/getFollowsInfo/%d/%d", String.valueOf(UserInfo.getInstance().userid),++currentPage), null,  new Response.Listener<JSONObject>()  
	        {  
	
	            @Override  
	            public void onResponse(JSONObject response)  
	            {  
						try {
							
							
							jsonArray=MyApplication.joinJSONArray(jsonArray, response.getJSONArray("followsData"));
							
							Log.d("tab1share", "resopn"+String.valueOf(jsonArray.length()));
							
							if (response.getInt("currentPage")>=response.getInt("followsMaxPage")) {
								listView.setMode(Mode.PULL_FROM_START);
								Toast.makeText(Tab3MyCollectionActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
								isEnd=true;
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						listView.onRefreshComplete();
						mAdaAdapter.notifyDataSetChanged();
	                 
	        }}, new Response.ErrorListener()  
	        {  
	
	            @Override  
	            public void onErrorResponse(VolleyError error)  
	            {  
	                Log.e("TAG", error.getMessage(), error);  
	            }  
	        });
	
			//将JsonObjectRequest 加入RequestQuene
	MyApplication.requestQueue.add(request);
	Log.d("liow","request start");
	MyApplication.requestQueue.start();
			
		}
	private class MylistAdapter extends BaseAdapter
	{
	
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			Log.d("tab1share", String.valueOf(jsonArray.length()));
			return jsonArray.length();
	
		}
	
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			try {
				return jsonArray.getJSONObject(position);
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
			// TODO Auto-generated method stub
			Log.d("mywork", "get");
			if (convertView==null) {
				convertView=getLayoutInflater().inflate(R.layout.tab1_share_list_item,null);
				
			}
			NetworkImageView imageView=(NetworkImageView)convertView.findViewById(R.id.tab3_mywork_item_img);
			try {
				imageView.setImageUrl("http://110.84.129.130:8080/Yuf"+jsonArray.getJSONObject(position).getString("postpicurl"),mImageLoader);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TextView posttitle=(TextView)convertView.findViewById(R.id.tab3_mywork_item_name);
			try {
				posttitle.setText(jsonArray.getJSONObject(position).getString("posttitle"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TextView posttime=(TextView)convertView.findViewById(R.id.tab3_mywork_item_time);
			try {
				long currentTime=jsonArray.getJSONObject(position).getLong("posttime");
			
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
}
