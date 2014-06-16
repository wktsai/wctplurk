package wct.apps.plurk;

import java.util.ArrayList;
//import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private final static int _DBVersion = 1;
	private final static String _DBName = "wctplurk.db";
	
	public DBHelper(Context context)
	{
		super(context, _DBName, null, _DBVersion);
	}
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		final String SQL_Create_Config = "CREATE TABLE IF NOT EXISTS Config ("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "Name TEXT,"
				+ "Value TEXT"
				+ ");";
		db.execSQL(SQL_Create_Config);
		
		final String SQL_Create_Friends = "CREATE TABLE IF NOT EXISTS Friends ("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "nick_name TEXT,"
				+ "full_name TEXT,"
				+ "display_name TEXT,"
				+ "CreateTime TEXT,"
				+ "ModifiedTime TEXT,"
				+ "RemoveTime TEXT"
				+ ");";
		db.execSQL(SQL_Create_Friends);
		
		final String SQL_Create_NameHistory = "CREATE TABLE IF NOT EXISTS NameHistory ("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "nick_name TEXT,"
				+ "full_name TEXT,"
				+ "display_name TEXT,"
				+ "CreateTime TEXT"
				+ ");";
		db.execSQL(SQL_Create_NameHistory);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void ReadConfig()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query("Config",
				new String[] {"_id", "Name", "Value"},
				null, null, null, null, null);
		//Cursor cursor = db.rawQuery("SELECT _id, Name, Value FROM Config", null);
		while(cursor.moveToNext())
		{
			int _id = cursor.getInt(0);
			String Name = cursor.getString(1);
			String Value = cursor.getString(2);
			if(Name == "Token")
				ConfigValue.Token = Value;
			else if(Name == "TokenSecret")
				ConfigValue.TokenSecret = Value;
			else if(Name == "DeviceID")
				ConfigValue.DeviceID = Value;
		}
		cursor.close();
		db.close();
	}
	
	public void WriteConfig()
	{
		SQLiteDatabase dbr = this.getReadableDatabase();
		SQLiteDatabase dbw = this.getWritableDatabase();
		ArrayList<String> updateSql = new ArrayList<String>();
		Cursor cursor = dbr.query("Config",
				new String[] {"_id", "Name", "Value"},
				null, null, null, null, null);
		Boolean flag_Token = false;
		Boolean flag_TokenSecret = false;
		Boolean flag_DeviceID = false;
		while(cursor.moveToNext())
		{
			int _id = cursor.getInt(0);
			String Name = cursor.getString(1);
			String Value = cursor.getString(2);
			if(Name == "Token")
			{
				if(Value != ConfigValue.Token)
				{
					dbw.execSQL("");
					updateSql.add("update Config set Value = ? where Config = ?");
				}
				flag_Token = true;
			}
			else if(Name == "TokenSecret")
				ConfigValue.TokenSecret = Value;
			else if(Name == "DeviceID")
				ConfigValue.DeviceID = Value;
		}
	}

}
