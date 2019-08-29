package com.swaf.showtimebuzzer;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AlarmtoneActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarmtone);

		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivityForResult(new Intent()
						.setAction(RingtoneManager.ACTION_RINGTONE_PICKER), 0);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == 0) {

			Intent Alarm = new Intent()
					.setAction(android.provider.AlarmClock.ACTION_SET_ALARM);
			Alarm.putExtra(AlarmClock.EXTRA_MESSAGE, "Kesa Dia");
			Alarm.putExtra(AlarmClock.EXTRA_HOUR, 2);
			startActivity(Alarm);

			GlobalVar.ALARM_TONE = RingtoneManager
					.getRingtone(
							this,
							(Uri) data
									.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI));

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarmtone, menu);
		return true;
	}

}
