package com.yuf.app.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.CategorysEntity;
import com.yuf.app.adapter.InsiderPageAdapter;
import com.yuf.app.adapter.MyFragmentPagerAdapter;
import com.yuf.app.ui.indicator.TitlePageIndicator;

public class Tab0Fragment extends Fragment {

	private TitlePageIndicator tab0Indicator;
	private ViewPager tab0Viewpage;
	private MyFragmentPagerAdapter fragmentPagerAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = (View) inflater.inflate(R.layout.main_tab_0, null);
		//创建变量
		initMember(view);
		//为变量赋值
		getRecommendedDishSet();
		
		return view;
	}

	private void initMember(View view) {
		// TODO Auto-generated method stub
		
		tab0Viewpage = (ViewPager) view.findViewById(R.id.tab0_pager);
		tab0Indicator = (TitlePageIndicator) view
				.findViewById(R.id.tab0_indicator);
		fragmentPagerAdapter = new MyFragmentPagerAdapter(getFragmentManager());

	}

	private void getRecommendedDishSet() {
		JsonObjectRequest request = new JsonObjectRequest(
				Method.GET,
				"https://110.84.129.130:8443/Yuf/dishset/getRecommendedDishset",
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						JSONArray dishsetsArray;
						try {
							dishsetsArray = response.getJSONArray("dishsets");
							fragmentPagerAdapter.addTitle("家庭套餐");
							fragmentPagerAdapter.addFragment(new Tab0RecommendFragment(
									dishsetsArray.getJSONObject(0)
											.getJSONArray("dishsetdetail")));
							fragmentPagerAdapter.addTitle("情侣套餐");
							fragmentPagerAdapter.addFragment(new Tab0RecommendFragment(
									dishsetsArray.getJSONObject(1)
											.getJSONArray("dishsetdetail")));
							fragmentPagerAdapter.addFragment(new Tab0RecommendFragment(
									dishsetsArray.getJSONObject(2)
											.getJSONArray("dishsetdetail")));
							fragmentPagerAdapter.addTitle("单身套餐");
							tab0Viewpage.setAdapter(fragmentPagerAdapter);
							tab0Viewpage.setOffscreenPageLimit(3);
							
							tab0Indicator.setViewPager(tab0Viewpage);
							tab0Viewpage.setCurrentItem(1);
							Log.e("TAG", response.toString());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});
		MyApplication.requestQueue.add(request);
		MyApplication.requestQueue.start();
	}

}
