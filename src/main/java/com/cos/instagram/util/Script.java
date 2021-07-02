package com.cos.instagram.util;

public class Script {
	public static String alert(String msg) {
		return "<script>" +
				"alert('" + msg + "');" +
				"</script>";
	}
	
	public static String back(String msg) {
		return "<script>" +
				"alert('" + msg + "');" +
				"history.back();" +
				"</script>";
	}
}
