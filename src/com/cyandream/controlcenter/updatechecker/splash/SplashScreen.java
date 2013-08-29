package com.cyandream.controlcenter.updatechecker.splash;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;

import com.cyandream.controlcenter.R;
import com.cyandream.controlcenter.updatechecker.UpdateChecker;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import org.apache.http.impl.client.DefaultHttpClient;

public class SplashScreen extends Activity {
	DefaultHttpClient httpclient = new DefaultHttpClient();
    String line, currentversion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new PrefetchData().execute();
		
		// Some code copyright by androidhive.info

	}
	private class PrefetchData extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
            try {
            	HttpGet httppost = new HttpGet("http://yanniks.de/roms/version-" + android.os.Build.PRODUCT + ".txt");
            	HttpResponse response = httpclient.execute(httppost);
            	        HttpEntity ht = response.getEntity();

            	        BufferedHttpEntity buf = new BufferedHttpEntity(ht);

            	        InputStream is = buf.getContent();


            	        BufferedReader r = new BufferedReader(new InputStreamReader(is));

            	        StringBuilder total = new StringBuilder();
            	        while ((line = r.readLine()) != null) {
            	            total.append(line);
            	            currentversion = line;

            	            }
            } catch (Exception e) {
                e.printStackTrace();
            }
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Intent i = new Intent(SplashScreen.this, UpdateChecker.class);
			i.putExtra("currentversion", currentversion);
			startActivity(i);
			finish();
		}

	}

}
