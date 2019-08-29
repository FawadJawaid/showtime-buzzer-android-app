package com.swaf.showtimebuzzerDataBase;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Html;

import com.swaf.showtimebuzzer.R;

public class Hum2 extends Channels {

	int Daynum = 0;
	String[] dayname = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY",
			"THURSDAY", "FRIDAY", "SATURDAY" };
	int dcalc = 0;
	public static int channelID = 3;

	public Hum2(Context c) {
		super(c);
		if (!CheckChannel(channelID)) {
			Pattern pat = Pattern
					.compile("<td height=\"176\" align=\"center\" bgcolor=\"#F1F1F1\" class=\"Heading_txt_red\">([\\d\\D]+?)</td>[\\d\\D]+?<td width=\"334\" class=\"blue_heading_txt\">([\\d\\D]+?)</td>");
			Matcher mat;
			for (int i = 1; i <= 7; i++) {
				try {

					mat = pat.matcher((new ExtractData()
							.execute("http://www.hum2.tv/schedule.php?wid="
									+ Integer.toString(i)).get()));

					while (mat.find() != false) {
						AddShow(channelID,
								Html.fromHtml(mat.group(2).toString())
										.toString(),
								Html.fromHtml(mat.group(1).toString())
										.toString(), dayname[i - 1]);
						AddImage(Html.fromHtml(mat.group(2).toString())
								.toString(), BitmapFactory.decodeResource(
								c.getResources(), R.drawable.imgntavlble));
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
