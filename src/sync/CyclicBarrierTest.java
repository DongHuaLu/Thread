package sync;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		final CyclicBarrier cb = new CyclicBarrier(5);
		for (int i = 0; i < 4; i++) {
			Runnable r = new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep((long) (Math.random() * 10000));
						System.out.println(Thread.currentThread().getName()
								+ " 已经到集结点1 当前有" + (cb.getNumberWaiting() + 1));
						cb.await();

						Thread.sleep((long) (Math.random() * 10000));
						System.out.println(Thread.currentThread().getName()
								+ " 已经到集结点2 当前有" + (cb.getNumberWaiting() + 1));
						cb.await();

						Thread.sleep((long) (Math.random() * 10000));
						System.out.println(Thread.currentThread().getName()
								+ " 已经到集结点3 当前有" + (cb.getNumberWaiting() + 1));
						cb.await();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			threadPool.execute(r);
		}
		threadPool.shutdown();
	}

}
