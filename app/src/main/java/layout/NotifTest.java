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

import Adapters.TestAdapter;
import SupportClasses.TestObject;
import friday.project.notifier.DetailActivity;
import friday.project.notifier.Notifier;
import friday.project.notifier.R;

public class NotifTest extends Fragment {
	public static final String MyPREFERENCES = "MyPrefs";
	SharedPreferences sharedpreferences;

	public NotifTest() {
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


		View rv = inflater.inflate(R.layout.fragment_test, container, false);
		((Notifier) getActivity()).setActionBarTitle("Tests");
		RelativeLayout rl = (RelativeLayout) rv.findViewById(R.id.no_notif_test);
		ListView lv = (ListView) rv.findViewById(R.id.test_list);
		rl.setVisibility(View.GONE);
		lv.setVisibility(View.VISIBLE);
		final ArrayList<TestObject> tests = new ArrayList<TestObject>();
		setValues(tests);
		TestAdapter adapt = new TestAdapter(getContext(), tests);
		if (tests.isEmpty()) {
			rl.setVisibility(View.VISIBLE);
			lv.setVisibility(View.GONE);
		}
		lv.setAdapter(adapt);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TestObject data = tests.get(position);
				Intent i=new Intent(getContext(), DetailActivity.class);
				i.putExtra("date",data.getDate()).putExtra("head",data.getSubname()).putExtra("text",data.getText()).putExtra("type","Test");
				startActivity(i);
			}
		});
		return rv;


	}

	void setValues(ArrayList<TestObject> tests) {
		TestObject ob;
		sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//		Log.i("DID ASSIGN",did + "NULL");
		String assign = sharedpreferences.getString("test", null);

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
							ob.setDate(jsonObject.getString("timestamp"));
							ob.setSubname(jsonObject.getString("head"));
							ob.setText(jsonObject.getString("text"));
							tests.add(ob);
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
