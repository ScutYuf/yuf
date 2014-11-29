package com.yuf.app.ui;

import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuf.app.db.Address;
import com.yuf.app.db.Order;
import com.yuf.app.mywidget.NoScrollListview;

public class Tab2Fragment extends Fragment{

	private NoScrollListview cartListView;
	private MyTab2Adapter mAdapter; 
	private ArrayList<Order> orderList; 
	
	private TextView totalPrice;
	private double price = 0;
	private TextView person,phone,address;
//修改收货地址	
	private Button changeButton;
	private ArrayList<Address> addressList;
	
	private String TAG="Tab2Fragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view=(View)inflater.inflate(R.layout.main_tab_2_v, null);
		initMember(view);
		refresh();
		return view;
	}

	private void initMember(View view) {
		totalPrice = (TextView)view.findViewById(R.id.textView1);
		
		person = (TextView)view.findViewById(R.id.person);
		phone = (TextView)view.findViewById(R.id.phone);
		address = (TextView)view.findViewById(R.id.address);
		showAddress();
		
		cartListView=(NoScrollListview)view.findViewById(R.id.cart_listview);
		changeButton = (Button)view.findViewById(R.id.changeButton);
		changeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), Tab2AddressActivity.class);
				startActivityForResult(intent,1);
			}
		});
	}


	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		showAddress();
	}

	private void showAddress() {
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences("address", Activity.MODE_PRIVATE);
		
		person.setText(sharedPreferences.getString("nameString", ""));
		phone .setText(sharedPreferences.getString("phoneString", ""));
		address.setText(sharedPreferences.getString("detailString", ""));
	}


	private class MyTab2Adapter extends BaseAdapter{

		@Override
		public int getCount() {
			return orderList.size();
		}

		@Override
		public Object getItem(int position) {
			
			return orderList.get(position);
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup viewGroup) {
			final int pos = position;
			final Order order = orderList.get(position);
			if(convertView==null){
				convertView = Tab2Fragment.this.getActivity().getLayoutInflater().inflate(R.layout.tab2_listview_item, null);
			}
			
			TextView nameOfDish = (TextView)convertView.findViewById(R.id.name);
			TextView numberOfDishTextView = (TextView)convertView.findViewById(R.id.number);
			Button changeDish = (Button)convertView.findViewById(R.id.changeDish);
			
			nameOfDish.setText(order.orderName);
			numberOfDishTextView.setText(order.orderAmount + "份" + order.orderPrice);
			changeDish.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					final MyDialog myDialog = new MyDialog(getActivity(),R.style.OrderAmountDialog);
					myDialog.show();
					
					TextView nameTextView = (TextView)myDialog.getWindow().findViewById(R.id.name);					
					final TextView amount     = (TextView)myDialog.getWindow().findViewById(R.id.amount);
					Button okButton = (Button)myDialog.getWindow().findViewById(R.id.ok);
					ImageView small = (ImageView) myDialog.getWindow().findViewById(R.id.small); 
					ImageView bigButton    = (ImageView)myDialog.getWindow().findViewById(R.id.big);
					Button delete       = (Button)myDialog.getWindow().findViewById(R.id.delete);
					
					nameTextView.setText(order.orderName);
					amount.setText(order.orderAmount+"");
				//减少order	
					small.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            	if(order.orderAmount>0){
                            		v.setClickable(true);
                            		order.orderAmount--;
                            		order.modifyAmount(0);
                            		mAdapter.notifyDataSetChanged();
                            		amount.setText(order.orderAmount+"");
                            		}else{v.setClickable(false);}
                            }
                    });
				//增加order
					bigButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							order.orderAmount++;
							order.modifyAmount(1);
							mAdapter.notifyDataSetChanged();
							amount.setText(order.orderAmount+"");
						}
					});
				//删除order
					delete.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							Order.deleteFromDb(orderList.get(pos)._id);
							orderList.remove(pos);
							mAdapter.notifyDataSetChanged();
							myDialog.dismiss();
						}
					});
					okButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							myDialog.dismiss();
						}
					});
				}
			});
			return convertView;
		}
		
	}
	
	private class MyDialog extends Dialog{

		public MyDialog(Context context, int theme) {
			super(context,theme);
			
		}
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.tab2_order_amount_change);
		}
		
	}
	public void refresh(){
		
		orderList=Order.readFromDb(); 
		mAdapter = new MyTab2Adapter();
		cartListView.setAdapter(mAdapter);
		
		for(int i=0 ; i<orderList.size(); i++){
			price = orderList.get(i).orderPrice + price;
		}
		totalPrice.setText("订单总计：$ " + price);
	}
}
