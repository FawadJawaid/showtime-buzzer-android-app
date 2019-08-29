package com.swaf.showtimebuzzer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.swaf.showtimebuzzerDataBase.GeoKahani;
import com.swaf.showtimebuzzerDataBase.Hum2;
import com.swaf.showtimebuzzerDataBase.Urdu1;

public class SplashActivity extends Activity {
	private Handler mHandler = new Handler();
	// DBbuilder Db;
	private ProgressDialog progressDialog;
	SharedPreferences prefs = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		prefs = getSharedPreferences("com.swaf.showtimebuzzer", MODE_PRIVATE);
		if (prefs.getBoolean("firstrun", true)) {
			ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
			NetworkInfo mWifi = connManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (!mWifi.isConnected()) {
				startActivity(new Intent()
						.setAction(android.provider.Settings.ACTION_WIFI_SETTINGS));
			}
			prefs.edit().putBoolean("firstrun", false).commit();
		}

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		progressDialog = ProgressDialog.show(SplashActivity.this,
				"Loading...", "Loading Views, please wait...", true, false);
	mHandler.postDelayed(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
			new LoadViewTask().execute();

		}
	}, 3000);
		
		// MediaPlayer song = MediaPlayer.create(SplashActivity.this,
		// R.raw.sleepaway); //the song is sleepaway.mp3
		// song.start();
		// song.release();
	}

	// To use the AsyncTask, it must be subclassed
	private class LoadViewTask extends AsyncTask<Void, Integer, Void> {
		// Before running code in the separate thread
		@Override
		protected void onPreExecute() {
			// Create a new progress dialog
			/*
			 * progressDialog = new ProgressDialog(LoadingScreenActivity.this);
			 * //Set the progress dialog to display a horizontal progress bar
			 * progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			 * //Set the dialog title to 'Loading...'
			 * progressDialog.setTitle("Loading..."); //Set the dialog message
			 * to 'Loading application View, please wait...'
			 * progressDialog.setMessage
			 * ("Loading application View, please wait..."); //This dialog can't
			 * be canceled by pressing the back key
			 * progressDialog.setCancelable(false); //This dialog isn't
			 * indeterminate progressDialog.setIndeterminate(false); //The
			 * maximum number of items is 100 progressDialog.setMax(100); //Set
			 * the current progress to zero progressDialog.setProgress(0);
			 * //Display the progress dialog progressDialog.show();
			 */
			
	
		}

		// The code to be executed in a background thread.
		@Override
		protected Void doInBackground(Void... params) {
			/*
			 * This is just a code that delays the thread execution 4 times,
			 * during 850 milliseconds and updates the current progress. This is
			 * where the code that is going to be executed on a background
			 * thread must be placed.
			 */
		
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
				
						new Urdu1(SplashActivity.this);
					new GeoKahani(SplashActivity.this);
					new Hum2(SplashActivity.this);
				}
			});

			return null;
		}

		// after executing the code in the thread
		@Override
		protected void onPostExecute(Void result) {
			// close the progress dialog

			// initialize the View
			startActivity(new Intent(SplashActivity.this, MainActivity.class));

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	
		finish();
	}
}