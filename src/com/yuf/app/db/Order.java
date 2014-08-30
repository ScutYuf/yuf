package com.yuf.app.db;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;

public class Order {
	public static final String ID = "_id";
	public static final String USERID = "userId";
	public static final String ORDERPRICE = "orderPrice";
	public static final String ORDERTIME = "orderTime";
	public static final String ORDERPAYMETHOD = "orderPaymethod";
	public static final String ORDERAMOUNT = "orderAmount";
	public static final String ORDERDISHID = "dishId";

	int userId;
	int dishId;
	String orderPrice;
	String orderTime;
	String orderPaymethod;
	int orderAmount;

	public JSONObject toJsonObject() {
		JSONObject object = new JSONObject();
		try {
			object.put("userId", userId);
			object.put("dishId", dishId);
			object.put("orderPrice", orderPrice);
			object.put("orderTime", orderTime);
			object.put("orderPaymethod", orderPaymethod);
			object.put("orderAmount", orderAmount);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;
	}

	public void writeToDb() {

	}

	public void readFromDb() {
	}

}
