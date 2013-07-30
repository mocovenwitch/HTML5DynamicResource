package com.moco.html5dynamicresource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	WebView webview = null;
	Boolean LOADTRUE = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		webview = (WebView)findViewById(R.id.web);
		webview.setWebViewClient(new MyWebView());
		webview.loadUrl("file:///android_asset/www/index.html");
	}

	private class MyWebView extends WebViewClient {
		@SuppressLint("NewApi")
		@Override
        public WebResourceResponse shouldInterceptRequest (final WebView view, String url) {
        	Log.i("shouldInterceptRequest", "url: " + url);
        	if (url.contains(".css")) {
                return getCssWebResourceResponseFromExternalStorage();
            } else {
                return super.shouldInterceptRequest(view, url);
            }
        }

        /**
         * Return WebResourceResponse with CSS markup from a String. 
         */
        private WebResourceResponse getCssWebResourceResponseFromString() {
        	Log.i("getCssWebResourceResponseFromString", "");
        	String style = "body { background-color: #123456; }";
        	InputStream is = new ByteArrayInputStream(style.getBytes());
            return getUtf8EncodedCssWebResourceResponse(is);
        }

        /**
         * Return WebResourceResponse with CSS markup from an asset (e.g. "assets/www/resource/new-theme.css"). 
         */
		private WebResourceResponse getCssWebResourceResponseFromAsset() {
            try {
            	Log.i("getCssWebResourceResponseFromAsset", "getCssWebResourceResponseFromAsset");
                return getUtf8EncodedCssWebResourceResponse(getAssets().open("www/resource/new-theme.css"));
            } catch (IOException e) {
                return null;
            }
        }
        
        /**
         * Return WebResourceResponse with CSS markup from external storage (e.g. "mnt/sdcard/files/external-theme.css"). 
         */
        private WebResourceResponse getCssWebResourceResponseFromExternalStorage() {
            try {
            	Log.i("getCssWebResourceResponseFromExternalStorage", "getCssWebResourceResponseFromExternalStorage");
            	File file = new File(Environment.getExternalStorageDirectory().getPath() + "/files/external-theme.css");
            	FileInputStream cssfile = new FileInputStream(file);
                return getUtf8EncodedCssWebResourceResponse(cssfile);
            } catch (IOException e) {
                return null;
            }
        }

        @SuppressLint("NewApi")
		private WebResourceResponse getUtf8EncodedCssWebResourceResponse(InputStream data) {
            return new WebResourceResponse("text/css", "UTF-8", data);
        }

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
