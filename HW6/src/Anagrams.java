import java.util.*;
import java.io.*;

public class Anagrams {
	private final Integer[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61,
			67, 71, 73, 79, 83, 89, 97, 101};
	
	private Map<Character,Integer> letterTable;
	
	private Map<Long,ArrayList<String>> anagramTable;
	
	// constructor that calls buildLetterTable()
	public Anagrams() {
		this.buildLetterTable();
		this.anagramTable = new HashMap<Long,ArrayList<String>>();
	}
	
	// build the letterTable with all letters
	private void buildLetterTable() {
		this.letterTable = new HashMap<Character,Integer>(){
			{
				put('a', 2);
				put('b', 3);
				put('c', 5);
				put('d', 7);
				put('e', 11);
				put('f', 13);
				put('g', 17);
				put('h', 19);
				put('i', 23);
				put('j', 29);
				put('k', 31);
				put('l', 37);
				put('m', 41);
				put('n', 43);
				put('o', 47);
				put('p', 53);
				put('q', 59);
				put('r', 61);
				put('s', 67);
				put('t', 71);
				put('u', 73);
				put('v', 79);
				put('w', 83);
				put('x', 89);
				put('y', 97);
				put('z', 101);
				put('A', 2);
				put('B', 3);
				put('C', 5);
				put('D', 7);
				put('E', 11);
				put('F', 13);
				put('G', 17);
				put('H', 19);
				put('I', 23);
				put('J', 29);
				put('K', 31);
				put('L', 37);
				put('M', 41);
				put('N', 43);
				put('O', 47);
				put('P', 53);
				put('Q', 59);
				put('R', 61);
				put('S', 67);
				put('T', 71);
				put('U', 73);
				put('V', 79);
				put('W', 83);
				put('X', 89);
				put('Y', 97);
				put('Z', 101);
			}
		};
	}
	
	// read the file and and build hash table
	private void processFile(String s) throws IOException{
		FileInputStream fstream = new FileInputStream(s);
		BufferedReader br = new BufferedReader (new InputStreamReader(fstream)); 
		String strLine ;
		while ((strLine = br.readLine()) != null ){
			this.addWord(strLine);
		}
		br.close ();
	}
	
	// the method computing the hash code and add the word into hash table
	private void addWord(String s) {
		// key is the hashCode computed from myHashCode
		Long hashCode = this.myHashCode(s);
		
		// if anagramTable has the key, then check s is in the value or not
		if (this.anagramTable.containsKey(hashCode)) {
			// check s is duplicated or not
			// if s is not in the list, then we add, otherwise, do nothing
			if (!this.anagramTable.get(hashCode).contains(s)) {
				this.anagramTable.get(hashCode).add(s);
			}
		}
		// if not has the key, create a new ArrayList as the value and put it into the table
		else {
			ArrayList<String> word = new ArrayList<String>();
			word.add(s);
			this.anagramTable.put(hashCode, word);
		}
	}
	
	// compute the hash code
	private Long myHashCode(String s) {
		Long hashCode = 1L;
		
		for (int i = 0; i < s.length(); i++) {
			// extract the character
			Character letter = s.charAt(i);
			// convert it into Long type
			Long value = Long.valueOf(letterTable.get(letter).longValue());
			// product to hashCode
			hashCode *= value;
		}
		return hashCode;
	}
	
	// returns a list of them since there may be more than one list of anagrams with a 
	// maximal size, which means there may be not only one max entries 
	private ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries(){
		ArrayList<Map.Entry<Long,ArrayList<String>>> maxEntries = new ArrayList<Map.Entry<Long,ArrayList<String>>>();
		// find the max length first
		int maxLen = 0;
		for (ArrayList<String> value : this.anagramTable.values()) {
			if (value.size() > maxLen) {
				maxLen = value.size();
			}
		}
		
		// find the ArrayList whose size is same as maxLen, append it into maxEntries
		for (Map.Entry<Long,ArrayList<String>> entry : this.anagramTable.entrySet()) {
			ArrayList<String> value = entry.getValue();
			if (value.size() == maxLen) {
				maxEntries.add(entry);
			}
		}
		return maxEntries;
	}
	
	public static void main ( String [] args ) {
		Anagrams a = new Anagrams ();
		final long startTime = System.nanoTime (); 
		try {
			a.processFile ( "words_alpha.txt" ); 
		} 
		catch ( IOException e1 ) {
			e1.printStackTrace ();
		}
		ArrayList <Map.Entry<Long, ArrayList<String>>> maxEntries = a.getMaxEntries(); 
		final long estimatedTime = System.nanoTime() - startTime;
		final double seconds = ((double)estimatedTime / 1000000000) ; 
		System.out.println ("Time : "+ seconds);
		System.out.println ("List of max anagrams : "+ maxEntries);
		System.out.println ("Length of list of max anagrams : "+ maxEntries.get(0).getValue().size());
	}
}
