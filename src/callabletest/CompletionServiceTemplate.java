package callabletest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import utils.HttpUtil;

public abstract class CompletionServiceTemplate {
	private int timeout = 10;

	// private List<String> list = new ArrayList<String>();

	public void setTimeout(int second) {
		this.timeout = second;
	}

	public void execute(final Set<String> urls) {
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		CompletionService<Map<String, String>> completionService = new ExecutorCompletionService<Map<String, String>>(
				threadPool);
		for (final String url : urls) {
			Future<Map<String, String>> result = completionService
					.submit(new Callable<Map<String, String>>() {
						@Override
						public Map<String, String> call() throws Exception {
							System.out
									.println(Thread.currentThread().getName());
							Thread.sleep(10);
							Map<String, String> map = new HashMap<String, String>();
							map.put(url, post(url));
							return map;
						}
					});
		}
		Map<String, String> result = new HashMap<String, String>();
		try {
			for (int i = 0; i < urls.size(); i++) {
				result.putAll(completionService.take().get(timeout,
						TimeUnit.SECONDS));
			}
		} catch (Exception e) {

		}
		onSuccess(result);
		threadPool.shutdown();
	}

	public abstract void onSuccess(Map<String, String> map);

	public abstract void onFaile();

	public String post(String url) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "210");
		params.put("deviceId", "123");
		params.put("password", "202cb962ac59075b964b07152d234b70");
		return HttpUtil.post(url, null, params);
	}
}
