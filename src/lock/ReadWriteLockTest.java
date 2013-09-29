package lock;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * 读写锁,保证
 * 1.在读取的时候其他线程可以读取,但是不能写入
 * 2.在写入的时候其他线程不能进行读取写入操作都
 * @author dlu
 *
 */
public class ReadWriteLockTest {

	public static void main(String[] args) {
		final A a = new A();
		for (int i = 0; i < 3; i++) {
			new Thread() {
				@Override
				public void run() {
					while (true) {
						a.get();
					}
				}
			}.start();
		}

		for (int i = 0; i < 3; i++) {
			new Thread() {
				@Override
				public void run() {
					while (true) {
						a.write();
					}
				}
			}.start();
		}
	}

	static class A {
		private Object data = null;
		ReadWriteLock rwl = new ReentrantReadWriteLock();

		public void get() {
			rwl.readLock().lock();
			try {
				System.out.println(Thread.currentThread().getName()
						+ " ready to read");
				Thread.sleep((long) (Math.random() * 1000));
				System.out.println(Thread.currentThread().getName()
						+ " get data " + data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				rwl.readLock().unlock();
			}
		}

		public void write() {
			rwl.writeLock().lock();
			try {
				System.out.println(Thread.currentThread().getName()
						+ " ready to write");
				Thread.sleep((long) (Math.random() * 1000));
				int temp = new Random().nextInt();
				data = temp;
				System.out.println(Thread.currentThread().getName()
						+ " write data " + data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				rwl.writeLock().unlock();
			}
		}
	}
}
