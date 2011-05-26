package com.nvisium.tapjacking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Main extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void launchCallDemo (View v) {
		startService(new Intent(DialerService.class.getName()));
	}
	
	public void launchBackgroundInstallDemo(View v) {
		startService(new Intent(BackgroundInstallerService.class.getName()));
	}
	
}
