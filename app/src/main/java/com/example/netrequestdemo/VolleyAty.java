package com.example.netrequestdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.netrequestdemo.utils.HttpUrlConfig;

import java.util.HashMap;
import java.util.Map;

public class VolleyAty extends Activity {
	private ImageView imageview1;
	private ImageView imageview2;
	private NetworkImageView networkImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.volley_request);
		/** 获取控件、图片url地址 */
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		networkImageView = (NetworkImageView) findViewById(R.id.networkImageview);
		String url = HttpUrlConfig.url;

		StringRequest request = new StringRequest(Request.Method.GET,url.toString(),new Response.Listener<String>() {

			@Override
			public void onResponse(String s) {
				Log.v("XPC","s"+s);
			}
		},new Response.ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError volleyError) {
				Log.v("XPC","volleyError"+volleyError.toString());
			}
		}){
			@Override
			protected Response<String> parseNetworkResponse(NetworkResponse response) {
				return super.parseNetworkResponse(response);
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
					Map<String, String> headers = super.getHeaders();
				if(headers.size() == 0){
					headers = new HashMap<>();
					headers.put("User-Agent", "okhttp/2.5.0");
				}

					return headers;
			}
		};
		DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(50 * 1000, 0, 3f);
		request.setRetryPolicy(retryPolicy);
		MyApplication.getHttpRequestQueue().add(request);
		MyApplication.getHttpRequestQueue().start();

	}

}
