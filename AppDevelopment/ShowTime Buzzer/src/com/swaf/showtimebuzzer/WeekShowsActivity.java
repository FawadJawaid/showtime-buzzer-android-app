package com.swaf.showtimebuzzer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class WeekShowsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weekshows);
		setTitle(getIntent().getExtras().getString("CName"));

		final WeekShowsImageAdapter sia = new WeekShowsImageAdapter(this);
		final GridView gridView = (GridView) findViewById(R.id.weekgrid);
		sia.filterchan(getIntent().getExtras().getString("CName"));
        // Instance of ImageAdapter Class
        gridView.setAdapter(sia);
        
        /**
        * On Click event for Single List view Item
        * */
       gridView.setOnItemClickListener(new OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View v,
                   int position, long id) {

               // Sending image id to FullScreenActivity
              new SetTimeActivity(WeekShowsActivity.this, sia.getItem(position));
           }
       });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.today_activity1, menu);
		return true;
	}

}
