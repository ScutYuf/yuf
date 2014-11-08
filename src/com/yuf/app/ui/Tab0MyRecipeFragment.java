package com.yuf.app.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.dodola.model.DuitangInfo;
import com.dodowaterfall.Helper;
import com.dodowaterfall.widget.ScaleImageView;
import com.example.android.bitmapfun.util.ImageFetcher;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.http.extend.BitmapCache;
import com.yuf.app.mywidget.FoodViewPage;
import com.yuf.app.ui.indicator.CirclePageIndicator;

@SuppressLint("ValidFragment")
public class Tab0MyRecipeFragment extends Fragment  implements IXListViewListener{

//
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
	//瀑布流
			private ImageFetcher mImageFetcher;
		    private XListView mAdapterView = null;
		    private StaggeredAdapter mAdapter = null;
		    private int currentPage = 0;
		    ContentTask task = new ContentTask(this.getActivity(), 2);
	public Tab0MyRecipeFragment(JSONArray _dataArray) {
		mdataArray=_dataArray;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("liow", "myrecipefragment");
		mImageLoader = new ImageLoader(MyApplication.requestQueue, new BitmapCache()); 
		
		View view=(View)inflater.inflate(R.layout.tab0_recipe_fragment00,container,false);
		mAdapterView = (XListView)view.findViewById(R.id.list);
	    mAdapterView.setPullLoadEnable(true);
	    mAdapterView.setXListViewListener(this);
        mAdapter = new StaggeredAdapter(this.getActivity(), mAdapterView);
        mImageFetcher = new ImageFetcher(this.getActivity(), 240);
	    mImageFetcher.setLoadingImage(R.drawable.empty_photo);
		
//	     View view=inflater.inflate(R.layout.tab0_recipe_fragment,container,false);
//		difficultyTextView=(TextView)view.findViewById(R.id.tab0_recipe_fragment__dishdifficulty_textview);
//		doseTextView=(TextView)view.findViewById(R.id.tab0_recipe_fragment_dishamount_textview);
//		timeTextView=(TextView)view.findViewById(R.id.tab0_recipe_fragment_dishcooktime_textview);
//		nameTextView=(TextView)view.findViewById(R.id.tab0_recipe_fragment_foodname_textview);
//		skillTextView=(TextView)view.findViewById(R.id.tab0_recipe_fragment_skill_textview);
//		
//		commentbuttoButton=(Button)view.findViewById(R.id.comment_button);
//		commentbuttoButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				showDialg();
//			}
//		});
//		
//		collectionButton=(Button)view.findViewById(R.id.collection_button);
//		collectionButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//				addCollectionRelationShip();
//				
//				
//
//			}
//		});
//		
//		
//		detailButton=(Button)view.findViewById(R.id.detail_button);
//		detailButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				
//				Intent intent=new Intent(Main.mainActivity,
//						Tab0FoodActivity.class);
//				Bundle bundle = new Bundle();                           //创建Bundle对象   
//				try {
//					bundle.putString("dishid",mdataArray.getJSONObject(currentPageIndex).getString("dishid") );
//					bundle.putString("dishname",mdataArray.getJSONObject(currentPageIndex).getString("dishname") );
//					bundle.putDouble("dishprice",mdataArray.getJSONObject(currentPageIndex).getDouble("dishprice") );
//				
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}     //装入数据   
//				intent.putExtras(bundle);                                //把Bundle塞入Intent里面  
//				startActivity(intent);
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		
//		//
//		try {
//			initView();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		viewPager=(FoodViewPage)view.findViewById(R.id.recipe_fragment_viewpage);
//		viewPager.setAdapter(new OutsidePagerAdapter(mViews) );
//		viewPager.setOffscreenPageLimit(7);
//		foodindiactor=(CirclePageIndicator)view.findViewById(R.id.circle_page_indicator);
//		foodindiactor.setViewPager(viewPager);
//		foodindiactor.setOnPageChangeListener(new MyPageChangeAdapter1());
//	
		return  view;
	}
	
	protected void addCollectionRelationShip() {
		// TODO Auto-generated method stub
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("userId",Long.valueOf( UserInfo.getInstance().userid));
			jsonObject.put("sessionId", UserInfo.getInstance().sessionid);
			jsonObject.put("dishId", mdataArray.getJSONObject(currentPageIndex).getLong("dishid"));
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JsonObjectRequest request=new JsonObjectRequest(Method.POST, "http://110.84.129.130:8080/Yuf/collection/addCollection", jsonObject, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				try {
					if (response.getInt("code")==0) {
						
						int currentCollectionnum=mdataArray.getJSONObject(currentPageIndex).getInt("dishcollectionnum");
						currentCollectionnum++;
						mdataArray.getJSONObject(currentPageIndex).put("dishcollectionnum",currentCollectionnum );
						collectionButton.setText(String.format("收藏(%s)",String.valueOf(currentCollectionnum)));
						Toast toast = Toast.makeText(Tab0MyRecipeFragment.this.getActivity(),
							     "收藏成功", Toast.LENGTH_SHORT);
							   toast.setGravity(Gravity.CENTER, 0, 0);
							   toast.show();
					}
					else {
						Toast toast = Toast.makeText(Tab0MyRecipeFragment.this.getActivity(),
							     "已收藏", Toast.LENGTH_SHORT);
							   toast.setGravity(Gravity.CENTER, 0, 0);
							   toast.show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		},new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
		MyApplication.requestQueue.add(request);
		MyApplication.requestQueue.start();
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
		timeTextView.setText(jObject.getString("dishcooktime"));
		commentbuttoButton.setText(String.format("评论(%s)",String.valueOf(jObject.getInt("dishcommentnum"))));
		collectionButton.setText(String.format("收藏(%s)",String.valueOf(jObject.getInt("dishcollectionnum"))));
		nameTextView.setText(jObject.getString("dishname"));
		skillTextView.setText(jObject.getString("dishcookmethod"));
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
							
							int currentDishCommentnum=mdataArray.getJSONObject(currentPageIndex).getInt("dishcommentnum");
							currentDishCommentnum++;
							mdataArray.getJSONObject(currentPageIndex).put("dishcommentnum",currentDishCommentnum );
							commentbuttoButton.setText(String.format("评论(%s)",String.valueOf(currentDishCommentnum)));
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
				timeTextView.setText(jObject.getString("dishcooktime"));
				commentbuttoButton.setText(String.format("评论(%s)", String.valueOf(jObject.getInt("dishcommentnum"))));
				collectionButton.setText(String.format("收藏(%s)",String.valueOf(jObject.getInt("dishcollectionnum"))));
				nameTextView.setText(jObject.getString("dishname"));
				skillTextView.setText(jObject.getString("dishcookmethod"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		}
	
	 private class ContentTask extends AsyncTask<String, Integer, List<DuitangInfo>> {

	        private Context mContext;
	        private int mType = 1;

	        public ContentTask(Context context, int type) {
	            super();
	            mContext = context;
	            mType = type;
	        }

	        @Override
	        protected List<DuitangInfo> doInBackground(String... params) {
	            try {
	                return parseNewsJSON(params[0]);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            return null;
	        }

	        @Override
	        protected void onPostExecute(List<DuitangInfo> result) {
	            if (mType == 1) {
	                mAdapter.addItemTop(result);
	                mAdapter.notifyDataSetChanged();
	                mAdapterView.stopRefresh();

	            } else if (mType == 2) {
	                mAdapterView.stopLoadMore();
	                mAdapter.addItemLast(result);
	                mAdapter.notifyDataSetChanged();
	            }else if(mType == 3){
	            	  mAdapterView.stopRefresh();
	            	  mAdapterView.stopLoadMore();
	            	  mAdapter.notifyDataSetChanged();
	            }

	        }

	        @Override
	        protected void onPreExecute() {
	        }

	        public List<DuitangInfo> parseNewsJSON(String url) throws IOException {
	            List<DuitangInfo> duitangs = new ArrayList<DuitangInfo>();
	            String json = "";
	            if (Helper.checkConnection(mContext)) {
	                try {
	                    json = Helper.getStringFromUrl(url);

	                } catch (IOException e) {
	                    Log.e("IOException is : ", e.toString());
	                    e.printStackTrace();
	                    return duitangs;
	                }
	            }
	            Log.d("MainActiivty", "json:" + json);

	            try {
	                if (null != json) {
	                    JSONObject newsObject = new JSONObject(json);
	                	JSONArray json1 = newsObject.getJSONArray("dishsets");
						JSONArray blogsJson = json1.getJSONObject(0).getJSONArray("dishsetdetail");
	                    for (int i = 0; i < blogsJson.length(); i++) {
	               JSONObject newsInfoLeftObject = blogsJson.getJSONObject(i);
	               DuitangInfo newsInfo1 = new DuitangInfo();
	               newsInfo1.setAlbid(newsInfoLeftObject.isNull("dishid") ? "": newsInfoLeftObject.getString("dishid"));
				   newsInfo1.setIsrc(newsInfoLeftObject.isNull("dishpicurl") ? "": "http://110.84.129.130:8080/Yuf"+newsInfoLeftObject.getString("dishpicurl"));
				   newsInfo1.setMsg(newsInfoLeftObject.isNull("dishname") ? "": newsInfoLeftObject.getString("dishname"));
	               newsInfo1.setHeight(150);
	               duitangs.add(newsInfo1);
	                    }
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	            return duitangs;
	        }
	    }
	 /**
	     * 添加内容
	     * 
	     * @param pageindex
	     * @param type
	     *            1为下拉刷新 2为加载更多
	     */
	    private void AddItemToContainer(int pageindex, int type) {
	    	if(currentPage==0){
	    		 if (task.getStatus() != Status.RUNNING) {
//	               String url = "http://www.duitang.com/album/1733789/masn/p/" + pageindex + "/24/";
	           	String url = "http://110.84.129.130:8080/Yuf/dishset/getRecommendedDishset";
	               Log.d("MainActivity", "current url:" + url);
	               ContentTask task = new ContentTask(this.getActivity(), type);
	               task.execute(url);
	           }	
	    	}else{
	    		 if (task.getStatus() != Status.RUNNING) {
//	               String url = "http://www.duitang.com/album/1733789/masn/p/" + pageindex + "/24/";
	           	String url = "http://110.84.129.130:8080/Yuf/dishset/getRecommendedDishset";
	               Log.d("MainActivity", "current url:" + url);
	               ContentTask task = new ContentTask(this.getActivity(), 3);
	               task.execute(url);
	           }	
	    	}
	       
	    }

	    public class StaggeredAdapter extends BaseAdapter {
	        private Context mContext;
	        private LinkedList<DuitangInfo> mInfos;
	        private XListView mListView;

	        public StaggeredAdapter(Context context, XListView xListView) {
	            this.mContext = context;
	            this.mInfos = new LinkedList<DuitangInfo>();
	            this.mListView = xListView;
	        }

	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {

	            ViewHolder holder;
	            DuitangInfo duitangInfo = mInfos.get(position);

	            if (convertView == null) {
	                LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
	                convertView = layoutInflator.inflate(R.layout.infos_list, null);
	                holder = new ViewHolder();
	                holder.tab0_imageView = (ScaleImageView) convertView.findViewById(R.id.tab0_news_pic);
	                holder.tab0_nameOfDish = (TextView) convertView.findViewById(R.id.tab0_news_title);
	                holder.tab0_collection_button = (Button)convertView.findViewById(R.id.tab0_collection_button);
	                holder.tab0_comment_button = (Button)convertView.findViewById(R.id.tab0_comment_button);
	                convertView.setTag(holder);
	            }

	            holder = (ViewHolder) convertView.getTag();
	            holder.tab0_imageView.setImageWidth(duitangInfo.getWidth());
	            holder.tab0_imageView.setImageHeight(duitangInfo.getHeight());
	            holder.tab0_nameOfDish.setText(duitangInfo.getMsg());
	            mImageFetcher.loadImage(duitangInfo.getIsrc(), holder.tab0_imageView);
	            return convertView;
	        }

	        class ViewHolder {
	            ScaleImageView tab0_imageView;//图片
	            TextView tab0_nameOfDish;//菜名
	            Button tab0_collection_button;//收藏
	            Button tab0_comment_button;//评论
	        }

	        @Override
	        public int getCount() {
	            return mInfos.size();
	        }

	        @Override
	        public Object getItem(int arg0) {
	            return mInfos.get(arg0);
	        }

	        @Override
	        public long getItemId(int arg0) {
	            return 0;
	        }

	        public void addItemLast(List<DuitangInfo> datas) {
	            mInfos.addAll(datas);
	        }

	        public void addItemTop(List<DuitangInfo> datas) {
	            for (DuitangInfo info : datas) {
	                mInfos.addFirst(info);
	            }
	        }
	    }
	    @Override
		public void onResume() {
	        super.onResume();
	        mImageFetcher.setExitTasksEarly(false);
	        mAdapterView.setAdapter(mAdapter);
	        AddItemToContainer(currentPage, 2);
	    }

	    @Override
		public void onDestroy() {
	        super.onDestroy();

	    }

	    @Override
	    public void onRefresh() {
	        AddItemToContainer(++currentPage, 1);

	    }

	    @Override
	    public void onLoadMore() {
	        AddItemToContainer(++currentPage, 2);

	    }

}
