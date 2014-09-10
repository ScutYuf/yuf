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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.yuf.app.MyApplication;
import com.yuf.app.http.extend.BitmapCache;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class Tab1SocietyShareFragment extends Fragment {
	private PullToRefreshListView listView;
	private ImageLoader mImageLoader;
	private JSONArray jsonArray;
	private int currentPage;
	private MylistAdapter mAdaAdapter;
	private boolean isEnd;
	public Tab1SocietyShareFragment(){
		super();
		mImageLoader=new ImageLoader(MyApplication.requestQueue, new BitmapCache());
		jsonArray=new JSONArray();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.tab1_share,container,false);
		listView=(PullToRefreshListView)view.findViewById(R.id.tab1_share_listview);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
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
			
					if (!isEnd) {
						
						loadingNextPage();
					}
					
			}	});

		listView.setMode(Mode.BOTH);
		mAdaAdapter=new MylistAdapter();
		listView.setAdapter(mAdaAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(Tab1SocietyShareFragment.this.getActivity(),
						Tab1ShareDetailActivity.class);
				Bundle bundle = new Bundle();                           //创建Bundle对象   
				try {
					JSONObject jsonObject=jsonArray.getJSONObject(position-1);
					bundle.putInt("postid",jsonObject.getInt("postid"));
					bundle.putInt("userid", jsonObject.getInt("userid"));
					bundle.putString("posttitle", jsonObject.getString("posttitle"));
					bundle.putString("postpicurl", jsonObject.getString("postpicurl"));
					bundle.putString("postcontent",jsonObject.getString("postcontent"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}     //装入数据   
				intent.putExtras(bundle);                                //把Bundle塞入Intent里面  
				startActivity(intent);
				// TODO Auto-generated method stub
	
				// TODO Auto-generated method stub
				
			}
		});
		refreshListView();
		 
		 //
		 return  view;
	}
		private void refreshListView() {
			// TODO Auto-generated method stub
			isEnd=false;
			listView.setMode(Mode.BOTH);
			jsonArray=new JSONArray();
			currentPage=0;
			getSharePage(++currentPage);
			
		}
	
		private void loadingNextPage() {
			getSharePage(++currentPage);
			// TODO Auto-generated method stub
			
		}

	
	private	void getSharePage(int page)
	{
	

		JsonObjectRequest request=new JsonObjectRequest(Method.GET, "http://110.84.129.130:8080/Yuf/post/getAllPost/"+String.valueOf(page), null,  new Response.Listener<JSONObject>()  
        {  

            @Override  
            public void onResponse(JSONObject response)  
            {  
					try {
						
						
						jsonArray=MyApplication.joinJSONArray(jsonArray, response.getJSONArray("postData"));
						
						Log.d("tab1share", "resopn"+String.valueOf(jsonArray.length()));
						
					
						if (response.getInt("currentPageNum")>=response.getInt("maxPageNum")) {
							listView.setMode(Mode.PULL_FROM_START);
							isEnd=true;
							Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();
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
			convertView=getActivity().getLayoutInflater().inflate(R.layout.tab1_share_list_item,null);
			
		}
		try {
			JSONObject jsonObject=jsonArray.getJSONObject(position);
			NetworkImageView headimageView=(NetworkImageView)convertView.findViewById(R.id.tab1_share_list_item_headimg);
			headimageView.setDefaultImageResId(R.drawable.no_pic);
			headimageView.setImageUrl("http://110.84.129.130:8080/Yuf"+jsonObject.getString("useravatarurl"),mImageLoader);
			NetworkImageView foodImageView=(NetworkImageView)convertView.findViewById(R.id.tab1_share_list_item_foodimage);
			foodImageView.setDefaultImageResId(R.drawable.no_pic);
			foodImageView.setImageUrl("http://110.84.129.130:8080/Yuf"+jsonObject.getString("postpicurl"), mImageLoader);		
			TextView titleTextView=(TextView)convertView.findViewById(R.id.tab1_share_item_titile_textview);
			titleTextView.setText(jsonObject.getString("posttitle"));
			TextView usernameTextView=(TextView)convertView.findViewById(R.id.tab1_share_list_item_name_textview);
			usernameTextView.setText(jsonObject.getString("username"));
			TextView timeTextView=(TextView)convertView.findViewById(R.id.tab1_share_list_item_time_textview);
			long currentTime =jsonObject.getLong("posttime");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(currentTime);
			timeTextView.setText(formatter.format(date));
			TextView contentTextView=(TextView)convertView.findViewById(R.id.tab1_share_list_item_content_textview);
			contentTextView.setText(jsonObject.getString("postcontent"));
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// TODO Auto-generated method stub
		return convertView;
	}
	
	}


}
