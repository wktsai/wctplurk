package wct.apps.plurk.Database.Tables;

import java.util.ArrayList;

import wct.apps.plurk.Database.DBHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public final class FriendsFactory implements IFactory
{
	/* Private variable defined. */
	private Context _context = null;
	private DBHelper _db = null;
	
	/* Public variable defined. */
	public static final String TableName = "Friends";
	public static final String CreateSQL = 
			  "CREATE TABLE IF NOT EXISTS " + TableName + " ("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "nick_name TEXT,"
			+ "full_name TEXT,"
			+ "display_name TEXT,"
			+ "CreateTime TEXT,"
			+ "ModifiedTime TEXT,"
			+ "RemoveTime TEXT"
			+ ");";
	public static final String DropSQL = "DROP TABLE IF EXISTS " + TableName + ";";
	public final String[] Columns = new String[] {"_id", "nick_name", "full_name", "display_name", "CreateTime", "ModifiedTime", "RemoveTime"};
	
	/* Class Constructor */	
	public FriendsFactory(Context context)
	{
		this._context = context;
		this._db = new DBHelper(context, this);
	}

	public FriendsFactory(Context context, DBHelper db)
	{
		this._context = context;
		if(db == null)
			this._db = new DBHelper(this._context, this);
		else
			this._db = db;
	}

	/* Class private function*/
	
	/* Class public function*/
	public static FriendsFactory getInstance(Context context)
	{
		return new FriendsFactory(context);
	}
	
	public static FriendsFactory getInstance(Context context, DBHelper db)
	{
		return new FriendsFactory(context, db);
	}
	
	public ArrayList<Friends> getAllData()
	{
		ArrayList<Friends> list = new ArrayList<Friends>();
		
		SQLiteDatabase dbr = _db.getReadableDatabase();
		Cursor cursor = dbr.query(TableName, Columns, null, null, null, null, null);
				
		while(cursor.moveToNext())
		{
			Friends item = new Friends();
			item._id = cursor.getInt(0);
			item.nick_name = cursor.getString(1);
			item.full_name = cursor.getString(2);
			item.display_name = cursor.getString(3);
			item.CreateTime = cursor.getString(4);
			item.ModifiedTime = cursor.getString(5);
			item.RemoveTime = cursor.getString(6);
			list.add(item);
		}
		cursor.close();
		dbr.close();
		
		return list;
	}
	
	public void Create()
	{
		SQLiteDatabase dw = _db.getWritableDatabase();
		dw.execSQL(CreateSQL);
		dw.close();
	}
	
	public void Drop()
	{
		SQLiteDatabase dw = _db.getWritableDatabase();
		dw.execSQL(DropSQL);
		dw.close();
	}
}
