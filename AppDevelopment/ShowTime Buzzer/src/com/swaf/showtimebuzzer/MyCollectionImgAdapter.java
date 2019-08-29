package com.swaf.showtimebuzzer;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.swaf.showtimebuzzerDataBase.Channels;

public class MyCollectionImgAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;

	static class ViewHolder {
		ImageView mImageView;
		TextView mTextView;
	}

	static ArrayList<String> ShowNames = new ArrayList<String>();
	ArrayList<Bitmap> Images = new ArrayList<Bitmap>();
	ArrayList<String> temp = new ArrayList<String>();

	// Constructor
	public MyCollectionImgAdapter(Context context)
	// TODO Auto-generated constructor stub
	{
		ShowNames = Channels.GetCollection();
		temp.addAll(ShowNames);
		for (String s : ShowNames) {
			Images.add(Channels.GetShowImage(s));
		}
		mLayoutInflater = LayoutInflater.from(context);

	}
	
	public void Refresh()
	{
		ShowNames = Channels.GetCollection();
		for (String s : ShowNames) {
			Images.add(Channels.GetShowImage(s));
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return ShowNames.size();
	}

	@Override
	public String getItem(int position) {
		return ShowNames.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View converView, ViewGroup parent) {
		ViewHolder mVHolder;

		if (converView == null) {

			converView = mLayoutInflater.inflate(R.layout.custom_grid, parent,
					false);
			mVHolder = new ViewHolder();
			mVHolder.mImageView = (ImageView) converView
					.findViewById(R.id.imgview);
			mVHolder.mTextView = (TextView) converView.findViewById(R.id.text);
			mVHolder.mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			mVHolder.mImageView.setPadding(8, 8, 8, 8);
			converView.setTag(mVHolder);
		} else {
			mVHolder = (ViewHolder) converView.getTag();
		}

		mVHolder.mImageView.setImageBitmap(Images.get(position));
		mVHolder.mTextView.setText(ShowNames.get(position));

		return converView;
	}

}
