package Shoutbox;

public class statusRetrive<K, V> extends Thread {
	ConcurrentDB<K, V> conDB;

	statusRetrive(ConcurrentDB<K, V> conDBVal) {
		this.conDB = conDBVal;
	}

	@Override
	public void run() {
		while (true) {

			while (conDB.size() > 0) {
				conDB.pop();
			}
		}

	}

}
