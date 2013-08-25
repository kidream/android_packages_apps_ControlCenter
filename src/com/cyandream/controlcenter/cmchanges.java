package com.cyandream.controlcenter;

import com.cyandream.controlcenter.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

public class cmchanges extends Activity {
	private WebView mWebView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    	mWebView = (WebView) findViewById(R.id.webview);
    	mWebView.loadUrl("http://yanniks.de/roms/cd-changes.php?device=" + android.os.Build.PRODUCT);
        mWebView.setInitialScale(110);
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.changes, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	{
    	       switch (item.getItemId()) 
    	        {
    	        case android.R.id.home: 
    	            onBackPressed();
    	            break;

    	        default:
    	            if(item.getItemId() == R.id.refresh){
    	                mWebView.reload();
    	                return true;
    	}
    	}
        return super.onOptionsItemSelected(item);
    }
    }
}
