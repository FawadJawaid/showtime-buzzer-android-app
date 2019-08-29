package com.swaf.showtimebuzzerDataBase;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Html;

import com.swaf.showtimebuzzer.R;

public class PtvHome extends Channels {

	public static int channelID = 5;
	String[] dayname = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY",
			"FRIDAY", "SATURDAY", "SUNDAY" };

	String[] temp = { "TVSchedule.asp", "Schedule-Tue.asp", "Schedule-Wed.asp",
			"Schedule-Thu.asp", "Schedule-Fri.asp", "Schedule-Sat.asp",
			"Schedule-Sun.asp" };

	public PtvHome(Context c) {
		super(c);
		if (!CheckChannel(channelID)) {
			for (int i = 0; i < 7; i++) {
				try {
					Pattern pat = Pattern
							.compile("PTV-HOME PROGRAMMES ON(?: NETWORK &)? SATELLITE(?: ONLY)?([\\d\\D]+?)SELECTION FROM DAY TIME PROGRAMMES");

					Matcher mat = pat
							.matcher((new ExtractData()
									.execute("http://home.ptv.com.pk/"
											+ temp[i]).get()));

					while (mat.find() != false) {
						pat = Pattern.compile("(\\d{1,4}) -(.+)");
						
						Matcher mat1 = pat.matcher(Html.fromHtml(mat.group(1))
								.toString());
						while (mat1.find() != false) {
						
							AddShow(channelID, mat1.group(2).toString().trim(), mat1
									.group(1).toString(), dayname[i]);

							AddImage(mat1.group(2).toString(),
									BitmapFactory.decodeResource(
											c.getResources(),
											R.drawable.imgntavlble));
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
