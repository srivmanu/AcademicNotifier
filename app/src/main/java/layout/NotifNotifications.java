package layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.NotificationAdapter;
import SupportClasses.NotificationObject;
import friday.project.notifier.DetailActivity;
import friday.project.notifier.Notifier;
import friday.project.notifier.R;

public class NotifNotifications extends Fragment {
	public static final String MyPREFERENCES = "MyPrefs";
	SharedPreferences sharedpreferences;

	public NotifNotifications() {
		// Required empty public constructor
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

		View rv = inflater.inflate(R.layout.fragment_notifications, container, false);
		((Notifier) getActivity()).setActionBarTitle("Notifications");
		RelativeLayout rl = (RelativeLayout) rv.findViewById(R.id.no_notif_notif);
		ListView lv = (ListView) rv.findViewById(R.id.notif_list);
		rl.setVisibility(View.GONE);
		lv.setVisibility(View.VISIBLE);
		final ArrayList<NotificationObject> notificationObjects = new ArrayList<NotificationObject>();
		setValues(notificationObjects);
		NotificationAdapter adapt = new NotificationAdapter(getContext(), notificationObjects);
		if (notificationObjects.isEmpty()) {
			rl.setVisibility(View.VISIBLE);
			lv.setVisibility(View.GONE);
		}
		lv.setAdapter(adapt);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				NotificationObject data = notificationObjects.get(position);
				Intent i=new Intent(getContext(), DetailActivity.class);
				i.putExtra("date",data.getDate()).putExtra("head",data.getTitle()).putExtra("text",data.getText()).putExtra("type","Notification");
				startActivity(i);
			}
		});
		return rv;

	}


	void setValues(ArrayList<NotificationObject> notifications) {
		NotificationObject ob;
		sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//		Log.i("DID ASSIGN",did + "NULL");
		String assign = sharedpreferences.getString("notification", null);

		JSONArray jsonArray;
		JSONObject jsonObject = null;

		if (assign != null) {
			try {
				jsonArray = new JSONArray(assign);
				if (jsonArray.length() > 0) {
					try {
						for (int i = 0; i < jsonArray.length(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							ob = new NotificationObject();
							ob.setDate(jsonObject.getString("timestamp"));
							ob.setTitle(jsonObject.getString("head"));
							ob.setText(jsonObject.getString("text"));
							notifications.add(ob);
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
