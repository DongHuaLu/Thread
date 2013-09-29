package BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/***
 * 利用BlockingQueue来实现生产者/消费者模式
 * 
 * @author dlu
 * 
 */
public class ThreadCommunicationWithQueue {

	public static void main(String[] args) {

		final Business b = new Business();
		new Thread() {
			public void run() {
				for (int i = 0; i < 50; i++) {
					b.sub();
				}
			};
		}.start();

		for (int i = 0; i < 50; i++) {
			b.main();
		}
	}
}

class Business {
	BlockingQueue<Integer> queueSub = new ArrayBlockingQueue<Integer>(1);
	BlockingQueue<Integer> queueMain = new ArrayBlockingQueue<Integer>(1);

	{
		try {
			queueSub.put(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public void sub() {
		try {
			queueSub.put(1);
			System.out.println("sub thread ");
			queueMain.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void main() {
		try {
			queueMain.put(1);
			System.out.println("main thread ");
			queueSub.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
