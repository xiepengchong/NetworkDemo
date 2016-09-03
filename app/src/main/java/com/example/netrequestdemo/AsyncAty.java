package com.example.netrequestdemo;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.netrequestdemo.utils.HttpUrlConfig;

public class AsyncAty extends Activity {

	private ImageView imageView;
	private ProgressBar progressBar;
private static String url = HttpUrlConfig.url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.async_request);
		imageView = (ImageView) findViewById(R.id.imageView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		new MyAsycTask().execute(url);
	}

	/**
	 * 第一个参数是运行参数 第二个参数是progress是加载的进度 第三个参数是返回的类型是bitmap
	 * 
	 */
	class MyAsycTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			progressBar.setVisibility(View.GONE);
			imageView.setImageBitmap(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// 因为只传一个参数
			Bitmap bitmap = null;
			String url = params[0];
			URLConnection connection;
			InputStream inputStream;
			try {
				// 打开连接
				connection = new URL(url).openConnection();
				// 获取输入流，读取网络数据
				inputStream = connection.getInputStream();

				byte [] data = readInputStream(inputStream);
				String result = new String(data);
				Log.v("XPC","result="+result);
//				// 包装下
//				BufferedInputStream bis = new BufferedInputStream(inputStream);
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				// 直接返回一个图片
//				bitmap = BitmapFactory.decodeStream(bis);
//				// 关闭流
//				inputStream.close();
//				bis.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
				Log.v("XPC","MalformedURLException="+Log.getStackTraceString(e));

			} catch (IOException e) {
				Log.v("XPC","IOException="+Log.getStackTraceString(e));
				e.printStackTrace();
			} catch (Exception e) {
				Log.v("XPC","reExceptionsult="+Log.getStackTraceString(e));
				e.printStackTrace();
			}
			return bitmap;
		}

	}

	public static byte[] readInputStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len = inStream.read(buffer)) !=-1 ){
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();//网页的二进制数据
		outStream.close();
		inStream.close();
		return data;
	}
}
