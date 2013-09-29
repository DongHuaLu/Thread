package Thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 三种线程池
 * 
 * @author dlu
 * 
 */
public class ThreadPoolText {

	public static void main(String[] args) {
		// 固定大小的线程池
		// ExecutorService threadPool = Executors.newFixedThreadPool(3);
		// 缓存的线程池,自动增加线程数
		// ExecutorService threadPool = Executors.newCachedThreadPool();
		// 单个线程的线程池
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		for (int i = 1; i <= 10; i++) {
			final int task = i;
			threadPool.execute(new Runnable() {

				@Override
				public void run() {
					for (int i = 1; i <= 3; i++) {
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("task " + task + " "
								+ Thread.currentThread().getName() + " loop "
								+ i);
					}
				}
			});
		}
		threadPool.shutdown();

		Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				System.out.println("scheduled");
			}
		}, 10, 2, TimeUnit.SECONDS);
	}
}
