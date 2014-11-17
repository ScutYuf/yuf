package com.yuf.app.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yuf.app.MyApplication;
import com.yuf.app.adapter.FoodMaterialGVAdapter;
import com.yuf.app.mywidget.MyGridView;

public class Tab0FoodMaterialActivity extends Activity{

	private ImageView back_imageView;
	private Button buyFood;
	
	private JSONArray mainMaterial;
	private JSONArray helpMaterial;
	private JSONArray addMaterial;
	private MyGridView gridView1;
	private MyGridView gridView2;
	private MyGridView gridView3;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab0_food_material);
		initView();
		getDishDetail();
//放在getDishDetail();中		
//      sortMaterial(jsonArray);   
//		gridView1.setAdapter(new FoodMaterialGVAdapter(Tab0FoodMaterialActivity.this,mainMaterial));
//		gridView2.setAdapter(new FoodMaterialGVAdapter(Tab0FoodMaterialActivity.this,helpMaterial));
//		gridView3.setAdapter(new FoodMaterialGVAdapter(Tab0FoodMaterialActivity.this,addMaterial));
	}
	
	private void initView() {
//返回
		back_imageView = (ImageView)findViewById(R.id.back_imageView);
		back_imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});
//购买食材
		buyFood = (Button)findViewById(R.id.buyFood);
		buyFood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(Tab0FoodMaterialActivity.this, Tab2WaitForPayActivity.class);
				startActivity(intent);
			}
		});
		
		mainMaterial = new JSONArray();
		helpMaterial = new JSONArray();
		addMaterial = new JSONArray();
		
		gridView1 = (MyGridView)findViewById(R.id.food_material_gridview1);
		gridView2 = (MyGridView)findViewById(R.id.food_material_gridview2);
		gridView3 = (MyGridView)findViewById(R.id.food_material_gridview3);
	}

//食材分类
	private void sortMaterial(JSONArray jsonArray)
	{
		String typeString=new String();
		JSONObject jsonObject=new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				jsonObject = jsonArray.getJSONObject(i);
				 typeString=jsonObject.getString("foodtype");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			if (typeString.equals("主料")) {
				mainMaterial.put(jsonObject);
			}
			else if (typeString.equals("辅料")) {
				helpMaterial.put(jsonObject);
			}
			else  {
				addMaterial.put(jsonObject);
			}
		}
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
					jsonArray =jsonObject.getJSONArray("RelatedFood");
					Log.d("Tab0FoodMaterialActivity", jsonArray.toString());
					sortMaterial(jsonArray);
					gridView1.setAdapter(new FoodMaterialGVAdapter(Tab0FoodMaterialActivity.this,mainMaterial));
					gridView2.setAdapter(new FoodMaterialGVAdapter(Tab0FoodMaterialActivity.this,helpMaterial));
					gridView3.setAdapter(new FoodMaterialGVAdapter(Tab0FoodMaterialActivity.this,addMaterial));
				} catch (JSONException e) {
					e.printStackTrace();
				}
	        }  
	    }, new Response.ErrorListener()  
	    {  @Override  
	        public void onErrorResponse(VolleyError error)  
	        {  
	            Log.e("Tab0FoodMaterialActivity", error.getMessage(), error);  
	        }  
	    });
	
	//将JsonObjectRequest 加入RequestQuene
	MyApplication.requestQueue.add(request);
	Log.d("Tab0FoodMaterialActivity","request start");
	MyApplication.requestQueue.start();
	}

}
