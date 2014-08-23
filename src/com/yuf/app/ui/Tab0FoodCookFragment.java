package com.yuf.app.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.animation.Animation.AnimationListener;
public class Tab0FoodCookFragment extends Fragment{

	
	private TextView comment;
	 private TextView showComment;
	 private TextView collection;
	 private TextView share;
	private ScrollView mScrollView; 
	private float start_y=0;
	private boolean isDisappear=false;
	
	
	@SuppressLint("NewApi") @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.tab0_food_cook,container,false);
		comment=(TextView)view.findViewById(R.id.comment_textView);
		showComment=(TextView)view.findViewById(R.id.show_comment_textView);
		collection=(TextView)view.findViewById(R.id.collection_textView);
		share=(TextView)view.findViewById(R.id.share_TextView);
		mScrollView=(ScrollView)view.findViewById(R.id.cook_scrollView);
		mScrollView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction()==MotionEvent.ACTION_DOWN) {
					start_y=event.getY();
				
				}
				
			if (event.getAction()==MotionEvent.ACTION_MOVE&&start_y!=0) {
				
				if(event.getY()-start_y<0&&!isDisappear)
					{
					
					disappearTextView();
					Log.d("liow", "disappeartextview");
					isDisappear=true;
					return false;
					}
				
				if(event.getY()-start_y>0&&isDisappear)
					{
						appearTextView();
						Log.d("liow", "appeartextview");
						isDisappear=false;
					}
			}
			return false;
			}
		});
		return  view;
	}


protected void appearTextView() {
		// TODO Auto-generated method stub
	Animation animation= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_in_from_left);
	animation.setFillAfter(true);
	animation.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
			Log.d("liow", "commenttextview before left:"+String.valueOf(comment.getLeft())+  "right:"+String.valueOf(comment.getRight()));
			
			
			comment.setClickable(true);
			Log.d("liow", "commenttextview left:"+String.valueOf(comment.getLeft())+  "right:"+String.valueOf(comment.getRight()));
			// TODO Auto-generated method stub
			
		}
	});
	
	Animation animation2= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_in_from_left);
animation2.setFillAfter(true);
	animation2.setAnimationListener(new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
showComment.setClickable(true);			
			// TODO Auto-generated method stub
			
		}
	});
	Animation animation3= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_in_from_right);
	animation3.setFillAfter(true);
	animation3.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
collection.setClickable(true);			
			// TODO Auto-generated method stub
			
		}
	});
	
	Animation animation4= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_in_from_right);
	animation4.setFillAfter(true);
	animation4.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
share.setClickable(true);			
			// TODO Auto-generated method stub
			
		}
	});
	
	
	comment.startAnimation(animation);
	
	showComment.startAnimation(animation2);
	collection.startAnimation(animation3);
	share.startAnimation(animation4);
	
	}


private void disappearTextView() {
	Animation animation= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_out_to_left);
	animation.setFillAfter(true);
	animation.setAnimationListener(new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
			Log.d("liow", "commenttextview before left:"+String.valueOf(comment.getLeft())+  "right:"+String.valueOf(comment.getRight()));
			comment.setClickable(false);
			Log.d("liow", "commenttextview left:"+String.valueOf(comment.getLeft())+  "right:"+String.valueOf(comment.getRight()));
			// TODO Auto-generated method stub
			
		}
	});
	
	Animation animation2= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_out_to_left);
	animation2.setFillAfter(true);
	animation2.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
showComment.setClickable(false);			
			// TODO Auto-generated method stub
			
		}
	});
	Animation animation3= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_out_to_right);
	animation3.setFillAfter(true);
	animation3.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
collection.setClickable(false);			
			// TODO Auto-generated method stub
			
		}
	});
	
	Animation animation4= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_out_to_right);
	animation4.setFillAfter(true);
	animation4.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
			share.setClickable(false);			
			// TODO Auto-generated method stub
			
		}
	});
	
	
	comment.startAnimation(animation);
	showComment.startAnimation(animation2);
	collection.startAnimation(animation3);
	share.startAnimation(animation4);
	
	
	
}

}
