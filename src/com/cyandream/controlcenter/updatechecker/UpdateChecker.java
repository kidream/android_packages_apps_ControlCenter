package com.cyandream.controlcenter.updatechecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.impl.client.DefaultHttpClient;

import com.cyandream.controlcenter.R;
import com.cyandream.controlcenter.updatechecker.splash.SplashScreen;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateChecker extends Activity {
    /** Called when the activity is first created. */
    private TextView mTextView, mTextView2, sizetext;
	private DownloadManager mgr=null;
	private long lastDownload=-1L;;
	DefaultHttpClient httpclient = new DefaultHttpClient();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatecheck);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    	Intent i = getIntent();
    	String currentversion = i.getStringExtra("currentversion");
    	String size = i.getStringExtra("size");
    	String compare = i.getStringExtra("compare");
    	Button button = (Button) findViewById(R.id.start);
    	String installupdate = i.getStringExtra("installupdate");
    	if( installupdate == compare ) {
            button.setEnabled(false);
            }
    	mTextView = (TextView) findViewById(R.id.current);
    	mTextView2 = (TextView) findViewById(R.id.installed);
    	sizetext = (TextView) findViewById(R.id.filesize);
        mTextView.setText(getString(R.string.current) + " " + currentversion);
        sizetext.setText(getString(R.string.size) + " " + size);
        mTextView2.setText(getString(R.string.installed) + " " + android.os.Build.VERSION.INCREMENTAL);
        ((TextView)findViewById(R.id.installedversion)).setText(android.os.Build.VERSION.INCREMENTAL.equals(currentversion) ? getString(R.string.noupdates) : getString(R.string.updatesavalibale));
        mgr=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
      }
      
      @Override
      public void onDestroy() {
        super.onDestroy();
        
        unregisterReceiver(onComplete);
      }
      
      public void startDownload() {
      	Intent i = getIntent();
      	String filename = i.getStringExtra("filename");
        Uri uri=Uri.parse("http://yauniks.dynvpn.de:85/jenkins/mirror/" + filename + ".patch" );
        
        Environment
          .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
          .mkdirs();
        
        lastDownload=
          mgr.enqueue(new DownloadManager.Request(uri)
                      .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                              DownloadManager.Request.NETWORK_MOBILE)
                      .setAllowedOverRoaming(false)
                      .setTitle(getString(R.string.loadingcurrentrom))
                      .setDescription(getString(R.string.gettingnewbuild))
                      .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                                                         filename + ".patch"));
        
        findViewById(R.id.query).setEnabled(true);
      }
      
      public void queryStatus(View v) {
        Cursor c=mgr.query(new DownloadManager.Query().setFilterById(lastDownload));
        
        if (c==null) {
        }
        else {
          c.moveToFirst();
          
          Log.d(getClass().getName(), "COLUMN_ID: "+
                c.getLong(c.getColumnIndex(DownloadManager.COLUMN_ID)));
          Log.d(getClass().getName(), "COLUMN_BYTES_DOWNLOADED_SO_FAR: "+
                c.getLong(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
          Log.d(getClass().getName(), "COLUMN_LAST_MODIFIED_TIMESTAMP: "+
                c.getLong(c.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)));
          Log.d(getClass().getName(), "COLUMN_LOCAL_URI: "+
                c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
          Log.d(getClass().getName(), "COLUMN_STATUS: "+
                c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS)));
          Log.d(getClass().getName(), "COLUMN_REASON: "+
                c.getInt(c.getColumnIndex(DownloadManager.COLUMN_REASON)));
          
          Toast.makeText(this, statusMessage(c), Toast.LENGTH_LONG).show();
        }
      }
      
      public void viewLog(View v) {
        startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
      }
      
      private String statusMessage(Cursor c) {
        String msg="???";
        
        switch(c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
          case DownloadManager.STATUS_SUCCESSFUL:
            break;
          
          default:
            break;
        }
        
        return(msg);
      }
      
      BroadcastReceiver onComplete=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
        	createzip(null);
        	delfile(null);
        	delfile2(null);
        	notification(null);
//        	Process p;  
//           try {  
               // Preform su to get root privledges  
//               p = Runtime.getRuntime().exec("su");   
//              
//               // Performing commands for flashing...
//               DataOutputStream os = new DataOutputStream(p.getOutputStream());  
//               os.writeBytes("mkdir -p /cache/recovery\n");  
//               os.writeBytes("echo 'boot-recovery' > /cache/recovery/command\n");  
//               os.writeBytes("echo '--update_package=/sdcard/0/Download/cyandream-current.zip' >> /cache/recovery/command\n");  
//               os.writeBytes("echo '--update_package=/sdcard/0/Download/gapps-current.zip' >> /cache/recovery/command\n");  
//               os.writeBytes("reboot recovery\n");  
//               os.flush();  
//            } catch (IOException e) {  
                // TODO Code to run in input/output exception  
//             }  
          findViewById(R.id.start).setEnabled(true);
        }
      };

      public void startdl (final View view) {
    	delfile(view);
    	Button button = (Button) findViewById(R.id.start);
      	startDownload();
        button.setEnabled(false);
      }
      public void notification (final View view) {
         	Intent i = getIntent();
          	String filename = i.getStringExtra("filename");
    	  NotificationManager nm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    	  Notification notification=new Notification(R.drawable.ic_launcher, getString(R.string.notifupdate), System.currentTimeMillis());
    	  Context context=UpdateChecker.this;
    	  CharSequence title=getString(R.string.prepared);
    	  CharSequence detail=getString(R.string.notifdetail1) + " " + filename + getString(R.string.notifdetail2);
    	  Intent intent=new Intent(context,Reboot.class);
    	  PendingIntent  pending=PendingIntent.getActivity(context, 0, intent, 0);
    	  notification.setLatestEventInfo(context, title, detail, pending);
    	  nm.notify(0, notification);
      }
      public void delfile (final View view) {
       	Intent i = getIntent();
      	String filename = i.getStringExtra("filename");
    	  File file = new File("/storage/emulated/0/Download/" + filename + ".patch");
          @SuppressWarnings("unused")
    		boolean deleted = file.delete();
      }
      public void delfile2 (final View view) {
         	Intent i = getIntent();
        	String upgradefrom = i.getStringExtra("upgradefrom");
      	  File file = new File("/storage/emulated/0/Download/" + upgradefrom + ".zip");
            @SuppressWarnings("unused")
      		boolean deleted = file.delete();
        }
      public void createzip (final View view) {
         	Intent i = getIntent();
        	String upgradefrom = i.getStringExtra("upgradefrom");
        	String filename = i.getStringExtra("filename");
            Runtime rt = Runtime.getRuntime();
            Process proc;
			try {
				proc = rt.exec("ls -all");

            proc = rt.exec("xdelta3 -d -s " + "/storage/emulated/0/Download/" + upgradefrom + " " + "/storage/emulated/0/Download/" + filename + ".patch " + "/storage/emulated/0/Download/" + filename + ".zip");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

       while ((line = br.readLine()) != null) {
         System.out.println(line);
       }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
            	startActivity (new Intent (this,SplashScreen.class));
                Toast.makeText(UpdateChecker.this, getString(R.string.updated), Toast.LENGTH_LONG).show();
                return true;
                }
            return super.onOptionsItemSelected(item);
        }
}
