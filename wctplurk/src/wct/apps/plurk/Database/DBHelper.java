package wct.apps.plurk.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	/* Private variable defined. */
	private final static int _DBVersion = 1;
	private final static String _DBName = "wctplurk.db";
	private Context _context = null;
	
	private wct.apps.plurk.Database.Tables.ConfigFactory _ConfigFactory = null;
	private wct.apps.plurk.Database.Tables.FriendsFactory _FriendsFactory = null;
	private wct.apps.plurk.Database.Tables.NameHistoryFactory _NameHistoryFactory = null;
	
	/* Public variable defined. */
	
	/* Class Constructor */
	public DBHelper(Context context)
	{
		super(context, _DBName, null, _DBVersion);
		_context = context;
	}
	
	public DBHelper(Context context, wct.apps.plurk.Database.Tables.IFactory tableFactory)
	{
		super(context, _DBName, null, _DBVersion);
		_context = context;
		if(tableFactory != null && tableFactory instanceof wct.apps.plurk.Database.Tables.ConfigFactory)
			_ConfigFactory = (wct.apps.plurk.Database.Tables.ConfigFactory) tableFactory;
		else if(tableFactory != null && tableFactory instanceof wct.apps.plurk.Database.Tables.FriendsFactory)
			_FriendsFactory = (wct.apps.plurk.Database.Tables.FriendsFactory) tableFactory;
		else if(tableFactory != null && tableFactory instanceof wct.apps.plurk.Database.Tables.NameHistoryFactory)
			_NameHistoryFactory = (wct.apps.plurk.Database.Tables.NameHistoryFactory) tableFactory;
	}
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		_context = context;
	}
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
		_context = context;
	}
	
	/* Class static function. */
	public static DBHelper getInstance(Context context)
	{
		return new DBHelper(context);
	}

	/* Class private function*/
	
	/* Class public function*/
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(wct.apps.plurk.Database.Tables.ConfigFactory.CreateSQL);
		db.execSQL(wct.apps.plurk.Database.Tables.FriendsFactory.CreateSQL);
		db.execSQL(wct.apps.plurk.Database.Tables.NameHistoryFactory.CreateSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(wct.apps.plurk.Database.Tables.ConfigFactory.DropSQL);
		db.execSQL(wct.apps.plurk.Database.Tables.FriendsFactory.DropSQL);
		db.execSQL(wct.apps.plurk.Database.Tables.NameHistoryFactory.DropSQL);
	}
		
	public wct.apps.plurk.Database.Tables.ConfigFactory getConfigFactory()
	{
		if(_ConfigFactory == null)
			_ConfigFactory = wct.apps.plurk.Database.Tables.ConfigFactory.getInstance(_context, this);
		return _ConfigFactory;
	}
	
	public wct.apps.plurk.Database.Tables.FriendsFactory getFriendsFactory()
	{
		if(_FriendsFactory == null)
			_FriendsFactory = wct.apps.plurk.Database.Tables.FriendsFactory.getInstance(_context, this);
		return _FriendsFactory;
	}
	
	public wct.apps.plurk.Database.Tables.NameHistoryFactory getNameHistoryFactory()
	{
		if(_NameHistoryFactory == null)
			_NameHistoryFactory = wct.apps.plurk.Database.Tables.NameHistoryFactory.getInstance(_context, this);
		return _NameHistoryFactory;
	}
}
