package com.yuf.app.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ShareActionProvider;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.CategorysEntity;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.adapter.InsiderPageAdapter;
import com.yuf.app.adapter.MyFragmentPagerAdapter;
import com.yuf.app.http.extend.BitmapCache;
import com.yuf.app.mywidget.CircularNetWorkImageView;
import com.yuf.app.ui.indicator.TitlePageIndicator;
public class Main extends FragmentActivity {

	//全局变量
	private ViewPager mTabPager;
	private ImageView mTab0, mTab1, mTab2, mTab3,mTab4;
	private int currIndex = 0;
	
 @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	
		mTabPager = (ViewPager) findViewById(R.id.tabpager);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mTabPager.setOffscreenPageLimit(5);
		mTab0 = (ImageView) findViewById(R.id.img_0);
		mTab1 = (ImageView) findViewById(R.id.img_1);
		mTab2 = (ImageView) findViewById(R.id.img_2);
		mTab3 = (ImageView) findViewById(R.id.img_3);
		mTab4 = (ImageView) findViewById(R.id.img_4);
        mTab0.setOnClickListener(new MyOnClickListener(0));
		mTab1.setOnClickListener(new MyOnClickListener(1));
		mTab2.setOnClickListener(new MyOnClickListener(2));
		mTab3.setOnClickListener(new MyOnClickListener(3));
		mTab4.setOnClickListener(new MyOnClickListener(4));
		
		MyFragmentPagerAdapter outsidePagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager());
		 //导航界面布局初始化
		outsidePagerAdapter.addFragment(new Tab0Fragment());
		outsidePagerAdapter.addFragment(new Tab1Fragment());
		outsidePagerAdapter.addFragment(new Tab2Fragment());
		outsidePagerAdapter.addFragment(new Tab3Fragment());
		outsidePagerAdapter.addFragment(new Tab4Fragment());
		
		mTabPager.setAdapter(outsidePagerAdapter);
	

	}// end the onCreate method





	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
	}



	@Override
	protected void onStart() {
		super.onStart();
	}


@Override
	protected void onPause() {
		super.onPause();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	@Override
	protected void onStop() {
		super.onStop();
	}


	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}
		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};
	
//选择某个tab时相应的图标颜色变化
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				mTab0.setImageDrawable(getResources().getDrawable(R.drawable.tab_0_pressed));
				if (currIndex == 1) {
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_1_normal));
				} else if (currIndex == 2) {
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_2_normal));
				}else if (currIndex == 3) {
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_3_normal));
				}
				else if (currIndex==4) {
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_3_normal));
				}
				
				break;
			case 1:
				mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_1_pressed));
				if (currIndex == 0) {
					mTab0.setImageDrawable(getResources().getDrawable(R.drawable.tab_0_normal));
				} else if (currIndex == 2) {
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_2_normal));
				}else if (currIndex == 3) {
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_3_normal));
				}else if (currIndex==4) {
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_3_normal));
				}
				break;
			case 2:
				mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_2_pressed));
				if (currIndex == 0) {
					mTab0.setImageDrawable(getResources().getDrawable(R.drawable.tab_0_normal));
				} else if (currIndex == 1) {
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_1_normal));
				}else if (currIndex == 3) {
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_3_normal));
				}else if (currIndex==4) {
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_3_normal));
				}
				break;
			case 3:
				mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_3_pressed));
                if (currIndex == 0) {
					mTab0.setImageDrawable(getResources().getDrawable(R.drawable.tab_0_normal));
				} else if (currIndex == 1) {
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_1_normal));
				}else if (currIndex == 2) {
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_2_normal));
				}else if (currIndex==4) {
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_3_normal));
				}
                break;
			case 4:
				
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_3_pressed));
				
				if (currIndex == 0) {
					mTab0.setImageDrawable(getResources().getDrawable(R.drawable.tab_0_normal));
				} else if (currIndex == 1) {
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_1_normal));
				}else if (currIndex == 2) {
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_2_normal));
				}else if (currIndex == 3) {
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_3_normal));
				}
				break;
			default:
				break;
			}
			currIndex = arg0;
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
	}
	
	
	private long frist_back_time=0;
//双返回键退出
	// 此部分为了实现按两下返回退出
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		
			finish();
				if (System.currentTimeMillis() - frist_back_time < 1500) {
				
				}
				frist_back_time=System.currentTimeMillis();
	}
		return true;
	}
	

}// end this Main class
