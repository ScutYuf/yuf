package com.yuf.app.ui;

import java.util.ArrayList;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
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
	private ArrayList<Address> addressList;
	private String TAG="Tab2AddressActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//初始化数据
		addressList = Address.readFromDb();
		mAdapter=new MyListAdapter();
		setContentView(R.layout.tab2_address_list);
		backImageView=(ImageView)findViewById(R.id.tab2_address_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				Address.positionOfStartAddress = -1;
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
				Address.positionOfStartAddress = -1;
				Tab2AddressActivity.this.finish();
			}
		});
		
		
		addressListView=(PullToRefreshListView)findViewById(R.id.tab2_address_list);
		addressListView.setMode(Mode.PULL_FROM_END);
		addressListView.setAdapter(mAdapter);
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
				Log.d(TAG, "onLastItemVisible");
				if (addressList.size()<Address.numberOfAddress()) {
					loadingNextPage();
				}		
			}	});
    }
	protected void refreshListView() {
		Log.d(TAG, "Refresh!");
		if(addressList.size()<Address.numberOfAddress())
		{loadingNextPage();}
		else {
			Toast.makeText(Tab2AddressActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
			DataBaseTask task=new DataBaseTask();
			task.execute();
		}
	}
	protected void loadingNextPage() {
		Log.d(TAG, "loadnextpage");
		ArrayList<Address> list0 = addressList;
		ArrayList<Address> list1 = Address.readFromDb();
		for(int i=0;i<list1.size();i++){
			list0.add(list1.get(i));
		}
		addressList=list0;
		DataBaseTask task=new DataBaseTask();
		task.execute();
	}
	
	private class MyListAdapter extends BaseAdapter
	{
		@Override
		public int getCount() {
			return addressList.size();
		}

		@Override
		public Object getItem(int position) {
			return addressList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView==null) {
				convertView=Tab2AddressActivity.this.getLayoutInflater().inflate(R.layout.tab2_address_list_item,null);
				
			}
			Address address = addressList.get(position);
			RadioButton defaultButton=(RadioButton)convertView.findViewById(R.id.tab2_address_list_item_setdefault_radiobutton);
			switch (address.isDefault) {
			case 0:defaultButton.setChecked(false);break;
			case 1:defaultButton.setChecked(true);break;
			default:defaultButton.setChecked(false);break;
			}
			TextView deleteTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_delete_textview);
			deleteTextView.setText("删除");
			TextView ediTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_edit_textview);
			ediTextView.setText("编辑");
			TextView nameTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_name_textview);
			nameTextView.setText(address.nameString);
			TextView zonetTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_zone_textview);
			zonetTextView.setText(address.zoneString);
			TextView detailTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_detailaddress_textview);
			detailTextView.setText(address.detailString);
			TextView phoneTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_phone_textview);
			phoneTextView.setText(address.phoneString);
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
			addressListView.onRefreshComplete();
			Log.d(TAG, "onRefreshComplete");
			mAdapter.notifyDataSetChanged();
		}
	}
}
