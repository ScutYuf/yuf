package com.yuf.app.ui;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.yuf.app.MyApplication;
import com.yuf.app.http.extend.BitmapCache;
import com.yuf.app.ui.R;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Tab1ShareDetailActivity extends Activity {

	private TextView nameTextView;
	private TextView commentBtnTextView;
	private TextView collectionBtnTextView;
	private TextView shareBtnTextView;
	private NetworkImageView foodNeworkImageView;
	private TextView shareContentTextView;
	private ImageView addFocuseImageView;
	private ImageView backImageView;
	
	private RelativeLayout relativeLayout;
	private String shareid;
	private JSONObject dishInfoJsonObject;
	private JSONArray dishCommentInfoArray;
	private int currentPage=0;
	private int postId;
	private int userid;
	private String posttitle;
	private String postpicurl;
	private String postcontent;
	private ImageLoader mImageLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mImageLoader=new ImageLoader(MyApplication.requestQueue, new BitmapCache());
		dishCommentInfoArray=new JSONArray();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1_share_detail_activity);
		backImageView=(ImageView)findViewById(R.id.tab1_share_detail_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();// TODO Auto-generated method stub
				
			}
		});
		nameTextView=(TextView) findViewById(R.id.tab1_share_detail_recipename_textview);
		foodNeworkImageView=(NetworkImageView)findViewById(R.id.tab0_share_detail_food_imageView);
		shareContentTextView=(TextView)findViewById(R.id.tab0_share_detail_sharecontent_textview);
		commentBtnTextView=(TextView)findViewById(R.id.tab0_share_detail_comment_textView);
		collectionBtnTextView=(TextView)findViewById(R.id.tab0_share_detail_collection_textView);
		shareBtnTextView=(TextView)findViewById(R.id.tab0_share_detail_share_TextView);
		addFocuseImageView=(ImageView)findViewById(R.id.tab1_share_detail_addfocus_imageView);
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
		foodNeworkImageView.setImageUrl("http://110.84.129.130:8080/Yuf/images/post/"+postpicurl, mImageLoader);
		
		
		getShareDetail();
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
}
