package com.yuf.app;

import java.io.File;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;
import android.content.Context;

public  class MyApplication extends Application {
	
	public static RequestQueue requestQueue;
	public static MyApplication myapplication;
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		myapplication=this;
		requestQueue=Volley.newRequestQueue(this); 
	}
	
	
	}
