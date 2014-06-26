package wct.apps.plurk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IntroduceSelfActivity extends Fragment {
	private View v;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
