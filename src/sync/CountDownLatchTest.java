package sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		final CountDownLatch cdMain = new CountDownLatch(3);
		final CountDownLatch cdSub = new CountDownLatch(3);
		for (int i = 0; i < 3; i++) {
			Runnable r = new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println(Thread.currentThread().getName()
								+ " 已经开始,等待计数");
						cdSub.await();
						System.out.println(Thread.currentThread().getName()
								+ " 已经得到命令");
						Thread.sleep((long) (Math.random() * 10000));
						System.out.println(Thread.currentThread().getName()
								+ " 回应主线程命令");
						cdMain.countDown();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			};
			threadPool.execute(r);
		}
		threadPool.shutdown();

		try {
			for (int i = 0; i < 3; i++) {
				Thread.sleep((long) (Math.random() * 10000));
				System.out.println("主线程计数" + i);
				cdSub.countDown();
			}
			System.out.println("主线程等待");
			cdMain.await();
			System.out.println("主线程响应");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
