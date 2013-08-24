package com.cyandream.controlcenter;

import com.cyandream.controlcenter.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class ROMs extends Activity {
	private WebView mWebView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roms);
    	mWebView = (WebView) findViewById(R.id.dropbox);
    	mWebView.loadUrl("http://yanniks.de/roms/cm-buildbot-ace");
    }
}
