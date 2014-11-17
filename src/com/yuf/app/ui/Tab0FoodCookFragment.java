package com.yuf.app.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuf.app.Entity.UserInfo;
import com.yuf.app.db.Order;
@SuppressLint("ValidFragment")
public class Tab0FoodCookFragment extends Fragment{
	private TextView comment;
	 private TextView showComment;
	 private TextView collection;
	 private TextView share;
	 private TextView addcart;
	private float start_y=0;
	private boolean isDisappear=false;
	private JSONArray stepJsonObject;

	private JSONObject dishInfoJsonObject;
	private  AlertDialog dlg;
	
	
	public Tab0FoodCookFragment(JSONArray _mStepJsonObject,JSONObject _dishInfoJsonObject,String dishId) {
		stepJsonObject=_mStepJsonObject;
		dishInfoJsonObject=_dishInfoJsonObject;
	}
	
	
	@SuppressLint("NewApi") @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.tab0_food_cook,container,false);
		
		showComment=(TextView)view.findViewById(R.id.show_comment_textView);
		showComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(), CommentListAcitvity.class);
				Bundle bundle=new Bundle();
				try {
					bundle.putInt("dishid", dishInfoJsonObject.getInt("dishid"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				intent.putExtras(bundle);
				startActivity(intent);
				
				
			}
		});
	
//加入购物车
		addcart=(TextView)view.findViewById(R.id.add_cart_textview);
		addcart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				addOrder();
				// TODO Auto-generated method stub
				
			}
		});
		
	
		return  view;
	}

protected void addOrder() {
	try {
		
	
        	Order order=new Order();
			order.dishId=dishInfoJsonObject.getInt("dishid");
			order.orderAmount=1;
			order.orderImage=dishInfoJsonObject.getString("dishpicurl");
			order.orderName=dishInfoJsonObject.getString("dishname");
			order.orderPaymethod="货到付款";
			order.orderPrice=dishInfoJsonObject.getDouble("dishprice");
			order.orderTime=timeString();
			order.userId=Integer.valueOf(UserInfo.getInstance().userid);
			order.writeToDb();
			Toast.makeText(getActivity(), "加入购物车成功", Toast.LENGTH_SHORT).show();
        
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


protected void appearTextView() {
		// TODO Auto-generated method stub
	Animation animation= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_in_from_left);
	animation.setFillAfter(true);
	animation.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
			Log.d("liow", "commenttextview before left:"+String.valueOf(comment.getLeft())+  "right:"+String.valueOf(comment.getRight()));
			
			
			comment.setClickable(true);
			Log.d("liow", "commenttextview left:"+String.valueOf(comment.getLeft())+  "right:"+String.valueOf(comment.getRight()));
			// TODO Auto-generated method stub
			
		}
	});
	
	Animation animation2= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_in_from_left);
animation2.setFillAfter(true);
	animation2.setAnimationListener(new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
showComment.setClickable(true);			
			// TODO Auto-generated method stub
			
		}
	});
	Animation animation3= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_in_from_right);
	animation3.setFillAfter(true);
	animation3.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
collection.setClickable(true);			
			// TODO Auto-generated method stub
			
		}
	});
	
	Animation animation4= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_in_from_right);
	animation4.setFillAfter(true);
	animation4.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
share.setClickable(true);			
			// TODO Auto-generated method stub
			
		}
	});
	
	Animation animation5= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_in_from_right);
	animation5.setFillAfter(true);
	animation5.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
addcart.setClickable(true);			
			// TODO Auto-generated method stub
			
		}
	});
	
	
	comment.startAnimation(animation);
	showComment.startAnimation(animation2);
	collection.startAnimation(animation3);
	share.startAnimation(animation4);
	addcart.startAnimation(animation5);
	}


private void disappearTextView() {
	Animation animation= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_out_to_left);
	animation.setFillAfter(true);
	animation.setAnimationListener(new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
			Log.d("liow", "commenttextview before left:"+String.valueOf(comment.getLeft())+  "right:"+String.valueOf(comment.getRight()));
			comment.setClickable(false);
			Log.d("liow", "commenttextview left:"+String.valueOf(comment.getLeft())+  "right:"+String.valueOf(comment.getRight()));
			// TODO Auto-generated method stub
			
		}
	});
	
	Animation animation2= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_out_to_left);
	animation2.setFillAfter(true);
	animation2.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
showComment.setClickable(false);			
			// TODO Auto-generated method stub
			
		}
	});
	Animation animation3= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_out_to_right);
	animation3.setFillAfter(true);
	animation3.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
collection.setClickable(false);			
			// TODO Auto-generated method stub
			
		}
	});
	
	Animation animation4= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_out_to_right);
	animation4.setFillAfter(true);
	animation4.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
			share.setClickable(false);			
			// TODO Auto-generated method stub
			
		}
	});
	Animation animation5= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_out_to_right);
	animation5.setFillAfter(true);
	animation5.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
			addcart.setClickable(false);			
			// TODO Auto-generated method stub
			
		}
	});
	
	comment.startAnimation(animation);
	showComment.startAnimation(animation2);
	collection.startAnimation(animation3);
	share.startAnimation(animation4);
	addcart.startAnimation(animation5);

	
}
   
private boolean changeTextViewHideOrAppear(MotionEvent event) {

	// TODO Auto-generated method stub
	if (event.getAction()==MotionEvent.ACTION_DOWN) {
		start_y=event.getY();
	
	}
	
if (event.getAction()==MotionEvent.ACTION_MOVE&&start_y!=0) {
	
	if(event.getY()-start_y<0&&!isDisappear)
		{
		
		disappearTextView();
		Log.d("liow", "disappeartextview");
		isDisappear=true;
		return false;
		}
	
	if(event.getY()-start_y>0&&isDisappear)
		{
			appearTextView();
			Log.d("liow", "appeartextview");
			isDisappear=false;
		}
}
return false;
}



private String timeString() {
	// TODO Auto-generated method stub
	long currentTime = System.currentTimeMillis();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = new Date(currentTime);
	return formatter.format(date);
}
}
