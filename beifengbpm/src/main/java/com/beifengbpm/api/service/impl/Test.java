package com.beifengbpm.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1=new Date();
		String s=sdf.format(d1);
		System.out.println(s);
		Date d2=sdf.parse(s);
		System.out.println(d2);
		System.out.println(d1.compareTo(d2));

	}

}
