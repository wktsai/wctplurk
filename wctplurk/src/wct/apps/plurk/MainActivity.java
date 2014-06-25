package wct.apps.plurk;

import wct.apps.plurk.Database.DBHelper;
import wct.apps.plurk.OAuth.Plurk;
import wct.apps.plurk.OAuth.Plurk.OnRequestListener;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	private DBHelper _dbHelper = null;
	private Plurk _plurk = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("MainActivity", "onCreate");
		
		_dbHelper = new DBHelper(this);
		_plurk = Plurk.getInstance();
		
		Log.i("Plurk", _plurk.getToken());
		Log.i("Plurk", _plurk.getSecret());
		
		(new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				

				_plurk.request("Profile/getOwnProfile", null, new OnRequestListener(){

					@Override
					public void onComplete(String response) {
						// TODO Auto-generated method stub
						if (response == null || response.length() <= 0){
						}
						else
						{
							Log.d("MainActivity.onCreate.AsyncTask", response);
						}
					}

					@Override
					public void onError() {
						// TODO Auto-generated method stub
						
					}
				});
				
				return null;
			}
			
		}).execute();
		
		/*
		String Token = _dbHelper.getConfigFactory().getToken();
		String TokenSecret = _dbHelper.getConfigFactory().getTokenSecret();
		
		_plurk = new Plurk();
		if(Token != null && Token != "" && TokenSecret != null && TokenSecret != "")
			_plurk.setTokenAndSecret(Token, TokenSecret);
		_plurk.authorize(this, new OnAuthorizeListener() {
			@Override
			public void onAuthorized() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onComplete(Bundle values) {
				// TODO Auto-generated method stub
				
			}
		});
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
		_dbHelper.close();
		super.onDestroy();
	}
}