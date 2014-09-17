package com.yuf.app.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.adapter.OutsidePagerAdapter;
import com.yuf.app.http.extend.BitmapCache;
import com.yuf.app.mywidget.FoodViewPage;
import com.yuf.app.ui.indicator.CirclePageIndicator;
//import com.yuf.app.mywidget.FoodViewPage;

@SuppressLint("ValidFragment")
public class Tab0MyRecipeFragment extends Fragment {


	private Button detailButton;
	private Button commentbuttoButton;
	private Button collectionButton;
	private CirclePageIndicator foodindiactor;
	private FoodViewPage viewPager;
	private ArrayList<View> mViews;
	private JSONArray mdataArray;
	private ImageLoader mImageLoader;
	private TextView difficultyTextView;
	private TextView doseTextView;
	private TextView skillTextView;
	private TextView timeTextView;
	private TextView nameTextView;
	private int currentPageIndex;
	private  AlertDialog dlg;
	public Tab0MyRecipeFragment(JSONArray _dataArray) {
		mdataArray=_dataArray;
		// TODO Auto-generated constructor stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("liow", "myrecipefragment");
		
		
		
		
		mImageLoader = new ImageLoader(MyApplication.requestQueue, new BitmapCache()); 
		
		
		
		
		View view=inflater.inflate(R.layout.tab0_recipe_fragment,container,false);

		difficultyTextView=(TextView)view.findViewById(R.id.tab0_recipe_fragment__dishdifficulty_textview);
		doseTextView=(TextView)view.findViewById(R.id.tab0_recipe_fragment_dishamount_textview);
		timeTextView=(TextView)view.findViewById(R.id.tab0_recipe_fragment_dishcooktime_textview);
		nameTextView=(TextView)view.findViewById(R.id.tab0_recipe_fragment_foodname_textview);
		commentbuttoButton=(Button)view.findViewById(R.id.comment_button);
		commentbuttoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialg();
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
				Bundle bundle = new Bundle();                           //创建Bundle对象   
				try {
					bundle.putString("dishid",mdataArray.getJSONObject(currentPageIndex).getString("dishid") );
					bundle.putString("dishname",mdataArray.getJSONObject(currentPageIndex).getString("dishname") );
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}     //装入数据   
				intent.putExtras(bundle);                                //把Bundle塞入Intent里面  
				startActivity(intent);
				// TODO Auto-generated method stub
				
			}
		});
		
		//
		try {
			initView();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		viewPager=(FoodViewPage)view.findViewById(R.id.recipe_fragment_viewpage);
		viewPager.setAdapter(new OutsidePagerAdapter(mViews) );
		viewPager.setOffscreenPageLimit(7);
		foodindiactor=(CirclePageIndicator)view.findViewById(R.id.circle_page_indicator);
		foodindiactor.setViewPager(viewPager);
		foodindiactor.setOnPageChangeListener(new MyPageChangeAdapter1());
	
		return  view;
	}
	
	protected void showDialg() {

		//显示评论对话框
		LayoutInflater factory = LayoutInflater.from(getActivity());
		final View textEntryView = factory.inflate(R.layout.dialog, null);
		final EditText editText=(EditText)textEntryView.findViewById(R.id.comment_comment_editText);
		Button commentButton =(Button)textEntryView.findViewById(R.id.comment_dialog_comment_button);
		commentButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				commentDish(editText.getText().toString());
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
		dlg = new AlertDialog.Builder(getActivity())
        .setView(textEntryView)
        .create();
        dlg.show();
		
		
		// TODO Auto-generated method stub
		
	// TODO Auto-generated method stub
		
	}
	private void initView() throws JSONException
	{
		mViews=new ArrayList<View>();
		//初始化7张图片
		for(int i=0;i<7;i++)
		{
			NetworkImageView imageView=new NetworkImageView(getActivity());
			imageView.setImageUrl("http://110.84.129.130:8080/Yuf"+mdataArray.getJSONObject(i).getString("dishpicurl"), mImageLoader);
			mViews.add(imageView);
		}
		JSONObject jObject = mdataArray.getJSONObject(0);
		difficultyTextView.setText(String.valueOf(jObject.getDouble("dishdifficulty")));
		doseTextView.setText(String.valueOf(jObject.getInt("dishamount")));
		skillTextView.setText(jObject.getString("dishcookmethod"));
		timeTextView.setText(jObject.getString("dishcooktime"));
		commentbuttoButton.setText(String.format("评论(%s)",String.valueOf(jObject.getInt("dishcommentnum"))));
		collectionButton.setText(String.format("收藏(%s)",String.valueOf(jObject.getInt("dishcollectionnum"))));
		nameTextView.setText(jObject.getString("dishname"));
	}
	private void commentDish(String commentContent) {
			JSONObject jsonObject=new JSONObject();
			try {
				jsonObject.put("userId",Integer.valueOf(UserInfo.getInstance().getUserid()));
				JSONObject mjsonObject=mdataArray.getJSONObject(currentPageIndex);
				jsonObject.put("dishId", mjsonObject.getInt("dishid"));
				jsonObject.put("dishcommentContent", commentContent);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JsonObjectRequest request=new JsonObjectRequest(Method.POST,"http://110.84.129.130:8080/Yuf/dishcomment/postDishcomment/", jsonObject, new Response.Listener<JSONObject>()  
			        {  
	
	            @Override  
	            public void onResponse(JSONObject response)  
	            {
	            	try {
						if (response.getInt("code")==0) {
							Toast toast=Toast.makeText(getActivity().getApplicationContext(), "评论成功", Toast.LENGTH_SHORT);
							toast.show();
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }}, new Response.ErrorListener()  
	        {  
	
	            @Override  
	            public void onErrorResponse(VolleyError error)  
	            {  
	                Log.e("TAG", error.getMessage(), error);  
	            }  
	        });
	
	
		//将JsonObjectRequest 加入RequestQuene
	MyApplication.requestQueue.add(request);
	Log.d("liow","request start");
	MyApplication.requestQueue.start();
		}
	class MyPageChangeAdapter1 implements OnPageChangeListener
	{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		Log.d("", "");
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			Log.d("", "");
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			currentPageIndex=arg0;
			JSONObject jObject;
			try {
				jObject = mdataArray.getJSONObject(arg0);
				difficultyTextView.setText(String.valueOf(jObject.getDouble("dishdifficulty")));
				doseTextView.setText(String.valueOf(jObject.getInt("dishamount")));
//				skillTextView.setText(jObject.getString(""));
				timeTextView.setText(jObject.getString("dishcooktime"));
				commentbuttoButton.setText(String.format("评论(%s)", String.valueOf(jObject.getInt("dishcommentnum"))));
				collectionButton.setText(String.format("收藏(%s)",String.valueOf(jObject.getInt("dishcollectionnum"))));
				nameTextView.setText(jObject.getString("dishname"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		}
}
