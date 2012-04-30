package com.zbj.AllwinnerToolbox;

import java.io.IOException;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class AllwinnerToolboxActivity extends PreferenceActivity implements OnPreferenceClickListener {
	
	Preference shutdownButton;
	Preference rebootButton;
	Preference recoveryButton;
	Preference flashRecoveryButton;
	Preference otaButton;
	Preference appinfoButton;
	
    /** Called when the activity is first created. */
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
        
        shutdownButton = findPreference("shutdown");
        rebootButton = findPreference("reboot");
        recoveryButton = findPreference("recovery");
        flashRecoveryButton = findPreference("flashrecovery");
        otaButton = findPreference("ota");
        appinfoButton = findPreference("appinfo");
        
        shutdownButton.setOnPreferenceClickListener(this);
        rebootButton.setOnPreferenceClickListener(this);
        recoveryButton.setOnPreferenceClickListener(this);
        flashRecoveryButton.setOnPreferenceClickListener(this);
        otaButton.setOnPreferenceClickListener(this);
        appinfoButton.setOnPreferenceClickListener(this);
        
    }

	@Override
	public boolean onPreferenceClick(Preference preference) {
		// TODO Auto-generated method stub
		switch (preference.getOrder()) {
		case 0:
			doShellCommand("reboot -p");
			break;
		case 1:
			doShellCommand("reboot");
			break;
		case 2:
			doShellCommand("echo -n boot-recovery | busybox dd of=/dev/block/nandf count=1 conv=sync;sync;reboot");
			break;
		case 3:
			doShellCommand("cat /sdcard/recovery.img > /dev/block/nandg;sync");
			Toast.makeText(AllwinnerToolboxActivity.this, "已执行刷入recovery操作，请自行验证是否刷入成功", Toast.LENGTH_SHORT).show();
			break;
		case 4:
			doShellCommand("cp /sdcard/bootanimation.zip /system/media/");
			Toast.makeText(AllwinnerToolboxActivity.this, "请重启验证是否更换成功", Toast.LENGTH_SHORT).show();
			break;
		case 5:
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("http://weibo.com/zbj543720"));
			this.startActivity(intent);
			break;
		default:
			break;
		}
		return false;
	}
	
	private void doShellCommand(String commandValue){
		
		Runtime runtime = Runtime.getRuntime();
		Process proc;
		
		String[] args = new String[]{"su", "-c", commandValue};  
		
		try {
			proc = runtime.exec(args);
			
			if (proc.waitFor() != 0) {   
				System.err.println("exit value = " + proc.exitValue());   
			} 
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}