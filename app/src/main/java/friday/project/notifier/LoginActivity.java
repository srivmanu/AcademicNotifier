package friday.project.notifier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import utilities.ConnectionDetector;
import utilities.NetObject;
import utilities.ProjNet;

public class LoginActivity extends FragmentActivity {

	public static final String MyPREFERENCES = "MyPrefs";
	public static final String ROLLNO = "rollno";
	public static final String Name = "name";
	public static final String DID = "did";
	private static final String LOG_TAG = "FRIDAY";
	Button loginButton;
	EditText RollNo;
	String[] data;
	RelativeLayout r1;
	RelativeLayout r2;
	TextView name;
	TextView rollNumber;
	TextView branch;
	TextView semester;
	TextView section;
	Button Proceed;
	String[] dataR = {"", "", "", "", ""};
	SharedPreferences.Editor editor;
	Boolean isInternetPresent = false;

	// Connection detector class
	ConnectionDetector cd;

	SharedPreferences sharedpreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		r1 = (RelativeLayout) findViewById(R.id.rel1);
		r2 = (RelativeLayout) findViewById(R.id.rel2);
		loginButton = (Button) findViewById(R.id.loginButton);
		RollNo = (EditText) findViewById(R.id.rollNo);
		name = (TextView) findViewById(R.id.data_name);
		rollNumber = (TextView) findViewById(R.id.data_rollno);
		branch = (TextView) findViewById(R.id.data_branch);
		semester = (TextView) findViewById(R.id.data_sem);
		section = (TextView) findViewById(R.id.data_section);
		Proceed = (Button) findViewById(R.id.proceed);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		String url;
		HashMap<String, String> param = new HashMap<String, String>();
		final NetObject netobject = new NetObject();


		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		editor = sharedpreferences.edit();
		String a = sharedpreferences.getString(ROLLNO, null);
		String b = sharedpreferences.getString(DID, null);

		if (a != null) {

			param.put("did", b);
			url = "http://fridaygaba.net78.net/checktimetable.php?data=1"; //IP ACCORDONG TO NEED
			netobject.putdata(url, param);
			String timetable = new ProjNet().getOutput(netobject);
			editor.putString("timetable", timetable).apply();

			url = "http://fridaygaba.net78.net/checknotification.php?data=1";
			netobject.putdata(url, param);
			String notification = new ProjNet().getOutput(netobject);
			editor.putString("notification", notification).apply();

			url = "http://fridaygaba.net78.net/getcalendar.php?data=1";
			netobject.putdata(url, param);
			String calendar = new ProjNet().getOutput(netobject);
			editor.putString("calendar", calendar).apply();

			url = "http://fridaygaba.net78.net/checktest.php?data=1";
			netobject.putdata(url, param);
			String test = new ProjNet().getOutput(netobject);
			editor.putString("test", test).apply();

			url = "http://fridaygaba.net78.net/checkassign.php?data=1";
			netobject.putdata(url, param);
			String assign = new ProjNet().getOutput(netobject);
			editor.putString("assign", assign).apply();

			Log.i("DATA", a);
			Intent intent = new Intent(getBaseContext(), Notifier.class);
			startActivity(intent);
			finish();
		} else {
			cd = new ConnectionDetector(getApplicationContext());
			isInternetPresent = cd.isConnectingToInternet();
			if (!isInternetPresent) {
//                Toast.makeText(getApplicationContext(),,Toast.LENGTH_LONG).show();
				Snackbar.make(getCurrentFocus(), "No Internet Connection", Snackbar.LENGTH_LONG).show();
			} else
//                Toast.makeText(getApplicationContext(), "Internet Connected",Toast.LENGTH_LONG).show();


				Proceed.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {


						Intent intent = new Intent(getBaseContext(), Notifier.class);
						intent.putExtra("data", dataR);
						startActivity(intent);
						finish();

					}
				});


			loginButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (isInternetPresent) {
						data = validate(RollNo.getText().toString());
						if (data != null) {
							r1.setVisibility(View.GONE);
							r2.setVisibility(View.VISIBLE);
							rollNumber.setText(data[0]);
							name.setText(data[1]);
							branch.setText(data[2]);
							semester.setText(data[3]);
							section.setText(data[4]);
						} else
							Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
					}
				}
			});
		}
	}

	private String[] validate(String s) {
		JSONArray jsonData = null;
		String did = null;
		final NetObject netobject = new NetObject();
		String url = "http://fridaygaba.net78.net/checkdata.php?data=1";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("roll", s);
		netobject.putdata(url, param);
		String out = new ProjNet().getOutput(netobject);
		try {
			jsonData = new JSONArray(out);

		} catch (JSONException e) {

			e.printStackTrace();
		}
		JSONObject ob = null;
		try {
			ob = (JSONObject) jsonData.get(0);
			Log.i(LOG_TAG, ob.toString());
			try {
				dataR[1] = ob.getString("name");
				dataR[0] = ob.getString("uni_roll");
				did = ob.getString("d_id");
				Log.i("CHECCK DATA", dataR[1] + " " + dataR[0] + " " + did);
			} catch (JSONException e) {
				e.printStackTrace();
			}


		} catch (JSONException e) {

			e.printStackTrace();
		}

		String[] tokens = did.split("_");
		dataR[2] = tokens[1];
		dataR[3] = tokens[2];
		dataR[4] = tokens[3];

		editor.putString(ROLLNO, dataR[0]);
		editor.putString(Name, dataR[1]);
		editor.putString(DID, did);
		editor.commit();


		if (dataR != null)
			return dataR;
		else
			return null;
	}


}
