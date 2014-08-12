package com.beifengbpm.pd.datasource;

public class TestXML {
	public static void main(String[] args) {
		String uri="http://2c3c15c0-a1c4-4ced-b266-240beca2411c.bcserver.com/framework/index.jsp";
		if(uri.contains("http")){
			uri=uri.substring(7,uri.length());
			int index=uri.indexOf("/");
			uri=uri.substring(0,index);
			System.out.println(uri);
			String [] s=uri.split("\\.");
			System.out.println(s[0]);
		}
	}
}
