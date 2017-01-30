package utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Manu on 10/11/15.
 */
public class ProjNet {
	String out = "";

	public String getOutput(NetObject data) {
		try {
			out = new BackgroundTask().execute(data).get();
		} catch (Exception e) {
		}
		return out;
	}

	public String connect(String urlin, HashMap<String, String> params) {
		String myurl = urlin;
		String param = "";
		StringBuilder output = new StringBuilder();
		int flag = 0, i = 0;
		ArrayList<String> keys = new ArrayList();
		for (String k : params.keySet()) {
			if (k != null) {
				keys.add(k);
			}
		}
		int size = 0;
		size = keys.size();
		for (; i < size; i++)
			try {
				param += ("&" + keys.get(i) + "=" + URLEncoder.encode(params.get(keys.get(i)), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		myurl += param;
		URL url = null;
		try {
			url = new URL(myurl);
			Log.i("URL", url.toString());
		} catch (MalformedURLException e) {
			flag = 1;
		}
		if (flag != 1) {
			try {
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				try {
					conn.connect();
				} catch (Exception e) {
					output.append("Wrong URL");
					flag = 1;
				}
				if (flag != 1) {
					Scanner sc = new Scanner(conn.getInputStream());
					do {
						output.append(sc.next()).append(" ");
					} while (sc.hasNext());
				}
				conn.disconnect();
			} catch (IOException e) {
				output .append(" - IOE");
			}
		}
		return output.toString();
	}

	class BackgroundTask extends AsyncTask<NetObject, Void, String> {
		String _out;

		BackgroundTask() {
			_out = "";

		}

		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(NetObject... params) {
			NetObject mydata = new NetObject(params[0]);
			_out = new ProjNet().connect(mydata.geturl(), mydata.getparams());
			return _out;
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
		}
	}
}