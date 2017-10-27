package com.lunamaan.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CommonUtil {
	private JSONObject getJsonObject(String responseStr) {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject)jsonParser.parse(responseStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		return jsonObj;
	}
	
	public static String encodeURIComponent(String str) {
		String encodeStr = null;

		try {
			encodeStr = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			encodeStr = str;
		}

		return encodeStr;
	}
}
