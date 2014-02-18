package com.example.utils;

import com.example.objects.StaticVar;

import android.content.Context;

public class DBFactory {
	public static BaseDB GetBaseDB(Context ctx){
		BaseDB db=new BaseDB(ctx);
		return db;
	}
	
	public static UserDB GetUserDB(Context ctx){
		UserDB db=new UserDB(ctx,String.valueOf(StaticVar.USERID));
		return db;
	}
}
