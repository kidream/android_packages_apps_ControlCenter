package com.cyandream.controlcenter.updatechecker;

import java.io.DataOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;

	public class Reboot extends Activity {
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
    try {
    	Process p;
        // Preform su to get root privledges  
        p = Runtime.getRuntime().exec("su");   
//       
        // Performing commands for flashing...
        DataOutputStream os = new DataOutputStream(p.getOutputStream());  
        os.writeBytes("reboot recovery\n");  
        os.flush();  
     } catch (IOException e) {  
         // TODO Code to run in input/output exception  
      }
}
}