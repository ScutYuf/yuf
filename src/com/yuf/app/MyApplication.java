package com.yuf.app;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.yuf.app.http.extend.BitmapCache;

import android.R.string;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

public  class MyApplication extends Application {
	
	public static RequestQueue requestQueue;
	public static MyApplication myApplication;
	public static ImageLoader mImageLoader;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//严格模式
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
	    .detectAll()
	    .penaltyLog()  
	    .build()); 
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()  
        .detectAll()  
        .penaltyLog()  
        .build()); 
		
		myApplication=this;
		requestQueue=Volley.newRequestQueue(getApplicationContext()); 
		mImageLoader=new ImageLoader(requestQueue, new BitmapCache());
		
		super.onCreate();
		
		
	}
	
	public static JSONArray joinJSONArray(JSONArray mData, JSONArray array) {
	if (mData.toString().equals("[]")) {
		return array;
	}
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
