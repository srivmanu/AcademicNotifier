package friday.project.notifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.HashMap;

import SupportClasses.Config;
import gcm.GcmIntentService;
import layout.NotifAssignments;
import layout.NotifCalendar;
import layout.NotifHome;
import layout.NotifNotifications;
import layout.NotifSettings;
import layout.NotifTest;
import layout.NotifTimeTable;
import utilities.NetObject;

public class Notifier extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	public static final String MyPREFERENCES = "MyPrefs";
	SharedPreferences sharedpreferences;
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private String TAG = Notifier.class.getSimpleName();
	private BroadcastReceiver mRegistrationBroadcastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TypefaceProvider.registerDefaultIconSets();
		setContentView(R.layout.activity_notifier);
		NavigationView headView = (NavigationView) findViewById(R.id.nav_view);
		View header = headView.getHeaderView(0);

		final NetObject netobject = new NetObject();
		HashMap<String, String> param = new HashMap<String, String>();


		TextView session = (TextView) header.findViewById(R.id.nav_session);
		TextView branch = (TextView) header.findViewById(R.id.nav_branch);
		TextView sem = (TextView) header.findViewById(R.id.nav_sem);
		TextView sec = (TextView) header.findViewById(R.id.nav_sec);
		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		String did = sharedpreferences.getString("did", null);//null if no did value found.
		param.put("did", did);


		if (did != null) {
			String[] x = did.split("_");
			String text = x[0] + "-" + (Integer.parseInt(x[0]) + 4);
			session.setText(text);
			branch.setText(x[1].toUpperCase());
			sem.setText(x[2]);
			sec.setText(x[3].toUpperCase());
		}


		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);


		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		Fragment fragment = new NotifHome();


		// update the main content by replacing fragments
		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.fragmy, fragment)
				.commit();


		mRegistrationBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				// checking for type intent filter
				if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
					// gcm successfully registered
					// now subscribe to `global` topic to receive app wide notifications
					String token = intent.getStringExtra("token");

					Toast.makeText(getApplicationContext(), "GCM registration token: " + token, Toast.LENGTH_LONG).show();

				} else if (intent.getAction().equals(Config.SENT_TOKEN_TO_SERVER)) {
					// gcm registration id is stored in our server's MySQL

					Toast.makeText(getApplicationContext(), "GCM registration token is stored in server!", Toast.LENGTH_LONG).show();

				} else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
					// new push notification is received

					Toast.makeText(getApplicationContext(), "Push notification is received!", Toast.LENGTH_LONG).show();
				}
			}
		};

		if (checkPlayServices()) {
			registerGCM();
		}

	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}


	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.

		Fragment fragment = null;

		// update the main content by replacing fragments
		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		int id = item.getItemId();
		if (id == R.id.nav_home) {
			fragment = new NotifHome();
		} else if (id == R.id.nav_timetable) {
			fragment = new NotifTimeTable();
		} else if (id == R.id.nav_calendar) {
			fragment = new NotifCalendar();
		} else if (id == R.id.nav_assign) {
			fragment = new NotifAssignments();
		} else if (id == R.id.nav_tests) {
			fragment = new NotifTest();
		} else if (id == R.id.nav_notif) {
			fragment = new NotifNotifications();
		} else if (id == R.id.nav_setting) {
			fragment = new NotifSettings();
		}

		fragmentManager.beginTransaction()
				.replace(R.id.fragmy, fragment)
				.commit();

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	// starting the service to register with GCM
	private void registerGCM() {
		Intent intent = new Intent(this, GcmIntentService.class);
		intent.putExtra("key", "register");
		startService(intent);
	}

	private boolean checkPlayServices() {
		GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
		int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (apiAvailability.isUserResolvableError(resultCode)) {
				apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
						.show();
			} else {
//				Log.i(TAG, "This device is not supported. Google Play Services not installed!");
				Toast.makeText(getApplicationContext(), "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();
				finish();
			}
			return false;
		}
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		// register GCM registration complete receiver
		LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
				new IntentFilter(Config.REGISTRATION_COMPLETE));

		// register new push message receiver
		// by doing this, the activity will be notified each time a new message arrives
		LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
				new IntentFilter(Config.PUSH_NOTIFICATION));
	}

	@Override
	protected void onPause() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
		super.onPause();
	}

	public void setActionBarTitle(String title) {
		getSupportActionBar().setTitle(title);
	}
}
