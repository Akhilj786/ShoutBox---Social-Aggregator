package Shoutbox;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;

public class crawlTweet {
	static List<Status> statuses0;
	static List<Status> statuses1;
	static List<Status> statuses2;

	static Map<Date, String> map = new TreeMap<Date, String>();
	static Map<Date, String> synmap = Collections.synchronizedMap(map);

	// static final String LARGE_TWITTER_DATE_FORMAT =
	// "EEE MMM dd HH:mm:ss Z yyyy";

	public void twitterSetUp(final ConcurrentDB<Date, String> conDB)
			throws IllegalStateException, TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		GlobalParamater gp = new GlobalParamater();
		statuses0 = twitter.getUserTimeline(gp.twitterHandle[0]);
		statuses1 = twitter.getUserTimeline(gp.twitterHandle[1]);
		statuses2 = twitter.getUserTimeline(gp.twitterHandle[2]);
		final SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ssZ");
		new Thread() {
			public void run() {
				for (Status status : statuses0) {
					String userName = (String) status.getUser().getName();
					String msg = userName + ":" + (String) status.getText();
					Date date = status.getCreatedAt();

					// crawlTweet.synmap.put(date,msg);
					conDB.put(date, msg);

				}
			}
		}.start();
		new Thread() {
			public void run() {
				for (Status status : statuses1) {
					String userName = (String) status.getUser().getName();
					String msg = userName + ":" + (String) status.getText();
					Date date = status.getCreatedAt();
					// crawlTweet.synmap.put(date,msg);
					conDB.put(date, msg);

				}
			}
		}.start();
		
		new Thread() {
			public void run() {
				for (Status status : statuses2) {
					String userName = (String) status.getUser().getName();
					String msg = userName + ":" + (String) status.getText();
					Date date = status.getCreatedAt();
					// crawlTweet.synmap.put(date,msg);
					conDB.put(date, msg);

				}
			}
		}.start();

		

	}

	public void liveTweet(final ConcurrentDB<Date, String> conDB) {

		final UserStreamListener listener = new UserStreamListener() {
			@Override
			public void onStatus(Status status) {
				String userName = (String) status.getUser().getName();
				String msg = userName + ":" + (String) status.getText();
				Date date = status.getCreatedAt();
				// crawlTweet.synmap.put(date,msg);
				conDB.put(date, msg);
				
			}

			@Override
			public void onException(Exception ex) {
				ex.printStackTrace();
				System.out.println("onException:" + ex.getMessage());
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBlock(User arg0, User arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDeletionNotice(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDirectMessage(DirectMessage arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFavorite(User arg0, User arg1, Status arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFollow(User arg0, User arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFriendList(long[] arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUnblock(User arg0, User arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUnfavorite(User arg0, User arg1, Status arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUnfollow(User arg0, User arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUserListCreation(User arg0, UserList arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUserListDeletion(User arg0, UserList arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUserListMemberAddition(User arg0, User arg1,
					UserList arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUserListMemberDeletion(User arg0, User arg1,
					UserList arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUserListSubscription(User arg0, User arg1,
					UserList arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUserListUnsubscription(User arg0, User arg1,
					UserList arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUserListUpdate(User arg0, UserList arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUserProfileUpdate(User arg0) {
				// TODO Auto-generated method stub

			}
		};
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(listener);

		// user() method internally creates a thread which manipulates
		// TwitterStream and calls these adequate listener methods continuously.
		twitterStream.user();
	}

}
