package com.swaf.showtimebuzzer;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.swaf.showtimebuzzerDataBase.Channels;

public class ChannelImageAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;

	static class ViewHolder {
		ImageView mImageView;
		TextView mTextView;
	}

	static final ArrayList<String> ChannelNames = Channels.GetAllChannelNames();
	private Integer[] Images = { R.drawable.a_plus, R.drawable.atv,
			R.drawable.geokahani, R.drawable.hum_2, R.drawable.ptv_home,
			R.drawable.urdu1 };

	// Constructor
	public ChannelImageAdapter(Context context)
	// TODO Auto-generated constructor stub
	{
		mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return ChannelNames.size();
	}

	@Override
	public Object getItem(int position) {
		return ChannelNames.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View converView, ViewGroup parent) {
		ViewHolder mVHolder;
		if (converView == null) {
			converView = mLayoutInflater.inflate(R.layout.channel_list, parent,
					false);
			mVHolder = new ViewHolder();
			mVHolder.mImageView = (ImageView) converView
					.findViewById(R.id.cimgview);
			mVHolder.mTextView = (TextView) converView.findViewById(R.id.ctext);
			converView.setTag(mVHolder);
		} else {
			mVHolder = (ViewHolder) converView.getTag();
		}
		mVHolder.mImageView.setImageResource(Images[position]);
		mVHolder.mTextView.setText(ChannelNames.get(position));

		return converView;
	}

}
