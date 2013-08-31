package com.cyandream.controlcenter.updatechecker;

import java.io.DataOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;

public class flashupdate extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // flash commands from CMUpdater
        Process p;  
        try {  
           // Preform su to get root privledges  
           p = Runtime.getRuntime().exec("su");   
          
           // Performing commands for flashing...
           DataOutputStream os = new DataOutputStream(p.getOutputStream());  
           os.writeBytes("mkdir -p /cache/recovery\n");  
           os.writeBytes("echo 'boot-recovery' > /cache/recovery/command\n");  
           os.writeBytes("echo '--update_package=/sdcard/0/Download/cyandream-current.zip' >> /cache/recovery/command\n");  
           os.writeBytes("echo '--update_package=/sdcard/0/Download/gapps-current.zip' >> /cache/recovery/command\n");  
           os.writeBytes("reboot recovery\n");  
           os.flush();  
           try {  
              p.waitFor();  
                   if (p.exitValue() != 255) {  
                      // TODO Code to run on success  
                   }  
                   else {  
                       // TODO Code to run on unsuccessful  
                   }  
           } catch (InterruptedException e) {  
              // TODO Code to run in interrupted exception  
           }  
        } catch (IOException e) {  
           // TODO Code to run in input/output exception  
        }  
    }
}