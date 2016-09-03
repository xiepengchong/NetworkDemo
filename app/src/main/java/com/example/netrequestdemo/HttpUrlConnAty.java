package com.example.netrequestdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.netrequestdemo.utils.HttpThread;
import com.example.netrequestdemo.utils.HttpUrlConfig;

public class HttpUrlConnAty extends Activity {

	private ImageView imageView;  
	private Handler handler = new Handler();  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.http_url_conn);
		imageView = (ImageView) findViewById(R.id.imageView);  
        new HttpThread(HttpUrlConfig.url ,imageView, handler).start();
	}
}