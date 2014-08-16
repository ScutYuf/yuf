package com.yuf.app;

import java.io.File;

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
	
	
	}
