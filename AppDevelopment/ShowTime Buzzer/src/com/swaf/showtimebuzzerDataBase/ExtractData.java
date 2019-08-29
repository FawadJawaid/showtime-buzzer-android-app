package com.swaf.showtimebuzzerDataBase;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import android.os.AsyncTask;

public class ExtractData extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... urlStr) {
		// do stuff on non-UI thread
		StringBuffer htmlCode = new StringBuffer();
		try {
			URL url = new URL(urlStr[0]);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				htmlCode = htmlCode.append(inputLine);

			}

			in.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

		return htmlCode.toString();
	}

}
