package com.yuf.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TabHost;
import com.yuf.app.Entity.CategorysEntity;
import com.yuf.app.adapter.MiddlePageAdapter;
import com.yuf.app.ui.Main.MyPageChangeListener;
import com.yuf.app.ui.indicator.TitlePageIndicator;

/**
 * @author DU
 *
 */
public class Tab0FoodActivity extends FragmentActivity {
private ImageView backImageView;
	class MyPageChangeListener implements OnPageChangeListener {
	    @Override
	    public void onPageScrollStateChanged(int arg0) {
	        // TODO Auto-generated method stub
	    }
	
	    @Override
	    public void onPageScrolled(int arg0, float arg1, int arg2) {
	        // TODO Auto-generated method stub
	    }
	
	    @Override
	    public void onPageSelected(int arg0) {
	        // TODO Auto-generated method stub
	       
	    }
	}
	private TabHost myTabhost;
	private ArrayList<Fragment> fragments;
	private ArrayList<CategorysEntity> fooddetailcategorysEntities;
	private TitlePageIndicator fooddetailIndicator;
	private ViewPager Viewpage;
	private MiddlePageAdapter mInsidePageAdapter;
	private ImageView moreImageView;
	private PopupWindow mPopupWindow; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab0_recipe_detail);
		
		backImageView=(ImageView)findViewById(R.id.tab0_detail_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
		addFragment();
		
		
		
	}
	

	void addFragment(){
		 fragments=new ArrayList<Fragment>();
		fooddetailcategorysEntities=new ArrayList<CategorysEntity>();
		
		fooddetailcategorysEntities.add(new CategorysEntity("烹饪方法"));
		fragments.add(new Tab0FoodCookFragment());
		fooddetailcategorysEntities.add(new CategorysEntity("食材"));
		fragments.add(new Tab0FoodMaterialFragment());
		
		
		fooddetailIndicator=(TitlePageIndicator) findViewById(R.id.tab0_indicator);
		Viewpage=(ViewPager) findViewById(R.id.tab0_pager);
		mInsidePageAdapter=new MiddlePageAdapter(getSupportFragmentManager());
		Viewpage.setAdapter(mInsidePageAdapter);
		Viewpage.setOffscreenPageLimit(3);
		mInsidePageAdapter.addFragments(fragments,fooddetailcategorysEntities);
		fooddetailIndicator.setViewPager(Viewpage);
		fooddetailIndicator.setOnPageChangeListener( new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
}
