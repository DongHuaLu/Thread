package callabletest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/***
 * 利用回调模板发送web请求
 * 
 * @author dlu
 * 
 */
public class CallAbleTest {

	public static void main(String[] args) {
		CompletionServiceTemplate t = new CompletionServiceTemplate() {

			@Override
			public void onSuccess(Map<String, String> map) {
				for (String key : map.keySet()) {
					System.out.println(key + " : " + map.get(key));
				}
			}

			@Override
			public void onFaile() {
				System.out.println("请求失败");
			}
		};
		long start = System.currentTimeMillis();
		Set<String> urls = new HashSet<String>();
		urls.add("http://10.200.0.157:82/Login");
		urls.add("http://10.200.0.157:82/loginfilter/ContactList");
		t.execute(urls);
		System.out.println(System.currentTimeMillis() - start);
	}
}
