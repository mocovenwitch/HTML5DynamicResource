package com.moco.html5dynamicresource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringBufferInputStream;
import java.util.Random;

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
//		webview.loadUrl("http://www.mocoven.com");
		
		webview.loadUrl("file:///android_asset/www/index.html");
		
		
//		webview.loadUrl("file://" + Environment.getExternalStorageDirectory().getPath() + "/files/index.html");
		
//		webview.getSettings().setJavaScriptEnabled(true);
//		webview.getSettings().setBuiltInZoomControls(true);
//		webview.loadUrl("file://mnt/sdcard/files/index.html");
//		webview.loadUrl("http://www.google.com.hk");
		
	}

	private class MyWebView extends WebViewClient {
		@SuppressLint("NewApi")
		@Override
        public WebResourceResponse shouldInterceptRequest (final WebView view, String url) {
        	Log.i("--shouldInterceptRequest---", "url: " + url);
        	if (url.contains(".css")) {
                return getCssWebResourceResponseFromExternalStorage();
            } else {
                return super.shouldInterceptRequest(view, url);
            }
        }

        /**
         * Return WebResourceResponse with CSS markup from a String. 
         */
        @SuppressWarnings("deprecation")
        private WebResourceResponse getCssWebResourceResponseFromString() {
        	Log.i("------", "getCssWebResourceResponseFromString");
            return getUtf8EncodedCssWebResourceResponse(new StringBufferInputStream("body { background-color: #F781F3; }"));
        }

        /**
         * Return WebResourceResponse with CSS markup from an asset (e.g. "assets/style.css"). 
         */
        private WebResourceResponse getCssWebResourceResponseFromAsset() {
            try {
            	Log.i("------", "getCssWebResourceResponseFromAsset");
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
            	Log.i("------", "getCssWebResourceResponseFromAsset");
            	File file = new File(Environment.getExternalStorageDirectory().getPath() + "/files/external-theme.css");
            	FileInputStream cssfile = new FileInputStream(file);
                return getUtf8EncodedCssWebResourceResponse(cssfile);
            } catch (IOException e) {
                return null;
            }
        }

        /**
         * Return WebResourceResponse with CSS markup from a raw resource (e.g. "raw/style.css"). 
         */
//        private WebResourceResponse getCssWebResourceResponseFromRawResource() {
//            return getUtf8EncodedCssWebResourceResponse(getResources().openRawResource(R.raw.style));
//        }

        @SuppressLint("NewApi")
		private WebResourceResponse getUtf8EncodedCssWebResourceResponse(InputStream data) {
            return new WebResourceResponse("text/css", "UTF-8", data);
        }
        
        @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.i("shouldOverrideUrlLoading", "url:" + url);
			
//			String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"www/resource/new-theme.css\" />";
//			view.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
			
			String htmlData = "<!DOCTYPE html> <head> <meta charset='UTF-8'> <title>Load the CSS file from the sdcard</title> <link href='www/resource/new-theme.css' rel='stylesheet'> </head> <body> <div id='show_me'>origin is grey; new CSS in assets is png Mocoven Avatar on the below; external CSS in sdcard is Base64 Mocoven Avatar on the top;</div> <a href='http://www.google.com.hk'>sdfdsfdsf</a> <div id='mocoven'>png file</div> </body> </html>";
			webview.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
	        return true;
	    }
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
