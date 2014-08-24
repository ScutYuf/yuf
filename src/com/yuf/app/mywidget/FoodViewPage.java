package com.yuf.app.mywidget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class FoodViewPage extends ViewPager {

	public FoodViewPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public FoodViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		Log.d("foodviewpage", "onInterceptTouchEvent"); 
		getParent().requestDisallowInterceptTouchEvent(true);
		 super.onInterceptTouchEvent(arg0);
return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		Log.d("foodviewpage", "onTouchevent"); 
		super.onTouchEvent(arg0);
		return true;
	}
	

}
