package com.swaf.showtimebuzzer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

//http://www.androidbegin.com/tutorial/android-search-filter-listview-images-and-texts-tutorial/

public class SearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		final SearchImageAdapter sia = new SearchImageAdapter(this);
		final GridView gridView = (GridView) findViewById(R.id.gridview);
		SearchView sv = (SearchView) findViewById(R.id.searchView1);
		final RadioGroup Rgrp = (RadioGroup) findViewById(R.id.Rgrp);
		Rgrp.check(R.id.fshow);

		gridView.setAdapter(sia);
		sv.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				if (Rgrp.getCheckedRadioButtonId() == R.id.fshow)
					sia.filtershow(newText);
				else
					sia.filterchan(newText);
				return false;
			}
		});

		// Instance of ImageAdapter Class

		/**
		 * On Click event for Single Grid view Item
		 * */
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				// Sending image id to FullScreenActivity
				new SetTimeActivity(SearchActivity.this,sia.getItem(position));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

}
