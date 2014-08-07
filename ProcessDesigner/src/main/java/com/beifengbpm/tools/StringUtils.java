package com.beifengbpm.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StringUtils {
	public static String getkeys() {
		String key = UUID.randomUUID().toString();
		return key;
	}

	public static boolean isEmpty(String str) {
		if (str != null && !str.equals("")) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNotEmpty(String str) {
		if (str != null && !str.equals("")) {
			return true;

		} else {
			return false;
		}
	}

	public static String parseDateToString(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static void main(String[] args) {
		System.out.println(getkeys());
	}

}
