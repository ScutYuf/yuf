package com.yuf.app.ui;

import java.util.ArrayList;

import com.yuf.app.adapter.OutsidePagerAdapter;
import com.yuf.app.mywidget.FoodViewPage;
import com.yuf.app.ui.R.drawable;
import com.yuf.app.ui.indicator.CirclePageIndicator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class Tab0MyRecipeFragment extends Fragment {

	private ImageView lefImageView;
	private ImageView righImageView;
	private Button detailButton;
	private Button commentbuttoButton;
	private Button collectionButton;
	private CirclePageIndicator foodindiactor;
	private FoodViewPage viewPager;
	private ArrayList<View> mViews;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("liow", "myrecipefragment");
		mViews=new ArrayList<View>();
		
		
		
		
		//初始化7张图片
		for(int i=0;i<7;i++)
		{
			
			ImageView imageView=new ImageView(getActivity());
			if (i%2==0) {
				imageView.setImageResource(R.drawable.food1);
				
			}
			else {
				imageView.setImageResource(R.drawable.food2);
			}
			
			mViews.add(imageView);
		}
		
//		//初始化viewpage
//		
		View view=inflater.inflate(R.layout.tab0_recipe_fragment,container,false);
		
		
		
		
		
		
		
		
		
		viewPager=(FoodViewPage)view.findViewById(R.id.recipe_fragment_viewpage);
		viewPager.setAdapter(new OutsidePagerAdapter(mViews) );
		viewPager.setOffscreenPageLimit(7);
		
		foodindiactor=(CirclePageIndicator)view.findViewById(R.id.circle_page_indicator);
		foodindiactor.setViewPager(viewPager);

		
			
		
		commentbuttoButton=(Button)view.findViewById(R.id.comment_button);
		commentbuttoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//显示评论对话框
				LayoutInflater factory = LayoutInflater.from(getActivity());
				final View textEntryView = factory.inflate(R.layout.dialog, null);
                AlertDialog dlg = new AlertDialog.Builder(getActivity())
               
                .setTitle("评论：")
                .setView(textEntryView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                      System.out.println("-------------->6");
                      EditText secondPwd = (EditText) textEntryView.findViewById(R.id.comment_comment_editText);
                      String inputPwd = secondPwd.getText().toString();
                      System.out.println("-------------->1");
                      
//输入的内容会在页面上显示来因为是做来测试，所以功能不是很全，只写了username没有学password
                       
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                     System.out.println("-------------->2");
                       
                    }
                })
                .create();
                dlg.show();
				
				
				// TODO Auto-generated method stub
				
			}
		});
		
		collectionButton=(Button)view.findViewById(R.id.collection_button);
		collectionButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast toast = Toast.makeText(getActivity(),
					     "收藏成功", Toast.LENGTH_SHORT);
					   toast.setGravity(Gravity.CENTER, 0, 0);
					   toast.show();

			}
		});
		
		
		detailButton=(Button)view.findViewById(R.id.detail_button);
		detailButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(Main.mainActivity,
						Tab0FoodActivity.class);
				startActivity(intent);
				// TODO Auto-generated method stub
				
			}
		});
		
		return  view;
	}
	
	

}
