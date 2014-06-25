package wct.apps.plurk;

import wct.apps.plurk.Database.DBHelper;
import wct.apps.plurk.OAuth.Plurk;
import wct.apps.plurk.OAuth.Plurk.OnRequestListener;
import wct.apps.plurk.OAuth.PlurkAuthActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Window;

public class EntryActivity extends Activity {
	private DBHelper _dbHelper = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_dbHelper = new DBHelper(this);
		
		String Token = _dbHelper.getConfigFactory().getToken();
		String TokenSecret = _dbHelper.getConfigFactory().getTokenSecret();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		Log.d("EntryActivity", "onCreate");
		
		Plurk _plurk = new Plurk();
		if(Token != null && Token != "" && TokenSecret != null && TokenSecret != "")
			_plurk.setTokenAndSecret(Token, TokenSecret);
		
		_plurk.authorize(this, new OnRequestListener() {

			@Override
			public void onComplete(String response) {
				// TODO Auto-generated method stub				
				Intent intent = new Intent();
				intent.setClass(EntryActivity.this, MainActivity.class);
				EntryActivity.this.startActivity(intent);
				EntryActivity.this.finish();
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(EntryActivity.this, PlurkAuthActivity.class);
				EntryActivity.this.startActivity(intent);
				EntryActivity.this.finish();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		_dbHelper.close();
		
		Log.d("EntryActivity", "onDestroy");
	}
}
