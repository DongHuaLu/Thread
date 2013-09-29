package sync;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 * Exchanger,exchanger.exchange(data),第一个线程在此点等待第二个线程,当两个线程同时到达此点是,交换数据后继续执行
 * 
 * @author dlu 交换对象的同步点
 * 
 */
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
