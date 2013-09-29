package lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * 最基本的Lock
 * 
 * @author dlu
 * 
 */
public class LockTest {
	public static void main(String[] args) {
		final Outputer outputer = new Outputer();
		for (int i = 0; i < 100; i++) {
			new Thread() {
				@Override
				public void run() {
					outputer.output();
				};
			}.start();
		}
	}

	static class Outputer {
		Lock lock = new ReentrantLock();

		public void output() {
			String str = "ludonghua";
			lock.lock();
			try {
				for (int i = 0; i < str.length(); i++) {
					System.out.print(str.charAt(i));
				}
				System.out.println();
			} finally {
				lock.unlock();
			}
		}
	}
}
