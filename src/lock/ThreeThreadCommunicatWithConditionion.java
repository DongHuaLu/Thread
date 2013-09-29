package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * 利用三个Condition来完成三个线程的生产者/消费者
 * 
 * @author dlu
 * 
 */
public class ThreeThreadCommunicatWithConditionion {

	public static void main(String[] args) {
		final Bussiness b = new Bussiness();
		for (int i = 0; i < 500; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					b.main();
				}
			}).start();
			new Thread(new Runnable() {
				@Override
				public void run() {
					b.sub();
				}
			}).start();
			new Thread(new Runnable() {
				@Override
				public void run() {
					b.third();
				}
			}).start();
		}
	}

	static class Bussiness {
		private int shouldRun = 1;
		Lock lock = new ReentrantLock();
		Condition runMainCondition = lock.newCondition();
		Condition runSubCondition = lock.newCondition();
		Condition runThirdCondition = lock.newCondition();

		public void main() {
			lock.lock();
			try {
				while (shouldRun != 1) {
					runMainCondition.await();
				}
				Thread.sleep(100);
				System.out.print("1");
				shouldRun = 2;
				runSubCondition.signal();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();

			}

		}

		public void sub() {
			lock.lock();
			try {
				while (shouldRun != 2) {
					runSubCondition.await();
				}
				Thread.sleep(100);
				System.out.print("2");
				shouldRun = 3;
				runThirdCondition.signal();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		public void third() {
			lock.lock();
			try {
				while (shouldRun != 3) {
					runThirdCondition.await();
				}
				Thread.sleep(100);
				System.out.print("3");
				System.out.println();
				shouldRun = 1;
				runMainCondition.signal();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

}
