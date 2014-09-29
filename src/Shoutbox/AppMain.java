package Shoutbox;

import java.util.Date;

import twitter4j.TwitterException;
public class AppMain {

	public static void main(String[] args) throws InterruptedException {
		final ConcurrentDB<Date, String> conDB = new ConcurrentDB<>();
		final CrawlFB fb = new CrawlFB();
		
		
		Thread fbThread = new Thread() {
			public void run() {
				try {
					fb.fbSetup(conDB);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread twitterThread = new Thread() {
			public void run() {
				crawlTweet twitter = new crawlTweet();
				try {
					twitter.twitterSetUp(conDB);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
		};

		twitterThread.start();
		fbThread.start();
		
		twitterThread.join();
		statusRetrive<Date,String> statusCapture=new statusRetrive(conDB);
		statusCapture.start();
		Entry e;
		
		TweetRetrieve tweetCapture=new TweetRetrieve(conDB);
		tweetCapture.start();


	}

}
