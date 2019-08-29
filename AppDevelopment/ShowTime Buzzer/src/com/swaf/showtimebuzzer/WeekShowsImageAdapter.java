package com.swaf.showtimebuzzer;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.swaf.showtimebuzzerDataBase.Channels;

public class WeekShowsImageAdapter extends BaseAdapter{

    private LayoutInflater mLayoutInflater;

static class ViewHolder{
    ImageView mImageView;
    TextView mTextView;
}

static final ArrayList<String>ShowNames = Channels.GetAllShowName();
static final ArrayList<String>ChannelNames = Channels.GetAllChannelNames();
final ArrayList<String>arraylist = new ArrayList<String>();
static ArrayList<Bitmap>Images = Channels.GetAllImages();


 
    // Constructor
    public WeekShowsImageAdapter(Context context) 
		// TODO Auto-generated constructor stub
    {
        mLayoutInflater=LayoutInflater.from(context);
        this.arraylist.addAll(ShowNames);
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
        if(converView == null){
            converView=mLayoutInflater.inflate(R.layout.custom_grid, parent, false);
            mVHolder=new ViewHolder();
            mVHolder.mImageView=(ImageView)converView.findViewById(R.id.imgview);
            mVHolder.mTextView=(TextView)converView.findViewById(R.id.text);
            mVHolder.mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mVHolder.mImageView.setPadding(8,8,8,8);
            converView.setTag(mVHolder);
        }else{
            mVHolder=(ViewHolder)converView.getTag();
        }
        mVHolder.mImageView.setImageBitmap(Images.get(position));
        mVHolder.mTextView.setText(ShowNames.get(position));
        
        return converView;
    }
    
    public void filterchan(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ShowNames.clear();
        Images.clear();
        if (charText.length() == 0) {
            ShowNames.addAll(arraylist);
            Images= Channels.GetAllImages();
        } else {
            for (String wp : ChannelNames) {
                if (wp.toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    ShowNames.addAll(Channels.GetAllShowName(wp));
                    Images.addAll(Channels.GetAllShowImage(wp));
                }
            }
        }
        notifyDataSetChanged();
    }
    
	
}
