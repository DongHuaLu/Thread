package Thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ThreadRandom {

	private static int data = 0;

	public static void main(String[] args) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				data = new Random().nextInt();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "data :"
						+ data);
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				data = new Random().nextInt();
				System.out.println(Thread.currentThread().getName() + "data :"
						+ data);
			}
		}).start();

	}

	static class A {
		public void get() {
			System.out.println("A get data " + Thread.currentThread().getName()
					+ " get data " + data);
		}
	}

	static class B {
		public void get() {
			System.out.println("B get data " + Thread.currentThread().getName()
					+ " get data " + data);
		}
	}
}
