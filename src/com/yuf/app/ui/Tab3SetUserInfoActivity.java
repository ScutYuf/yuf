package com.yuf.app.ui;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yuf.app.MyApplication;
import com.yuf.app.http.extend.BitmapCache;

import android.R.mipmap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
		new AlertDialog.Builder(this).setTitle("列表框").setItems(
			     new String[] { "从相册选择", "拍照上传" }, new DialogInterface.OnClickListener() {  
			    	    @Override  
			    	    public void onClick(DialogInterface dialog, int which) {  
			    	        // TODO Auto-generated method stub  
			    	        switch (which) {  
			    	        case 0:  
			    	        	postImageFromAlbum();
			    	            break;  
			    	        case 1:  
			    	        	postImageFromCamera();
			    	            break;  
			    	        }  
			    	    } } ).setNegativeButton(
			     "取消", null).show();
	}
	public void onClickNickName(View view){
		
		final EditText editText = new EditText(this); 
		new AlertDialog.Builder(this).setTitle("请输入").setIcon(
			     android.R.drawable.ic_dialog_info).setView(
			     editText).setPositiveButton("确定", 
			    		 new DialogInterface.OnClickListener() {                
			    	    @Override  
			    	    public void onClick(DialogInterface dialog, int which) {  
			    	        // TODO Auto-generated method stub  
			    	        Toast.makeText(Tab3SetUserInfoActivity.this, "您输入的内容是："+editText.getText(), Toast.LENGTH_SHORT).show();  
			    	    }  
			     }
			    		 )
			    .setNegativeButton("取消", null).show();
	}
	public void onClickPhone(View view){
		final EditText editText = new EditText(this); 
		new AlertDialog.Builder(this).setTitle("请输入").setIcon(
			     android.R.drawable.ic_dialog_info).setView(
			     editText).setPositiveButton("确定", 
			    		 new DialogInterface.OnClickListener() {                
			    	    @Override  
			    	    public void onClick(DialogInterface dialog, int which) {  
			    	        // TODO Auto-generated method stub  
			    	        Toast.makeText(Tab3SetUserInfoActivity.this, "您输入的内容是："+editText.getText(), Toast.LENGTH_SHORT).show();  
			    	    }  
			     }
			    		 )
			    .setNegativeButton("取消", null).show();
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
	private void postImageFromAlbum() {
		
	}
	private void postImageFromCamera()
	{
		
	}
}
