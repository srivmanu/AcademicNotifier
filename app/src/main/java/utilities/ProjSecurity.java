package utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Manu on 13/11/15.
 */
public class ProjSecurity {
	String message;

	public ProjSecurity() {
		message = "Fr1day-6aba-0m1-Para5";
	}

	public String MD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(input.getBytes());
			StringBuffer output = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				output.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return output.toString();
		} catch (NoSuchAlgorithmException e) {
			return "ERROR";
		}
	}

	public String SHA1(String input) {
		try {

			MessageDigest mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(input.getBytes());
			StringBuffer output = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				output.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return output.toString();
		} catch (NoSuchAlgorithmException e) {
			return "ERROR";
		}
	}

	public String getSecurityToken() {
		String code = getSecurityToken(new SimpleDateFormat("HHddMMmmyy").format(new Date()));
		return code;
	}

	public String getSecurityToken(String timeStamp) {
		message = timeStamp + message;
		// Log.i(TAG + ".encrypt.message", message);
		String code = timeStamp + SHA1(MD5(message));
		return code;
	}
}