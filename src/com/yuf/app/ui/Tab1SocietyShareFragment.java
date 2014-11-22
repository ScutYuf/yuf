package com.yuf.app.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.http.extend.BitmapCache;


public class Tab1SocietyShareFragment extends Fragment {
	//
	private PopupWindow mPopupWindow;
	private PullToRefreshListView listView;
	private ImageLoader mImageLoader;
	private JSONArray jsonArray;
	private int currentPage;
	private MylistAdapter mAdaAdapter;
	private boolean isEnd;	
//评论
	private PopupWindow commentWindow;
	private InputMethodManager inputMethodManager;
	public Tab1SocietyShareFragment(){
		super();
		mImageLoader=new ImageLoader(MyApplication.requestQueue, new BitmapCache());
		jsonArray=new JSONArray();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.tab1_share,container,false);
		listView=(PullToRefreshListView)view.findViewById(R.id.tab1_share_listview);
		
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				refreshListView();
			}

			
		});
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
			
					if (!isEnd) {
						
						loadingNextPage();
					}
					
			}	});
		
		listView.setMode(Mode.PULL_FROM_START);
		mAdaAdapter=new MylistAdapter();
		listView.setAdapter(mAdaAdapter);

		refreshListView();
		return  view;
	}
		private void refreshListView() {
			// TODO Auto-generated method stub
			isEnd=false;
			listView.setMode(Mode.PULL_FROM_START);
			jsonArray=new JSONArray();
			currentPage=0;
			getSharePage(++currentPage);
			
		}
	
		private void loadingNextPage() {
			getSharePage(++currentPage);
		}

	
	private	void getSharePage(int page)
	{
	

		JsonObjectRequest request=new JsonObjectRequest(Method.GET, "http://110.84.129.130:8080/Yuf/post/getAllPost/"+String.valueOf(page), null,  new Response.Listener<JSONObject>()  
        {  

            @Override  
            public void onResponse(JSONObject response)  
            {  
					try {
						
						
						jsonArray=MyApplication.joinJSONArray(jsonArray, response.getJSONArray("postData"));
						
						Log.d("tab1share", "resopn"+String.valueOf(jsonArray.length()));
						
					
						if (response.getInt("currentPageNum")>=response.getInt("maxPageNum")) {
							listView.setMode(Mode.PULL_FROM_START);
							isEnd=true;
							Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					listView.onRefreshComplete();
					mAdaAdapter.notifyDataSetChanged();
                 
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


MyApplication.requestQueue.start();
		
	}
private class MylistAdapter extends BaseAdapter
{

	@Override
	public int getCount() {
		Log.d("tab1share", String.valueOf(jsonArray.length()));
		return jsonArray.length();

	}
	public Object getItem(int position) {
		try {
			return jsonArray.getJSONObject(position);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("mywork", "get");
	    ViewHolder holder;
	    final int index=position;
		if (convertView==null) {
			convertView=getActivity().getLayoutInflater().inflate(R.layout.tab1_share_list_item,null);
			//
			holder = new ViewHolder();
			holder.headimageView=(NetworkImageView)convertView.findViewById(R.id.tab1_share_list_item_headimg);
			holder.foodImageView=(NetworkImageView)convertView.findViewById(R.id.tab1_share_list_item_foodimage);
			holder.contentTextView=(TextView)convertView.findViewById(R.id.tab1_share_list_item_content_textview);
			holder.timeTextView=(TextView)convertView.findViewById(R.id.tab1_share_list_item_time_textview);
			holder.titleTextView=(TextView)convertView.findViewById(R.id.tab1_share_item_titile_textview);
			holder.usernameTextView=(TextView)convertView.findViewById(R.id.tab1_share_list_item_name_textview);
			holder.moreImageView=(ImageView)convertView.findViewById(R.id.tab1_share_list_item_more);
			holder.commentContent=(LinearLayout)convertView.findViewById(R.id.tab1_share_list_item_comment_linearlayout);
			holder.likeTextView=(TextView)convertView.findViewById(R.id.tab1_share_list_item_like_textview);
			convertView.setTag(holder);
			
		}
		else
		{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.foodImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(Tab1SocietyShareFragment.this.getActivity(), ImageShowActivity.class);
				try {
					intent.putExtra("imageurl", "http://110.84.129.130:8080/Yuf"+jsonArray.getJSONObject(index).getString("postpicurl"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				startActivity(intent);
				Tab1SocietyShareFragment.this.getActivity().overridePendingTransition(R.anim.image_zoom_in,R.anim.image_zoom_out);
			}
		});
		
		holder.headimageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(getActivity(),
						Tab3MyWorkActivity.class);
				Bundle bundle = new Bundle();   
				try {
					bundle.putInt("userId", jsonArray.getJSONObject(index).getInt("userid"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				intent.putExtras(bundle);
				startActivity(intent);
				// TODO Auto-generated method stub
			}
		});
		holder.moreImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater mLayoutInflater = (LayoutInflater)Tab1SocietyShareFragment.this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
				ViewGroup viewGroup=(ViewGroup) mLayoutInflater.inflate(R.layout.comment_popupwindow, null);
				if (mPopupWindow==null) {
					
					mPopupWindow=new PopupWindow(viewGroup, 300, 60);
					mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
					mPopupWindow.setFocusable(false);  
					mPopupWindow.setOutsideTouchable(true);
					mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
					int[] location = new int[2];  
					v.getLocationOnScreen(location);  
					mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0]-300, location[1]); 
//点赞					
					TextView likeTextView=(TextView) viewGroup.findViewById(R.id.comment_popupwindow_like);
					likeTextView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							try {
								addLikeRelationship(jsonArray.getJSONObject(index).getInt("postid"),index);
								
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
//评论
			TextView commentTextView=(TextView) viewGroup.findViewById(R.id.comment_popupwindow_comment);
			commentTextView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					
				}
			});
			
				}
				else {
					mPopupWindow.dismiss();
					mPopupWindow=null;
					
				}
			}
		});
		
		try {
			JSONObject jsonObject=jsonArray.getJSONObject(position);
			holder.headimageView.setDefaultImageResId(R.drawable.no_pic);
			holder.headimageView.setImageUrl("http://110.84.129.130:8080/Yuf"+jsonObject.getString("useravatarurl"),mImageLoader);
			holder.foodImageView.setDefaultImageResId(R.drawable.no_pic);
			holder.foodImageView.setImageUrl("http://110.84.129.130:8080/Yuf"+jsonObject.getString("postpicurl"), mImageLoader);	
			
			holder.titleTextView.setText(jsonObject.getString("posttitle"));
			
			holder.usernameTextView.setText(jsonObject.getString("username"));
			
			long currentTime =jsonObject.getLong("posttime");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(currentTime);
			holder.timeTextView.setText(formatter.format(date));
			holder.contentTextView.setText(jsonObject.getString("postcontent"));
			
			showCommentItemList(holder.commentContent, jsonObject.getJSONArray("CommentList"));
			showLikeList(holder.likeTextView, jsonObject.getJSONArray("likeUsersList"));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return convertView;
	}
	
	 class ViewHolder {
		 NetworkImageView headimageView;
		 NetworkImageView foodImageView;
		 TextView titleTextView;
		 TextView usernameTextView;
		 TextView timeTextView;
		 TextView contentTextView;
		 ImageView moreImageView;
		 TextView likeTextView;
		 LinearLayout commentContent; 
		}
	}
private void showCommentItemList(LinearLayout content,JSONArray array)
{
	content.removeAllViews();
	for (int i = 0; i < array.length(); i++) {
		View itemView=getActivity().getLayoutInflater().inflate(R.layout.tab1_share_list_item_comment_item, null);
		TextView usernameTextView=(TextView)itemView.findViewById(R.id.comment_name);
		TextView contentTextView=(TextView)itemView.findViewById(R.id.comment_content);
		try {
			usernameTextView.setText(array.getJSONObject(i).getString("username")+":");
			contentTextView.setText(array.getJSONObject(i).getString("commentcontent"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		content.addView(itemView);
	}
}
private void showLikeList(TextView view,JSONArray array)
{
	view.setText("");
	boolean isFrist=true;
	for (int i = 0; i < array.length(); i++) {
		try {
			if (isFrist) {
				view.setText(array.getString(i));
				isFrist=false;
			}
			else 
				view.setText(view.getText()+","+array.getString(i));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
private void addCommentRelationship(int postId,final String dishComment,final int index){
	JSONObject jsonObject = new JSONObject();
	final JSONObject js = new JSONObject();
	try{
		jsonObject.put("userId",UserInfo.getInstance().userid);
		jsonObject.put("postId", postId);
		jsonObject.put("sessionId",UserInfo.getInstance().sessionid);
		jsonObject.put("commentContent", dishComment);
		js.put("username", UserInfo.getInstance().username);
		js.put("commentcontent", dishComment);
	}catch(NumberFormatException e){
		e.printStackTrace();
	}catch(JSONException e){
		e.printStackTrace();
	}
	
	JsonObjectRequest request = new JsonObjectRequest(Method.POST, "http://110.84.129.130:8080/Yuf/comment/newComment", jsonObject, new Response.Listener<JSONObject>(){

		@Override
		public void onResponse(JSONObject response) {
			try {
				if (response.getInt("code")==0) {
					Toast.makeText(getActivity(), "评论成功", Toast.LENGTH_SHORT).show();
					jsonArray.getJSONObject(index).getJSONArray("CommentList").put(js);
					mAdaAdapter.notifyDataSetChanged();
				}
				else {
					Toast.makeText(getActivity(), "评论失败", Toast.LENGTH_SHORT).show();
                }
				} catch (JSONException e) {
					e.printStackTrace();
				}
		}
	},new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub	
		}
	});
	MyApplication.requestQueue.add(request);
	MyApplication.requestQueue.start();	
}
private void addLikeRelationship(int postid,final int index)
{
	JSONObject jsonObject=new JSONObject();
	try {
		jsonObject.put("userId",UserInfo.getInstance().userid);
		jsonObject.put("postId", postid);
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	JsonObjectRequest request=new JsonObjectRequest(Method.POST, "http://110.84.129.130:8080/Yuf/like/insertLike", jsonObject, new Response.Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			// TODO Auto-generated method stub
			try {
				if (response.getString("insertLike").equals("success")) {
					Toast.makeText(getActivity(), "点赞成功", Toast.LENGTH_SHORT).show();
					jsonArray.getJSONObject(index).getJSONArray("likeUsersList").put(UserInfo.getInstance().username);
					mAdaAdapter.notifyDataSetChanged();
				}
				else {
					Toast.makeText(getActivity(), "点赞失败", Toast.LENGTH_SHORT).show();

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	},new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub			
		}
	});
	MyApplication.requestQueue.add(request);
	MyApplication.requestQueue.start();
}
}
