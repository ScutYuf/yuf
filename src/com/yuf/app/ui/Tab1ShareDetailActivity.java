package com.yuf.app.ui;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.http.extend.BitmapCache;
import com.yuf.app.ui.R;

import android.R.integer;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class Tab1ShareDetailActivity extends Activity {

	private TextView titleTextView;
	private TextView nameTextView;
	private TextView commentBtnTextView;
	private TextView shareBtnTextView;
	private NetworkImageView foodNeworkImageView;
	private TextView shareContentTextView;
	private ImageView addFocuseImageView;
	private ImageView backImageView;
	private float start_y=0;
	private boolean isDisappear=false;
	private RelativeLayout relativeLayout;
	private String shareid;
	/**
	 * 菜谱信息
	 * “dishdifficulty”: float,
		“dishcollectionnum”: int,
		“dishid”: int,
		“dishcommentnum”: int,
		“dishname”: String,
		“dishpicurl”: String,
		“dishamount”: int,
		“dishcooktime”: String,
		“dishprice”: double,
		“dishcookmethod”: String

	 */
	private JSONArray dishCommentInfoArray;
	private int currentPage=0;
	private int postId;
	private int userid;
	private String posttitle;
	private String postpicurl;
	private String postcontent;
	private ImageLoader mImageLoader;
	private ScrollView mScrollView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mImageLoader=new ImageLoader(MyApplication.requestQueue, new BitmapCache());
		dishCommentInfoArray=new JSONArray();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1_share_detail_activity);
		
		
		mScrollView=(ScrollView)findViewById(R.id.tab1_share_detail_scrollView);
		mScrollView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return changeTextViewHideOrAppear(event);
			}
		});
		
		
		backImageView=(ImageView)findViewById(R.id.tab1_share_detail_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();// TODO Auto-generated method stub
				
			}
		});
		titleTextView=(TextView)findViewById(R.id.tab1_share_detail_title_textview);
		
		nameTextView=(TextView) findViewById(R.id.tab1_share_detail_recipename_textview);
		foodNeworkImageView=(NetworkImageView)findViewById(R.id.tab0_share_detail_food_imageView);
		shareContentTextView=(TextView)findViewById(R.id.tab0_share_detail_sharecontent_textview);
		commentBtnTextView=(TextView)findViewById(R.id.tab0_share_detail_comment_textView);
		shareBtnTextView=(TextView)findViewById(R.id.tab0_share_detail_share_TextView);
		addFocuseImageView=(ImageView)findViewById(R.id.tab1_share_detail_addfocus_imageView);
		addFocuseImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addFocuseRelationShip();
				// TODO Auto-generated method stub
				
			}
		});
		relativeLayout=(RelativeLayout)findViewById(R.id.tab1_share_detail_relativeLayout);
		Intent intent=getIntent();
	Bundle bundle=intent.getExtras();
		postId=bundle.getInt("postid");
		userid=bundle.getInt("userid");
		
		posttitle=bundle.getString("posttitle");
		postpicurl=bundle.getString("postpicurl");
		postcontent=bundle.getString("postcontent");
		nameTextView.setText(posttitle);
		shareContentTextView.setText(postcontent);
		foodNeworkImageView.setDefaultImageResId(R.drawable.no_pic);
		foodNeworkImageView.setImageUrl("http://110.84.129.130:8080/Yuf"+postpicurl, mImageLoader);
//		
		
		
		getShareDetail();
	}
	protected void addFocuseRelationShip() {
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("userId", Integer.valueOf(UserInfo.getInstance().userid));
			jsonObject.put("sessionId", UserInfo.getInstance().sessionid);
			jsonObject.put("friendId", userid);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JsonObjectRequest request=new JsonObjectRequest(Method.POST, "http://110.84.129.130:8080/Yuf/relation/addRelation", jsonObject, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				try {
					if (response.getInt("code")==0) {
						Toast.makeText(Tab1ShareDetailActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
					}
					else {
						Toast.makeText(Tab1ShareDetailActivity.this,response.getString("msg"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
		// TODO Auto-generated method stub
		MyApplication.requestQueue.add(request);
		MyApplication.requestQueue.start();
	}
	private void getShareDetail()
	{
		JsonObjectRequest request;
			request = new JsonObjectRequest(Method.GET, String.format("http://110.84.129.130:8080/Yuf/dishcomment/getDishcomment/%d/%d",postId,++currentPage), null,  new Response.Listener<JSONObject>()  
			        {  

			            @Override  
			            public void onResponse(JSONObject response)  
			            {  
			        
								
								try {
									MyApplication.joinJSONArray(dishCommentInfoArray, response.getJSONArray("dishcomment"));
	
									if (response.getInt("currentPageNum")<response.getInt("maxpagenum")) {
										currentPage++;
										getShareDetail();
									}
									else {
										addComments();
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
	private void addComments(){
		titleTextView.setText(String.format("评论（%d）",dishCommentInfoArray.length()));
		for (int i = 0; i <dishCommentInfoArray.length(); i++) {
			
			LayoutInflater inflater=LayoutInflater.from(this);
			LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.tab1_share_detail_comment_item, null);
			linearLayout.setOrientation(LinearLayout.VERTICAL);
			TextView timeTextView=(TextView)linearLayout.findViewById(R.id.tab1_share_detail_item_time_textview);
			TextView nameTextView=(TextView)linearLayout.findViewById(R.id.tab1_share_detaile_item_username_textview);
			TextView contentTextView=(TextView)linearLayout.findViewById(R.id.tab1_share_detail_item_comment_content);
			try {
				JSONObject jsonObject=dishCommentInfoArray.getJSONObject(i);
				nameTextView.setText(jsonObject.getString("username"));
				long currentTime;
				currentTime = jsonObject.getLong("posttime");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(currentTime);
				timeTextView.setText(formatter.format(date));
				contentTextView.setText(jsonObject.getString("postcontent"));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			relativeLayout.addView(linearLayout);
		}

	}
	private void commentDish(String commentContent) {
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("userId", userid);
			jsonObject.put("dishId", postId);
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
						Toast toast=Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT);
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
	private void appearTextView() {

		// TODO Auto-generated method stub
	Animation animation= AnimationUtils.loadAnimation(this,R.anim.textview_in_from_left);
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
			Log.d("liow", "commenttextview before left:"+String.valueOf(commentBtnTextView.getLeft())+  "right:"+String.valueOf(commentBtnTextView.getRight()));
			
			
			commentBtnTextView.setClickable(true);
			Log.d("liow", "commenttextview left:"+String.valueOf(commentBtnTextView.getLeft())+  "right:"+String.valueOf(commentBtnTextView.getRight()));
			// TODO Auto-generated method stub
			
		}
	});
	
	Animation animation2= AnimationUtils.loadAnimation(this,R.anim.textview_in_from_right);
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
shareBtnTextView.setClickable(true);			
			// TODO Auto-generated method stub
			
		}
	});
	
	
	commentBtnTextView.startAnimation(animation);
	shareBtnTextView.startAnimation(animation2);
		// TODO Auto-generated method stub
		
	}
	private void disappearTextView() {
		Animation animation= AnimationUtils.loadAnimation(this,R.anim.textview_out_to_left);
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
				Log.d("liow", "commenttextview before left:"+String.valueOf(commentBtnTextView.getLeft())+  "right:"+String.valueOf(commentBtnTextView.getRight()));
				commentBtnTextView.setClickable(false);
				Log.d("liow", "commenttextview left:"+String.valueOf(commentBtnTextView.getLeft())+  "right:"+String.valueOf(commentBtnTextView.getRight()));
				// TODO Auto-generated method stub
				
			}
		});
		
		Animation animation2= AnimationUtils.loadAnimation(this,R.anim.textview_out_to_right);
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
	shareBtnTextView.setClickable(false);			
				// TODO Auto-generated method stub
				
			}
		});
		
		commentBtnTextView.startAnimation(animation);
		shareBtnTextView.startAnimation(animation2);

		
	}
	
}
