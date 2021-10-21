// HW2, CWID: 10475677, Name: Qi-Rui Hong

public class Complexity {
	// complexity O(n^2)
	public static void method1(int n) {
		int counter = 1; 
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.println("Operation " + counter); 
				counter++;
			}
		}
	}
	
	// complexity O(n^3)
	public static void method2(int n) {
		int counter = 1; 
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					System.out.println("Operation " + counter); 
					counter++;
				}
			}
			
		}
	}
	
	// complexity O(logn)
	public static void method3(int n) {
		int counter = 1; 
		if (n == 1) {
			System.out.println("Operation " + counter); 
		}
		else {
			for (int i = 1; i < n; i *= 2) {
				System.out.println("Operation " + counter); 
				counter++;
			}
		}
	}
	
	// complexity O(nlogn)
	public static void method4(int n) {
		int counter = 1; 
		if (n == 1) {
			System.out.println("Operation " + counter); 
		}
		else {
			for (int i = 0; i < n; i++) {
				for (int j = 1; j < n; j *= 2) {
					System.out.println("Operation " + counter); 
				    counter++;
				}
			}
		}
	}
	
	// complexity O(loglogn)
	public static void method5(int n) {
		int counter = 1; 
		if (n == 1 || n == 2) {
			System.out.println("Operation " + counter); 
		}
		else {
			for (int i = 2; i < n; i *= i) {
				System.out.println("Operation " + counter); 
			    counter++;
			}
		}
	}
	
	
	// complexity O(2^n) with recursion
	// the number of each node's branches = 2(select or not select)
	// recursion depth = n
	// complexity = traverse the whole recursion tree = O(2^n)
	static int counter = 0;
	public static int method6(int n) {
		method6_helper(0, n);
		return 0;
	}
	
	private static void method6_helper(int i, int n) {
		counter++;
		System.out.println("Operation " + counter); 
		
		if (i == n) {
			return;
		}
		
		for (int k = i; k < n; k++) {
			method6_helper(k + 1, n);
		}
		 
	}
	
	public static void main(String[] args) {
		method6(4);
	}
	
}