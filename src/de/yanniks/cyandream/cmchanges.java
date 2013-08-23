package de.yanniks.cyandream;

import de.yanniks.cyandream.R;
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
    	mWebView.loadUrl("http://yanniks.de/roms/cm10-changes.php");
        mWebView.setInitialScale(110);
    }
}
