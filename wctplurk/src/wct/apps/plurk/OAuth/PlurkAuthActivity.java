package wct.apps.plurk.OAuth;

import wct.apps.plurk.MainActivity;
import wct.apps.plurk.Database.DBHelper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class PlurkAuthActivity extends Activity {
	private static PlurkAuthActivity _instance = null;
	
	private FrameLayout mContent = null;
	private WebView mWebView;
	private String mUrl = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_instance = this;
		
		Log.d("PlurkAuthActivity", "onCreate");
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		Plurk.getInstance();
		
		new wct.apps.plurk.OAuth.AsyncRequestTokenTask(Plurk.getInstance().getConsumer(), Plurk.getInstance().getProvider(), new wct.apps.plurk.OAuth.IAsyncResponse() {
			@Override
			public void processFinish(String response) {
				mUrl = response;
				
				Log.d("PlurkAuthActivity", mUrl);
				
				setUpWebView(0);
				
				addContentView(mContent, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			}
		}).execute();
		
		mContent = new FrameLayout(this);
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void setUpWebView(int margin) {
        LinearLayout webViewContainer = new LinearLayout(this);
        LinearLayout webViewContainerInner = new LinearLayout(this);
        mWebView = new WebView(this);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebViewClient(new WebViewClient() {
        	public void onPageStarted(WebView view, String url, Bitmap favicon){
        	}
        	public void onPageFinished(WebView view, String url){
        	}
        	public boolean shouldOverrideUrlLoading(WebView view, String url){
        		
        		Log.d("TAG", "url = " + url);
        		
        		if (url.contains(Plurk.CALLBACK_URL)){
        			(new AsyncTask<String, Void, Long>() {

						@Override
						protected Long doInBackground(String... params) {
							// TODO Auto-generated method stub
							if(params.length > 0)
							{
								Plurk.parseTokenAndSecret(params[0]);
								
								DBHelper _dbHelper = new DBHelper(PlurkAuthActivity.this);
								_dbHelper.getConfigFactory().setToken(Plurk.getInstance().getToken());
								_dbHelper.getConfigFactory().setTokenSecret(Plurk.getInstance().getSecret());
								_dbHelper.close();
								
								Intent intent = new Intent();
			        			intent.setClass(PlurkAuthActivity.this, MainActivity.class);
			        			startActivity(intent);
			        			PlurkAuthActivity.this.finish();
							}
							return (long) 0;
						}
        				
        			}).execute(url);
        			return true;
        		}
        		if (url.compareToIgnoreCase("http://www.plurk.com/m/t") == 0){
        			Log.d("TAG", "redirect to login");
        			url = Plurk.user_aithorize_url;
        	    	Log.d("TAG", "redirect to login url = " + url);
        			view.loadUrl(url);
        			
        			return false;
        		}
        		
        		view.loadUrl(url);
        		return false;
        	}
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mUrl);
        mWebView.setVisibility(View.VISIBLE);
        
        webViewContainer.setPadding(margin, margin, margin, margin);
        
        webViewContainer.addView(webViewContainerInner, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        webViewContainerInner.setPadding(10, 10, 10, 5);
        
        webViewContainerInner.addView(mWebView);
        
        mContent.addView(webViewContainer);
    }
	
	public static PlurkAuthActivity getInstance()
	{
		return _instance;
	}
}