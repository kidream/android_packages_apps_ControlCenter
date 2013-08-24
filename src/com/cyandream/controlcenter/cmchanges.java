package com.cyandream.controlcenter;

import com.cyandream.controlcenter.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class cmchanges extends Activity {
	private WebView mWebView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
    	mWebView = (WebView) findViewById(R.id.webview);
    	mWebView.loadUrl("http://yanniks.de/roms/cd-changes.php?device=" + android.os.Build.PRODUCT);
        mWebView.setInitialScale(110);
    }
}
