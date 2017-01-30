package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Adapters.AdapterMiniTest;
import Adapters.NotificationMiniAdapter;
import SupportClasses.NotificationObject;
import SupportClasses.TestObject;
import friday.project.notifier.Notifier;
import friday.project.notifier.R;

public class NotifHome extends Fragment {

	public static final String MyPREFERENCES = "MyPrefs";
	SharedPreferences _sf;

	public NotifHome() {
		// Required empty public constructor
	}

	public static NotifHome newInstance() {
		NotifHome fragment = new NotifHome();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		((Notifier) getActivity()).setActionBarTitle("Home");

		_sf = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		TextView day_home = (TextView) view.findViewById(R.id.notif_home_day);
		AwesomeTextView tt_1 = (AwesomeTextView) view.findViewById(R.id.home_tt_one);
		AwesomeTextView tt_2 = (AwesomeTextView) view.findViewById(R.id.home_tt_two);
		AwesomeTextView tt_3 = (AwesomeTextView) view.findViewById(R.id.home_tt_three);
		AwesomeTextView tt_4 = (AwesomeTextView) view.findViewById(R.id.home_tt_four);
		AwesomeTextView tt_5 = (AwesomeTextView) view.findViewById(R.id.home_tt_five);
		AwesomeTextView tt_6 = (AwesomeTextView) view.findViewById(R.id.home_tt_six);
		AwesomeTextView tt_7 = (AwesomeTextView) view.findViewById(R.id.home_tt_seven);
		AwesomeTextView tt_8 = (AwesomeTextView) view.findViewById(R.id.home_tt_eight);
		Calendar calendar = Calendar.getInstance();
		int day = -1;
		String daystr = "DAY";
		int calday = calendar.get(Calendar.DAY_OF_WEEK);

		switch (calday) {
			case Calendar.SUNDAY:
				day = -1;
				daystr = "SUNDAY";
				break;
			case Calendar.MONDAY:
				day = 0;
				daystr = "MONDAY";
				break;
			case Calendar.TUESDAY:
				daystr = "TUESDAY";
				day = 1;
				break;
			case Calendar.WEDNESDAY:

				daystr = "WEDNESDAY";
				day = 2;
				break;
			case Calendar.THURSDAY:
				daystr = "THURSDAY";
				day = 3;
				break;
			case Calendar.FRIDAY:

				daystr = "FRIDAY";
				day = 4;
				break;
			case Calendar.SATURDAY:

				daystr = "SATURDAY";
				day = -1;
				break;
		}
		if (day >= 0) {
			JSONObject jsonData = null;
			String[] dataR = new String[40];
			String[] TITLES = null;

			TITLES = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

			String out = _sf.getString("timetable", null);
			out.substring(0, out.length() - 2);
			if (!(out.compareTo("WRONG URL") == 0)) {
				try {
					jsonData = new JSONObject(out);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				JSONArray jsonday = null;
				try {
					for (int i = 0; i < 5; i++) {

						jsonday = (JSONArray) jsonData.getJSONArray(TITLES[i].toLowerCase());
						JSONObject lecture;
						try {
							for (int j = 0; j < 8; j++) {
								lecture = jsonday.getJSONObject(j);
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
			day_home.setText(daystr);
			tt_1.setText(dataR[day * 8]);
			tt_2.setText(dataR[day * 8 + 1]);
			tt_3.setText(dataR[day * 8 + 2]);
			tt_4.setText(dataR[day * 8 + 3]);
			tt_5.setText(dataR[day * 8 + 4]);
			tt_6.setText(dataR[day * 8 + 5]);
			tt_7.setText(dataR[day * 8 + 6]);
			tt_8.setText(dataR[day * 8 + 7]);
		}
		ListView lv_notif = (ListView) view.findViewById(R.id.list_notif_home);
		final ArrayList<NotificationObject> notificationObjects = new ArrayList<NotificationObject>();
		setNotifValues(notificationObjects);
		NotificationMiniAdapter adapt = new NotificationMiniAdapter(getContext(), notificationObjects);
		if (notificationObjects.isEmpty()) {
			view.findViewById(R.id.home_no_notif).setVisibility(View.VISIBLE);
			view.findViewById(R.id.home_no_notif_text).setVisibility(View.VISIBLE);
			lv_notif.setVisibility(View.GONE);
		} else
			lv_notif.setAdapter(adapt);
		ListView lv_event = (ListView) view.findViewById(R.id.list_event_home);

		final ArrayList<TestObject> tests = new ArrayList<TestObject>();
		setEventValues(tests);
		AdapterMiniTest adapt_test = new AdapterMiniTest(getContext(), tests);
		if (tests.isEmpty()) {
			view.findViewById(R.id.home_no_event).setVisibility(View.VISIBLE);
			view.findViewById(R.id.home_no_event_text).setVisibility(View.VISIBLE);
			lv_event.setVisibility(View.GONE);
		} else {
			lv_event.setAdapter(adapt_test);
//			lv_event.setVisibility(View.VISIBLE);
		}
		return view;

	}

	void setNotifValues(ArrayList<NotificationObject> notifications) {
		Calendar caldata = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-d");
		String date = df.format(caldata.getTime());
		NotificationObject ob;
		_sf = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		String assign = _sf.getString("notification", null);

		JSONArray jsonArray;
		JSONObject jsonObject = null;

		if (assign != null) {
			try {
				jsonArray = new JSONArray(assign);
				if (jsonArray.length() > 0) {
					try {
						for (int i = 0; i < jsonArray.length(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							Log.e("CHECK", jsonObject.getString("head")+"::"+jsonObject.getString("timestamp"));

							if (jsonObject.getString("timestamp").compareTo(date) == 0) {

								ob = new NotificationObject();
								ob.setDate(jsonObject.getString("timestamp"));
								ob.setTitle(jsonObject.getString("head"));
								ob.setText(jsonObject.getString("text"));
								notifications.add(ob);
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}


	void setEventValues(ArrayList<TestObject> tests) {
		Calendar caldata = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-d");
		String date = df.format(caldata.getTime());
		TestObject ob;
		_sf = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//		Log.i("DID ASSIGN",did + "NULL");
		String assign = _sf.getString("test", null);

		JSONArray jsonArray;
		JSONObject jsonObject = null;

		if (assign != null) {
			try {
				jsonArray = new JSONArray(assign);
				if (jsonArray.length() > 0) {
					try {
						for (int i = 0; i < jsonArray.length(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							ob = new TestObject();
							Log.e("CHECK", jsonObject.getString("head")+"::"+jsonObject.getString("timestamp"));
							if (jsonObject.getString("timestamp").compareTo(date) == 0) {
								ob.setDate(jsonObject.getString("timestamp"));
								ob.setSubname(jsonObject.getString("head"));
								ob.setText(jsonObject.getString("text"));
								tests.add(ob);
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		assign = _sf.getString("assign", null);
		if (assign != null) {
			try {
				jsonArray = new JSONArray(assign);
				if (jsonArray.length() > 0) {
					try {
						for (int i = 0; i < jsonArray.length(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							ob = new TestObject();
							if (jsonObject.getString("timestamp").compareTo(date) == 0) {
								ob.setDate(jsonObject.getString("timestamp"));
								Log.i("REACHED","HERE");
								ob.setSubname(jsonObject.getString("head"));
								ob.setText(jsonObject.getString("text"));
								tests.add(ob);
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}
}
