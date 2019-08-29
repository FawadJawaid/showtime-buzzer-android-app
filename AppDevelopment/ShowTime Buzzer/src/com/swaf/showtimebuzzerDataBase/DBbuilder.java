package com.swaf.showtimebuzzerDataBase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;

public class DBbuilder {
	private Context con;
	private static final String DATABASE_NAME = "ShowBuzzDB";
	private static final int DBVersion = 1;
	private static final String[] Channel_Names = { "A-PLUS", "ATV", "HUM2",
			"GEOKAHANI", "PTVHOME", "URDU1" };
	private static final String[] Channel_Websites = { "www.a-plus.tv",
			"www.atv.com.pk", "www.hum2.tv", "www.geokahani.tv",
			"home.ptv.com.pk", "www.urdu1.tv" };

	private DatabaseHelper DBhelp;
	private SQLiteDatabase DB;
	
	public DBbuilder(Context c) {
		this.con = c;
		DBhelp = new DatabaseHelper(con);
	}

	public DBbuilder open() {
		DB = DBhelp.getWritableDatabase();
		ContentValues cv = new ContentValues();
		for (int i = 0; i < Channel_Names.length; i++) {

			cv.put("Name", Channel_Names[i]);
			cv.put("Website", Channel_Websites[i]);
			try {
				DB.insertOrThrow("Channel", null, cv);
			} catch (SQLException s) {
				s.printStackTrace();
			}
			cv.clear();
		}

		return this;
	}

	public void close() {
		DBhelp.close();
	}

	public void insertshow(int cID, String dname, String time, String day) {
		ContentValues cv = new ContentValues();
		cv.put("cID", cID);
		cv.put("Name", dname);
		cv.put("Time", time);
		cv.put("Day", day);
		DB.insert("Show", null, cv);
	}

	public void insetchannel(int ChannelID, String Cname, String Cweb) {
		ContentValues cv = new ContentValues();
		cv.put("ChannelID", ChannelID);
		cv.put("Name", Cname);
		cv.put("Website", Cweb);
		DB.insert("Channel", null, cv);
	}

	public void insertImage(String show, String ImagePath) {

		if (!checkimage(show)) {
			show = show.replace("'", "''");
			Cursor c = DB.rawQuery("SELECT showID FROM show WHERE Name ='"
					+ show + "';", null);

			if (c.moveToFirst()) {
				ContentValues cv = new ContentValues();
				cv.put("sID", c.getInt(0));
				try {
					cv.put("Image", new ImageDownloader().execute(ImagePath)
							.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DB.insert("show_Image", null, cv);
			}
		}
	}

	public void insertImage(String show, Bitmap Image) {
		if (!checkimage(show)) {
			show = show.replace("'", "''");

			Cursor c = DB.rawQuery("SELECT showID FROM show WHERE Name ='"
					+ show + "';", null);
			ByteArrayOutputStream blob = new ByteArrayOutputStream();
			Image.compress(CompressFormat.PNG, 0, blob);

			if (c.moveToFirst()) {
				ContentValues cv = new ContentValues();
				cv.put("sID", c.getInt(0));
				cv.put("Image", blob.toByteArray());
				DB.insert("show_Image", null, cv);
			}
		}
	}

	public boolean checkimage(String show) {
		show = show.replace("'", "''");
		if (DB.rawQuery(
				"SELECT * FROM Show , show_Image WHERE showID = sID AND Name ='"
						+ show + "';", null).moveToFirst())
			return true;
		else
			return false;
	}
	
	public Bitmap getimage(String show) {
		show = show.replace("'", "''");

		Cursor c = DB.rawQuery(
				"SELECT Image,sID FROM Show_Image, show WHERE showID = sID AND Name ='"
						+ show + "';", null);
		BitmapFactory.Options imageopt = new Options();
		Cursor chan;
		
		if (c.moveToFirst()) {
			chan =DB.rawQuery("SELECT cID FROM Show,Show_image WHERE sID = ShowID AND sID ="+ c.getInt(1)+ ";" , null);
			chan.moveToFirst();
			if(chan.getInt(0) == 4) imageopt.inSampleSize = 2;
			else imageopt.inSampleSize =1;
			return BitmapFactory.decodeByteArray(c.getBlob(0), 0,
					c.getBlob(0).length, imageopt);
		} else
			return null;
	}

	public ArrayList<String> getshowbychannel(int ChannelID) {
		ArrayList<String> ret = new ArrayList<String>();
		Cursor c = DB.rawQuery("SELECT DISTINCT Name FROM Show WHERE cID ='"
				+ ChannelID + "';", null);

		if (c.moveToFirst()) {
			do {
				ret.add(c.getString(0));
			} while (c.moveToNext());
		}

		return ret;
	}

	public ArrayList<String> getshowbychannel(String ChannelName) {
		ArrayList<String> ret = new ArrayList<String>();
		Cursor c = DB.rawQuery("SELECT ChannelID FROM Channel WHERE Name ='"
				+ ChannelName + "';", null);

		if (c.moveToFirst()) {
			ret = getshowbychannel(c.getInt(0));
		}

		return ret;
	}

	public ArrayList<String> getallshows() {
		ArrayList<String> ret = new ArrayList<String>();
		Cursor c = DB.rawQuery("SELECT DISTINCT Name FROM Show ORDER BY Name;",
				null);

		if (c.moveToFirst()) {
			do {
				ret.add(c.getString(0));
			} while (c.moveToNext());
		}

		return ret;
	}

	public ArrayList<String> getimageshowname() {
		ArrayList<String> ret = new ArrayList<String>();
		Cursor c = DB
				.rawQuery(
						"SELECT Name FROM Show,Show_image WHERE sID = ShowID ORDER BY sID;",
						null);

		if (c.moveToFirst()) {
			do {
				ret.add(c.getString(0));
			} while (c.moveToNext());
		}

		return ret;
	}

	public ArrayList<Bitmap> getallimages() {
		ArrayList<Bitmap> ret = new ArrayList<Bitmap>();
		Cursor show = DB.rawQuery("SELECT Image,sID FROM Show_Image ORDER BY sID;",
				null);
		Cursor chan;
		BitmapFactory.Options imageopt = new Options();
		if (show.moveToFirst()) {
			do {
				chan =DB.rawQuery("SELECT cID FROM Show,Show_image WHERE sID = ShowID AND sID ="+ show.getInt(1)+ ";" , null);
				chan.moveToFirst();
				if(chan.getInt(0) == 4) imageopt.inSampleSize = 2;
				else imageopt.inSampleSize =1;
				ret.add(BitmapFactory.decodeByteArray(show.getBlob(0), 0,
						show.getBlob(0).length, imageopt));
			} while (show.moveToNext());
		}

		return ret;
	}

	public ArrayList<String> gettime(String ShowName, String Day) {
		ArrayList<String> ret = new ArrayList<String>();
		ShowName = ShowName.replace("'", "''");
		Cursor c = DB.rawQuery("SELECT Time FROM Show WHERE Name = '"
				+ ShowName + "' AND Day = '" + Day + "';", null);
		if (c.moveToFirst()) {
			do {
				if (!(c.getString(0).toLowerCase(Locale.getDefault())
						.contains("pm") || c.getString(0)
						.toLowerCase(Locale.getDefault()).contains("am"))) {
					int Time = Integer
							.parseInt(c.getString(0).replace(":", ""));
					StringBuilder SB = new StringBuilder();
					if (Time == 0)
						ret.add("12:00 AM");
					else if (Time == 1200)
						ret.add("12:00 PM");
					else if (Time > 1299 && Time < 2198)
						ret.add(SB.append(Time - 1200).append(" PM")
								.insert(1, ':').toString());
					else if (Time > 2199)
						ret.add(SB.append(Time - 1200).append(" PM")
								.insert(2, ':').toString());
					else if (Time < 1298 && Time > 999)
						ret.add(SB.append(Time).append(" AM").insert(2, ':')
								.toString());

					else
						ret.add(SB.append(Time).append(" AM").insert(1, ':')
								.toString());
				}
			} while (c.moveToNext());
		}
		return ret;
	}

	public int getday(int ShowID) {
		Cursor c = DB.rawQuery("SELECT Day FROM Show WHERE ShowID = '" + ShowID
				+ "';", null);
		if (c.moveToFirst()) {
			if (c.getString(0).equalsIgnoreCase("Sunday")) {
				return 1;
			} else if (c.getString(0).equalsIgnoreCase("Monday")) {
				return 2;
			} else if (c.getString(0).equalsIgnoreCase("Tuesday")) {
				return 3;
			} else if (c.getString(0).equalsIgnoreCase("Wednesday")) {
				return 4;
			} else if (c.getString(0).equalsIgnoreCase("Thursday")) {
				return 5;
			} else if (c.getString(0).equalsIgnoreCase("Friday")) {
				return 6;
			} else if (c.getString(0).equalsIgnoreCase("Saturday")) {
				return 7;
			} else
				return 0;
		} else
			return 0;
	}

	public int gethour(int ShowID) {
		Cursor c = DB.rawQuery("SELECT Time FROM Show WHERE ShowID = '"
				+ ShowID + "';", null);
		if (c.moveToFirst()) {
			if (c.getString(0).toLowerCase(Locale.getDefault()).contains("pm"))
				return Integer.parseInt(c.getString(0).substring(0,
						c.getString(0).indexOf(':'))) + 12;
			else if (c.getString(0).substring(0, c.getString(0).indexOf(':'))
					.equals("12"))
				return 0;
			else
				return Integer.parseInt(c.getString(0).substring(0,
						c.getString(0).indexOf(':')));
		} else
			return 0;
	}

	public int getminutes(int ShowID) {
		Cursor c = DB.rawQuery("SELECT Time FROM Show WHERE ShowID = '"
				+ ShowID + "';", null);
		if (c.moveToFirst()) {
			return Integer.parseInt(c.getString(0).substring(
					c.getString(0).indexOf(':') + 1,
					c.getString(0).indexOf(':') + 3));
		} else
			return 0;
	}

	public ArrayList<Bitmap> getimagesbychannel(int ChannelID) {
		ArrayList<Bitmap> ret = new ArrayList<Bitmap>();
		Cursor c = DB.rawQuery("SELECT DISTINCT Name FROM Show WHERE cID ='"
				+ ChannelID + "';", null);

		if (c.moveToFirst()) {
			do {
				ret.add(getimage(c.getString(0)));
			} while (c.moveToNext());
		}

		return ret;
	}

	public ArrayList<Bitmap> getimagesbychannel(String ChannelName) {
		ArrayList<Bitmap> ret = new ArrayList<Bitmap>();
		Cursor c = DB.rawQuery("SELECT ChannelID FROM Channel WHERE Name ='"
				+ ChannelName + "';", null);

		if (c.moveToFirst()) {
			ret = getimagesbychannel(c.getInt(0));
		}

		return ret;
	}

	public ArrayList<String> getallchannels() {
		ArrayList<String> ret = new ArrayList<String>();
		Cursor c = DB.rawQuery("SELECT Name FROM Channel ORDER BY Name;", null);
		if (c.moveToFirst()) {
			do
				ret.add(c.getString(0));
			while (c.moveToNext());
		}
		return ret;

	}

	public void addtocol(String ShowName, String Atime) {
		ShowName = ShowName.replace("'", "''");
		ContentValues cv = new ContentValues();
		cv.put("AlarmTime", Atime);
		Cursor c = DB.rawQuery("SELECT ShowID FROM Show WHERE Name = '"
				+ ShowName + "';", null);
		if (c.moveToFirst())
			cv.put("shID", c.getInt(0));

		DB.insert("My_Collection", null, cv);

	}

	public void remfromcol(String ShowName) {
		ShowName = ShowName.replace("'", "''");
		DB.execSQL("DELETE FROM My_Collection WHERE shID in (SELECT ShowID FROM Show WHERE Name='"
				+ ShowName + "');");
	}

	public boolean checkcol(String ShowName) {
		ShowName = ShowName.replace("'", "''");
		if (DB.rawQuery(
				"SELECT * FROM My_Collection,Show WHERE shID=showID AND Name='"
						+ ShowName + "';", null).moveToFirst())
			return true;
		else
			return false;
	}

	public ArrayList<String> getcolshows() {
		ArrayList<String> ret = new ArrayList<String>();
		Cursor c = DB.rawQuery(
				"SELECT Name FROM Show,My_Collection WHERE shID = showID;",
				null);

		if (c.moveToFirst()) {
			do {
				ret.add(c.getString(0));
			} while (c.moveToNext());
		}

		return ret;
	}

	public boolean checkchan(int ChanID) {
		if (DB.rawQuery("SELECT * FROM Show WHERE cID ='" + ChanID + "';", null)
				.moveToFirst())
			return true;
		else
			return false;
	}

	public ArrayList<Integer> getids(String ShowName) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		ShowName = ShowName.replace("'", "''");
		Cursor c = DB.rawQuery("SELECT showID FROM Show WHERE Name ='"
				+ ShowName + "';", null);

		if (c.moveToFirst()) {
			do {
				ret.add(c.getInt(0));
			} while (c.moveToNext());
		}
		return ret;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		private static final String TAG = "ShowBuzzDB Upgrading...";

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DBVersion);

		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL("CREATE TABLE Channel (ChannelID INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Name TEXT NOT NULL,"
					+ " Website TEXT NOT NULL, UNIQUE(Name));");

			_db.execSQL("CREATE TABLE Show("
					+ "showID INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "cID INTEGER NOT NULL," + "Name TEXT NOT NULL,"
					+ "Time TEXT NOT NULL," + "Day TEXT NOT NULL,"
					+ "FOREIGN KEY(cID) REFERENCES Channel(ChannelID),"
					+ "UNIQUE(Name,Time,Day));");

			_db.execSQL("CREATE TABLE show_Image("
					+ "sID INTEGER PRIMARY KEY , " + "Image BLOB NOT NULL,"
					+ "FOREIGN KEY(sID) REFERENCES Show(showID));");

			_db.execSQL("CREATE TABLE My_Collection("
					+ "shID INTEGER, "
					+ "AlarmTime TEXT NOT NULL,"
					+ "FOREIGN KEY(shID) REFERENCES Show(showID), PRIMARY KEY(shID,AlarmTime));");

		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version "
					+ oldVersion + " to " + newVersion
					+ ", which will destroy all old data!");

			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS Channel;");
			_db.execSQL("DROP TABLE IF EXISTS Show;");
			_db.execSQL("DROP TABLE IF EXISTS Show_Image;");

			// Recreate new database:
			onCreate(_db);

		}

	}
}
