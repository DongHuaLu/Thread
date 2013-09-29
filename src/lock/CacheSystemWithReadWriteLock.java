package lock;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 利用ReadWriteLock实现的缓存系统
 * 
 * @author dlu
 * 
 */
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
		// 读锁
		rwl.readLock().lock();

		try {
			value = cache.get(key);
			// 缓存中没有
			if (value == null) {
				// 取消读锁
				rwl.readLock().unlock();
				try {
					Thread.sleep(new Random().nextInt(5000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 上写锁
				rwl.writeLock().lock();
				try {
					// 上完写锁再判断一次,防止重复赋值
					if (value == null) {
						// 数据库操作
						value = "" + new Random().nextInt();
						// 缓存结果
						cache.put(key, value);
					}
				} finally {
					// 解锁写锁
					rwl.writeLock().unlock();
				}
				// 上写锁,准备读取
				rwl.readLock().lock();
			} else {
				// 从缓存读取
				value = cache.get(key);
			}
		} finally {
			// 释放读锁
			rwl.readLock().unlock();

		}
		return value;
	}

}
