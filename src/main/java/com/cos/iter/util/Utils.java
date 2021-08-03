package com.cos.iter.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
	
	public static List<String> tagParse(String tags){
		String[] temp = tags.split("#");

		return new ArrayList<String>(Arrays.asList(temp).subList(1, temp.length));
	}
}




