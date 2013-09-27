package sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		final Semaphore sp = new Semaphore(3,true);
		for (int i = 0; i < 10; i++) {
			Runnable r = new Runnable() {
				@Override
				public void run() {
					try {
						sp.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println(Thread.currentThread().getName()
							+ " 已经进入 当前有  " + (3 - sp.availablePermits()));

					try {
						Thread.sleep((long) (Math.random() * 10000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()
							+ " 即将离开");
					sp.release();

					System.out.println(Thread.currentThread().getName()
							+ " 已经离开  当前有  " + (3 - sp.availablePermits()));

				}
			};
			threadPool.execute(r);
		}
		threadPool.shutdown();
	}

}
