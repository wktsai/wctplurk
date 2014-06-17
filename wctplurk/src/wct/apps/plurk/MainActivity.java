package wct.apps.plurk;

import wct.apps.plurk.Database.DBHelper;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
//import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private DBHelper _db = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		openDB();
		
		String tmp = _db.getConfigFactory().getToken();
		if(tmp == null)
			Log.i("MainActivity.onCreate", "Token is null");
		else
			Log.i("MainActivity.onCreate", "Token = " + _db.getConfigFactory().getToken());
		
		_db.getConfigFactory().setToken("test11");
		
		tmp = _db.getConfigFactory().getToken();
		if(tmp == null)
			Log.i("MainActivity.onCreate", "Token is null");
		else
			Log.i("MainActivity.onCreate", "Token = " + _db.getConfigFactory().getToken());
		
		/*
		ConfigValue.setContext(this);
			
		openDB();
		ConfigValue.setToken("tset1");
		ConfigValue.setTokenSecret("tset2");
		ConfigValue.setDeviceID("tset3");
		
		Log.i("MainActivity.onCreate", ConfigValue.getToken());
		Log.i("MainActivity.onCreate", ConfigValue.getTokenSecret());
		Log.i("MainActivity.onCreate", ConfigValue.getDeviceID());
		*/
		
		/*
		if(!AuthUser())
		{
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			MainActivity.this.finish();
		}
		*/
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDB();
	}
	
	private void openDB() {
		_db = DBHelper.getInstance(this);
	}
	
	private void closeDB() {
		_db.close();
	}
	
	private boolean AuthUser() {
		
		return false;
	}
	
	@Override
	public void onClick(View v)
	{
		TextView t =(TextView)findViewById(R.id.textView1);
		t.setText(_db.getConfigFactory().getToken());
		/*
		Log.i("MainActivity.onClick", ConfigValue.getToken());
		Log.i("MainActivity.onClick", ConfigValue.getTokenSecret());
		Log.i("MainActivity.onClick", ConfigValue.getDeviceID());
		
		ConfigValue.setToken("tset4");
		ConfigValue.setTokenSecret("tset5");
		ConfigValue.setDeviceID("tset6");
		
		Log.i("MainActivity.onClick", ConfigValue.getToken());
		Log.i("MainActivity.onClick", ConfigValue.getTokenSecret());
		Log.i("MainActivity.onClick", ConfigValue.getDeviceID());
		*/
	}
}
