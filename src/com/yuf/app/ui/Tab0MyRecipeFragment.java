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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dodola.model.DuitangInfo;
import com.dodowaterfall.widget.ScaleImageView;
import com.example.android.bitmapfun.util.ImageFetcher;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener;

@SuppressLint("ValidFragment")
public class Tab0MyRecipeFragment extends Fragment  implements IXListViewListener{
	
	private JSONArray mdataArray;//JSONArrray数据
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
//点击进入菜单的detail
	    mAdapterView.setOnItemClickListener(new OnItemClickListener() {
	    	
	    	@Override
	    	public void onItemClick(PLA_AdapterView<?> parent, View view,int position, long id) {
	    		Intent intent=new Intent(Main.mainActivity,Tab0FoodActivity.class);
	    		Bundle bundle = new Bundle();                           //创建Bundle对象   
	    		try {
	    			bundle.putString("dishid",mdataArray.getJSONObject(position-1).getString("dishid") );
	    			bundle.putString("dishname",mdataArray.getJSONObject(position-1).getString("dishname") );
	    			bundle.putDouble("dishprice",mdataArray.getJSONObject(position-1).getDouble("dishprice") );
	    		} catch (JSONException e) {
	    			e.printStackTrace();
	    		}     //装入数据   
	    		intent.putExtras(bundle);                                //把Bundle塞入Intent里面  
	    		startActivity(intent);
	    	}
	    });
	    
        mAdapter = new StaggeredAdapter(this.getActivity(), mAdapterView);
        mImageFetcher = new ImageFetcher(this.getActivity(), 240);
	    mImageFetcher.setLoadingImage(R.drawable.empty_photo);
//数据填充初始化
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
		return  view;
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
	                holder.tab0_comment_text = (TextView)convertView.findViewById(R.id.tab0_comment_text);
	                holder.tab0_collection_text = (TextView)convertView.findViewById(R.id.tab0_collection_text);
	                convertView.setTag(holder);
	            }

	            holder = (ViewHolder) convertView.getTag();
	            holder.tab0_imageView.setImageWidth(duitangInfo.getWidth());
                holder.tab0_imageView.setImageHeight(duitangInfo.getHeight());
	            holder.tab0_nameOfDish.setText(duitangInfo.getMsg());
	            holder.tab0_comment_text.setText(duitangInfo.getDishcommentnum()+"");
	            holder.tab0_collection_text.setText(duitangInfo.getDishcollectionnum()+"");
	            mImageFetcher.loadImage(duitangInfo.getIsrc(), holder.tab0_imageView);
	            return convertView;
	        }

	        class ViewHolder {
	        	TextView tab0_nameOfDish;//菜名
	            ScaleImageView tab0_imageView;//图片
	            TextView tab0_comment_text;//评论
	            TextView tab0_collection_text;//收藏	            
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
