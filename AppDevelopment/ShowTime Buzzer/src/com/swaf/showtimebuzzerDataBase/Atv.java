package com.swaf.showtimebuzzerDataBase;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;

public class Atv extends Channels {
	String[] dayname = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY",
			"FRIDAY", "SATURDAY", "SUNDAY" };
	public static int channelID = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Pattern pat = Pattern
				.compile("<div class=\"time\">([\\d\\D]+?)</div>[\\d\\D]+?<div class=\"prog\">([\\d\\D]+?)</div>");

		for (int i = 1; i <= 7; i++) {
			try {
				Matcher mat = pat.matcher((new ExtractData()
						.execute("http://www.atv.com.pk/schedule.php?d="
								+ Integer.toString(i)).get()));

				while (mat.find() != false) {
					AddShow(channelID, mat.group(2).toString(), mat.group(1)
							.toString(), dayname[i - 1]);

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
