package com.swaf.showtimebuzzer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class AlarmActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		Intent incoming = getIntent();
		TextView alrmtxt = (TextView) findViewById(R.id.alarmtxt);
		ImageView alrmimg = (ImageView) findViewById(R.id.alarmimg);
		
		alrmimg.setImageBitmap((Bitmap)incoming.getParcelableExtra("Simage"));
		alrmtxt.setText(incoming.getStringExtra("Sname"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm, menu);
		return true;
	}

}
