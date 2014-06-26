package wct.apps.plurk;

import java.util.ArrayList;
import java.util.List;

import wct.apps.plurk.Database.DBHelper;
import wct.apps.plurk.OAuth.Plurk;
import wct.apps.plurk.OAuth.Plurk.OnRequestListener;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;

public class MainActivity extends FragmentActivity {
	private DBHelper _dbHelper = null;
	private Plurk _plurk = null;
	private int tabIndex = 0; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("MainActivity", "onCreate");
		
		setContentView(R.layout.activity_main);
		
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("tab1").setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("tab2").setContent(R.id.tab2));
		
		//tabHost.getTabWidget().getTabCount()
		
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
	
	@SuppressWarnings("deprecation")
	private GestureDetector detector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	});
	
	protected void showNext() {
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		if(tabIndex + 1 >= tabHost.getTabWidget().getTabCount())
			tabIndex++;
		else
			tabIndex = 0;
		tabHost.setCurrentTab(tabIndex);
	}
	
	protected void showPre() {
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		if(tabIndex == 0)
			tabIndex = tabHost.getTabWidget().getTabCount() - 1;
		else
			tabIndex--;
		tabHost.setCurrentTab(tabIndex);
	}
}