import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements Playfair Cipher with console interaction
 * Can decrypt and encrypt messages
 */
public class PlayfairCipher {
	private static final Set<Character> alphabet = new HashSet<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','k','l','m','n','o','p','q','r','s','t','u','v','w', 'x','y','z'));
	private static Map<Character,int[]> map;
	private char[][] table;
	
	/**
	 * Creates an instance of PlayfairCipher using the given key to build encoding map
	 * @param key
	 */
	public PlayfairCipher(String key){
		char[] chars = key.toLowerCase().toCharArray();
		
		//fill in map and table
		table = new char[5][5];
		map = new HashMap<Character,int[]>();
		Set<Character> used = new HashSet<Character>();
		int n = 0;
		for(char c: chars){
			c = (c == 'j') ? 'i' : c; //replace all i's with j's
			if(c >= 'a' && c <= 'z' && used.add(c)){
				int[] index = {n/5,n%5};
				
				map.put(c, index);
				table[n/5][n%5] = c;
				n++;
			}
		}
		Set<Character> unused = setDiff(alphabet,used);
		for(Character c : unused){
			int[] index = {n/5,n%5};
			map.put(c, index);
			table[n/5][n%5] = c;
			n++;
		}
	}
	
	/**
	 * encrypt given message using playfair cipher
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
	 * Decodes encrypted phrase that was encoded using the same cipher key
	 * @param encoding 
	 * @return decrypted message
	 */
	public String decrypt(String encoding){
		String message = "";
		List<String> digraphs = getDigraphs(encoding, false);
		for(String pair: digraphs){
			message += decode(pair);	
		}
	
		String messageMinX = "";
		if(message.length() > 0){
		//remove padding x's
			char prev = 0;
			char current = 0;
			char next = message.charAt(0);
			for(int i = 0; i < message.length()-1; i++){
				prev = current;
				current = next;
				next = message.charAt(i+1);
				if(!(prev == next && current == 'x')){
					messageMinX += current;
				}
			}
			char last = message.charAt(message.length()-1);
			if (last != 'x')
				messageMinX += message.charAt(message.length()-1);
		}
		
		//TODO split along words
		return messageMinX;
	}
	
	
	/**
	 * Computes the set difference s1 - s2
	 * @param <T> type of elements contains in s1 and s2
	 * @param s1
	 * @param s2
	 * @return s3 = s1 - s2
	 */
	private <T> Set<T> setDiff(Set<T> s1, Set<T> s2){
		Set<T> s3 = new HashSet<T>(s1);
		s3.removeAll(s2);
		return s3;
	}
	
	/**
	 * Separates a string input into digraphs adding padding between repeated values
	 * @param message
	 * @return list of digraphs
	 */
	private List<String> getDigraphs(String message, boolean encrypt){
		char[] chars = message.toLowerCase().toCharArray();
		List<String> digraphs = new ArrayList<String>();
		char prev = 0;
		String current = "";
		for(char c: chars){
			c = (c == 'j') ? 'i' : c;
			if(c >= 'a' && c <= 'z'){
				if(prev == 0)
					current += c;
				else if(encrypt && prev == c){
					//pad X
					if(current.length() < 2)
					{	current += 'x';
						digraphs.add(current);
						current = "" + c;
					}
					else{
						digraphs.add(current);
						current = "" + 'x' + c;
					}
				}
				else if(current.length() < 2)
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
	 * Encodes digraph using initialized map 
	 * @param pair digraph of letters
	 * @return encoding of digraph
	 */
	private String encode(String pair){
		String encoding = "";
		char[] chars = pair.toCharArray();
		int[] index0 = map.get(chars[0]);
		int[] index1 = map.get(chars[1]);
		//if letters appear on the same row, replace with letters imm. to right
		if(index0[0] == index1[0]){
			encoding += "" + table[index0[0]][(index0[1]+1)%5] + table[index1[0]][(index1[1]+1)%5];
		}
		//if letters appear on the same column, replace with letters imm. below
		else if(index0[1] == index1[1]){
			encoding += "" + table[(index0[0]+1)%5][index0[1]] + table[(index1[0]+1)%5][index1[1]];
		}
		//replace them with the letters on the same row respectively but at the other pair of corners of the rectangle defined by the original pair.
		else {
			encoding += "" + table[index0[0]][index1[1]] + table[index1[0]][index0[1]];
		}

		return encoding;
	}
	
	/**
	 * Decodes digraph using initialized map 
	 * @param pair digraph of letters
	 * @return decoding of digraph
	 */
	private String decode(String pair){
		String encoding = "";
		char[] chars = pair.toCharArray();
		int[] index0 = map.get(chars[0]);
		int[] index1 = map.get(chars[1]);
		//if letters appear on the same row, replace with letters imm. to left
		if(index0[0] == index1[0]){
			int col1 = ((index0[1]-1)%5)==-1 ? 4 : (index0[1]-1)%5;
			int col2 = ((index1[1]-1)%5)==-1 ? 4 : (index1[1]-1)%5;
			encoding += "" + table[index0[0]][col1] + table[index1[0]][col2];
		}
		//if letters appear on the same column, replace with letters imm. above
		else if(index0[1] == index1[1]){
			int row1 = ((index0[0]-1)%5)==-1 ? 4 : (index0[0]-1)%5;
			int row2 = ((index1[0]-1)%5)==-1 ? 4 : (index1[0]-1)%5;
			encoding += "" + table[row1][index0[1]] + table[row2][index1[1]];
		}
		//replace them with the letters on the same row respectively but at the other pair of corners of the rectangle defined by the original pair.
		else {
			encoding += "" + table[index0[0]][index1[1]] + table[index1[0]][index0[1]];
		}

		return encoding;
	}
	
	public static void main(String[] args){
		System.out.print("Please choose a key for the cipher: ");
		Scanner in = new Scanner(System.in);
		String key = in.nextLine();
		PlayfairCipher pf = new PlayfairCipher(key);
		
		while(true){
			System.out.print("Please enter new message: ");
			String input = in.nextLine();
			String encoding = pf.encrypt(input);
			System.out.println(encoding);
			System.out.println(sep.determineWords(pf.decrypt(encoding)));
		}
	}
}
