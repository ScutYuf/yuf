package com.yuf.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class Tab3FanFocusActivity extends Activity {

	private ImageView backImageView;
	private TabHost tabHost;
	private GridView fansGridView;
	private GridView concernsGridView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		setContentView(R.layout.tab3_fans_focus);
		
		fansGridView=(GridView)findViewById(R.id.fans_gridview);
		concernsGridView=(GridView)findViewById(R.id.concerns_gridview);
		
		tabHost=(TabHost)findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("tab1").setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("tab2").setContent(R.id.tab2));
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		super.onCreate(savedInstanceState);
	}
	//获取粉丝信息
	private void getFans()
	{
		
	}
	//获取关注信息
	private void getConcerns()
	{
		
	}
	
}
