package com.yuf.app.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.onekeyshare.OnekeyShare;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.db.Order;
import com.yuf.app.http.extend.BitmapCache;

//缺分享图片；    翻页动画；  评论还不行
public class Tab0FoodActivity extends Activity{

	private ImageLoader mImageLoader;
	private ImageView back_imageView;
	private Button shareButton;
	private Button collectionButton;
	private Button buyFood;
	private EditText comment;
	
	private JSONObject jsonObject;
	private JSONArray stepJsonObject;//烹饪步骤
	private JSONObject dishInfoJsonObject;
	
	private ViewPager Viewpage;
	private List<View> views;
	private View vierwView;
	private NetworkImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab0_recipe_detail);
		mImageLoader=new ImageLoader(MyApplication.requestQueue, new BitmapCache());	
		initView();
		initViewPager();
		getDishDetail();
	}
	
	private void initView() {
//返回	
		back_imageView=(ImageView)findViewById(R.id.back_imageView);
		back_imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
//一键分享
		shareButton = (Button)findViewById(R.id.shareButton);
		shareButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showShare();
			}
		});
//收藏		
		collectionButton = (Button)findViewById(R.id.collectionButton);
		collectionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				addCollectionRelationShip();
			}
		});
//购买食材
		buyFood = (Button)findViewById(R.id.buyFood);
		buyFood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				addOrder();
				onBackPressed();
				Main.mTabPager.setCurrentItem(2);
			}

		});
		
//评论		
		comment = (EditText)findViewById(R.id.text3);
		comment.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId ==EditorInfo.IME_ACTION_NONE){
					 InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
			         if(im.isActive()){  
			        	 im.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);  
			         } 
			         commentDish(comment.getText().toString());
			         comment.setText(""); 
			         return true;
				}
				return false;
			}
		});
	}
	
	private void initViewPager() {
		LayoutInflater inflater=getLayoutInflater();
		views = new ArrayList<View>();
		Viewpage=(ViewPager) findViewById(R.id.tab0_recipe_pager);
		
		for(int i = 0;i<3;i++){
			vierwView = (View)inflater.inflate(R.layout.tab0_food_activity_photo, null);
			image = (NetworkImageView)vierwView.findViewById(R.id.image_photo);
			image.setImageUrl("http://110.84.129.130:8080//Yuf/images/dish/"+getIntent().getStringExtra("dishid")+".jpg",mImageLoader);
			views.add(vierwView);
		}
		
		Viewpage.setAdapter(new MyPageAdapter(views));
		Viewpage.setCurrentItem(0);
		Viewpage.setOnPageChangeListener(new OnPageChangeListener() {
			
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
	

	private void addOrder() {
		try {
			Order order=new Order();
			order.dishId=dishInfoJsonObject.getInt("dishid");
			order.orderAmount=1;
			order.orderImage=dishInfoJsonObject.getString("dishpicurl");
			order.orderName=dishInfoJsonObject.getString("dishname");
			order.orderPaymethod="货到付款";
			order.orderPrice=dishInfoJsonObject.getDouble("dishprice");
			order.orderTime=timeString();
			order.userId=Integer.valueOf(UserInfo.getInstance().userid);
			order.writeToDb();
			Toast.makeText(Tab0FoodActivity.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
			} catch (JSONException e) {
			e.printStackTrace();
			}
		
	}
	private String timeString() {
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(currentTime);
		return formatter.format(date);
		}
	
	
    public void OnClick(View view){
    	switch (view.getId()) {
		case R.id.relative1://打开食材
			Intent intent1 = new Intent(Tab0FoodActivity.this, Tab0FoodMaterialActivity.class);
			intent1.putExtra("DishId",getIntent().getExtras().getString("dishid"));
			startActivity(intent1);
			break;
		case R.id.relative2://打开烹饪步骤	
			Intent intent = new Intent(Tab0FoodActivity.this,Tab0CookStepActivity.class);
			intent.putExtra("DishId",getIntent().getExtras().getString("dishid"));
			startActivity(intent);
			break;
		default:
			break;
		}
    }
//一键分享	
	@SuppressLint("SdCardPath")
	private void showShare() {
		 ShareSDK.initSDK(Tab0FoodActivity.this);
	        OnekeyShare oks = new OnekeyShare();
	        //关闭sso授权
	        oks.disableSSOWhenAuthorize();
	        // 分享时Notification的图标和文字
	        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
	        oks.setTitle(getString(R.string.share));// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
	        oks.setTitleUrl("http://sharesdk.cn"); // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setUrl("http://sharesdk.cn");// url仅在微信（包括好友和朋友圈）中使用
	        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
	        oks.setComment("我是测试评论文本");
	        oks.setSite(getString(R.string.app_name));// site是分享此内容的网站名称，仅在QQ空间使用
	        oks.setSiteUrl("http://sharesdk.cn");// siteUrl是分享此内容的网站地址，仅在QQ空间使用
	        String a ="";
	        for (int i = 0; i <stepJsonObject.length(); i++) {
	    		JSONObject mJsonObject;
	    		try {mJsonObject = stepJsonObject.getJSONObject(i);
	    			a=a+String.valueOf(mJsonObject.getInt("recipeorder"))+":"+mJsonObject.getString("recipedetail")+";";
	    		} catch (JSONException e) {e.printStackTrace();}}
	        try {
	        	// text是分享文本，所有平台都需要这个字段
		        oks.setText("#煮乜嘢#"+dishInfoJsonObject.getString("dishname")+a);
		    } catch (JSONException e) {e.printStackTrace();}
	       
//	        BitmapDrawable drawable=(BitmapDrawable)image.getDrawable();
//			Bitmap bitmap = drawable.getBitmap();
//			try {
//				saveMyBitmap("share",bitmap);
//				oks.setImagePath("/sdcard/yuf_image/" + "share" + ".PNG");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
	        // 启动分享GUI
	        oks.show(Tab0FoodActivity.this);
	}

	 @SuppressLint("SdCardPath")
	public void saveMyBitmap(String bitName,Bitmap mBitmap) throws IOException {  
	        File f = new File("/sdcard/yuf_image/" + bitName + ".PNG");  
	        f.createNewFile();  
	        FileOutputStream fOut = null;  
	        try {  
	                fOut = new FileOutputStream(f);  
	        } catch (FileNotFoundException e) {  
	                e.printStackTrace();  
	        }  
	        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);  
	        try {  
	                fOut.flush();  
	        } catch (IOException e) {  
	                e.printStackTrace();  
	        }  
	        try {  
	                fOut.close();  
	        } catch (IOException e) {  
	                e.printStackTrace();  
	        }  
	    }  

//收藏	
//收藏	
    protected void addCollectionRelationShip() {
			JSONObject jsonObject=new JSONObject();
			try {
				jsonObject.put("userId",UserInfo.getInstance().userid);
				jsonObject.put("sessionId", UserInfo.getInstance().sessionid);
				jsonObject.put("dishId", dishInfoJsonObject.getInt("dishid"));
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			JsonObjectRequest request=new JsonObjectRequest(Method.POST, "http://110.84.129.130:8080/Yuf/collection/addCollection", jsonObject, new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					try {
						if (response.getInt("code")==0) {
							
							Toast toast = Toast.makeText(Tab0FoodActivity.this,"收藏成功", Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}
						else {
							Toast toast = Toast.makeText(Tab0FoodActivity.this,"已收藏", Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			},new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					error.printStackTrace();
				}
			});
			MyApplication.requestQueue.add(request);
			MyApplication.requestQueue.start();
		}

//获取网络数据	
	 private void getDishDetail() {
	
		JsonObjectRequest request=new JsonObjectRequest(Method.GET, "http://110.84.129.130:8080/Yuf/dish/getDishById/"+getIntent().getExtras().getString("dishid"), null,  new Response.Listener<JSONObject>()  
        {  

            @Override  
            public void onResponse(JSONObject response)  
            {  
            	try {
					jsonObject=response.getJSONObject("dishDetail");
					stepJsonObject = jsonObject.getJSONArray("Recipe");
					dishInfoJsonObject=jsonObject.getJSONObject("Dish");
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	//addFragment();
            }  
        }, new Response.ErrorListener()  
        {  @Override  
            public void onErrorResponse(VolleyError error)  
            {  
                Log.e("Tab0FoodActivity", error.getMessage(), error);  
            }  
        });

	//将JsonObjectRequest 加入RequestQuene
	MyApplication.requestQueue.add(request);
	Log.d("Tab0FoodActivity","request start");
	MyApplication.requestQueue.start();
	}

	
	
//评论	 
 private void commentDish(String commentContent) {
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("userId",Integer.valueOf(UserInfo.getInstance().getUserid()));
			jsonObject.put("dishId", dishInfoJsonObject.getInt("dishid"));
			jsonObject.put("dishcommentContent", commentContent);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		JsonObjectRequest request=new JsonObjectRequest(Method.POST,"http://110.84.129.130:8080/Yuf/dishcomment/postDishcomment/", jsonObject, new Response.Listener<JSONObject>()  
		        {  

         @Override  
         public void onResponse(JSONObject response)  
         {
         	try {
					if (response.getInt("code")==0) {
						Toast toast=Toast.makeText(Tab0FoodActivity.this, "评论成功", Toast.LENGTH_SHORT);
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
Log.d("Tab0FoodActivity-commentDish","request start");
MyApplication.requestQueue.start();
	}
 
    private class MyPageAdapter extends PagerAdapter{

		private List<View> views;
		
		public MyPageAdapter(List<View> views){
			this.views = views;
		}
		
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));
		}


		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(views.get(position),0);
			return views.get(position);
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		
	}
}
