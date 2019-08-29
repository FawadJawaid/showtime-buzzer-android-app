package com.swaf.showtimebuzzerDataBase;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.swaf.showtimebuzzer.MainActivity;

public class Channels extends MainActivity {

	private static DBbuilder DB;
	
	public Channels() {};
	public Channels(Context con) {
		// TODO Auto-generated constructor stub
		DB = new DBbuilder(con);
		DB.open();
		}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		DB.close();
	}



	protected void AddShow(int ChannelID, String dname, String time, String day) {
		DB.insertshow(ChannelID, dname, time, day);
	}

	protected void AddImage(String Dname, String Image_Path) {
		DB.insertImage(Dname, Image_Path);

	}
	
	protected void AddImage(String Dname, Bitmap Image) {
		DB.insertImage(Dname, Image);

	}

	protected boolean CheckImage(String Name) {
		return DB.checkimage(Name);
	}

	protected ArrayList<String> GetAllShowName(int ChannelID) {
		return DB.getshowbychannel(ChannelID);
	}

	public static ArrayList<String> GetAllShowName(String ChannelName) {
		return DB.getshowbychannel(ChannelName);
	}

	public static ArrayList<String> GetAllShowName() {
		return DB.getimageshowname();
	}

	protected ArrayList<Bitmap> GetAllShowImage(int ChannelID) {
		return DB.getimagesbychannel(ChannelID);
	}
	
	public static ArrayList<Bitmap> GetAllShowImage(String ChannelName) {
		return DB.getimagesbychannel(ChannelName);
	}

	public static ArrayList<String> GetTime(String ShowName, String Day)
	{
		return DB.gettime(ShowName,Day);
	}

	public static int GetDay(int ShowID)
	{
		return DB.getday(ShowID);
	}
	
	public static Bitmap GetShowImage(String Name) {
		return DB.getimage(Name);
	}

	public static ArrayList<Bitmap> GetAllImages() {
		return DB.getallimages();

	}
	
	public static ArrayList<String> GetAllChannelNames()
	{
		return DB.getallchannels();
	}
	
	public static void AddToCollection(String Name, String Time)
	{
		DB.addtocol(Name, Time);
	}

	public static void RemFromCollection(String Name)
	{
		DB.remfromcol(Name);
	}
	
	public static boolean CheckInCol(String Name)
	{
		return DB.checkcol(Name);
	}
	
	public boolean CheckChannel(int cID)
	{
		return DB.checkchan(cID);
	}
	
	public static ArrayList<String> GetCollection()
	{
		return DB.getcolshows();
	}
	
	public static ArrayList<Integer> GetAllIDs(String ShowName)
	{
		return DB.getids(ShowName);
	}
	
	public static Integer GetHour(int ShowID)
	{
		return DB.gethour(ShowID);
	}
	
	public static Integer GetMinutes(int ShowID)
	{
		return DB.getminutes(ShowID);
	}
	
	public static void CloseDB()
	{
		DB.close();
	}
}
