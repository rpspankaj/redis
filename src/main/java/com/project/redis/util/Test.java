package com.project.redis.util;

import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String args[]) {
		int[] arr = { 1, 2, 7, 7, 7, 7, 8, 8, 13, 13, 13, 1, 1 };
		int count = 1;
		Map<Integer, Integer> map = new HashMap<>();
		for (int i : arr) {
			Integer ii = null == map.get(i) ? map.put(i, count) : map.put(i, map.get(i) + 1);
		}
		System.out.println(map);

		String input = "aaaabbccAAdda";
		char search = 'a'; // Character to search is 'a'.
		Map<Character,Integer> hash = new HashMap<Character,Integer>();
		int ctr = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == search) {
				ctr++;
			}
		
			Integer chr =null == hash.get(input.charAt(i))
						?hash.put(input.charAt(i), 1):hash.put(input.charAt(i),hash.get(input.charAt(i))+ 1);
		}
		System.out.println("The Character '" + search + "' appears " + ctr + " times.");
		System.out.println(hash);
	}
}
