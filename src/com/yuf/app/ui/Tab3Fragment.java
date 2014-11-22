package com.yuf.app.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yuf.app.Config;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.mywidget.CircularNetWorkImageView;
import com.yuf.app.util.TimeHelper;

enum ItemType {
	WORK, ORDER, COLLECTION
};

public class Tab3Fragment extends Fragment {

	private CircularNetWorkImageView tab3profileImageView;
	private TabHost tabHost;
	private LinearLayout goFanFocusLinerLayout;

	private PullToRefreshListView ordersListView;
	private PullToRefreshListView worksListView;
	private PullToRefreshListView collectionListView;

	private JSONArray works;
	private JSONArray orders;
	private JSONArray collections;

	private MyListAdapter worksAdatper;
	private MyListAdapter ordersAdatper;
	private MyListAdapter collectionsAdatper;

	private int worksCurrentPage;
	private int ordersCurrentPage;
	private int collectionsCurrentPage;
	protected boolean isWorksEnd;
	protected boolean isCollectionsEnd;
	protected boolean isOrdersEnd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.main_tab_3_v, null);
		initMember(view);
		getWorks(++worksCurrentPage);
		getOrders(++ordersCurrentPage);
		getCollection(++ordersCurrentPage);
		return view;
	}

	private void initMember(View view) {
		// TODO Auto-generated method stub

		goFanFocusLinerLayout = (LinearLayout) view
				.findViewById(R.id.goFanFocusLinerLayout);
		goFanFocusLinerLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goFanFocusActivity();
			}
		});

		tab3profileImageView = (CircularNetWorkImageView) view
				.findViewById(R.id.tab3_profile);
		tab3profileImageView.setDefaultImageResId(R.drawable.no_pic);
		tabHost = (TabHost) view.findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("tab1")
				.setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("tab2")
				.setContent(R.id.tab2));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("tab3")
				.setContent(R.id.tab3));

		ordersListView = (PullToRefreshListView) view
				.findViewById(R.id.orders_listview);
		worksListView = (PullToRefreshListView) view
				.findViewById(R.id.works_listview);
		collectionListView = (PullToRefreshListView) view
				.findViewById(R.id.collections_listview);

		ordersListView.setMode(Mode.PULL_FROM_START);
		worksListView.setMode(Mode.PULL_FROM_START);
		collectionListView.setMode(Mode.PULL_FROM_START);

		orders = new JSONArray();
		works = new JSONArray();
		collections = new JSONArray();

		worksCurrentPage = 0;
		ordersCurrentPage = 0;
		collectionsCurrentPage = 0;

		collectionsAdatper = new MyListAdapter(orders, ItemType.COLLECTION);
		ordersAdatper = new MyListAdapter(orders, ItemType.ORDER);
		worksAdatper = new MyListAdapter(works, ItemType.WORK);

		ordersListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				orders = new JSONArray();
				ordersCurrentPage = 0;
				isOrdersEnd = false;
				getOrders(++ordersCurrentPage);

			}
		});
		ordersListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						// TODO Auto-generated method stub
						if (!isOrdersEnd)
							getOrders(++ordersCurrentPage);
					}
				});

		worksListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				works = new JSONArray();
				worksCurrentPage = 0;
				isWorksEnd = false;
				getWorks(++worksCurrentPage);

			}
		});
		worksListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						// TODO Auto-generated method stub
						if (!isWorksEnd)
							getWorks(++worksCurrentPage);

					}
				});
		collectionListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						collections = new JSONArray();
						collectionsCurrentPage = 0;
						isCollectionsEnd = false;
						getCollection(++collectionsCurrentPage);

					}
				});
		collectionListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						if (!isCollectionsEnd)
							getCollection(++collectionsCurrentPage);
						// TODO Auto-generated method stub

					}
				});

		ordersListView.setAdapter(ordersAdatper);
		worksListView.setAdapter(worksAdatper);
		collectionListView.setAdapter(collectionsAdatper);

	}

	public void goFanFocusActivity() {
		Intent intent = new Intent(getActivity(), Tab3FanFocusActivity.class);
		startActivity(intent);
	}

	private void getWorks(int currentPage) {
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				String.format(
						"http://110.84.129.130:8080/Yuf/post/getPost/%d/%d",
						UserInfo.getInstance().userid, currentPage), null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						try {
							if (response.getInt("currentPage") >= response
									.getInt("postMaxPage")) {
								Toast.makeText(getActivity(), "end of list",
										Toast.LENGTH_SHORT).show();
								isWorksEnd = true;
							}
							JSONArray jsonArray = response
									.getJSONArray("postData");
							works = MyApplication.joinJSONArray(works,
									jsonArray);
							worksAdatper.setData(works);

						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// TODO Auto-generated method stub
						worksListView.onRefreshComplete();
						worksAdatper.notifyDataSetChanged();

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

	private void getOrders(int currentPage) {
		JsonObjectRequest request = new JsonObjectRequest(
				Method.GET,
				String.format(
						"http://110.84.129.130:8080/Yuf/order/getOrderInfo/%d/%d",
						UserInfo.getInstance().userid, currentPage), null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						try {
							if (response.getInt("currentPage") >= response
									.getInt("orderMaxPage")) {

								Toast.makeText(getActivity(), "End of List!",
										Toast.LENGTH_SHORT).show();
								isOrdersEnd = true;
							}
							orders = MyApplication.joinJSONArray(orders,
									response.getJSONArray("orderData"));
							ordersAdatper.setData(orders);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						ordersListView.onRefreshComplete();
						ordersAdatper.notifyDataSetChanged();

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

	private void getCollection(int currentPage) {
		JsonObjectRequest request = new JsonObjectRequest(
				Method.GET,
				String.format(
						"http://110.84.129.130:8080/Yuf/collection/getCollection/%d/%s/%d",
						UserInfo.getInstance().userid,
						UserInfo.getInstance().sessionid, currentPage), null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						try {
							if (response.getInt("currentPageNum") >= response
									.getInt("maxPageNum")) {
								Toast.makeText(getActivity(), "End of List!",
										Toast.LENGTH_SHORT).show();
								isCollectionsEnd = true;
							}
							collections = MyApplication.joinJSONArray(
									collections,
									response.getJSONArray("collection"));
							collectionsAdatper.setData(collections);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						collectionListView.onRefreshComplete();
						collectionsAdatper.notifyDataSetChanged();

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

	private void getUserInfo() {
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				"http://110.84.129.130:8080/Yuf/user/getUser/"
						+ UserInfo.getInstance().getSessionid(), null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						Log.e("TAG", response.toString());

						UserInfo tmpInfo = UserInfo.getInstance();

						try {
							tmpInfo.setUserpic(Config.urlHead
									+ response.getString("useravatarurl"));
							tmpInfo.setLeveldiscout(response
									.getDouble("leveldiscount"));
							tmpInfo.setUsername(response.getString("username"));
							tmpInfo.setUserfollows(response
									.getInt("userfollows"));
							tmpInfo.setUserfans(response.getInt("userfans"));
							tmpInfo.setUserpoints(response.getInt("userpoints"));
							tmpInfo.setUseraccount(response
									.getString("useraccount"));
							tmpInfo.setLevelname(response
									.getString("levelname"));
							tmpInfo.setLevelpoints(response
									.getInt("levelpoints"));
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

	class MyListAdapter extends BaseAdapter {
		private JSONArray dataArray;
		private ItemType type;

		MyListAdapter(JSONArray _dataArray, ItemType _type) {
			dataArray = _dataArray;
			type = _type;
		}

		public void setData(JSONArray _dataArray) {
			dataArray = _dataArray;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dataArray.length();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			try {
				return dataArray.get(position);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.tab3_mywork_mycollection_item, null);

			}
			switch (type) {
			case WORK:
				NetworkImageView workImage = (NetworkImageView) convertView
						.findViewById(R.id.tab3_mywork_item_img);
				TextView workTitle = (TextView) convertView
						.findViewById(R.id.tab3_mywork_item_name);
				TextView workTime = (TextView) convertView
						.findViewById(R.id.tab3_mywork_item_time);
				TextView workComment = (TextView) convertView
						.findViewById(R.id.tab3_mywork_item_commentnum);
				try {
					workImage.setImageUrl(
							"http://110.84.129.130:8080/Yuf"
									+ works.getJSONObject(position).getString(
											"postpicurl"),
							MyApplication.mImageLoader);
					workTitle.setText(works.getJSONObject(position).getString(
							"posttitle"));
					long time = works.getJSONObject(position).getLong(
							"posttime");
					workTime.setText(TimeHelper.getTimeStringFromLong(time));
					workComment.setText(String.format("评论（%d）", works
							.getJSONObject(position).getInt("postcommentnum")));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// TODO Auto-generated method stub
				break;
			case COLLECTION:
				NetworkImageView collectionImage = (NetworkImageView) convertView
						.findViewById(R.id.tab3_mywork_item_img);
				TextView collectionName = (TextView) convertView
						.findViewById(R.id.tab3_mywork_item_name);
				TextView collectionTime = (TextView) convertView
						.findViewById(R.id.tab3_mywork_item_time);
				try {
					collectionImage.setImageUrl(
							Config.urlHead
									+ collections.getJSONObject(position)
											.getString("dishpicurl"),
							MyApplication.mImageLoader);
					collectionName.setText(collections.getJSONObject(position)
							.getString("dishname"));
					collectionTime.setText("");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case ORDER:

				try {
					JSONObject jsonObject = orders.getJSONObject(position);
					TextView nameOfOrder = (TextView) convertView
							.findViewById(R.id.tab2_bought_item_name);
					nameOfOrder.setText(jsonObject.getString("dishname"));
					TextView priceOfOrder = (TextView) convertView
							.findViewById(R.id.tab2_bought_item_price);
					priceOfOrder.setText(jsonObject.getString("orderprice"));
					NetworkImageView orderImageView = (NetworkImageView) convertView
							.findViewById(R.id.tab2_bought_item_img);
					orderImageView.setDefaultImageResId(R.drawable.meat);
					orderImageView.setImageUrl(String.format(
							"http://110.84.129.130:8080/Yuf%s",
							jsonObject.getString("dishpicurl")),
							MyApplication.mImageLoader);
					TextView statusOfOrder = (TextView) convertView
							.findViewById(R.id.status);
					statusOfOrder.setText(jsonObject.getString("orderstatus"));
					TextView timeTextView = (TextView) convertView
							.findViewById(R.id.tab2_bought_item_time);
					timeTextView.setText(TimeHelper
							.getTimeStringFromLong(jsonObject
									.getLong("ordertime")));
					TextView amountTextView = (TextView) convertView
							.findViewById(R.id.tab2_bought_item_amount);
					amountTextView.setText("份数："
							+ jsonObject.getString("orderamount"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			default:
				break;
			}
			return convertView;
		}

	}
}
// private void logout() {
//
// JSONObject logJsonObject=new JSONObject();
// try{
// logJsonObject.put("userId",UserInfo.getInstance().getUserid());
// logJsonObject.put("sessionId",UserInfo.getInstance().getSessionid());
// }catch (JSONException e) {
// e.printStackTrace();
// }
// JsonObjectRequest request=new JsonObjectRequest(Method.POST,
// "http://110.84.129.130:8080/Yuf/user/logout", logJsonObject, new
// Response.Listener<JSONObject>()
// {
//
// @Override
// public void onResponse(JSONObject response)
// {
// try {
// if(response.getInt("code")==0)
// {
// SharedPreferences sharepPreferences=getSharedPreferences("login",
// Context.MODE_PRIVATE);
// Editor editor = sharepPreferences.edit();//获取编辑器
// editor.putBoolean("isLogined", false);
// editor.commit();
//
// finish();
// Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
// startActivity(intent);
//
//
// }
// else {
// Toast toast = Toast.makeText(getApplicationContext(),"登出失败",
// Toast.LENGTH_SHORT);
// toast.setGravity(Gravity.CENTER, 0, 0);
// toast.show();
// }
// } catch (JSONException e) {
// e.printStackTrace();
// }
//
//
// }
// }, new Response.ErrorListener()
// {
//
// @Override
// public void onErrorResponse(VolleyError error)
// {
// Log.e("Main-logout()", error.getMessage(), error);
// }
// });
//
// //将JsonObjectRequest 加入RequestQuene
// MyApplication.requestQueue.add(request);
// Log.d("Main-logout()","request start");
// MyApplication.requestQueue.start();
//
//
//
// }
