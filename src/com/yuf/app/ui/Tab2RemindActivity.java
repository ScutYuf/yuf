package com.yuf.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Tab2RemindActivity extends Activity {
	private ImageView backImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2_time_remind);
		backImageView=(ImageView)findViewById(R.id.tab2_remind_back_ImageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				// TODO Auto-generated method stub
				
			}
		});
	}
}
