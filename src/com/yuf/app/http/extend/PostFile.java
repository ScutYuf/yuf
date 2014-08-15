//package com.yuf.app.http.extend;
//
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.UUID;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Environment;
//import android.util.Log;
//
//public class PostFile {
//	 //上传代码，第一个参数，为要使用的URL，第二个参数，为表单内容，第三个参数为要上传的文件，可以上传多个文件，这根据需要页定
//		private static final String TAG = "uploadFile";
//		private static final int TIME_OUT = 10*1000;   //超时时间
//		private static final String CHARSET = "utf-8"; //设置编码
//		private static Bitmap photo;
//		private Intent intent;
//		/**
//		 * android上传文件到服务器
//		 * @param file  需要上传的文件
//		 * @param RequestURL  请求的rul
//		 * @return  返回响应的内容
//		 */
//		public static String uploadFile(File file,String RequestURL)
//		{
//			String result = null;
//			String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成
//			String PREFIX = "--" , LINE_END = "\r\n"; 
//			String CONTENT_TYPE = "multipart/form-data";   //内容类型
//			
//			try {
//				URL url = new URL(RequestURL);
//				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//				conn.setReadTimeout(TIME_OUT);
//				conn.setConnectTimeout(TIME_OUT);
//				conn.setDoInput(true);  //允许输入流
//				conn.setDoOutput(true); //允许输出流
//				conn.setUseCaches(false);  //不允许使用缓存
//				conn.setRequestMethod("POST");  //请求方式
//				conn.setRequestProperty("Charset", CHARSET);  //设置编码
//				conn.setRequestProperty("connection", "keep-alive");   
//				conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY); 
//				
//				if(file!=null)
//				{
//					/**
//					 * 当文件不为空，把文件包装并且上传
//					 */
//					DataOutputStream dos = new DataOutputStream( conn.getOutputStream());
//					StringBuffer sb = new StringBuffer();
//					sb.append(PREFIX);
//					sb.append(BOUNDARY);
//					sb.append(LINE_END);
//					/**
//					 * 这里重点注意：
//					 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
//					 * filename是文件的名字，包含后缀名的   比如:abc.png  
//					 */
//					sb.append("Content-Disposition: form-data; name=\"fup\"; filename=\""+file.getName()+"\""+LINE_END); 
//					sb.append("Content-Type: image/pjpeg; charset="+CHARSET+LINE_END);
//					sb.append(LINE_END);
//					dos.write(sb.toString().getBytes());
//					InputStream is = new FileInputStream(file);
//					byte[] bytes = new byte[1024];
//					int len = 0;
//					while((len=is.read(bytes))!=-1)
//					{
//						dos.write(bytes, 0, len);
//					}
//					is.close();
//					dos.write(LINE_END.getBytes());
//					byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
//					dos.write(end_data);
//					dos.flush();
//					/**
//					 * 获取响应码  200=成功
//					 * 当响应成功，获取响应的流  
//					 */
//					int res = conn.getResponseCode();  
//					Log.i(TAG, "response code:"+res);
//					if(res==200)
//					{
//						Log.e(TAG, "request success");
//						InputStream input =  conn.getInputStream();
//						StringBuffer sb1= new StringBuffer();
//						int ss ;
//						while((ss=input.read())!=-1)
//						{
//							sb1.append((char)ss);
//						}
//						result = sb1.toString();
//						Log.i(TAG, "result : "+ result);
//					}
//					else{
//						Log.i(TAG, "request error");
//					}
//				}
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return result;
//		}
//		
//		
//		public void takePhotoAndPost() {
//			destoryBimap();
//            String state = Environment.getExternalStorageState();
//            if (state.equals(Environment.MEDIA_MOUNTED)) {
//            	intent = new Intent("android.media.action.IMAGE_CAPTURE");
//            	startActivityForResult(intent, cameraCode);
//            } else {
//            	Toast.makeText(SsActivity.this,"请插入SD卡", Toast.LENGTH_LONG).show();
//            }
//			break;
//		}
//		
//		@Override  
//		protected void onActivityResult(int requestCode, int resultCode, Intent data)  
//		{  
//		    Uri uri = data.getData();  
//		    if (uri != null) {  
//		        this.photo = BitmapFactory.decodeFile(uri.getPath());  
//		    }  
//		    if (this.photo == null) {  
//		        Bundle bundle = data.getExtras();  
//		        if (bundle != null) {  
//		            this.photo = (Bitmap) bundle.get("data");  
//		        } else {  
//		            Toast.makeText(MainActivity.this, "拍照失败", Toast.LENGTH_LONG).show();  
//		            return;  
//		        }  
//		    }  
//		  
//		    FileOutputStream fileOutputStream = null;  
//		    try {  
//		        // 获取 SD 卡根目录  
//		        String saveDir = Environment.getExternalStorageDirectory() + "/meitian_photos";  
//		        // 新建目录  
//		        File dir = new File(saveDir);  
//		        if (! dir.exists()) dir.mkdir();  
//		        // 生成文件名  
//		        SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");  
//		        String filename = "MT" + (t.format(new Date())) + ".jpg";  
//		        // 新建文件  
//		        File file = new File(saveDir, filename);  
//		        // 打开文件输出流  
//		        fileOutputStream = new FileOutputStream(file);  
//		        // 生成图片文件  
//		        this.photo.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);  
//		        // 相片的完整路径  
//		        this.picPath = file.getPath();  
//		        ImageView imageView = (ImageView) findViewById(R.id.showPhoto);  
//		        imageView.setImageBitmap(this.photo);  
//		    } catch (Exception e) {  
//		        e.printStackTrace();  
//		    } finally {  
//		        if (fileOutputStream != null) {  
//		            try {  
//		                fileOutputStream.close();  
//		            } catch (Exception e) {  
//		                e.printStackTrace();  
//		            }  
//		        }  
//		    }  
//		}  
//		
//		private void destoryBimap() { 
//	        if (photo != null && !photo.isRecycled()) { 
//	            photo.recycle(); 
//	            photo = null; 
//	        } 
//	    } 
//}
