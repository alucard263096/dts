package com.example.objects;

public class StaticVar {
	// Agent connection related
	public static String USERID="";
	public static String ServerName="192.168.0.10";
	//public static int ServerPort=8008;
	public static int ServerPort=29999;
	// Transfer Byte Array Size 512Kb
	public static int ByteArraySize = 524288;

	// DB related
	public static String DB_Working_PATH = "/data/data/com.example.dts/databases/";
	public static String DB_Backup_PATH = "/data/data/com.example.dts/databases/";
	public static String DB_Backup_filename_suffix = "bak";
	public static String Data_DB_Data_Template = "user_0.db";
}
