package com.yuf.app.ui;

import java.util.ArrayList;

import org.json.JSONException;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yuf.app.db.Order;

public class Tab2BougthActivity extends Activity {
	private ImageView backImageView;
	
	private PullToRefreshListView listView;
	private MyListAdapter mAdapter;
	private ArrayList<Order> ordersList;
	private Boolean isEnd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		isEnd=false;
		mAdapter=new MyListAdapter();
		setContentView(R.layout.tab2_bought);
		
		listView=(PullToRefreshListView)findViewById(R.id.tab2_bougth_listview);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				
			}
			
		});
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				// TODO Auto-generated method stub
				
			}
		});
			
				
				
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(Main.mainActivity,
				Tab0FoodActivity.class);
				Bundle bundle = new Bundle();                           //创建Bundle对象   
				bundle.putString("dishid",String.valueOf( ordersList.get(position-1).dishId));
				bundle.putString("dishname",ordersList.get(position-1).orderName);
				bundle.putBoolean("isSeeJust",true);
				intent.putExtras(bundle);                                //把Bundle塞入Intent里面  
				startActivity(intent);
				// TODO Auto-generated method stub
				
			}
		});
		
		
		backImageView=(ImageView)findViewById(R.id.tab2_bougth_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	class MyListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ordersList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return ordersList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView==null) {
				convertView=Tab2BougthActivity.this.getLayoutInflater().inflate(R.layout.tab2_bougth_item,null);
				
			}
			//设置已购买项信息
			return convertView;
		}
		
	}
	
	class GetDataAsycTask extends AsyncTask<Integer, Integer, Integer>
	{

		@Override
		protected Integer doInBackground(Integer... params) {
			//从数据库中加载下一页
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
		}
		
		
	}
}
