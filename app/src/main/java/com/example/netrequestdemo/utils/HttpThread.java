package com.example.netrequestdemo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

public class HttpThread extends Thread {
	private String url;
	private ImageView imageView;
	private Handler handler;

	public HttpThread(String url, ImageView imageView, Handler handler) {
		super();
		this.url = url;
		this.imageView = imageView;
		this.handler = handler;
	}

	@Override
	public void run() {
		super.run();
		try {
			URL httpUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) httpUrl
					.openConnection();

			connection.setReadTimeout(5000);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// 得到可以读取文件的输入流
			InputStream in = connection.getInputStream();
			byte[] data = readInputStream(in);
			String result = new String(data,"UTF-8");
			Log.v("XPC","result="+result);
//			FileOutputStream out = null;
//			File downLoadFile = null;
//			// 文件目录
//			String fileName = String.valueOf(System.currentTimeMillis());
//			if (Environment.getExternalStorageState().equals(
//					Environment.MEDIA_MOUNTED)) {
//				// sd卡目录
//				File parent = Environment.getExternalStorageDirectory();
//				downLoadFile = new File(parent, fileName);
//
//				out = new FileOutputStream(downLoadFile);
//			}
//
//			byte[] b = new byte[1024];
//			int len;
//			if (out != null) {
//				while ((len = in.read(b)) != -1) {
//					out.write(b, 0, len);
//
//				}
//			}
//
//			final Bitmap bitmap = BitmapFactory.decodeFile(downLoadFile
//					.getAbsolutePath());
//			;
//
//			handler.post(new Runnable() {
//
//				@Override
//				public void run() {
//					// 更新主线程
//					imageView.setImageBitmap(bitmap);
//				}
//			});
		} catch (Exception e) {
			Log.v("XPC","result="+Log.getStackTraceString(e));

			e.printStackTrace();
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
