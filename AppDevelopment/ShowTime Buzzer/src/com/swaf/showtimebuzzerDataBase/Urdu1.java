package com.swaf.showtimebuzzerDataBase;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

public class Urdu1 extends Channels {

	String[] dayname = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY",
			"FRIDAY", "SATURDAY", "SUNDAY" };

	public static int channelID = 6;

	public Urdu1(Context c) {
		super(c);
		if (!CheckChannel(channelID)) {
			Pattern pat = Pattern
					.compile("<div class=\"pro-sch-time\">([\\d\\D]+?)</div>[\\d\\D]+?<img src=\"(.*?)\"[\\d\\D]+?<div class=\"sch-pro-name\">([\\d\\D]+?)</div>");

			for (int i = 1; i <= 7; i++) {
				try {
					Matcher mat = pat
							.matcher((new ExtractData()
									.execute("http://www.urdu1.tv/schedule/index.php?days_id="
											+ dayname[i - 1]).get()));

					while (mat.find() != false) {
						AddShow(channelID,
								mat.group(3).toString(),
								mat.group(1)
										.toString()
										.replaceFirst(" - \\d{1,2}:\\d{1,2}",
												""), dayname[i - 1]);

						AddImage(mat.group(3).toString(),
								"http://www.urdu1.tv/"
										+ mat.group(2).toString());
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