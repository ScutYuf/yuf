package com.yuf.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Tab2AddressActivity extends Activity {
	private ImageView backImageView;
	private Button okButton;
	public static Activity mActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mActivity=this;
		setContentView(R.layout.tab2_address);
		backImageView=(ImageView)findViewById(R.id.tab2_address_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				// TODO Auto-generated method stub
				
			}
		});
		
		
		okButton=(Button)findViewById(R.id.tab2_address_ok_button);
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				// TODO Auto-generated method stub
				
			}
		});
	}
}
