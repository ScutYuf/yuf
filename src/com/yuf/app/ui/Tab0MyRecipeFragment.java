package com.yuf.app.ui;

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
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.dodola.model.DuitangInfo;
import com.dodowaterfall.widget.ScaleImageView;
import com.example.android.bitmapfun.util.ImageFetcher;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;

@SuppressLint("ValidFragment")
public class Tab0MyRecipeFragment extends Fragment  implements IXListViewListener{
	
	private JSONArray mdataArray;//JSONArrray数据

	private int currentPageIndex;
//瀑布流
	private ImageFetcher mImageFetcher;
	private XListView mAdapterView = null;
	private StaggeredAdapter mAdapter = null;
	
	public Tab0MyRecipeFragment(JSONArray _dataArray) {
		mdataArray=_dataArray;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("liow", "myrecipefragment");
		
		View view=(View)inflater.inflate(R.layout.tab0_recipe_fragment00,container,false);
		mAdapterView = (XListView)view.findViewById(R.id.list);
	    mAdapterView.setPullLoadEnable(true);
	    mAdapterView.setXListViewListener(this);
	    mAdapterView.setOnItemClickListener(new OnItemClickListener() {
	    	
	    	@Override
	    	public void onItemClick(PLA_AdapterView<?> parent, View view,
	    			int position, long id) {
	    		Toast.makeText(getActivity(), "CDC", Toast.LENGTH_SHORT).show();
	    		Intent intent=new Intent(Main.mainActivity,Tab0FoodActivity.class);
	    		Bundle bundle = new Bundle();                           //创建Bundle对象   
	    		try {
	    			bundle.putString("dishid",mdataArray.getJSONObject(position).getString("dishid") );
	    			bundle.putString("dishname",mdataArray.getJSONObject(position).getString("dishname") );
	    			bundle.putDouble("dishprice",mdataArray.getJSONObject(position).getDouble("dishprice") );
	    			
	    		} catch (JSONException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}     //装入数据   
	    		intent.putExtras(bundle);                                //把Bundle塞入Intent里面  
	    		startActivity(intent);
	    	}
	    });
	    
        mAdapter = new StaggeredAdapter(this.getActivity(), mAdapterView);
        mImageFetcher = new ImageFetcher(this.getActivity(), 240);
	    mImageFetcher.setLoadingImage(R.drawable.empty_photo);

		return  view;
	}
	@Override
	public void onResume() {
        super.onResume();
        mImageFetcher.setExitTasksEarly(false);
        mAdapterView.setAdapter(mAdapter);
        List<DuitangInfo> duitangs = new ArrayList<DuitangInfo>();
        try {
               for (int i = 0; i < mdataArray.length(); i++) {
               JSONObject newsInfoLeftObject = mdataArray.getJSONObject(i);
               DuitangInfo newsInfo1 = new DuitangInfo();
               newsInfo1.setAlbid(newsInfoLeftObject.isNull("dishid") ? "": newsInfoLeftObject.getString("dishid"));
			   newsInfo1.setIsrc(newsInfoLeftObject.isNull("dishpicurl") ? "": "http://110.84.129.130:8080/Yuf"+newsInfoLeftObject.getString("dishpicurl"));
			   newsInfo1.setMsg(newsInfoLeftObject.isNull("dishname") ? "": newsInfoLeftObject.getString("dishname"));
			   newsInfo1.setDishcommentnum(newsInfoLeftObject.getInt("dishcommentnum"));
			   newsInfo1.setDishcollectionnum(newsInfoLeftObject.getInt("dishcollectionnum"));
				   newsInfo1.setHeight(150);
	               duitangs.add(newsInfo1);
	                    }
	            } catch (JSONException e) {
	                e.printStackTrace();
	        }
	        mAdapter.addItemLast(duitangs);
            mAdapter.notifyDataSetChanged();
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
	                holder.tab0_comment_button = (Button)convertView.findViewById(R.id.tab0_comment_button);
	                holder.tab0_collection_button = (Button)convertView.findViewById(R.id.tab0_collection_button);
	                convertView.setTag(holder);
	            }

	            holder = (ViewHolder) convertView.getTag();
	            holder.tab0_imageView.setImageWidth(duitangInfo.getWidth());
                holder.tab0_imageView.setImageHeight(duitangInfo.getHeight());
	            holder.tab0_nameOfDish.setText(duitangInfo.getMsg());
	            holder.tab0_comment_button.setText(String.format("评论(%s)",String.valueOf(duitangInfo.getDishcommentnum())));
	            holder.tab0_collection_button.setText(String.format("收藏(%s)",String.valueOf(duitangInfo.getDishcollectionnum())));
	            mImageFetcher.loadImage(duitangInfo.getIsrc(), holder.tab0_imageView);
	            
	            holder.tab0_imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						
					}
				});
	            return convertView;
	        }

	        class ViewHolder {
	        	TextView tab0_nameOfDish;//菜名
	            ScaleImageView tab0_imageView;//图片
	            Button tab0_comment_button;//评论
	            Button tab0_collection_button;//收藏	            
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
	    
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
      mAdapterView.stopRefresh();
  	  mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore() {
    	mAdapterView.stopLoadMore();
      	mAdapter.notifyDataSetChanged();
    }

}
