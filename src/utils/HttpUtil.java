package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

	public static String post(String url, Map<String, String> heads,
			Map<String, String> params) {
		try {
			URL postUrl = new URL(url);
			// 打开连接
			HttpURLConnection connection = (HttpURLConnection) postUrl
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);

			connection.setInstanceFollowRedirects(true);

			if (heads != null) {
				for (String key : heads.keySet()) {
					connection.setRequestProperty(key, heads.get(key));
				}
			}

			connection.connect();
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			// 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
			if (params != null) {
				StringBuffer sb = new StringBuffer();
				for (String key : params.keySet()) {
					sb.append(key + "="
							+ URLEncoder.encode(params.get(key), "utf-8") + "&");
				}
				// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
				out.writeBytes(sb.toString());

				out.flush();
				out.close();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			StringBuffer result = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}

			reader.close();
			connection.disconnect();
			return result.toString();
		} catch (IOException e) {
			System.out.println(url + " 请求失败");
			return null;
		}
	}
}