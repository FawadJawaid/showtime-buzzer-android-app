package com.swaf.showtimebuzzer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class AlarmNotify extends Service {
	NotificationManager mManager;



	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String Title = "Don't Miss " + intent.getStringExtra("Sname");
		String Text = "About To Air In " + intent.getStringExtra("Minutes")
				+ " Minutes.";

		Toast.makeText(this, "Running Notification", Toast.LENGTH_LONG).show();
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(), 0);
		NotificationCompat.Builder not = new NotificationCompat.Builder(this)
				.setContentTitle(Title)
				.setContentText(Text)
				.setLargeIcon(
						BitmapFactory.decodeResource(this
								.getApplicationContext().getResources(),
								R.drawable.icon))
				.setContentIntent(contentIntent).setSmallIcon(R.drawable.icon)
				.setAutoCancel(true);
		mManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

		mManager.notify(0, not.build());
		/*
		 * Notification notification = new Notification( R.drawable.icon, Title,
		 * System .currentTimeMillis()); notification.flags =
		 * Notification.FLAG_AUTO_CANCEL; notification.flags =
		 * Notification.FLAG_ONLY_ALERT_ONCE; PendingIntent contentIntent =
		 * PendingIntent.getActivity(context, 0, new Intent(), 0);
		 * notification.setLatestEventInfo(context, Title, Text, contentIntent);
		 * 
		 * mManager.notify(0, notification);
		 */return super.onStartCommand(intent, flags, startId);
	}

}
