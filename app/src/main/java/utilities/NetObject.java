package utilities;

import java.util.HashMap;

/**
 * Created by Manu on 11/11/15.
 */
public class NetObject extends Object{
	String myurl;
	HashMap<String, String> myparams;

	NetObject(String url, HashMap<String, String> params) {
		myurl = url;
		myparams = params;
	}

	NetObject(NetObject data) {
		myparams = data.getparams();
		myurl = data.geturl();
	}

	public NetObject() {
		myurl = "";
		myparams = null;
	}

	public NetObject getdata() {
			return this;
	}

	public void putdata(String url, HashMap<String, String> params) {
		myurl = url;
		myparams = params;
	}

	public String value() {
		return (myurl + " : " + myparams.toString());
	}

	public HashMap<String, String> getparams() {
		return myparams;
	}

	public String geturl() {
		return myurl;
	}
}
