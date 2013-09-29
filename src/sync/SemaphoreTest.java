package sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/***
 * Semaphore(n),Semaphore.acquire(),当线程小于n时允许进入,执行完释放Semaphore.release();
 * 
 * @author dlu 计数信号量
 * 
 */
public class SemaphoreTest {
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		final Semaphore sp = new Semaphore(3);
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
