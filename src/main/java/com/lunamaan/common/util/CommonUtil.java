package com.lunamaan.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CommonUtil {
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
