package com.yuf.app.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yuf.app.MyApplication;
import com.yuf.app.http.extend.BitmapCache;

public class Tab0SearchActivity extends Activity {
private ImageView backImageView;
private EditText edt_search;
private PullToRefreshListView listView;
private boolean isEnd;
private int currentPage;
private JSONArray jsonArray;
private MyListAdapter adapter;
private ImageLoader mImageLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab0_search);
		
		edt_search=(EditText)findViewById(R.id.edt_search);
		listView=(PullToRefreshListView)findViewById(R.id.tab0_search_listview);
		jsonArray=new JSONArray();
		mImageLoader = new ImageLoader(MyApplication.requestQueue, new BitmapCache());
		adapter=new MyListAdapter();
		listView.setAdapter(adapter);
		listView.setMode(Mode.PULL_FROM_START);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(Tab0SearchActivity.this, System.currentTimeMillis(),
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
			        	getPage();
					}
			}	});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				

				
				Intent intent=new Intent(Tab0SearchActivity.this,
						Tab0FoodActivity.class);
				Bundle bundle = new Bundle();                           //创建Bundle对象   
				try {
					JSONObject jsonObject=jsonArray.getJSONObject(position-1);
					bundle.putString("dishid",jsonObject.getString("dishid") );
					bundle.putString("dishname",jsonObject.getString("dishname") );
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}     //装入数据   
				intent.putExtras(bundle);                                //把Bundle塞入Intent里面  
				startActivity(intent);
				finish();
				// TODO Auto-generated method stub
				
			
				
			}
		});

		backImageView=(ImageView)findViewById(R.id.tab0_search_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();	
			}
		});

	}
	public void onClick(View view){
		refreshListView();
}
	private void refreshListView() {
		isEnd=false;
		listView.setMode(Mode.PULL_FROM_START);
		jsonArray=new JSONArray();
		currentPage=0;
		getPage();
	}
    private void getPage() {
    	currentPage++;
		JSONObject searchJsonObject=new JSONObject();
		try{
		searchJsonObject.put("searchStr", edt_search.getText().toString());
		searchJsonObject.put("currentIndex",currentPage);
		}catch (JSONException e) {
			e.printStackTrace();
		}
		JsonObjectRequest request=new JsonObjectRequest(Method.POST, "http://110.84.129.130:8080/Yuf/dish/searchDish", searchJsonObject,  new Response.Listener<JSONObject>()  
	            {  @Override  
	                public void onResponse(JSONObject response)  
	                {  
	                	try {
	                		jsonArray=MyApplication.joinJSONArray(jsonArray, response.getJSONArray("dishes"));
							Log.d("tab1share", "resopn"+String.valueOf(jsonArray.length()));
							if (response.getInt("currentPageNum")>=response.getInt("maxPageNum")) {
								listView.setMode(Mode.PULL_FROM_START);
								isEnd=true;
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
	                	listView.onRefreshComplete();
	                	adapter.notifyDataSetChanged();
	                }  
            }, new Response.ErrorListener()  
            {  @Override  
                public void onErrorResponse(VolleyError error)  
                {  
                    Log.e("tab0search", error.getMessage(), error);  
                }  
            });
	//将JsonObjectRequest 加入RequestQuene
	MyApplication.requestQueue.add(request);
	
	MyApplication.requestQueue.start();
	}
private class MyListAdapter extends BaseAdapter{

	@Override
	public int getCount() {
		Log.d("tab0search", String.valueOf(jsonArray.length()));
		return jsonArray.length();
	}

	@Override
	public Object getItem(int position) {
		try {
			return jsonArray.getJSONObject(position);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			convertView=Tab0SearchActivity.this.getLayoutInflater().inflate(R.layout.tab0_search_item,null);
		}
		
		NetworkImageView dishpicurl = (NetworkImageView)convertView.findViewById(R.id.tab0_search_item_img);
		dishpicurl.setDefaultImageResId(R.drawable.no_pic);
		TextView dishname=(TextView)convertView.findViewById(R.id.tab0_search_item_name);
		TextView dishprice=(TextView)convertView.findViewById(R.id.tab0_search_item_price);
		TextView dishrecommended=(TextView)convertView.findViewById(R.id.tab0_search_item_re);
		
		try {
			JSONObject jsonObject = jsonArray.getJSONObject(position);
			dishpicurl.setImageUrl("http://110.84.129.130:8080/Yuf"+jsonObject.getString("dishpicurl"), mImageLoader);
			dishname.setText(jsonObject.getString("dishname"));
			dishprice.setText("价格："+jsonObject.getString("dishprice"));
			dishrecommended.setText("推荐数："+jsonObject.getString("dishrecommended"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return convertView;
	}	
}
}
