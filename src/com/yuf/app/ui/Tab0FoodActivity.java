package com.yuf.app.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.CategorysEntity;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.adapter.MiddlePageAdapter;
import com.yuf.app.db.Order;
import com.yuf.app.ui.Main.MyPageChangeListener;
import com.yuf.app.ui.indicator.TitlePageIndicator;

/**
 * @author DU
 *
 */
public class Tab0FoodActivity extends FragmentActivity {
private ImageView backImageView;
private JSONObject jsonObject;
private ImageView goPayImageView;
private boolean isSeeJust;
private TextView priceTextView;

	class MyPageChangeListener implements OnPageChangeListener {
	    @Override
	    public void onPageScrollStateChanged(int arg0) {
	        // TODO Auto-generated method stub
	    }
	
	    @Override
	    public void onPageScrolled(int arg0, float arg1, int arg2) {
	        // TODO Auto-generated method stub
	    }
	
	    @Override
	    public void onPageSelected(int arg0) {
	        // TODO Auto-generated method stub
	       
	    }
	}
	private ArrayList<Fragment> fragments;
	private ArrayList<CategorysEntity> fooddetailcategorysEntities;
	private TitlePageIndicator fooddetailIndicator;
	private ViewPager Viewpage;
	private MiddlePageAdapter mInsidePageAdapter;
	private TextView foodnameTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		isSeeJust=getIntent().getExtras().getBoolean("isSeeJust",false);
		
		setContentView(R.layout.tab0_recipe_detail);
		
		
		goPayImageView=(ImageView)findViewById(R.id.tab0_recipe_detail_gopay_image);
		goPayImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				addOrder();
				Intent intent=new Intent(Tab0FoodActivity.this, Tab2WaitForPayActivity.class);
				startActivity(intent);
				
				
			}
		});
if (isSeeJust) {
			goPayImageView.setVisibility(View.GONE);
		}
		foodnameTextView=(TextView)findViewById(R.id.tab0_detail_foodname);
		foodnameTextView.setText(getIntent().getExtras().getString("dishname"));
		
		
		priceTextView=(TextView)findViewById(R.id.tab1_share_item_titile_textview);
		priceTextView.setText(""+getIntent().getExtras().getDouble("dishprice"));
		
		backImageView=(ImageView)findViewById(R.id.tab0_detail_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				// TODO Auto-generated method stub
				
			}
		});
		
	getDishDetail();
		
		
		
	}
	
//待删除
	protected void addOrder() {
		// TODO Auto-generated method stub
		
	}


	void addFragment(){
		 fragments=new ArrayList<Fragment>();
		fooddetailcategorysEntities=new ArrayList<CategorysEntity>();
		fooddetailcategorysEntities.add(new CategorysEntity("烹饪方法"));
		fooddetailcategorysEntities.add(new CategorysEntity("食材"));		
		try {
			
			fragments.add(new Tab0FoodCookFragment(jsonObject.getJSONArray("Recipe"),jsonObject.getJSONObject("Dish")));
			fragments.add(new Tab0FoodMaterialFragment(jsonObject.getJSONArray("RelatedFood")));
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		fooddetailIndicator=(TitlePageIndicator) findViewById(R.id.tab0_indicator);
		Viewpage=(ViewPager) findViewById(R.id.tab0_pager);
		mInsidePageAdapter=new MiddlePageAdapter(getSupportFragmentManager());
		Viewpage.setAdapter(mInsidePageAdapter);
		Viewpage.setOffscreenPageLimit(3);
		mInsidePageAdapter.addFragments(fragments,fooddetailcategorysEntities);
		fooddetailIndicator.setViewPager(Viewpage);
		fooddetailIndicator.setOnPageChangeListener( new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	private void getDishDetail() {
	
		JsonObjectRequest request=new JsonObjectRequest(Method.GET, "http://110.84.129.130:8080/Yuf/dish/getDishById/"+getIntent().getExtras().getString("dishid"), null,  new Response.Listener<JSONObject>()  
        {  

            @Override  
            public void onResponse(JSONObject response)  
            {  
            	
            	try {
					jsonObject=response.getJSONObject("dishDetail");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	addFragment();
                 
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
Log.d("liow","request start");
MyApplication.requestQueue.start();
	}
}
