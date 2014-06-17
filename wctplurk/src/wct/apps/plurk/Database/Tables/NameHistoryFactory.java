package wct.apps.plurk.Database.Tables;

import java.util.ArrayList;

import wct.apps.plurk.Database.DBHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public final class NameHistoryFactory implements IFactory
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
			+ "CreateTime TEXT"
			+ ");";
	public static final String DropSQL = "DROP TABLE IF EXISTS " + TableName + ";";
	public final String[] Columns = new String[] {"_id", "nick_name", "full_name", "display_name", "CreateTime"};
	
	/* Class Constructor */
	/**
	 * The NameHistoryFactory constructor
	 * @param context
	 */
	public NameHistoryFactory(Context context)
	{
		this._context = context;
		this._db = new DBHelper(this._context, this);
	}

	public NameHistoryFactory(Context context, DBHelper db)
	{
		this._context = context;
		if(db == null)
			this._db = new DBHelper(this._context, this);
		else
			this._db = db;
	}

	/* Class private function*/
	
	/* Class public function*/
	public static NameHistoryFactory getInstance(Context context)
	{
		return new NameHistoryFactory(context);
	}

	public static NameHistoryFactory getInstance(Context context, DBHelper db)
	{
		return new NameHistoryFactory(context, db);
	}
	
	public ArrayList<NameHistory> getAllData()
	{
		ArrayList<NameHistory> list = new ArrayList<NameHistory>();
		SQLiteDatabase dbr = _db.getReadableDatabase();
		Cursor cursor = dbr.query(TableName, Columns, null, null, null, null, null);
		
		while(cursor.moveToNext())
		{
			NameHistory item = new NameHistory();
			item._id = cursor.getInt(0);
			item.nick_name = cursor.getString(1);
			item.full_name = cursor.getString(2);
			item.display_name = cursor.getString(3);
			item.CreateTime = cursor.getString(4);
			
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
	}
	
	public void Drop()
	{
		SQLiteDatabase dw = _db.getWritableDatabase();
		dw.execSQL(DropSQL);
	}
}
