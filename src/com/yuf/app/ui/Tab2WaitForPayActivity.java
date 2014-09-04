package com.yuf.app.ui;



import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
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
	private boolean isEnd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mImageLoader = new ImageLoader(MyApplication.requestQueue, new BitmapCache());
		orderList=Order.readFromDb(); 
		mAdapter = new MyListAdapter();
		setContentView(R.layout.tab2_wait_pay);
		listView=(PullToRefreshListView)findViewById(R.id.tab2_waitforpay_listView);
		listView.setMode(Mode.BOTH);
		listView.setAdapter(mAdapter);
		
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
		backImageView=(ImageView)findViewById(R.id.tab2_waitforpay_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				// TODO Auto-generated method stub
				
			}
		});
		okButton=(Button)findViewById(R.id.tab2_waitforpay_ok_button1);
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(Tab2WaitForPayActivity.this,Tab2AddressActivity.class);
				startActivity(intent);
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void refreshListView() {
		isEnd=false;
		listView.setMode(Mode.BOTH);
		orderList.removeAll(orderList);
		loadingNextPage();
	}

	private void loadingNextPage() {
		//orderList = Order.readFromDb();
		
		listView.onRefreshComplete();
		mAdapter.notifyDataSetChanged();
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
			NetworkImageView imageOrder = (NetworkImageView)convertView.findViewById(R.id.tab3_mywork_item_img);
			//imageOrder.setImageUrl(order.orderImage, mImageLoader);
			TextView nameOfOrder=(TextView)convertView.findViewById(R.id.tab3_mywork_item_name);
			nameOfOrder.setText(order.orderName);
			TextView payOfOrder=(TextView)convertView.findViewById(R.id.TextView01);
			payOfOrder.setText(order.orderAmount+"");
			return convertView;
		}
		
	}
}
