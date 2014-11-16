package com.yuf.app.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.yuf.app.MyApplication;
import com.yuf.app.http.extend.BitmapCache;

public class Tab0CookStepActivity extends Activity{
//ViewPager
	private ViewPager viewPager;
	private List<View> views;
	private View view;
//ViewPager内容	
	private TextView viewPagerTextView;
	private NetworkImageView viewPagerImageView;
//标题栏	
	private TextView titleTextView;
	private List<View> titles;
	private ImageView back_imageView;
	private int screenH;//屏幕高度
	
	private JSONArray stepJsonObject;
	private JSONObject jsonObject;
	private ImageLoader mImageLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab0_cook_step);
		mImageLoader=new ImageLoader(MyApplication.requestQueue, new BitmapCache());	
		
		initView();//初始化	
		getDishDetail();//获取网络数据
		
		back_imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			
				Tab0CookStepActivity.this.finish();
			}
		});
	}
	
	private void initView() {
		viewPager = (ViewPager)findViewById(R.id.tab0_cook_step_vPager);
		views = new ArrayList<View>();
		titles = new ArrayList<View>();
		back_imageView = (ImageView)findViewById(R.id.back_imageView);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenH = dm.heightPixels;// 获取分辨率高度		
		Log.d("A", "DSVD");
	}
	
	private void inintViewPager() {
		LayoutInflater inflater=getLayoutInflater();
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, screenH/9);		
		
		for(int i=0 ;i<stepJsonObject.length(); i++){
			JSONObject mJsonObject;
			view = (View)inflater.inflate(R.layout.tab0_cook_step_item, null);
			viewPagerTextView = (TextView)view.findViewById(R.id.text);
			viewPagerImageView = (NetworkImageView)view.findViewById(R.id.image);			
//初始化标题栏			
			titleTextView = new TextView(this);
			try {
				mJsonObject = stepJsonObject.getJSONObject(i);
				titleTextView.setText(String.valueOf(mJsonObject.getInt("recipeorder")));
				viewPagerTextView.setText(mJsonObject.getString("recipedetail"));
				viewPagerImageView.setImageUrl("http://110.84.129.130:8080//Yuf/images/dish/"+getIntent().getStringExtra("DishId")+".jpg",mImageLoader);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			views.add(view);
			titleTextView.setGravity(Gravity.CENTER);
			titleTextView.setBackgroundColor(Color.parseColor("#E6E6FA"));
			titleTextView.setOnClickListener(new MyOnClickListener(i));
			((LinearLayout)this.findViewById(R.id.tab0_cook_step_layout)).addView(titleTextView,p);
			titles.add(titleTextView);
		}
		
		viewPager.setAdapter(new MyPageAdapter(views));
		viewPager.setCurrentItem(0);
		titles.get(0).setBackgroundColor(Color.parseColor("#ff00ff"));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				setTitleBackGround(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
}
//获取网络数据	
	private void getDishDetail() {
		
		JsonObjectRequest request=new JsonObjectRequest(Method.GET, "http://110.84.129.130:8080/Yuf/dish/getDishById/"+getIntent().getStringExtra("DishId"), null,  new Response.Listener<JSONObject>()  
        {  
            @Override  
            public void onResponse(JSONObject response)  
            {  
            	try {
					jsonObject=response.getJSONObject("dishDetail");
					stepJsonObject = jsonObject.getJSONArray("Recipe");
					inintViewPager();
				} catch (JSONException e) {
					e.printStackTrace();
				}  
            }  
        }, new Response.ErrorListener()  
        {  
            @Override  
            public void onErrorResponse(VolleyError error)  
            {  
                Log.e("TAG", error.getMessage(), error);  
            }  
        });

//将JsonObjectRequest 加入RequestQuene
MyApplication.requestQueue.add(request);
Log.d("Tab0CookStepActivity","request start");
MyApplication.requestQueue.start();
	}
	
//动态改变标题栏背景颜色	
	private void setTitleBackGround(int position){
		for(int i = 0;i<stepJsonObject.length();i++){
			titles.get(i).setBackgroundColor(Color.parseColor("#E6E6FA"));
		}
		titles.get(position).setBackgroundColor(Color.parseColor("#ff00ff"));
	}	
	
//标题栏点击，改变tab页
	private class MyOnClickListener implements OnClickListener{

		private int index = 0;
		public MyOnClickListener(int number){
			this.index = number;
		}
		@Override
		public void onClick(View view) {
			setTitleBackGround(index);
			viewPager.setCurrentItem(index);
		}	
	}
	
	private class MyPageAdapter extends PagerAdapter{

		private List<View> views;
		
		public MyPageAdapter(List<View> views){
			this.views = views;
		}
		
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));
		}


		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(views.get(position),0);
			return views.get(position);
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		
	}
}
