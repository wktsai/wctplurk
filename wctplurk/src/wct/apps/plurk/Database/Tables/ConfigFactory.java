package wct.apps.plurk.Database.Tables;

import java.util.ArrayList;

import wct.apps.plurk.Database.DBHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public final class ConfigFactory implements IFactory
{
	/* Private variable defined. */
	private Context _context = null;
	private ArrayList<Config> list = null;
	private String _Token = null;
	private String _TokenSecret = null;
	private String _DeviceID = null;
	private DBHelper _db = null;
	
	/* Public variable defined. */
	public static final String TableName = "Config";
	public static final String CreateSQL = 
			  "CREATE TABLE IF NOT EXISTS " + TableName + " ("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "Name TEXT,"
			+ "Value TEXT"
			+ ");";
	public static final String DropSQL = "DROP TABLE IF EXISTS " + TableName + ";";
	public final String[] Columns = new String[] {"_id", "Name", "Value"};
	
	/* Class Constructor */	
	public ConfigFactory(Context context)
	{
		this._context = context;
		this._db = new DBHelper(context, this);
	}
	
	public ConfigFactory(Context context, DBHelper db)
	{
		this._context = context;
		if(db == null)
			this._db = new DBHelper(context, this);
		else
			this._db = db;
	}

	/* Class private function*/
	
	/* Class public function*/
	public static ConfigFactory getInstance(Context context)
	{
		return new ConfigFactory(context);
	}
	
	public static ConfigFactory getInstance(Context context, DBHelper db)
	{
		return new ConfigFactory(context, db);
	}
	
	public void update(String name, String value)
	{
		Log.i("ConfigFactory.update", "name="+name);
		Log.i("ConfigFactory.update", "value="+value);
		SQLiteDatabase dbw = _db.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("Value", value);
		long rtn = dbw.update(TableName, values, "Name=?", new String[]{name});
		dbw.close();
	}
	
	public void insert(String name, String value)
	{
		Log.i("ConfigFactory.insert", "name="+name);
		Log.i("ConfigFactory.insert", "value="+value);
		SQLiteDatabase dbw = _db.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("Name", name);
		values.put("Value", value);
		long rtn = dbw.insert(TableName, null, values);
		dbw.close();
	}
	
	public void ReloadDataFromDB()
	{
		DBHelper db = new DBHelper(_context);
		SQLiteDatabase dbr = db.getReadableDatabase();
		Cursor cursor = dbr.query(TableName, Columns, null, null, null, null, null);
		if(list == null)
			list = new ArrayList<Config>();
		else
			list.clear();
		
		_Token = _TokenSecret = _DeviceID = null;
		
		while(cursor.moveToNext())
		{
			Config c = new Config();
			c._id = cursor.getInt(0);
			c.Name = cursor.getString(1);
			c.Value = cursor.getString(2);
			list.add(c);
			
			if(c.Name.equals("Token"))
			{
				Log.i("ConfigFactory.ReloadDataFromDB", "Expr is Token, and value="+c.Value);
				_Token = c.Value;
			}
			else if(c.Name.equals("TokenSecret"))
			{
				Log.i("ConfigFactory.ReloadDataFromDB", "Expr is TokenSecret, and value="+c.Value);
				_TokenSecret = c.Value;
			}
			else if(c.Name.equals("DeviceID"))
			{
				Log.i("ConfigFactory.ReloadDataFromDB", "Expr is DeviceID, and value="+c.Value);
				_DeviceID = c.Value;
			}
		}
		cursor.close();
		dbr.close();
		db.close();
	}
	
	/**
	 * Use this function to get consumer key.
	 * @return string
	 */
	public String getToken()
	{
		if(list == null)
			ReloadDataFromDB();
		return _Token;
	}
	
	/**
	 * Use this function to get consumer secret.
	 * @return
	 */
	public String getTokenSecret()
	{
		if(list == null)
			ReloadDataFromDB();
		return _TokenSecret;
	}
	
	/**
	 * Use this function to get Device ID.
	 * @return
	 */
	public String getDeviceID()
	{
		if(list == null)
			ReloadDataFromDB();
		return _DeviceID;
	}
	
	/** Use this function to set consumer key.
	 * 
	 * @author WCT
	 * @param value string
	 */
	public void setToken(String value)
	{
		if(list == null)
			ReloadDataFromDB();
		
		Log.i("ConfigFactory.setToken", "_Token is null=" + (_Token == null));
		
		if(_Token != null)
		{
			if(_Token.equals(value))
				update("Token", value);
		}
		else
			insert("Token", value);
	}
	
	/** Use this function to set consumer secret.
	 * 
	 * @author WCT
	 * @param value string
	 */	
	public void setTokenSecret(String value)
	{
		if(list == null)
			ReloadDataFromDB();
		if(_TokenSecret != null)
		{
			if(_TokenSecret.equals(value))
				update("TokenSecret", value);
		}
		else
			insert("TokenSecret", value);
	}
	
	/** Use this function to set Device ID.
	 * 
	 * @author WCT
	 * @param value string
	 */	
	public void setDeviceID(String value)
	{
		if(list == null)
			ReloadDataFromDB();
		if(_DeviceID != null)
		{
			if(_DeviceID.equals(value))
				update("DeviceID", value);
		}
		else
			insert("DeviceID", value);
	}
	
	public ArrayList<Config> getAllData()
	{
		if(list == null)
			ReloadDataFromDB();
		return list;
	}
	
	public void Create()
	{
		DBHelper db = new DBHelper(_context);
		SQLiteDatabase dw = db.getWritableDatabase();
		dw.execSQL(CreateSQL);
		dw.close();
		db.close();
	}
	
	public void Drop()
	{
		DBHelper db = new DBHelper(_context);
		SQLiteDatabase dw = db.getWritableDatabase();
		dw.execSQL(DropSQL);
		dw.close();
		db.close();
	}
}
