package br.com.capanema.kers.velocity.util;

public class ArrayUtil {
	/**
	 * Compare two String arrays
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static boolean equals(String[] array1, String[] array2) {
		
		if (array1.length != array2.length) {
			return false;
		}
		
		for(int i=0; i<array1.length; i++) {
			if(!array1[i].equals(array2[i])) {
				return false;
			}
		}
		
		return true;
	}
}
