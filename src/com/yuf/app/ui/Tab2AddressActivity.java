package com.yuf.app.ui;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.yuf.app.db.Address;

public class Tab2AddressActivity extends Activity {
	private ImageView backImageView;
	private ImageView addImageView;
	private PullToRefreshListView addressListView;
	private MyListAdapter mAdapter;
	private Boolean isEnd;
	private ArrayList<Address> addressList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//初始化数据
		
		mAdapter=new MyListAdapter();
		isEnd=false;
		
		addressList=new ArrayList<Address>();
		
		
		
		
		
		setContentView(R.layout.tab2_address_list);
		backImageView=(ImageView)findViewById(R.id.tab2_address_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				// TODO Auto-generated method stub
				
			}
		});
		addImageView=(ImageView)findViewById(R.id.tab2_address_list_add_imageview);
		addImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Tab2AddressActivity.this,Tab2AddressEditActivity.class);
				Bundle bundle=new Bundle();
				
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		
		addressListView=(PullToRefreshListView)findViewById(R.id.tab2_address_list);
		addressListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(Tab2AddressActivity.this.getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				refreshListView();
			
			}

			
		});
		addressListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
			
					if (!isEnd) {
						
						loadingNextPage();
					}
					
			}	});

		addressListView.setMode(Mode.BOTH);
		addressListView.setAdapter(mAdapter);
		
	}
	protected void loadingNextPage() {
		// TODO Auto-generated method stub
		
	}
	protected void refreshListView() {
		// TODO Auto-generated method stub
		
	}
	class MyListAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return addressList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return addressList.get(position);
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
				convertView=Tab2AddressActivity.this.getLayoutInflater().inflate(R.layout.tab2_address_list_item,null);
				
			}
			RadioButton  defaultButton=(RadioButton)convertView.findViewById(R.id.tab2_address_list_item_setdefault_radiobutton);
			TextView deleteTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_delete_textview);
			TextView ediTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_edit_textview);
			
			TextView nameTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_name_textview);
			TextView zonetTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_zone_textview);
			TextView detailTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_detailaddress_textview);
			TextView phoneTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_phone_textview);
			
			return convertView;
		}
		
	}
}
