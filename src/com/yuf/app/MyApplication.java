package com.yuf.app;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.R.string;
import android.app.Application;
import android.content.Context;

public  class MyApplication extends Application {
	
	public static RequestQueue requestQueue;
	public static MyApplication myapplication;
	public static String sessionid;
	public static String userid;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		myapplication=this;
		requestQueue=Volley.newRequestQueue(this); 
	}
	
	public static JSONArray joinJSONArray(JSONArray mData, JSONArray array) {
	    StringBuffer buffer = new StringBuffer();
	    try {
	      int len = mData.length();
	      for (int i = 0; i < len; i++) {
	        JSONObject obj1 = (JSONObject) mData.get(i);
	        if (i == len - 1)
	          buffer.append(obj1.toString());
	        else
	          buffer.append(obj1.toString()).append(",");
	      }
	      len = array.length();
	      if (len > 0)
	        buffer.append(",");
	      for (int i = 0; i < len; i++) {
	        JSONObject obj1 = (JSONObject) array.get(i);
	        if (i == len - 1)
	          buffer.append(obj1.toString());
	        else
	          buffer.append(obj1.toString()).append(",");
	      }
	      buffer.insert(0, "[").append("]");
	      return new JSONArray(buffer.toString());
	    } catch (Exception e) {
	    }
	    return null;
	  }
	}
