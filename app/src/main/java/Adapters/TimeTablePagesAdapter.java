package Adapters;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import layout.Test1;

/**
 * Created by Manu Srivastava on 06-04-2016.
 */

// TODO change string array to Timetable object


public class TimeTablePagesAdapter extends FragmentPagerAdapter {

	public static final String MyPREFERENCES = "MyPrefs";
	SharedPreferences _sf;
	private String[] TITLES = null;
	private String[] timetable = null;
	private ArrayList<String> mTitles;

	public TimeTablePagesAdapter(FragmentManager fm, int numberOfTabs, SharedPreferences sf) {
		super(fm);
		this._sf =sf;
		getData(this._sf);
		mTitles = new ArrayList<>();
		for (int i = 0; i < numberOfTabs; i++) {
			mTitles.add(TITLES[i]);
		}
	}

	void getData(SharedPreferences sf) {
		String DID = sf.getString("did", null);//null if no did value found.
		TITLES = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		timetable = getTimetable(DID,sf);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles.get(position);
	}

	@Override
	public int getCount() {
		return mTitles.size();
	}

	@Override
	public Fragment getItem(int position) {
		getData(this._sf);
		return Test1.newInstance(TITLES[position], timetable, position);
	}

	public String[] getTimetable(String DID, SharedPreferences sf) {
		JSONObject jsonData = null;

		String[] dataR = new String[40];
		String did = DID;
		if (timetable == null) {
			String out=sf.getString("timetable",null);
			out.substring(0, out.length() - 2);
			if (!(out.compareTo("WRONG URL") == 0)) {
				try {
					jsonData = new JSONObject(out);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				JSONArray day = null;
				try {
					for (int i = 0; i < 5; i++) {

						day = (JSONArray) jsonData.getJSONArray(TITLES[i].toLowerCase());
						JSONObject lecture;
						try {
							for (int j = 0; j < 8; j++) {
								lecture = day.getJSONObject(j);
								dataR[i * 8 + j] = lecture.getString("subject");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				} catch (JSONException e) {

					e.printStackTrace();
				}
			}

		} else
			dataR = timetable;
		if (dataR != null)
			return dataR;
		else
			return null;
	}
}
