package com.swaf.showtimebuzzer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MyCollectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collection);

		final GridView gv = (GridView) findViewById(R.id.ColGrid);
		final MyCollectionImgAdapter col = new MyCollectionImgAdapter(this);
		// Instance of ImageAdapter Class
		gv.setAdapter(col);

		/**
		 * On Click event for Single List view Item
		 * */
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				new SetTimeActivity(MyCollectionActivity.this, gv.getItemAtPosition(position).toString());
				col.Refresh();
				gv.setAdapter(col);
			}
		});
		if(gv.isSelected()){ col.Refresh();gv.setAdapter(col);
		}}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_collection, menu);
		return true;
	}

}
