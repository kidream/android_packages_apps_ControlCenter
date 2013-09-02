package com.cyandream.controlcenter.updatechecker;

import org.apache.http.impl.client.DefaultHttpClient;

import com.cyandream.controlcenter.R;
import com.cyandream.controlcenter.updatechecker.splash.SplashScreen;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateChecker extends Activity {
    /** Called when the activity is first created. */
    private TextView mTextView, mTextView2;
	DefaultHttpClient httpclient = new DefaultHttpClient();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatecheck);
        getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent i = getIntent();
		String currentversion = i.getStringExtra("currentversion");
    	mTextView = (TextView) findViewById(R.id.current);
    	mTextView2 = (TextView) findViewById(R.id.installed);
        mTextView.setText(getString(R.string.current) + " " + currentversion);
        mTextView2.setText(getString(R.string.installed) + " " + android.os.Build.VERSION.INCREMENTAL);
        ((TextView)findViewById(R.id.installedversion)).setText(android.os.Build.VERSION.INCREMENTAL.equals(currentversion) ? getString(R.string.noupdates) : getString(R.string.updatesavalibale));

    }
    	public void downloadnew (final View view) {
        	startActivity (new Intent (this,DownloadNew.class));
    	}

        @Override
        public boolean onCreateOptionsMenu (Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
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
        	}
        	}
            if(item.getItemId() == R.id.item1){
            	startActivity (new Intent (this,SplashScreen.class));
                Toast.makeText(UpdateChecker.this, getString(R.string.updated), Toast.LENGTH_LONG).show();
                return true;
                }
            return super.onOptionsItemSelected(item);
        }
}