package com.yuf.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;

public class Tab3FanFocusActivity extends Activity {

	private ImageView backImageView;
	private TabHost tabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		setContentView(R.layout.tab3_fans_focus);
		
		tabHost=(TabHost)findViewById(R.id.tabhost);
		tabHost.setup();
		TabWidget tabWidget=tabHost.getTabWidget();
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("tab1").setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("tab2").setContent(R.id.tab2));
		super.onCreate(savedInstanceState);
	}

	
}
