package com.yuf.app.ui;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.util.TimeHelper;

public class Tab1Fragment extends Fragment {
	//标题栏控件
	private Button shareButton;
	
	
	// 分析数据
	private PullToRefreshListView listView;
	private JSONArray jsonArray;
	private int currentPage;
	private MylistAdapter mAdaAdapter;
	private boolean isEnd;
	// 评论有关
	private PopupWindow mPopupWindow;
	private LinearLayout commentViewGroup;
	private EditText commentEditText;
	private Button commentButton;
	private InputMethodManager inputMethodManager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.main_tab_1, container, false);

		initMember(view);

		return view;
	}

	private class MylistAdapter extends BaseAdapter {
	
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			Log.d("tab1share", String.valueOf(jsonArray.length()));
			return jsonArray.length();
	
		}
	
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			try {
				return jsonArray.getJSONObject(position);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			final int index = position;
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.tab1_share_list_item, null);
				holder = new ViewHolder();
				holder.headimageView = (NetworkImageView) convertView
						.findViewById(R.id.tab1_share_list_item_headimg);
				holder.foodImageView = (NetworkImageView) convertView
						.findViewById(R.id.tab1_share_list_item_foodimage);
				holder.contentTextView = (TextView) convertView
						.findViewById(R.id.tab1_share_list_item_content_textview);
				holder.timeTextView = (TextView) convertView
						.findViewById(R.id.tab1_share_list_item_time_textview);
				holder.titleTextView = (TextView) convertView
						.findViewById(R.id.tab1_share_item_titile_textview);
				holder.usernameTextView = (TextView) convertView
						.findViewById(R.id.tab1_share_list_item_name_textview);
				holder.moreImageView = (ImageView) convertView
						.findViewById(R.id.tab1_share_list_item_more);
				holder.commentContent = (LinearLayout) convertView
						.findViewById(R.id.tab1_share_list_item_comment_linearlayout);
				holder.likeTextView = (TextView) convertView
						.findViewById(R.id.tab1_share_list_item_like_textview);
				convertView.setTag(holder);
	
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.foodImageView.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							ImageShowActivity.class);
					try {
						intent.putExtra("imageurl",
								"http://110.84.129.130:8080/Yuf"
										+ jsonArray.getJSONObject(index)
												.getString("postpicurl"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					startActivity(intent);
					getActivity().overridePendingTransition(
							R.anim.image_zoom_in, R.anim.image_zoom_out);
				}
			});
	
			holder.headimageView.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
	
					Intent intent = new Intent(getActivity(),
							Tab3MyWorkActivity.class);
					Bundle bundle = new Bundle();
					try {
						bundle.putInt("userId", jsonArray.getJSONObject(index)
								.getInt("userid"));
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
					LayoutInflater mLayoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					ViewGroup viewGroup = (ViewGroup) mLayoutInflater.inflate(R.layout.comment_popupwindow, null);
					if (mPopupWindow == null) {
	
						mPopupWindow = new PopupWindow(viewGroup, 300, 60);
						mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
						mPopupWindow.setFocusable(false);
						mPopupWindow.setOutsideTouchable(true);
						mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
						int[] location = new int[2];
						v.getLocationOnScreen(location);
						mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY,location[0] - 300, location[1]);
// 点赞
						TextView likeTextView = (TextView) viewGroup.findViewById(R.id.comment_popupwindow_like);
						likeTextView.setOnClickListener(new OnClickListener() {
	
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								try {
									like(jsonArray.getJSONObject(index).getInt("postid"), index);
	
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						});
// 评论
	
						TextView commentTextView = (TextView) viewGroup.findViewById(R.id.comment_popupwindow_comment);
						commentTextView.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View view) {
										mPopupWindow.dismiss();
										mPopupWindow = null;
										
										commentViewGroup.setVisibility(View.VISIBLE);
										commentEditText.requestFocus();
										inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
										
										commentButton.setOnClickListener(new OnClickListener() {
	                                                @Override
													public void onClick(View view) {
														try {
															comment(jsonArray.getJSONObject(index).getInt("postid"),
																	commentEditText.getText().toString(),index);
														} catch (JSONException e) {
															e.printStackTrace();
														}
														commentViewGroup.setVisibility(View.GONE);
														commentEditText.setText("");
														inputMethodManager.hideSoftInputFromWindow(getActivity().getWindow().peekDecorView().getWindowToken(),0);
										         }
												});
	                                  }});
					} else {
						mPopupWindow.dismiss();
						mPopupWindow = null;
	
					}
				}
			});
	
			try {
				JSONObject jsonObject = jsonArray.getJSONObject(position);
				holder.headimageView.setDefaultImageResId(R.drawable.no_pic);
				holder.headimageView.setImageUrl(
						"http://110.84.129.130:8080/Yuf"
								+ jsonObject.getString("useravatarurl"),
						MyApplication.mImageLoader);
				;
				holder.foodImageView.setDefaultImageResId(R.drawable.no_pic);
				holder.foodImageView.setImageUrl(
						"http://110.84.129.130:8080/Yuf"
								+ jsonObject.getString("postpicurl"),
						MyApplication.mImageLoader);
				holder.titleTextView.setText(jsonObject.getString("posttitle"));
				holder.usernameTextView.setText(jsonObject
						.getString("username"));
	
				long currentTime = jsonObject.getLong("posttime");
				holder.timeTextView.setText(TimeHelper
						.getTimeStringFromLong(currentTime));
				holder.contentTextView.setText(jsonObject
						.getString("postcontent"));
	
				addCommentList(holder.commentContent,
						jsonObject.getJSONArray("CommentList"));
				addLikeList(holder.likeTextView,
						jsonObject.getJSONArray("likeUsersList"));
	
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

	private void initMember(View view) {

		jsonArray = new JSONArray();
		
		inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		commentViewGroup =(LinearLayout)view.findViewById(R.id.comment_viewgroup);
		commentEditText = (EditText)view.findViewById(R.id.comment_editText1);
		commentButton = (Button)view.findViewById(R.id.comment_button1);
		
		shareButton=(Button)view.findViewById(R.id.tab1_title_shareBtn);
		shareButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),Tab3AddWorkActivity.class);
				startActivity(intent);
				
			}
		});
		
		
		listView = (PullToRefreshListView) view.findViewById(R.id.tab1_share_listview);
		// 下拉刷新
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity()
						.getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				refreshListView();
          //隐藏虚拟键盘  
				View windowView = getActivity().getWindow().peekDecorView();
				if (windowView != null) {
                    inputMethodManager.hideSoftInputFromWindow(windowView.getWindowToken(),0);
                    commentViewGroup.setVisibility(View.GONE);
                    commentEditText.setText("");
				}
			}

		});
		// 看到最后一个数据后，加载后续数据
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {

				if (!isEnd) 
					loadingNextPage();

			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 //隐藏虚拟键盘  
				View windowView = getActivity().getWindow().peekDecorView();
				if (windowView != null) {
                    inputMethodManager.hideSoftInputFromWindow(windowView.getWindowToken(),0);
                    commentViewGroup.setVisibility(View.GONE);
                    commentEditText.setText("");
				}
			}
		});
		
		mAdaAdapter = new MylistAdapter();
		listView.setAdapter(mAdaAdapter);
		listView.setMode(Mode.PULL_FROM_START);
	
		refreshListView();

	}

	// 删除所有的数据，重新加载第一页数据
	private void refreshListView() {
		// TODO Auto-generated method stub
		isEnd = false;
		//清空数据
		jsonArray = new JSONArray();
		currentPage = 0;
		getSharePage(++currentPage);

	}

	private void loadingNextPage() {
		getSharePage(++currentPage);
	}

	private void getSharePage(int page) {

		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				"http://110.84.129.130:8080/Yuf/post/getAllPost/"
						+ String.valueOf(page), null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {

							jsonArray = MyApplication.joinJSONArray(jsonArray,
									response.getJSONArray("postData"));

							Log.d("tab1share",
									"resopn"
											+ String.valueOf(jsonArray.length()));

							if (response.getInt("currentPageNum") >= response
									.getInt("maxPageNum")) {
								listView.setMode(Mode.PULL_FROM_START);
								isEnd = true;
								Toast.makeText(getActivity(), "End of List!",
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						listView.onRefreshComplete();
						mAdaAdapter.notifyDataSetChanged();

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});

		// 将JsonObjectRequest 加入RequestQuene
		MyApplication.requestQueue.add(request);

		MyApplication.requestQueue.start();

	}

	private void addCommentList(LinearLayout content, JSONArray array) {
		content.removeAllViews();
		for (int i = 0; i < array.length(); i++) {
			View itemView = getActivity().getLayoutInflater().inflate(
					R.layout.tab1_share_list_item_comment_item, null);
			TextView usernameTextView = (TextView) itemView
					.findViewById(R.id.comment_name);
			TextView contentTextView = (TextView) itemView
					.findViewById(R.id.comment_content);
			try {
				usernameTextView.setText(array.getJSONObject(i).getString(
						"username")
						+ ":");
				contentTextView.setText(array.getJSONObject(i).getString(
						"commentcontent"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			content.addView(itemView);
		}
	}

	private void addLikeList(TextView view, JSONArray array) {
		view.setText("");
		boolean isFrist = true;
		for (int i = 0; i < array.length(); i++) {
			try {
				if (isFrist) {
					view.setText(array.getString(i));
					isFrist = false;
				} else
					view.setText(view.getText() + "," + array.getString(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void comment(int postId, final String dishComment, final int index) {
		JSONObject jsonObject = new JSONObject();
		final JSONObject js = new JSONObject();
		try {
			jsonObject.put("userId",
					UserInfo.getInstance().userid);
			jsonObject.put("postId", postId);
			jsonObject.put("sessionId", UserInfo.getInstance().sessionid);
			jsonObject.put("commentContent", dishComment);
			js.put("username", UserInfo.getInstance().username);
			js.put("commentcontent", dishComment);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				"http://110.84.129.130:8080/Yuf/comment/newComment",
				jsonObject, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							if (response.getInt("code") == 0) {
								Toast.makeText(getActivity(), "评论成功",
										Toast.LENGTH_SHORT).show();
								jsonArray.getJSONObject(index)
										.getJSONArray("CommentList").put(js);
								mAdaAdapter.notifyDataSetChanged();
							} else {
								Toast.makeText(getActivity(), "评论失败",
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
					}
				});
		MyApplication.requestQueue.add(request);
		MyApplication.requestQueue.start();
	}

	private void like(int postid, final int index) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("userId",
					UserInfo.getInstance().userid);
			jsonObject.put("postId", postid);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				"http://110.84.129.130:8080/Yuf/like/insertLike", jsonObject,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						try {
							if (response.getString("insertLike").equals(
									"success")) {
								Toast.makeText(getActivity(), "点赞成功",
										Toast.LENGTH_SHORT).show();
								jsonArray.getJSONObject(index)
										.getJSONArray("likeUsersList")
										.put(UserInfo.getInstance().username);
								mAdaAdapter.notifyDataSetChanged();
							} else {
								Toast.makeText(getActivity(), "点赞失败",
										Toast.LENGTH_SHORT).show();

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
					}
				});
		MyApplication.requestQueue.add(request);
		MyApplication.requestQueue.start();
	}

}
