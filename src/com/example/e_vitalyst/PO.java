package com.example.e_vitalyst;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PO extends SQLiteOpenHelper{

	public PO(Context context) 
	{
		super(context, "e-Vitalyst", null,1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL("CREATE TABLE config (domain_name varchar(40),port varchar(40))");	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		
		
	}
	
}
