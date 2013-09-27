package utils;

import java.util.HashMap;
import java.util.Map;

public class Utils {
	
	
	public static Map<String,String> toMap(String str){
		Map<String,String> map=new HashMap<String, String>();
		String[] strs=str.split("&");
		for(String s:strs){
			String[] ss=s.split("=");
			map.put(ss[0], ss[1]);
		}
		
		return map;
	}
}
