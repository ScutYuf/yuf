package com.yuf.app.adapter;

import com.yuf.app.ui.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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
//         ImageView imageView; 
//            if (convertView == null) { 
//                imageView = new ImageView(context); 
//                imageView.setLayoutParams(new GridView.LayoutParams(75, 75));//设置ImageView对象布局 
//                imageView.setAdjustViewBounds(false);//设置边界对齐 
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置刻度的类型 
//                imageView.setPadding(8, 8, 8, 8);//设置间距 
//            }  
//            else { 
//                imageView = (ImageView) convertView; 
//            } 
//            imageView.setImageResource(imgs[position]);//为ImageView设置图片资源 
//            
    	View view;
    	if (convertView==null) {
			
    		LayoutInflater myinflater=LayoutInflater.from(context);
    		 view=myinflater.inflate(R.layout.food_material_gridview_item, null);
		}
    	else {
			view=convertView;
		}
    	//加载数据
            
            
            return view; 
    } 

}
