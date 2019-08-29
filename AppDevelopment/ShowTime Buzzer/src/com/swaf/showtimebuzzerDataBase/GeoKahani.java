package com.swaf.showtimebuzzerDataBase;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

public class GeoKahani extends Channels {

	String[] dayname = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY",
			"THURSDAY", "FRIDAY", "SATURDAY" };

	public static int channelID = 4;

	public GeoKahani(Context c) {
		super(c);
		if (!CheckChannel(channelID)) {
			try {
				Pattern pat = Pattern
						.compile("<section id=\"page\">(.+)</section>");
				Pattern progtime = Pattern
						.compile("<header><h2>(.*?)</h2></header>.*?<img src=\"(.*?)\".*?/><br><strong>([\\d\\D]+?)</strong>");
					Matcher mat = pat.matcher(new ExtractData().execute(
						"http://geokahani.tv/program-guide").get());

				while (mat.find() != false) {
					String[] st = mat.group(1).toString()
							.split("<div class=\"title red\"(.*?)</div>");

					for (int i = 1; i < 8; i++) {
						mat = progtime.matcher(st[i]);
						while (mat.find() != false) {
							AddShow(channelID, mat.group(1).toString(), mat
									.group(3).toString(), dayname[i - 1]);

							AddImage(mat.group(1).toString(),
									mat.group(2).toString());

						}
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
