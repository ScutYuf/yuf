package com.yuf.app.ui;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import org.json.JSONArray;

import com.example.android.bitmapfun.util.ImageFetcher;
import com.yuf.app.ui.Tab0RecommendFragment.StaggeredAdapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
@SuppressLint("ValidFragment")
public class Tab4SearchFragment extends Fragment  implements IXListViewListener{
	
	private JSONArray mdataArray;//JSONArrray数据
//瀑布流
	private ImageFetcher mImageFetcher;
	private XListView mAdapterView = null;
	private StaggeredAdapter mAdapter = null;
	public Tab4SearchFragment(JSONArray _dataArray) {
		mdataArray=_dataArray;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}

}
