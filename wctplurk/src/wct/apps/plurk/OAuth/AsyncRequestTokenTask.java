package wct.apps.plurk.OAuth;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import wct.apps.plurk.OAuth.IAsyncResponse;
import android.os.AsyncTask;

public class AsyncRequestTokenTask extends AsyncTask<Void, Void, String>
{
	private IAsyncResponse _AsyncResponseDelegate=null;
	private OAuthConsumer _Consumer=null;
	private OAuthProvider _Provider=null;
	private String _callback_url = Plurk.CALLBACK_URL;

	public AsyncRequestTokenTask(OAuthConsumer consumer, OAuthProvider provider, IAsyncResponse AsyncResponseDelegate)
	{
		this._AsyncResponseDelegate = AsyncResponseDelegate;
		this._Consumer = consumer;
		this._Provider = provider;
	}
	
	public IAsyncResponse getAsyncResponseDelegate()
	{
		return _AsyncResponseDelegate;
	}
	
	public void setAsyncRespohnseDelegate(IAsyncResponse ResponseDelegate)
	{
		_AsyncResponseDelegate = ResponseDelegate;
	}
	
	public OAuthConsumer getConsumer()
	{
		return _Consumer;
	}
	
	public void setConsumer(OAuthConsumer consumer)
	{
		_Consumer = consumer;
	}
	
	public OAuthProvider getProvider()
	{
		return _Provider;
	}
	
	public void setProvider(OAuthProvider provider)
	{
		_Provider = provider;
	}
	
	public String getCallbackUrl()
	{
		return _callback_url;
	}
	
	public void setCallbackUrl(String url)
	{
		_callback_url = url;
	}
	
	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		String url = null;
		try {
			
			url = _Provider.retrieveRequestToken(_Consumer, _callback_url);
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	
	@Override
	protected void onPostExecute(String result) {
		if(_AsyncResponseDelegate != null)
			this._AsyncResponseDelegate.processFinish(result);
	}
}