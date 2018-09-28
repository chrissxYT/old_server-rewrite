package de.chrissx.server.rewrite.util;

import java.util.ArrayList;
import java.util.List;

public class MathUtil {

	public static int max(List<Integer> ints) {
		List<Integer> results = new ArrayList<Integer>();
		for(int i = 0; i < ints.size() - 1; i += 2) {
			results.add(Math.max(ints.get(i), ints.get(i + 1)));
		}
		if(results.size() > 1) {
			return results.get(0);
		}else {
			return max(results);
		}
	}
	
	public static int min(List<Integer> ints) {
		List<Integer> results = new ArrayList<Integer>();
		for(int i = 0; i < ints.size() - 1; i += 2) {
			results.add(Math.min(ints.get(i), ints.get(i + 1)));
		}
		if(results.size() > 1) {
			return results.get(0);
		}else {
			return min(results);
		}
	}
	
	public static int max(int[] ints) {
		List<Integer> results = new ArrayList<Integer>();
		for(int i = 0; i < ints.length - 1; i += 2) {
			results.add(Math.max(ints[i], ints[i + 1]));
		}
		if(results.size() > 1) {
			return results.get(0);
		}else {
			return max(results);
		}
	}
	
	public static int min(int[] ints) {
		List<Integer> results = new ArrayList<Integer>();
		for(int i = 0; i < ints.length - 1; i += 2) {
			results.add(Math.min(ints[i], ints[i + 1]));
		}
		if(results.size() > 1) {
			return results.get(0);
		}else {
			return min(results);
		}
	}
	
	public static boolean isNumber(String str) {
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}
}