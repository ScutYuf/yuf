package com.yuf.app.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHelper {
	@SuppressLint("SimpleDateFormat") 
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static public String getTimeStringFromLong(long currentTime)
	{
		Date date = new Date(currentTime);
		return formatter.format(date);	
	}
//	static public long getTimeLongFromString()
//	{
//		
//	}
}
