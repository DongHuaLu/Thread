package sync;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		final Exchanger exchanger = new Exchanger();
		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					String data = "thread1";
					Thread.sleep((long) (Math.random() * 10000));
					System.out.println(Thread.currentThread().getName()
							+ " 正准备把数据 " + data + " 交换出去");
					String data2 = (String) exchanger.exchange(data);
					System.out.println(Thread.currentThread().getName()
							+ " 得到交换的数据 " + data2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					String data = "thread2";
					// Thread.sleep((long) (Math.random() * 10000));
					System.out.println(Thread.currentThread().getName()
							+ " 正准备把数据 " + data + " 交换出去");
					String data2 = (String) exchanger.exchange(data);
					System.out.println(Thread.currentThread().getName()
							+ " 得到交换的数据 " + data2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		threadPool.shutdown();
	}
}
