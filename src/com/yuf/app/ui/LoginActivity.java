package com.yuf.app.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yuf.app.MyApplication;

import android.R.string;
import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import com.android.volley.Response;
import android.util.Log;
import com.android.volley.toolbox.StringRequest;
public class LoginActivity extends Activity {

	private Button loginBtn;
	private Button logonBtn;
	private EditText accountET;
	private EditText passwordET;
	private Button testBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		loginBtn=(Button)findViewById(R.id.login_button);
		logonBtn=(Button)findViewById(R.id.logon_button);
		accountET=(EditText)findViewById(R.id.account_editText);
		accountET=(EditText)findViewById(R.id.password_editText);
		testBtn=(Button)findViewById(R.id.test_button_login);
		testBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(),Main.class);
				startActivity(intent);
				finish();
			}
		});
		
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("liow","loginBtn onclick");
			JSONObject logJsonObject=new JSONObject();
			try{
			logJsonObject.put("userAccount",accountET.getText().toString());
			logJsonObject.put("userPassword",passwordET.getText().toString());
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			JsonObjectRequest request=new JsonObjectRequest(Method.POST, "http://110.84.129.130:8080/Yuf/user/login", logJsonObject,  new Response.Listener<JSONObject>()  
            {  
  
                @Override  
                public void onResponse(JSONObject response)  
                {  
                	try {
						if(response.get("login").equals("success"))
						{
							Intent intent=new Intent(getApplicationContext(),Main.class);
							startActivity(intent);
							finish();
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    Log.e("TAG", response.toString());  
                }  
            }, new Response.ErrorListener()  
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
			});
		
		
		logonBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent=new Intent(getApplicationContext(), LogonActivity.class);
				startActivity(intent);
			}
		});
	}

private void getUserInfo(String user_id)
{

	
	JsonObjectRequest request=new JsonObjectRequest(Method.GET, "http://110.84.129.130:8080/Yuf/user/getUser/"+user_id, null,  new Response.Listener<JSONObject>()  
    {  

        @Override  
        public void onResponse(JSONObject response)  
        {  
        	
            Log.e("TAG", response.toString());  
        }  
    }, new Response.ErrorListener()  
    {  

        @Override  
        public void onErrorResponse(VolleyError error)  
        {  
            Log.e("TAG", error.getMessage(), error);  
        }  
    });
	MyApplication.requestQueue.add(request);
	Log.d("liow","request start");
	MyApplication.requestQueue.start();
}

}
