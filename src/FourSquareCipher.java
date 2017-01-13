import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class FourSquareCipher {

	//alphabet minus Q
	private static final List<Character> alphabet = new ArrayList<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','r','s','t','u','v','w', 'x','y','z'));
	private char[][] one, two, three, four;
	
	public FourSquareCipher(String keyOne, String keyTwo){
		one = new char[5][5];
		fill(one, "");
		four = new char[5][5];
		fill(four, "");
		two = new char[5][5];
		fill(two, keyOne);
		three = new char[5][5];
		fill(three, keyTwo);
	}
	
	/**
	 * Separates a string input into digraphs adding padding between repeated values
	 * @param message
	 * @return list of digraphs
	 */
	public static List<String> getDigraphs(String message, boolean encrypt){
		char[] chars = message.toLowerCase().toCharArray();
		List<String> digraphs = new ArrayList<String>();
		char prev = 0;
		String current = "";
		for(char c: chars){
			c = (c == 'j') ? 'i' : c;
			if(c >= 'a' && c <= 'z'){
				if(prev == 0 || current.length() < 2)
					current += c;
				else{
					digraphs.add(current);
					current = "" + c;
				}
				prev = c;
			}
		}
		if(encrypt && current.length() == 1)
		{
			current += 'x';
			digraphs.add(current);
		}
		else if(current.length() == 2)
		{
			digraphs.add(current);
		}
		
		return digraphs;
	}
	
	/**
	 * Fills in table with the letters in the key given and pads the rest of the array with the alphabet in order ignoring duplicates
	 * @param table 5x5 char array to be filled in with key
	 * @param chars char array of keyword characters
	 */
	private void fill(char[][] table, String key){
		char[] chars = key.toLowerCase().toCharArray();
		
		//fill in map and table
	
		Set<Character> used = new HashSet<Character>();
		int n = 0;
		for(char c: chars){
			c = (c == 'j') ? 'i' : c; //replace all i's with j's
			if(c >= 'a' && c <= 'z' && used.add(c)){
				table[n/5][n%5] = c;
				n++;
			}
		}
		Set<Character> unused = PlayfairCipher.setDiff(new HashSet(alphabet),used);
		for(Character c : unused){
			table[n/5][n%5] = c;
			n++;
		}
	}
	
	private int[] find(char c1, char[][] arr1, char c2, char[][] arr2){
		int[] indexes = new int[4]; //r c1, c c1, r c2, c c2
		for(int r = 0; r < 5; r++)
			for(int c = 0; c < 5; c++){
				if(arr1[r][c] == c1)
				{
					indexes[0] = r;
					indexes[1] = c;
				}
				if(arr2[r][c] == c2)
				{
					indexes[2] = r;
					indexes[3] = c;
				}
			}
		
		return indexes;
	}

	
	/**
	 * encrypt given message using four square cipher
	 * @param message
	 * @return encrypted string
	 */
	public String encrypt(String message){
	
		String encoding = "";
		List<String> digraphs = getDigraphs(message,true);
		
		for(String pair: digraphs){
			encoding += encode(pair);	
		}

		return encoding;
	}
	
	/**
	 * decrypt given message using four square cipher
	 * @param message
	 * @return encrypted string
	 */
	public String decrypt(String message){
	
		String encoding = "";
		List<String> digraphs = getDigraphs(message,true);
		
		for(String pair: digraphs){
			encoding += decode(pair);	
		}

		return encoding;
	}
	
	/**
	 * Encodes digraph using initialized map 
	 * @param pair digraph of letters
	 * @return encoding of digraph
	 */
	private String encode(String pair){
		String encoding = "";
		char[] chars = pair.toCharArray();
		int[] indexes = find(chars[0],one,chars[1],four);
		
		//encode to corners of rectangle
		encoding += this.three[indexes[2]][indexes[1]]; //column of left, row of right
		encoding += this.two[indexes[0]][indexes[3]];	//column of right, row of left

		return encoding;
	}
	
	public String decode(String pair){
		String encoding = "";
		char[] chars = pair.toCharArray();
		int[] indexes = find(chars[0],three,chars[1],two);
		
		//encode to corners of rectangle
		encoding += this.one[indexes[2]][indexes[1]]; //column of left, row of right
		encoding += this.four[indexes[0]][indexes[3]];	//column of right, row of left

		return encoding;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter first keyword: ");
		String keyOne = in.nextLine();
		System.out.print("Please enter second keyword: ");
		String keyTwo = in.nextLine();
		FourSquareCipher cipher = new FourSquareCipher(keyOne,keyTwo);
		while(true){
			System.out.print("Please enter new message: ");
			String input = in.nextLine();
			String encoding = cipher.encrypt(input);
			System.out.println(encoding);
			System.out.println(cipher.decrypt(encoding));
		}
	}

}
