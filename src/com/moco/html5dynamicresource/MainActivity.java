package com.moco.html5dynamicresource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	WebView webview = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		webview = (WebView)findViewById(R.id.web);
		webview.loadUrl("file:///android_asset/www/index.html");
		
//		webview.setWebViewClient(new WebViewClient() {
//			@Override
//		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//		       view.loadUrl("file:///android_asset/www/new-theme.css");
//		       return true;
//		    }
//		});
		//SaveCSS();
		
		webview.setWebViewClient(new WebViewClient() {
   			@Override
   		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
   		       view.loadUrl("file://" + Environment.getExternalStorageDirectory().getPath() + "/files/external-theme.css");
   		       Log.d("------", Environment.getExternalStorageDirectory().toString() + "/files/external-theme.css");
   		       return true;
   		       
   		    }
		});
		
	}
	
	private void SaveCSS() {

	    String root = Environment.getExternalStorageDirectory().toString();
	    File myDir = new File(root + "/files");    
	    myDir.mkdirs();
	    Random generator = new Random();
	    int n = 10000;
	    n = generator.nextInt(n);
	    String fname = "external-theme.css";
	    File file = new File (myDir, fname);
	    if (file.exists ()) file.delete (); 
	    try {
	           FileOutputStream out = new FileOutputStream(file);
	           PrintWriter pw = new PrintWriter(out);
	           pw.println("#show_me{background-color: #000000;}");
	           pw.flush();
	           pw.close();
	           out.flush();
	           out.close();
	           
	           
	   		

	    } catch (Exception e) {
	           e.printStackTrace();
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
