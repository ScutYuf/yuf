package com.yuf.app.ui;



import java.util.ArrayList;

import org.json.JSONException;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yuf.app.MyApplication;
import com.yuf.app.db.Order;
import com.yuf.app.http.extend.BitmapCache;

public class Tab2WaitForPayActivity extends Activity {
	private ImageView backImageView;
	private Button okButton;
	private PullToRefreshListView listView; 
	private ArrayList<Order> orderList;  
    private MyListAdapter mAdapter;  
	private ImageLoader mImageLoader;
	private String TAG="tab2waitforpay";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageLoader = new ImageLoader(MyApplication.requestQueue, new BitmapCache());
		orderList=Order.readFromDb(); 
		mAdapter = new MyListAdapter();
		setContentView(R.layout.tab2_wait_pay);
		listView=(PullToRefreshListView)findViewById(R.id.tab2_waitforpay_listView);
		listView.setMode(Mode.PULL_FROM_END);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				 String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),  
	                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);  
				 // Update the LastUpdatedLabel  
	             refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);  
				// Do work to refresh the list here.
	             refreshListView();   
	         }
});
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
			
				Log.d(TAG, "onLastItemVisible");
					if (orderList.size()<Order.numberOfOrders()) {
						loadingNextPage();
					}
					
			}	});
		listView.setAdapter(mAdapter);
		backImageView=(ImageView)findViewById(R.id.tab2_waitforpay_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				// TODO Auto-generated method stub
				Order.positionOfStart = -1;
				Tab2WaitForPayActivity.this.finish();
			}
		});
		okButton=(Button)findViewById(R.id.tab2_waitforpay_ok_button1);
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(Tab2WaitForPayActivity.this,Tab2AddressActivity.class);
				startActivity(intent);
				Order.positionOfStart = -1;
//				Tab2WaitForPayActivity.this.finish();
			}
		});
	}
	private void refreshListView() {
		Log.d(TAG, "Refresh!");
		if(orderList.size()<Order.numberOfOrders())
		{loadingNextPage();}
		else {
			Toast.makeText(Tab2WaitForPayActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
			DataBaseTask task=new DataBaseTask();
			task.execute();
			listView.setMode(Mode.DISABLED);
		}
	}
   private void loadingNextPage() {
		Log.d(TAG, "loadnextpage");
		ArrayList<Order> list0 = orderList;
		ArrayList<Order> list1 = Order.readFromDb();
		for(int i=0;i<list1.size();i++){
			list0.add(list1.get(i));
		}
		orderList=list0;
		DataBaseTask task=new DataBaseTask();
		task.execute();
	}
	private class MyListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return orderList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return orderList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.d("mywork", "get");
			if (convertView==null) {
				convertView=Tab2WaitForPayActivity.this.getLayoutInflater().inflate(R.layout.tab2_waitforpay_item,null);
			}
			Order order = orderList.get(position);
			NetworkImageView imageOrder = (NetworkImageView)convertView.findViewById(R.id.tab2_waitforpay_item_img);
			imageOrder.setDefaultImageResId(R.drawable.no_pic);
			imageOrder.setImageUrl("http://110.84.129.130:8080/Yuf"+order.orderImage, mImageLoader);
			TextView timeOfOrder=(TextView)convertView.findViewById(R.id.tab2_waitforpay_item_time);
			timeOfOrder.setText(order.orderTime);
			
			TextView nameOfOrder=(TextView)convertView.findViewById(R.id.tab2_waitforpay_item_name);
			nameOfOrder.setText(order.orderName);
			TextView priceOfOrder=(TextView)convertView.findViewById(R.id.tab2_waitforpay_item_price);
			priceOfOrder.setText(String.valueOf( order.orderPrice)+"元");
			
			final int index=position;
			ImageView detailImageView=(ImageView)convertView.findViewById(R.id.tab2_waitforpay_item_detail);
			detailImageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(Main.mainActivity,
							Tab0FoodActivity.class);
					Bundle bundle = new Bundle();                           //创建Bundle对象   
						bundle.putString("dishid",String.valueOf( orderList.get(index).dishId));
						bundle.putString("dishname",orderList.get(index).orderName);
						bundle.putBoolean("isSeeJust",true);
						intent.putExtras(bundle);                                //把Bundle塞入Intent里面  
					startActivity(intent);
					// TODO Auto-generated method stub
				}
			});
			Button deletBtn=(Button)convertView.findViewById(R.id.tab2_waitforpay_item_delete_btn);
			deletBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					deleteOrder(index);
				
				}
			});
			return convertView;
		}
		
	}
	
	private class DataBaseTask extends AsyncTask<integer, integer, integer>
	{

		@Override
		protected integer doInBackground(integer... params) {
		return null;
		}

		@Override
		protected void onPostExecute(integer result) {
			super.onPostExecute(result);
			listView.onRefreshComplete();
			Log.d(TAG, "onRefreshComplete");
			mAdapter.notifyDataSetChanged();
		}

		
	}
	private void deleteOrder(int i)
	{
		Order.deleteFromDb(orderList.get(i)._id);
		orderList.remove(i);
		mAdapter.notifyDataSetChanged();
	}
	
}
