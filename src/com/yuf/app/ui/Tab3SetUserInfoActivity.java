package com.yuf.app.ui;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yuf.app.MyApplication;
import com.yuf.app.http.extend.BitmapCache;

import android.R.mipmap;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Tab3SetUserInfoActivity extends Activity {
private ImageView backImageView;
private NetworkImageView profileImageView;
private ImageLoader mImageLoader;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//
		super.onCreate(savedInstanceState);
		mImageLoader=new ImageLoader(MyApplication.requestQueue, new BitmapCache());
		setContentView(R.layout.tab3_setuserinfo_activity);
		profileImageView=(NetworkImageView)findViewById(R.id.tab3_setuserinfo_profile);
		profileImageView.setDefaultImageResId(R.drawable.no_pic);
		profileImageView.setErrorImageResId(R.drawable.no_pic);
		profileImageView.setImageUrl("http://www.baidu.com", mImageLoader);
		backImageView=(ImageView)findViewById(R.id.tab3_setUserInfo_back_imageview);
		backImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
				// TODO Auto-generated method stub
				
			}
		});
	}
	

}
