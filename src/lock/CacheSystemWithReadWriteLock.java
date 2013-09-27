package lock;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheSystemWithReadWriteLock {

	public static void main(String[] args) {
		final CacheSystemWithReadWriteLock c = new CacheSystemWithReadWriteLock();
		for (int i = 0; i < 5; i++) {
			new Thread() {
				@Override
				public void run() {
					System.out.println(c.get("aaa"));
				}
			}.start();
		}
	}

	private Map<String, Object> cache = new HashMap<String, Object>();
	Object value = null;

	public Object get(String key) {
		ReadWriteLock rwl = new ReentrantReadWriteLock();
		rwl.readLock().lock();

		try {
			value = cache.get(key);
			if (value == null) {
				rwl.readLock().unlock();
				try {
					Thread.sleep(new Random().nextInt(5000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rwl.writeLock().lock();
				try {
					if (value == null) {
						// 数据库操作
						value = "" + new Random().nextInt();
						cache.put(key, value);
					}
				} finally {
					rwl.writeLock().unlock();
				}
				rwl.readLock().lock();
			}
		} finally {
			rwl.readLock().unlock();

		}
		return value;
	}

}
