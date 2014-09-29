package Shoutbox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalParamater {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"EEE MMM dd HH:mm:ss Z yyyy");
	String userId[] = { "ABC" }; // Enter your Facebook ID
	String userToken[] = {"ABC" }; //Enter your token.

	String originalStartDate = "Fri Jan 01 02:23:34 EST 2010";

	Date userDate[] = { fromString(originalStartDate),
			fromString(originalStartDate), fromString(originalStartDate) };
	final String LARGE_TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";
	String twitterHandle[] = { "ABC"};// Enter twitter id.

	private static final Date fromString(String str) {
		try {

			return dateFormat.parse(str);
		} catch (ParseException dfe) {
			// return invalidDate;
			return null;
		}

	}

}
