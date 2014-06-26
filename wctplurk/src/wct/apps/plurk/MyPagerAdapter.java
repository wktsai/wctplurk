package wct.apps.plurk;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MyPagerAdapter extends PagerAdapter {
	public List<View> mListViews;
	
	public MyPagerAdapter(List<View> mListViews) {
		this.mListViews = mListViews;
	}
	
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(mListViews.get(arg1));
	}
	
	@Override
	public void finishUpdate(View arg0) {
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == (arg1);
	}
	
	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		
	}
	
	@Override
	public Parcelable saveState() {
		return null;
		//super.instantiateItem(container, position)
		//super.instantiateItem(ViewGroup, int)
	}
	
	@Override
	public void startUpdate(View arg0) {
		
	}
	
	//@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		((ViewPager) container).addView(mListViews.get(position), 0);
		return mListViews.get(position);
	}

}
