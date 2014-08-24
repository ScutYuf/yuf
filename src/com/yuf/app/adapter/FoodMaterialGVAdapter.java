package com.yuf.app.adapter;

import com.yuf.app.MyApplication;
import com.yuf.app.mywidget.MyDialog;
import com.yuf.app.ui.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class FoodMaterialGVAdapter extends BaseAdapter {

	 //上下文对象 
    private Context context; 
    //测试图片数组 
    private Integer[] imgs = { 
            R.drawable.food1, R.drawable.food1, R.drawable.food1,  
            R.drawable.food1, R.drawable.food1
    }; 
    public FoodMaterialGVAdapter(Context context){ 
        this.context = context; 
    } 
    public int getCount() { 
        return imgs.length; 
    } 

    public Object getItem(int item) { 
        return item; 
    } 

    public long getItemId(int id) { 
        return id; 
    } 
     
    //创建View方法 
    @SuppressLint("ViewHolder") public View getView(int position, View convertView, ViewGroup parent) { 
       
    	View view;
    	if (convertView==null) {
			
    		LayoutInflater myinflater=LayoutInflater.from(context);
    		 view=myinflater.inflate(R.layout.tab2_food_material_gridview_item, null);
		}
    	else {
			view=convertView;
		}
    	//加载数据
            
            view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					MyDialog myDialog=new MyDialog(context);
					myDialog.showDialog(R.layout.material_detail_dialg,0, 0);
					// TODO Auto-generated method stub
					
				}
			});
            return view; 
    } 

}
