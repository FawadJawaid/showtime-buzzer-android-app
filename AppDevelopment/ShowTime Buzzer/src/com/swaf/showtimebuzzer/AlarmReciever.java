package com.swaf.showtimebuzzer;

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.swaf.showtimebuzzerDataBase.Channels;

public class AlarmReciever extends BroadcastReceiver {

	boolean CheckTime(int ShowID) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, Channels.GetHour(ShowID));
		cal.set(Calendar.DAY_OF_WEEK, Channels.GetDay(ShowID));
		cal.set(Calendar.MINUTE, Channels.GetMinutes(ShowID));
		cal.set(Calendar.SECOND,0);

		if (cal.getTimeInMillis() == System.currentTimeMillis())
			return true;
		else
			return false;
	}

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		String Title = "Don't Miss " + arg1.getStringExtra("Sname");
		String Text = "About To Air In " + arg1.getStringExtra("Minutes")
				+ " Minutes.";

		PendingIntent contentIntent = PendingIntent.getService(arg0, 0,
				new Intent(), 0);
		Notification not = new Notification.Builder(arg0)
				.setContentTitle(Title)
				.setContentText(Text)
				.setLargeIcon(
						Bitmap.createScaledBitmap((Bitmap)arg1.getParcelableExtra("Image"), 100, 100, false))
				.setContentIntent(contentIntent).setSmallIcon(R.drawable.icon)
				.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL).build();
		NotificationManager mManager = (NotificationManager) arg0
				.getSystemService(Context.NOTIFICATION_SERVICE);

		//Toast.makeText(arg0, "Running Notification", Toast.LENGTH_LONG).show();
		mManager.notify(GlobalVar.NotifyID++, not);
	}

}
