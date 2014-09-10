package com.yuf.app.ui;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yuf.app.MyApplication;
import com.yuf.app.http.extend.BitmapCache;

import android.R.mipmap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Tab3SetUserInfoActivity extends Activity {
private ImageView backImageView;
private NetworkImageView profileImageView;
private ImageLoader mImageLoader;
private AlertDialog dlg;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//
		//
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
	public void onClickProfile(View view)
	{
		
	}
	public void onClickNickName(View view){
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.dialog, null);
		final EditText editText=(EditText)textEntryView.findViewById(R.id.comment_comment_editText);
		Button commentButton =(Button)textEntryView.findViewById(R.id.comment_dialog_comment_button);
		commentButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeNickName(editText.getText().toString());
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
		dlg = new AlertDialog.Builder(this)
        .setView(textEntryView)
        .create();
        dlg.show();
		
	}
	public void onClickPhone(View view){
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.dialog, null);
		final EditText editText=(EditText)textEntryView.findViewById(R.id.comment_comment_editText);
		Button commentButton =(Button)textEntryView.findViewById(R.id.comment_dialog_comment_button);
		commentButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changePhone(editText.getText().toString());
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
		dlg = new AlertDialog.Builder(this)
        .setView(textEntryView)
        .create();
        dlg.show();
	}
	public void onClickMyAddress(View view){
		Intent	intent=new Intent(Tab3SetUserInfoActivity.this,Tab2AddressActivity.class);
		startActivity(intent);
	}
	
	private void changeNickName(String newName) {
		
		
	}
	private void changePhone(String newPhone)
	{
		
	}
	private void changeProfile()
	{
		
	}

}
