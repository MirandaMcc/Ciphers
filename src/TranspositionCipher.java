import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TranspositionCipher {

	private static final List<Character> alphabet = new ArrayList<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w', 'x','y','z'));
	private String[][] arr;
	private int length;
	
	public TranspositionCipher(String message){
		length = message.length();
		int dimensions = closestSquare(message.length());
		arr = new String[dimensions][dimensions];
		fill(arr, message);
	}
	
	private int closestSquare(int i){
		int k = 1;
		int sqr = 1;
		while(sqr < i){
			k++;
			sqr = k*k;
		}
		return k;
	}

	/**
	 * Fills in table with the letters in the key given and pads the rest of the array with the alphabet in order ignoring duplicates
	 * @param table 5x5 char array to be filled in with key
	 * @param chars char array of keyword characters
	 */
	private void fill(String[][] table, String key){
		char[] chars = key.toLowerCase().toCharArray();
		
		//fill in  table
	
		int n = 0;
		for(char c: chars){
			c = (c == 'j') ? 'i' : c; //replace all i's with j's
			if(c >= 'a' && c <= 'z'){
				table[n/5][n%5] = "" +c;
				n++;
			}
		}
		
		int totalSpots = table.length*table.length;
		while(n < totalSpots){
			table[n/table.length][n%table.length] = ""+ alphabet.get((int)(Math.random()*26));
			n++;
		}
	}
	
	public String encode(){
		String encoding = "";
		for(int c = 0; c < arr.length; c++)
			for(int r = 0; r < arr.length; r++){
				encoding += arr[r][c];
			}
		return encoding;
	}
	
	public String decode(){
		String encoding = "";
		int i = 0;
		for(int r = 0; r < arr.length; r++)
			for(int c = 0; c < arr.length; c++){
				if(i < length)
					encoding += arr[r][c];
				else
					break;
				i++;
			}
		return encoding;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		while(true){
			System.out.print("Please enter new message: ");
			String input = in.nextLine();
			TranspositionCipher cipher = new TranspositionCipher(input);
			String encoding = cipher.encode();
			System.out.println(encoding);
			System.out.println(cipher.decode());
		}
		
	}

}
