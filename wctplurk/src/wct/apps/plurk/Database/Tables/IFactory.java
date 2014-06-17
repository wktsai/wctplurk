package wct.apps.plurk.Database.Tables;

import java.util.ArrayList;

public interface IFactory {
	String TableName = null;
	String[] Columns = null;
	
	String CreateSQL = null;
	String DropSQL = null;
	
	ArrayList<? extends IRow> getAllData();
	void Create();
	void Drop();
}