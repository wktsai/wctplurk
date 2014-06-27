package wct.apps.plurk;

import org.json.JSONException;
import org.json.JSONObject;

import wct.apps.plurk.OAuth.Plurk;
import wct.apps.plurk.OAuth.Plurk.OnRequestListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IntroduceSelfActivity extends Fragment {
	private View v;
	private Plurk _plurk = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		_plurk = Plurk.getInstance();
		
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
							try {
								JSONObject jobj = new JSONObject(response);
								JSONObject user_info = new JSONObject(jobj.getString("user_info"));
								
								TextView txtAccount =  (TextView)IntroduceSelfActivity.this.getView().findViewById(R.id.txtView_showAccount);
								txtAccount.setText(user_info.getString("nick_name"));
								
								TextView txtfull_name =  (TextView)IntroduceSelfActivity.this.getView().findViewById(R.id.txtView_showfull_name);
								txtfull_name.setText(user_info.getString("full_name"));
								
								TextView txtNickname =  (TextView)IntroduceSelfActivity.this.getView().findViewById(R.id.txtView_showNickname);
								txtNickname.setText(user_info.getString("display_name"));
								
								TextView txtKarma =  (TextView)IntroduceSelfActivity.this.getView().findViewById(R.id.txtView_showKarma);
								txtKarma.setText((new Double(user_info.getDouble("karma"))).toString());
								
								TextView txtFriendCount =  (TextView)IntroduceSelfActivity.this.getView().findViewById(R.id.txtView_showFriendCount);
								txtFriendCount.setText((new Integer(user_info.getInt("friends_count"))).toString());
								
								TextView txtFanCount =  (TextView)IntroduceSelfActivity.this.getView().findViewById(R.id.txtView_showFanCount);
								txtFanCount.setText((new Integer(user_info.getInt("fans_count"))).toString());
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.activity_introduceself, container, false);
		//TextView text = (TextView)v.findViewById(R.id.text_view);
		//text.setText("Page" + getShownIndex());
		return v;
    }

}
