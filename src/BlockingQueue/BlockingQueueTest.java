package BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * 多线程阻塞队列
 * 
 * @author dlu
 * 
 */
public class BlockingQueueTest {
	public static void main(String[] args) {
		final BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
		for (int i = 0; i < 5; i++) {
			new Thread() {
				@Override
				public void run() {
					try {
						while (true) {
							Thread.sleep(1000);
							System.out.println(Thread.currentThread().getName()
									+ " 准备放数据");
							queue.put(1);
							System.out.println(Thread.currentThread().getName()
									+ " 放入数据 当前队列数据 " + queue.size());
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();

			new Thread() {
				@Override
				public void run() {
					try {
						while (true) {
							Thread.sleep(100);
							System.out.println(Thread.currentThread().getName()
									+ " 准备取数据");
							queue.take();
							System.out.println(Thread.currentThread().getName()
									+ " 取出数据 当前队列数据 " + queue.size());
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

}
