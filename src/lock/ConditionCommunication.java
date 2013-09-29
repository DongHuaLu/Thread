package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * 采用Condition来完成生产者/消费者模式
 * 
 * @author dlu
 * 
 */
public class ConditionCommunication {

	public static void main(String[] args) {

		final Business b = new Business();
		new Thread() {
			public void run() {
				for (int i = 0; i < 50; i++) {
					b.sub(i);
				}
			};
		}.start();

		for (int i = 0; i < 50; i++) {
			b.main(i);
		}
	}

	static class Business {
		private boolean runSub = true;
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();

		public void sub(int i) {
			lock.lock();
			try {
				while (!runSub) {
					try {
						// this.wait();
						condition.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (int j = 0; j < 50; j++) {
					System.out.println("sub thread " + j + " loop " + i);
				}
				runSub = false;
				System.out.println("+++++++++++++++++++++++++++++++++");
				// this.notify();
				condition.signal();
			} finally {
				lock.unlock();
			}
		}

		public void main(int i) {
			lock.lock();
			try {
				while (runSub) {
					try {
						// this.wait();
						condition.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (int j = 0; j < 50; j++) {
					System.out.println("main thread " + j + " loop " + i);
				}
				runSub = true;
				System.out.println("-----------------------------------");
				// this.notify();
				condition.signal();
			} finally {
				lock.unlock();
			}
		}
	}
}
