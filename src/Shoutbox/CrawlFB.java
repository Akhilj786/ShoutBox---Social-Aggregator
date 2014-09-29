package Shoutbox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Shoutbox.GlobalParamater;

public class CrawlFB {
	static UserInfo info;

	static GlobalParamater gp = new GlobalParamater();

	public void fbSetup(final ConcurrentDB<Date, String> conDB)
			throws InterruptedException {
		info = new UserInfo(gp.userId, gp.userToken, gp.userDate);
		Thread t1 = new Thread() {
			public void run() {
				try {
					getUpdate(conDB, 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		Thread t2 = new Thread() {
			public void run() {
				try {
					getUpdate(conDB, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		Thread t3 = new Thread() {
			public void run() {
				try {
					getUpdate(conDB, 2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		t1.start();
		t2.start();
		t3.start();
		t1.join();
		t2.join();
		t3.join();

		while (true)
			liveFB(conDB);

	}

	public void liveFB(final ConcurrentDB<Date, String> conDB)
			throws InterruptedException {

		Thread t1 = new Thread() {
			public void run() {
				try {
					// do {
					getUpdate(conDB, 0);
					// Thread.sleep(1000);
					// } while (true);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		Thread t2 = new Thread() {
			public void run() {
				try {
					getUpdate(conDB, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		Thread t3 = new Thread() {
			public void run() {
				try {
					getUpdate(conDB, 2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		t1.start();
		t2.start();
		t3.start();
		t1.join();
		t2.join();
		t3.join();

	}

	private static void getUpdate(ConcurrentDB<Date, String> conDB, int index)
			throws Exception {
		String id = info.getID(index);
		String token = info.getToken(index);
		Date startingDate = info.getLiveDate(index);
		long timeStamp = startingDate.getTime();
		String url = "https://graph.facebook.com/" + id
				+ "/statuses/?fields=message&access_token=" + token + "&since="
				+ (timeStamp / 1000);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				DataCommunicator.sendGetDataToServer(url)));

		String inputLine = "";

		StringBuffer response = new StringBuffer();
		Date liveDate = null;
		boolean flag = false;
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);

		}
		in.close();
		JSONObject json = (JSONObject) new JSONParser().parse(response
				.toString());

		JSONArray msg = (JSONArray) json.get("data");

		@SuppressWarnings("rawtypes")
		Iterator i = msg.iterator();

		while (i.hasNext()) {
			JSONObject slide = (JSONObject) i.next();
			String mesg = info.getID(index) + (String) slide.get("message");

			String sdate = (String) slide.get("updated_time");

			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ssZ");
			Date date = dateFormat.parse(sdate);
			conDB.put(date, mesg);
			if (!flag) {
				liveDate = date;

			}
			flag = true;

		}
		if (liveDate != null)
			info.setLiveDate(index, liveDate);

	}

}