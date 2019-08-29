package com.swaf.showtimebuzzer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.swaf.showtimebuzzerDataBase.Channels;

public class MainActivity extends Activity {

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// GenerateDB Db = new GenerateDB();
		// Db.execute(this);

		// while(!(Db.getStatus() == AsyncTask.Status.FINISHED))
		// Toast.makeText(this, "Loading Database....",
		// Toast.LENGTH_LONG).show();
		// new Urdu1(this);
		// new Hum2(this);
		// new GeoKahani(this);
		// new PtvHome(this);
		ImageView img1 = (ImageView) findViewById(R.id.img1);
		ImageView img2 = (ImageView) findViewById(R.id.img2);
		ImageView img3 = (ImageView) findViewById(R.id.img3);
		ImageView img4 = (ImageView) findViewById(R.id.img4);

		img1.setImageBitmap(Channels.GetAllImages().get(4));
		img2.setImageBitmap(Channels.GetAllImages().get(0));
		img3.setImageBitmap(Channels.GetAllImages().get(1));
		img4.setImageBitmap(Channels.GetAllImages().get(3));

		ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper1);
		flipper.startFlipping();

		img1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// startActivity(new Intent(MainActivity.this,
				// SetTimeActivity.class));
			}
		});

		img2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// startActivity(new Intent(MainActivity.this,
				// SetTimeActivity.class));
			}
		});

		img3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// startActivity(new Intent(MainActivity.this,
				// SetTimeActivity.class));
			}
		});

		img4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// startActivity(new Intent(MainActivity.this,
				// SetTimeActivity.class));
			}
		});

		/*
		 * flipper.setClickable(true);
		 * 
		 * if(flipper.isClickable()==true){ Intent i = new
		 * Intent(getApplicationContext(), TodayActivity1.class);
		 * startActivity(i);}
		 */

		/*
		 * flipper.setOnTouchListener(new View.OnTouchListener() {
		 * 
		 * @Override public boolean onTouch(View arg0, MotionEvent arg1) {
		 * //Intent i = new Intent(getApplicationContext(),
		 * TodayActivity1.class); //startActivity(i); flipper.stopFlipping();
		 * return false; } });
		 */

		/*
		 * myHorizontalLayout =
		 * (MyHorizontalLayout)findViewById(R.id.mygallery);
		 * 
		 * String ExternalStorageDirectoryPath = Environment
		 * .getExternalStorageDirectory() .getAbsolutePath();
		 * 
		 * String targetPath = ExternalStorageDirectoryPath + "/test/";
		 * 
		 * Toast.makeText(getApplicationContext(), targetPath,
		 * Toast.LENGTH_LONG).show(); File targetDirector = new
		 * File(targetPath);
		 * 
		 * File[] files = targetDirector.listFiles(); for (File file : files){
		 * myHorizontalLayout.add(file.getAbsolutePath()); }
		 */

		ImageButton b1 = (ImageButton) findViewById(R.id.imageButton1);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,
						SearchActivity.class));

			}
		});

		ImageButton b2 = (ImageButton) findViewById(R.id.imageButton3);
		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,
						WeekChannelActivity.class));

			}
		});

		ImageButton b3 = (ImageButton) findViewById(R.id.imageButton2);
		b3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,
						MyCollectionActivity.class));

			}
		});

		ImageButton b4 = (ImageButton) findViewById(R.id.imageButton4);
		b4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MainActivity.this)
						.setMessage("Are you sure?")
						.setCancelable(true)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										finish();
									}
								}).setNegativeButton("No", null).show();

			}
		});

		ImageButton b6 = (ImageButton) findViewById(R.id.imageButton6);
		b6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,
						AlarmtoneActivity.class));

			}
		});

		ImageButton b5 = (ImageButton) findViewById(R.id.imageButton5);
		b5.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// Update Shows
				NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

				Toast.makeText(MainActivity.this, "Running Notification",
						Toast.LENGTH_LONG).show();
				Notification notification = new Notification(
						R.drawable.icon, "Check", System
								.currentTimeMillis());
				notification.flags = Notification.FLAG_AUTO_CANCEL;
				PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,
						0, new Intent(), 0);
				notification.setLatestEventInfo(MainActivity.this, "Check2", "Check",
						contentIntent);
				
				mManager.notify(0, notification);// am.set(AlarmManager.RTC_WAKEUP,
											// System.currentTimeMillis()+2000,
											// pi);
				/*
				 * SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
				 * Time t = new Time(); try { java.util.Date date =
				 * format.parse(dtStart); t.set(date.getTime());
				 * Toast.makeText(MainActivity.this,"" + t.hour+ ":" +t.minute,
				 * Toast.LENGTH_LONG).show(); } catch (java.text.ParseException
				 * e) { // TODO Auto-generated catch block e.printStackTrace();
				 * }
				 */
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Channels.CloseDB();
	}

}
