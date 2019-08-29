package com.swaf.showtimebuzzer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class WeekChannelActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weekchannel);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		 final ChannelImageAdapter clist =new ChannelImageAdapter(this);
        // Instance of ImageAdapter Class
        listView.setAdapter(clist);
        
        
        /**
         * On Click event for Single List view Item
         * */
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
 
                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), WeekShowsActivity.class);
                // passing array index
                i.putExtra("CName",(String)clist.getItem(position) );
                startActivity(i);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.today, menu);
		return true;
	}

}
