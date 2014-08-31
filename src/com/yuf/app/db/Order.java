package com.yuf.app.db;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.yuf.app.MyApplication;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class Order {
	public static final String ID = "_id";
	public static final String USERID = "userId";
	public static final String ORDERPRICE = "orderPrice";
	public static final String ORDERTIME = "orderTime";
	public static final String ORDERPAYMETHOD = "orderPaymethod";
	public static final String ORDERAMOUNT = "orderAmount";
	public static final String ORDERDISHID = "dishId";

	public int userId;
	public int dishId;
	public double orderPrice;
	public String orderTime;
	public String orderPaymethod;
	public int orderAmount;

	public JSONObject toJsonObject() {
		JSONObject object = new JSONObject();
		try {
			object.put("userId", userId);
			object.put("dishId", dishId);
			object.put("orderPrice", orderPrice);
			object.put("orderTime", orderTime);
			object.put("orderPaymethod", orderPaymethod);
			object.put("orderAmount",orderAmount);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;
	}

	public void writeToDb() {
		   ContentResolver contentResolver = MyApplication.myapplication.getContentResolver();  
           Uri url = Uri.parse("content://com.yuf.app.myprovider/order");  
           ContentValues values = new ContentValues();  
           values.put(Order.USERID, userId);  
           values.put(Order.ORDERPRICE, orderPrice);  
           values.put(Order.ORDERTIME, orderTime);  
           values.put(Order.ORDERPAYMETHOD, orderPaymethod);  
           values.put(Order.ORDERAMOUNT, orderAmount);  
           values.put(Order.ORDERDISHID, dishId);  
           Uri result = contentResolver.insert(url, values); 
           if (ContentUris.parseId(result)>0) {  
               Log.d("liow","添加成功！");        
           }  
	}

	public static ArrayList<Order> readFromDb() {
		ArrayList<Order> list = new ArrayList<Order>();
		Uri url = Uri.parse("content://com.yuf.app.myprovider/order");  
        Cursor cursor = MyApplication.myapplication.getContentResolver().query(url,  
                  new String[] { "_id", "userId", "orderPrice","orderTime","orderPaymethod","orderAmount","dishId" }, null, null, "_id");  
        while (cursor.moveToNext()) {  
        	Order order = new Order();
        	order.userId = cursor.getInt(cursor.getColumnIndex("userId"));  
        	order.dishId = cursor.getInt(cursor.getColumnIndex("dishId"));  
        	order.orderPrice = cursor.getDouble(cursor.getColumnIndex("orderPrice"));  
        	order.orderTime = cursor.getString(cursor.getColumnIndex("orderTime"));  
        	order.orderPaymethod = cursor.getString(cursor.getColumnIndex("orderPaymethod"));  
        	order.orderAmount = cursor.getInt(cursor.getColumnIndex("orderAmount"));  
        	list.add(order);  
        }  
        cursor.close();  
		return list;
	}

}
