package wct.apps.plurk.OAuth;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import oauth.signpost.OAuthProvider;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

public class Plurk {
	//Public final static variable defined.
	public final static String REQUEST_URL = "http://www.plurk.com/OAuth/request_token";
	public final static String ACCESS_URL = "http://www.plurk.com/OAuth/access_token";
	public final static String AUTHORIZE_URL = "http://www.plurk.com/m/authorize";
	public final static String CALLBACK_URL = "app://wctplurk";
	
	public static String user_aithorize_url = "";
	
	public final static String API_BASE = "http://www.plurk.com/APP/";
	
	public final static String CONSUMER_KEY = "ttw1Dl8fSVtr";
	public final static String CONSUMER_SECRET = "0791seqwNurC9EiGScsijxrVK0C6d4uc";
	
	//Private variable defined.
	private static Plurk _instance = null;
	private OAuthConsumer _consumer = null;
	private OAuthProvider _provider = null;
	
	private static String _TokenVerifier = "";
	private static String _Token = "";
	private static String _Secret = "";
	public static String _user_aithorize_url = "";
		
	public Plurk()
	{
		Log.i("Plurk", "Constr");
		_consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		_provider = new CommonsHttpOAuthProvider(REQUEST_URL, ACCESS_URL, AUTHORIZE_URL);
		_provider.setOAuth10a(true);		
		_instance = this;
	}
	
	public static Plurk getInstance()
	{
		if(_instance == null)
			_instance = new Plurk();
		return _instance;
	}
		
	public OAuthConsumer getConsumer()
	{
		return _consumer;
	}
	
	public OAuthProvider getProvider()
	{
		return _provider;
	}
	
	public String getToken(){
		return _Token;
	}

	public String getSecret(){
		return _Secret;
	}
	
	public void setTokenAndSecret(String token, String verifier){
		_Token = token;
		_Secret = verifier;
	}
	
	public void reset(){
		setTokenAndSecret("", "");
	}
	
	public String requestToken(){
        String url = "";
        try {
            url = _provider.retrieveRequestToken(_consumer, CALLBACK_URL);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return url;
	}
	
	public String getAccessToken(String verifier){
		try {
			_provider.retrieveAccessToken(_consumer, verifier);
		} catch (OAuthMessageSignerException e) {
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			e.printStackTrace();
		}
		return _consumer.getTokenSecret();
	}
	
	public static void parseTokenAndSecret(String url){
		try {
			List<NameValuePair> list = URLEncodedUtils.parse(new URI(url), "utf-8");
			for (int i = 0; i < list.size(); ++i){
				NameValuePair nvp = list.get(i);
				if (nvp.getName().compareTo("oauth_token") == 0){
					_Token = nvp.getValue();
					Log.i("parseTokenAndSecret.Token", _Token);
				}else if (nvp.getName().compareTo("oauth_verifier") == 0){
					_TokenVerifier = nvp.getValue();
					Log.i("parseTokenAndSecret.TokenVerifier", _TokenVerifier);
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		String tokenSecret = _instance.getAccessToken(_TokenVerifier);
		Log.i("parseTokenAndSecret.tokenSecret", tokenSecret);
		_Token = _instance._consumer.getToken();
		_Secret = _instance._consumer.getTokenSecret();
		
		Log.i("parseTokenAndSecret._Token", _Token);
		Log.i("parseTokenAndSecret._Secret", _Secret);
	}
	
	public String composeURL(String API, List<NameValuePair> list){
		if (null == list){
			return API;
		}

		String ret = API + ((list.size() > 0)? "?" : "");

		Iterator<NameValuePair> i = list.iterator();
		while(i.hasNext()){
			NameValuePair nvp = i.next();
			ret = String.format("%s%s=%s&", ret, nvp.getName(), nvp.getValue());
		}

		return ret;
	}
	
	public void request(String API, List<NameValuePair> list, OnRequestListener listener){
		new AsyncRequestTask(API, list, listener).execute("");
	}
	
	public String request(String API, List<NameValuePair> list){
		Log.d("PROFILE", "begin request");
		_consumer.setTokenWithSecret(_Token, _Secret);

        // create an HTTP request to a protected resource
        String content = "";
		try {
			HttpClient client = new DefaultHttpClient();
			
			Log.d("Plurk API", API_BASE + composeURL(API, list));
			
	        HttpGet request = new HttpGet(API_BASE + composeURL(API, list));
  
	        _consumer.sign(request);
	        // send the request
	        content = client.execute(request, new BasicResponseHandler());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OAuthMessageSignerException e) {
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			e.printStackTrace();
		} 
		Log.d("PROFILE", "end request");
		return content;
	}
	
	public void authorize(final Activity activity, final OnRequestListener listener)
	{
		isTokenValid(new OnRequestListener() {
			@Override
			public void onComplete(String response) {
				// TODO Auto-generated method stub
				listener.onComplete(response);
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub
				listener.onError();
			}
		});
	}
	
	public void isTokenValid(final OnRequestListener listener){
		request("Profile/getOwnProfile", null, new OnRequestListener(){
			@Override
			public void onComplete(String response) {
				if (response == null || response.length() <= 0){
					reset();
					listener.onError();
				}else{
					listener.onComplete(response);
				}
			}

			@Override
			public void onError() {}
		});
	}
	
	public interface OnRequestListener{
		abstract void onComplete(String response);
		abstract void onError();
	};
	
	public interface OnAuthorizeListener{
		public abstract void onAuthorized();
		public abstract void onError(String error);
		public abstract void onComplete(Bundle values);
	};
	
	public class AsyncRequestTask extends AsyncTask<String, Integer, String> {
		String API = "";
		List<NameValuePair> list = null;
		OnRequestListener listener = null;

		public AsyncRequestTask(String API, List<NameValuePair> list, OnRequestListener listener){
			this.API = API;
			this.list = list;
			this.listener = listener;
		}

		protected String doInBackground(String... urls) {
			String obj = request(API, list);
			return obj;
		}

		protected void onPostExecute(String result) {
			if (listener != null){
				listener.onComplete(result);
			}
		}
	}
	
}
