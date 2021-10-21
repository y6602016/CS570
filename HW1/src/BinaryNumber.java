// HW1, CWID: 10675677, Name: Qi-Rui Hong
import java.util.Arrays;

public class BinaryNumber {
	private int[] data;
	private boolean overflow;
	
	// constructor with int parameter
	public BinaryNumber(int length) throws Exception {
		this.overflow = false;
		
		// check length range, negative value is not allowed
		if (length < 0) {
			throw new Exception("Invalid Input. Length must be non-negative.");
		}
		
		this.data = new int[length];
		for (int i = 0; i < length; ++i) {
			data[i] = 0;
		}
	}
	
	// constructor with string parameter
	public BinaryNumber(String str) throws Exception {
		this.overflow = false;
		
		int length = str.length();
		
		// if length > 31, it's overflow
		if (length > 31) {
			this.overflow = true;
		}
		
		this.data = new int[length];
		
		for (int i = 0; i < length; ++i) {
			char c = str.charAt(i);
			int numericValue = Character.getNumericValue(c);
			
			// if the string contains char other than 1 or 0, throw error
			if (numericValue != 0 && numericValue != 1) {
				throw new Exception("Invalid Input. String must consist of only 1 or 0.");
			}
			
			this.data[i] = numericValue;
		}
	}
	
	// method to get the length of the binary number
	public int getLength() {
		return this.data.length;
	}
	
	// method to get the digit according to the given index
	public int getDigit(int index) throws Exception {
		int length = this.getLength();
		
		// check index range
		if (index >= length || index < 0) {
			throw new Exception("Invalid Input. Index must be non-negative and less than " + length);
		}
		
		return this.data[index];
	}
	
	// method to shift the binary number right
	public void shiftR(int amount) throws Exception {
		if (amount <= 0) {
			throw new Exception("Invalid Input. Amount must be positive.");
		}
		
		int length = this.getLength();
		
		// if the length after shifting > 31, it's overflow
		if (length + amount > 31) {
			this.overflow = true;
		}
		
		// resize the array by replace it with a temp array with new size 
		int[] temp = new int[length + amount];
		for (int i = 0; i < amount; ++i) {
			temp[i] = 0;
		}
		for (int i = 0; i < length; ++i) {
			temp[amount + i] = this.data[i];
		}
		
		// rearrange temp to this.data array
		this.data = temp;
	}
	
	// method to implement binary number adds
	public void add(BinaryNumber aBinaryNumber) {
		int length = this.getLength();
		
		// check both sizes are same or not
		if (length != aBinaryNumber.getLength()) {
			System.out.println("Two binary lengths do not coincide.");
			return;
		}
		
		// use a pre variable to record the sum result
		int pre = 0;
		String binary = aBinaryNumber.toString();
		for (int i = 0; i < length; ++i) {
			if (this.data[i] == 1 && binary.charAt(i) == '1') {
				if (pre == 1) {
					this.data[i] = 1;
				}
				else {
					this.data[i] = 0;	
				}
				pre = 1;
			}
			else if (this.data[i] == 1 || binary.charAt(i) == '1') {
				if (pre == 1) {
					this.data[i] = 0;
					pre = 1;
				}
				else {
					this.data[i] = 1;
					pre = 0;
				}
			}
			// both are 0
			else {
				if (pre == 1) {
					this.data[i] = 1;
					pre = 0;
				}
				else {
					this.data[i] = 0;
				}
			}
		}
		
		// if pre == 1, means the array is over length
		// replace with a new array with size length + 1
		if (pre == 1) {
			int[] temp = Arrays.copyOf(this.data, length + 1);
			temp[length] = 1;
			this.data = temp;
			
			if (length + 1 > 31) {
				this.overflow = true;
			}
		}
	}
	
	// method to transform binary number to string
	public String toString() {
		// check overflow or not
		if (this.overflow) {
			return "Overflow";
		}
		
		String str = "";
		for (int i = 0; i < this.getLength(); ++i) {
			str += String.valueOf(this.data[i]);
		}
		return str;
	}
	
	// method to transform binary number to its decimal notation
	public int toDecimal() {
		int base = 2;
		int num = 1;
		int decimal = 0;
		for (int i = 0; i < this.getLength(); ++i) {
			decimal += this.data[i] * num;
			num *= base;
		}
		return decimal;
	}
	
	// method to clear the overflow flag
	public void clearOverflow() {
		this.overflow = false;
	}
	
	public static void main(String[] args) {
		try {
			BinaryNumber test = new BinaryNumber("1111111111111111111111111111111");
			System.out.println(test.toDecimal());
			System.out.println(test.toString());
			System.out.println(Integer.MAX_VALUE);
//			BinaryNumber test2 = new BinaryNumber("11001");
//			test.add(test2);
//			System.out.println(test.toDecimal());
//			System.out.println(test.toString());
//			test.shiftR(32);
//			System.out.println(test.toString());
//			System.out.println(test.toDecimal());
		} catch (Exception excp) {
			System.out.print(excp.getMessage());
		} catch (OutOfMemoryError excp) {
			System.out.print(excp.getMessage());
		}
		
    }
}
