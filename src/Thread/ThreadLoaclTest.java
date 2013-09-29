package Thread;

import java.util.Random;

/***
 * 利用ThreadLocal来实现一个线程安全的domian类
 * 
 * @author dlu
 * 
 */
public class ThreadLoaclTest {

	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			new Thread() {
				@Override
				public void run() {
					int data1 = new Random().nextInt();
					System.out.println(Thread.currentThread().getName()
							+ " put data1 " + data1);
					int data2 = new Random().nextInt();
					System.out.println(Thread.currentThread().getName()
							+ " put data2 " + data2);
					ThreadLocalData threadData = ThreadLocalData
							.getThreadInstance();
					threadData.setData1(data1);
					threadData.setData2(data2);
					new A().get();
					new B().get();
				}
			}.start();
		}
	}

	static class A {
		public void get() {
			ThreadLocalData threadData = ThreadLocalData.getThreadInstance();
			System.out.println("A " + Thread.currentThread().getName()
					+ " data1 " + threadData.getData1() + " data2 "
					+ threadData.getData2());
		}
	}

	static class B {
		public void get() {
			ThreadLocalData threadData = ThreadLocalData.getThreadInstance();
			System.out.println("B " + Thread.currentThread().getName()
					+ " data1 " + threadData.getData1() + " data2 "
					+ threadData.getData2());
		}
	}
}

class ThreadLocalData {
	private int data1;
	private int data2;
	private static ThreadLocal<ThreadLocalData> threadLocalData = new ThreadLocal<ThreadLocalData>();

	private ThreadLocalData() {
	};

	public static ThreadLocalData getThreadInstance() {
		ThreadLocalData instance = threadLocalData.get();
		if (instance == null) {
			instance = new ThreadLocalData();
			threadLocalData.set(instance);
		}
		return instance;
	}

	public int getData1() {
		return data1;
	}

	public void setData1(int data1) {
		this.data1 = data1;
	}

	public int getData2() {
		return data2;
	}

	public void setData2(int data2) {
		this.data2 = data2;
	}

}
