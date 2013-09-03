/***
  Copyright (c) 2008-2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain	a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
	
  From _The Busy Coder's Guide to Android Development_
    http://commonsware.com/Android
 */

package com.cyandream.controlcenter.updatechecker;

import java.io.File;

import com.cyandream.controlcenter.R;

import android.app.DownloadManager;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class DownloadFragment extends Fragment implements
    View.OnClickListener {
  private DownloadManager mgr=null;
  private long lastDownload=-1L;
  private View query=null;
  private View start=null;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                           Bundle savedInstanceState) {
    mgr=
        (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

    View result=inflater.inflate(R.layout.downloadcurrent, parent, false);

    query=result.findViewById(R.id.query);
    query.setOnClickListener(this);
    start=result.findViewById(R.id.start);
    start.setOnClickListener(this);

    result.findViewById(R.id.view).setOnClickListener(this);

    return(result);
  }

  @Override
  public void onResume() {
    super.onResume();

    IntentFilter f=
        new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

    f.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);

    getActivity().registerReceiver(onEvent, f);
    getActivity().registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
  }

  @Override
  public void onPause() {
    getActivity().unregisterReceiver(onEvent);

    super.onPause();
  }

  @Override
  public void onClick(View v) {
    if (v == query) {
      queryStatus(v);
    }
    else if (v == start) {
      deleteOld();
      startDownload(v);
    }
    else {
      ((DownloadNew)getActivity()).viewLog();
    }
  }

private void deleteOld() {
    File file = new File("/storage/emulated/legacy/Download/cyandream-current.zip");
    @SuppressWarnings("unused")
	boolean deleted = file.delete();
}
  
private void startDownload(View v) {
    Uri uri=Uri.parse("http://yanniks.de/roms/cd-download/" + android.os.Build.PRODUCT);

    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
               .mkdirs();

    DownloadManager.Request req=new DownloadManager.Request(uri);

    req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                                   | DownloadManager.Request.NETWORK_MOBILE)
       .setAllowedOverRoaming(false)
       .setTitle(getString(R.string.loadingcurrentrom))
       .setDescription(getString(R.string.gettingnewbuild))
       .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "cyandream-current.zip");

    lastDownload=mgr.enqueue(req);

    v.setEnabled(false);
    query.setEnabled(true);
  }

  private void queryStatus(View v) {
    Cursor c=
        mgr.query(new DownloadManager.Query().setFilterById(lastDownload));

    if (c == null) {
      Toast.makeText(getActivity(), "Download nicht gefunden",
                     Toast.LENGTH_LONG).show();
    }
    else {
      c.moveToFirst();

      Log.d(getClass().getName(),
            "COLUMN_ID: "
                + c.getLong(c.getColumnIndex(DownloadManager.COLUMN_ID)));
      Log.d(getClass().getName(),
            "COLUMN_BYTES_DOWNLOADED_SO_FAR: "
                + c.getLong(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
      Log.d(getClass().getName(),
            "COLUMN_LAST_MODIFIED_TIMESTAMP: "
                + c.getLong(c.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)));
      Log.d(getClass().getName(),
            "COLUMN_LOCAL_URI: "
                + c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
      Log.d(getClass().getName(),
            "COLUMN_STATUS: "
                + c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS)));
      Log.d(getClass().getName(),
            "COLUMN_REASON: "
                + c.getInt(c.getColumnIndex(DownloadManager.COLUMN_REASON)));

      Toast.makeText(getActivity(), statusMessage(c), Toast.LENGTH_LONG)
           .show();
    }
  }

  private String statusMessage(Cursor c) {
    String msg="???";

    switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
      case DownloadManager.STATUS_FAILED:
        break;

      case DownloadManager.STATUS_PAUSED:
        break;

      case DownloadManager.STATUS_PENDING:
        break;

      case DownloadManager.STATUS_RUNNING:
        break;

      case DownloadManager.STATUS_SUCCESSFUL:
        break;

      default:
        break;
    }

    return(msg);
  }

  BroadcastReceiver onComplete=new BroadcastReceiver() {
	    public void onReceive(Context ctxt, Intent intent) {
	        // flash commands from CMUpdater
//	        Process p;  
//	        try {  
//	            // Preform su to get root privledges  
//	            p = Runtime.getRuntime().exec("su");   
//	           
//	            // Performing commands for flashing...
//	            DataOutputStream os = new DataOutputStream(p.getOutputStream());  
//	            os.writeBytes("mkdir -p /cache/recovery\n");  
//	            os.writeBytes("echo 'boot-recovery' > /cache/recovery/command\n");  
//	            os.writeBytes("echo '--update_package=/sdcard/0/Download/cyandream-current.zip' >> /cache/recovery/command\n");  
//	            os.writeBytes("reboot recovery\n");  
//	            os.flush();  
//	            try {  
//	               p.waitFor();  
//	                    if (p.exitValue() != 255) {  
//	                       // TODO Code to run on success  
//	                    }  
//	                    else {  
//	                        // TODO Code to run on unsuccessful  
//	                    }  
//	            } catch (InterruptedException e) {  
//	               // TODO Code to run in interrupted exception  
//	            }  
//	         } catch (IOException e) {  
//	            // TODO Code to run in input/output exception  
//	         }  
	    }
	};
  private BroadcastReceiver onEvent=new BroadcastReceiver() {
    public void onReceive(Context ctxt, Intent i) {
      if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(i.getAction())) {
      }
      else {
        start.setEnabled(true);
      }
    }
  };
}
