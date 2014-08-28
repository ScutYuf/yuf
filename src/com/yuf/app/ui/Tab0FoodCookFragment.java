package com.yuf.app.ui;

import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
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
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.http.extend.BitmapCache;
public class Tab0FoodCookFragment extends Fragment{

	
	private TextView comment;
	 private TextView showComment;
	 private TextView collection;
	 private TextView share;
	 private TextView addcart;
	private ScrollView mScrollView; 
	private LinearLayout mLinearLayout;
	private float start_y=0;
	private boolean isDisappear=false;
	private JSONArray stepJsonObject;
	private JSONObject dishInfoJsonObject;
	private ImageLoader mImageLoader;
	private  AlertDialog dlg;
	private TextView hardLevelTextview;
	private TextView doseTextView;
	private TextView skillTextView;
	private TextView timeTextView;
	private NetworkImageView foodImageView;
	
	public Tab0FoodCookFragment(JSONArray _mStepJsonObject,JSONObject _dishInfoJsonObject) {
		stepJsonObject=_mStepJsonObject;
		dishInfoJsonObject=_dishInfoJsonObject;
		// TODO Auto-generated constructor stub
	}
	
	
	@SuppressLint("NewApi") @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mImageLoader=new ImageLoader(MyApplication.requestQueue, new BitmapCache());	
		
		View view=inflater.inflate(R.layout.tab0_food_cook,container,false);
		
		foodImageView=(NetworkImageView)view.findViewById(R.id.tab0_cookfood_foodimage);
		
		hardLevelTextview=(TextView)view.findViewById(R.id.tab0_cookfood__dishdifficulty_textview);
		doseTextView=(TextView)view.findViewById(R.id.tab0_cookfood_dose_textview);
		skillTextView=(TextView)view.findViewById(R.id.tab0_cookfood_skill_textview);
		timeTextView=(TextView)view.findViewById(R.id.tab0_cookfood_time_textview);
		
		comment=(TextView)view.findViewById(R.id.comment_textView);
		comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialg();
				// TODO Auto-generated method stub
				
			}
		});
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
		collection=(TextView)view.findViewById(R.id.collection_textView);
		share=(TextView)view.findViewById(R.id.share_TextView);
		addcart=(TextView)view.findViewById(R.id.add_cart_textview);
		mLinearLayout=(LinearLayout)view.findViewById(R.id.scrolllinelayout);
		mScrollView=(ScrollView)view.findViewById(R.id.cook_scrollView);
		mScrollView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return changeTextViewHideOrAppear(event);
			}
		});
		initFoodInfo();
		addSteps();
		return  view;
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
private void initFoodInfo()
{
	try {
		hardLevelTextview.setText(dishInfoJsonObject.getString("dishdifficulty"));
		doseTextView.setText(dishInfoJsonObject.getString("dishamount"));
		skillTextView.setText(dishInfoJsonObject.getString("dishcookmethod"));
		timeTextView.setText(dishInfoJsonObject.getString("dishcooktime"));
		foodImageView.setImageUrl("http://110.84.129.130:8080/Yuf"+dishInfoJsonObject.getString("dishpicurl"), mImageLoader);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
private void addSteps(){
	for (int i = 0; i <stepJsonObject.length(); i++) {
		
		LayoutInflater inflater=LayoutInflater.from(getActivity());
		LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.tab0_fook_cook_step, null);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		TextView stepOrderTextView=(TextView)linearLayout.findViewById(R.id.tab0_food_cook_step_steporder_textview);
		NetworkImageView imageView=(NetworkImageView)linearLayout.findViewById(R.id.tab0_food_cook_step_image_imageview);
		TextView introduceTextView=(TextView)linearLayout.findViewById(R.id.tab0_food_cook_step_introuduce_textview);
		JSONObject mJsonObject;
		try {
			mJsonObject = stepJsonObject.getJSONObject(i);
			stepOrderTextView.setText(String.valueOf(mJsonObject.getInt("recipeorder")));
//			imageView.setImageUrl("http://110.84.129.130:8080/Yuf"+mJsonObject.getString("recipepicurl"), mImageLoader);
//			imageView.setImageUrl("http://110.84.129.130:8080//Yuf/images/dish/8.jpg", mImageLoader);
			introduceTextView.setText(mJsonObject.getString("recipedetail"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		mLinearLayout.addView(linearLayout);
	}

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


protected void showDialg() {

	//显示评论对话框
	LayoutInflater factory = LayoutInflater.from(getActivity());
	final View textEntryView = factory.inflate(R.layout.dialog, null);
	final EditText editText=(EditText)textEntryView.findViewById(R.id.comment_comment_editText);
	Button commentButton =(Button)textEntryView.findViewById(R.id.comment_dialog_comment_button);
	commentButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			commentDish(editText.getText().toString());
			dlg.dismiss();
		}
	});
	Button cancleButton=(Button)textEntryView.findViewById(R.id.comment_dialog_cancle_buttoon);
   cancleButton.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		dlg.dismiss();
	}
});
	dlg = new AlertDialog.Builder(getActivity())
    .setView(textEntryView)
    .create();
    dlg.show();
	
	
	// TODO Auto-generated method stub
	
// TODO Auto-generated method stub
	
}


private void commentDish(String commentContent) {
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("userId",Integer.valueOf(UserInfo.getInstance().getUserid()));
			jsonObject.put("dishId", dishInfoJsonObject.getInt("dishid"));
			jsonObject.put("dishcommentContent", commentContent);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JsonObjectRequest request=new JsonObjectRequest(Method.POST,"http://110.84.129.130:8080/Yuf/dishcomment/postDishcomment/", jsonObject, new Response.Listener<JSONObject>()  
		        {  

            @Override  
            public void onResponse(JSONObject response)  
            {
            	try {
					if (response.getInt("code")==0) {
						Toast toast=Toast.makeText(getActivity().getApplicationContext(), "评论成功", Toast.LENGTH_SHORT);
						toast.show();
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }}, new Response.ErrorListener()  
        {  

            @Override  
            public void onErrorResponse(VolleyError error)  
            {  
                Log.e("TAG", error.getMessage(), error);  
            }  
        });


	//将JsonObjectRequest 加入RequestQuene
MyApplication.requestQueue.add(request);
Log.d("liow","request start");
MyApplication.requestQueue.start();
	}


}
