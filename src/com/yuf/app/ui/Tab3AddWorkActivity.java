package com.yuf.app.ui;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;


import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yuf.app.MyApplication;
import com.yuf.app.http.extend.PostFile;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Tab3AddWorkActivity extends Activity {
	private ImageView backImageView;
	private Button photoButton;
	private Button publicButton;
	private AutoCompleteTextView nameEditText;
	private TextView timeTextView;
	private EditText shareContentEditText;
	private String saveDir;
	private File file;
	private Bitmap photo;
	private ImageView foodImageView;
	private String picName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.tab3_add_work);
	saveDir= Environment.getExternalStorageDirectory()
			.getPath() + "/temp_image";
	photoButton=(Button)findViewById(R.id.tab3_addwork_photo_button);
	publicButton=(Button)findViewById(R.id.tab3_addwork_public_button);
	nameEditText=(AutoCompleteTextView)findViewById(R.id.tab3_addwork_name);
	timeTextView=(TextView)findViewById(R.id.tab3_addwork_time_textview);
	shareContentEditText=(EditText)findViewById(R.id.tab3_addwork_content_textview);
	foodImageView=(ImageView)findViewById(R.id.tab3_addwork_photo_image);
	picName= MyApplication.userid+String.valueOf(System.currentTimeMillis())+".jpg";
	
	publicButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			JSONObject json=new JSONObject();
			try {
				json.put("userId",Integer.valueOf( MyApplication.userid));
				json.put("postTitle", nameEditText.getText().toString());
				
				long currentTime = System.currentTimeMillis();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(currentTime);
				json.put("postTime",formatter.format(date));
				json.put("postContent", shareContentEditText.getText().toString());
				json.put("postPicture",picName);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		JsonObjectRequest request=new JsonObjectRequest(Method.POST, "http://110.84.129.130:8080/Yuf/post/insertPost", json, new com.android.volley.Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				try {
					if (response.getString("insertPost").equals("success")) {
						
						UploadFilesTask task=new UploadFilesTask();
						task.execute(file);
					}
					else {
						Toast.makeText(Tab3AddWorkActivity.this, "分享失败",
								Toast.LENGTH_SHORT).show();
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new com.android.volley.Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
MyApplication.requestQueue.add(request);
MyApplication.requestQueue.start();
		}
	});
	photoButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			destoryImage();
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)) {
				file = new File(saveDir,picName);
				file.delete();
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
						Toast.makeText(Tab3AddWorkActivity.this, "照片创建失败!",
								Toast.LENGTH_LONG).show();
						return;
					}
				}
				Intent intent = new Intent(
						"android.media.action.IMAGE_CAPTURE");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
				startActivityForResult(intent, 1);
			} else {
				Toast.makeText(Tab3AddWorkActivity.this, "sdcard无效或没有插入!",
						Toast.LENGTH_SHORT).show();
			}
		
		}
	});

	
	backImageView=(ImageView)findViewById(R.id.tab3_add_work_back_imageview);
	backImageView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			onBackPressed();
			// TODO Auto-generated method stub
			
		}
	});
}
	private void destoryImage() {
		if (photo != null) {
			photo.recycle();
			photo = null;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK) {
			if (file != null && file.exists()) {
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inSampleSize = 2;
				photo = BitmapFactory.decodeFile(file.getPath(), option);
				foodImageView.setImageBitmap(photo);
			}
		}
	}
	@Override
	protected void onDestroy() {
		destoryImage();
		super.onDestroy();
	}
	
	 private class UploadFilesTask extends AsyncTask<File, Integer, Long> {


	     protected void onPostExecute(Long result) {
	    	 
	    		Toast.makeText(Tab3AddWorkActivity.this, "分享成功",
						Toast.LENGTH_SHORT).show();
	     }

		@Override
		protected Long doInBackground(File... params) {
			// TODO Auto-generated method stub
			PostFile.uploadFile(params[0], "http://110.84.129.130:8080/Yuf/post/upload/picture");
			return null;
		}
	 }


}
