package com.swaf.showtimebuzzer;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.swaf.showtimebuzzerDataBase.Channels;

public class SetTimeActivity extends Activity {

	String ShowName;
	Context con;
	final Dialog dialog;
	PendingIntent pi;
	Calendar cal = Calendar.getInstance();
	AlarmManager alrm;

	public SetTimeActivity(Context c, String SName) {
		// TODO Auto-generated constructor stub
		this.con = c;
		this.ShowName = SName;
		cal.setTimeInMillis(System.currentTimeMillis());
		alrm = (AlarmManager) c.getSystemService(ALARM_SERVICE);
		dialog = new Dialog(con);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_dialog);

		final EditText min = (EditText) dialog.findViewById(R.id.dialinput);
		min.setText("");
		final Button Sub = (Button) dialog.findViewById(R.id.dialSub);
		Button Can = (Button) dialog.findViewById(R.id.dialCan);

		if (Channels.CheckInCol(ShowName)) {
			dialog.setContentView(R.layout.custom_dialog2);
			final Button Unsub = (Button) dialog.findViewById(R.id.dial2Unsub);
			Can = (Button) dialog.findViewById(R.id.dial2Can);

			Unsub.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Channels.RemFromCollection(ShowName);
					ArrayList<Integer> ids = Channels.GetAllIDs(ShowName);
					for (int i = 0; i < ids.size(); i++) {
						pi = PendingIntent.getBroadcast(con, ids.get(i),
								new Intent(con, AlarmReciever.class).putExtra(
										"Sname", ShowName), 0);
						alrm.cancel(pi);
					}
					Toast.makeText(
							con,
							"You Have Successfully Unsubscribed For "
									+ ShowName, Toast.LENGTH_LONG).show();
					dialog.dismiss();
				}

			});

		}

		Can.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}

		});

		Sub.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((RadioButton) dialog.findViewById(R.id.dialrad1))
						.isChecked()) {

					Channels.AddToCollection(ShowName, "0");
					ArrayList<Integer> ids = Channels.GetAllIDs(ShowName);
					for (int i = 0; i < ids.size(); i++) {
						cal.set(Calendar.HOUR_OF_DAY,
								Channels.GetHour(ids.get(i)));
						cal.set(Calendar.MINUTE,
								Channels.GetMinutes(ids.get(i)));
						cal.set(Calendar.DAY_OF_WEEK,
								Channels.GetDay(ids.get(i)));
						pi = PendingIntent.getBroadcast(con, ids.get(i),
								new Intent(con, AlarmReciever.class).putExtra(
										"Sname", ShowName).putExtra("Minutes", "0").putExtra("Image", Channels.GetShowImage(ShowName)), 0);
						alrm.setRepeating(AlarmManager.RTC_WAKEUP,
								cal.getTimeInMillis(),
								AlarmManager.INTERVAL_DAY * 7, pi);
					}
					Toast.makeText(con,
							"You Have Successfully Subscribed For " + ShowName,
							Toast.LENGTH_LONG).show();
					dialog.dismiss();
				}

				else {
					if (!min.getText().toString().isEmpty()) {
						Channels.AddToCollection(ShowName, min.getText()
								.toString());
						ArrayList<Integer> ids = Channels.GetAllIDs(ShowName);
						for (int i = 0; i < ids.size(); i++) {
							cal.set(Calendar.HOUR_OF_DAY,
									Channels.GetHour(ids.get(i)));
							cal.set(Calendar.MINUTE,
									Channels.GetMinutes(ids.get(i)
											- Integer.parseInt(min.getText()
													.toString())));
							cal.set(Calendar.DAY_OF_WEEK,
									Channels.GetDay(ids.get(i)));
							pi = PendingIntent.getBroadcast(
									con,
									ids.get(i),
									new Intent(con, AlarmReciever.class)
											.putExtra("Sname", ShowName)
											.putExtra("Minutes",
													min.getText().toString()).putExtra("Image", Channels.GetShowImage(ShowName)),
									0);
							alrm.setRepeating(AlarmManager.RTC_WAKEUP,
									cal.getTimeInMillis(),
									AlarmManager.INTERVAL_DAY * 7, pi);
						}
						Toast.makeText(
								con,
								"You Have Successfully Subscribed For "
										+ ShowName, Toast.LENGTH_LONG).show();
						dialog.dismiss();
					} else
						min.setHint("!!!!!");
				}
			}

		});

		dialog.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settime, menu);
		return true;
	}

}
