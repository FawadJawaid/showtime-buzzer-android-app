package com.swaf.showtimebuzzerDataBase;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.os.AsyncTask;
import android.util.Log;

public class ImageDownloader extends AsyncTask<String,String,byte[]> {
	 
    @Override
    protected byte[] doInBackground(String... param) {
        // TODO Auto-generated method stub
        return downloadBitmap(param[0]);
    }


    private byte[] downloadBitmap(String url) {
    	 try {
             URL imageUrl = new URL(url);
             URLConnection ucon = imageUrl.openConnection();

             InputStream is = ucon.getInputStream();
             BufferedInputStream bis = new BufferedInputStream(is);

             ByteArrayBuffer baf = new ByteArrayBuffer(500);
             int current = 0;
             while ((current = bis.read()) != -1) {
                     baf.append((byte) current);
             }
             return baf.toByteArray();
     } catch (Exception e) {
             Log.d("ImageManager", "Error: " + e.toString());
     }
        return null;
    }
}
