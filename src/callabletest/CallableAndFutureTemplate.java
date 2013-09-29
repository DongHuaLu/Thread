package callabletest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 单线程回调模板 setTimeout(int) 设置超时时间 重写三个回调方法
 * 
 * @author dlu
 * 
 */
public abstract class CallableAndFutureTemplate {
	private int timeout = 10;

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void get() {
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		Future<Object> result = threadPool.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				return execute();
			}
		});

		try {
			Object o = result.get(timeout, TimeUnit.SECONDS);
			onSuccess(o);
		} catch (Exception e) {
			onFaile();
		}
		threadPool.shutdown();
	}

	public abstract void onSuccess(Object o);

	public abstract void onFaile();

	public abstract Object execute();
}
