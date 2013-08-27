package com.cyandream.controlcenter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import de.yanniks.updatez.Install;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateChecker extends Activity {
    /** Called when the activity is first created. */
    private TextView mTextView, mTextView2;
	DefaultHttpClient httpclient = new DefaultHttpClient();
    String line;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatecheck);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    	if (android.os.Build.VERSION.SDK_INT > 9) {
    		StrictMode.ThreadPolicy policy = 
    		        new StrictMode.ThreadPolicy.Builder().permitAll().build();
    		StrictMode.setThreadPolicy(policy);
    		}
                try {
                	mTextView = (TextView) findViewById(R.id.current);
                	mTextView2 = (TextView) findViewById(R.id.installed);
                	HttpGet httppost = new HttpGet("http://yanniks.de/roms/version-" + android.os.Build.PRODUCT + ".txt");
                	HttpResponse response = httpclient.execute(httppost);
                	        HttpEntity ht = response.getEntity();

                	        BufferedHttpEntity buf = new BufferedHttpEntity(ht);

                	        InputStream is = buf.getContent();


                	        BufferedReader r = new BufferedReader(new InputStreamReader(is));

                	        StringBuilder total = new StringBuilder();
                	        while ((line = r.readLine()) != null) {
                	            total.append(line);
                	            String currentversion = line;
                	            mTextView.setText(getString(R.string.current) + " " + currentversion);
                	            mTextView2.setText(getString(R.string.installed) + " " + android.os.Build.VERSION.INCREMENTAL);
                	            ((TextView)findViewById(R.id.installedversion)).setText(android.os.Build.VERSION.INCREMENTAL.equals(currentversion) ? getString(R.string.noupdates) : getString(R.string.updatesavalibale));
                	        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
    	public void downloadnew (final View view) {
        	startActivity (new Intent (this,DownloadNew.class));
    	}

    	public void updatez (final View view) {
    		try
    	        {
    	            Runtime rt = Runtime.getRuntime();
    	            Process proc = rt.exec("ls -all");

    	            proc = rt.exec("sh /data/data/com.cyandream.controlcenter/files/bin/otaxdelta3.sh x /sdcard/Download/cyandream-current.zip /sdcard/updateZ/patch.zip /sdcard/updateZ/update-cyandream.zip");
    	            InputStream is = proc.getInputStream();
    	            InputStreamReader isr = new InputStreamReader(is);
    	            BufferedReader br = new BufferedReader(isr);
    	            String line;

    	       while ((line = br.readLine()) != null) {
    	         System.out.println(line);
    	       }
    	           } catch (Throwable t)
    	          {
    	            t.printStackTrace();
    	          }
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
                try {
                	mTextView = (TextView) findViewById(R.id.current);
                	mTextView2 = (TextView) findViewById(R.id.installed);
                	HttpGet httppost = new HttpGet("http://yanniks.de/roms/version-" + android.os.Build.PRODUCT + ".txt");
                	HttpResponse response = httpclient.execute(httppost);
                	        HttpEntity ht = response.getEntity();

                	        BufferedHttpEntity buf = new BufferedHttpEntity(ht);

                	        InputStream is = buf.getContent();


                	        BufferedReader r = new BufferedReader(new InputStreamReader(is));

                	        StringBuilder total = new StringBuilder();
                	        while ((line = r.readLine()) != null) {
                	            total.append(line);
                	            String currentversion = line;
                	            mTextView.setText(getString(R.string.current) + " " + currentversion);
                	            mTextView2.setText(getString(R.string.installed) + " " + android.os.Build.VERSION.INCREMENTAL);
                	            ((TextView)findViewById(R.id.installedversion)).setText(android.os.Build.VERSION.INCREMENTAL.equals(currentversion) ? getString(R.string.noupdates) : getString(R.string.updatesavalibale));
                	        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(UpdateChecker.this, getString(R.string.updated), Toast.LENGTH_LONG).show();
                return true;
                }
            return super.onOptionsItemSelected(item);
        }
}