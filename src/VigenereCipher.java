

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * implementation of Vigenere cipher that performs multiple shifts 
 * on input text depending on index
 * Less vulnerable to frequency attacks than Caesar cipher
 */
public class VigenereCipher {
	private static final List<Character> alphabet = new ArrayList<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w', 'x','y','z'));
	private final String key;
	
	public VigenereCipher(String key){
		this.key = key;
	}
	
	private Character shift(char c, int i){
		int m = alphabet.indexOf(c);
		int k = alphabet.indexOf(key.charAt(i%key.length()));
		return alphabet.get((m+k)%alphabet.size());
	}
	
	private Character deshift(char c,int i){
		int e = alphabet.indexOf(c);
		int k = alphabet.indexOf(key.charAt(i%key.length()));
		int shift = e-k;
		shift = (shift >= 0) ? shift : alphabet.size() + shift ;
		return alphabet.get(shift%alphabet.size());
	}
	
	public String encode(String message){
		String encoding = "";
		message = message.toLowerCase();
		for(int i = 0; i < message.length(); i++){
			char c = message.charAt(i);
			if(c >= 'a' && c <= 'z')
				encoding += "" + shift(c,i);
		}
		return encoding;
	}
	
	public String decode(String message){
		String encoding = "";
		message = message.toLowerCase();
		for(int i = 0; i < message.length(); i++){
			char c = message.charAt(i);
			if(c >= 'a' && c <= 'z')
				encoding += "" + deshift(c,i);
		}
		
		return encoding;
	}
	
	public static void main(String[] args){
		System.out.print("Please choose a key for the cipher: ");
		Scanner in = new Scanner(System.in);
		String key = in.next();
		VigenereCipher cipher = new VigenereCipher(key);
		
		while(true){
			System.out.print("Please enter new message: ");
			String input = in.nextLine();
			String encoding = cipher.encode(input);
			System.out.println(encoding);
			System.out.println(cipher.decode(encoding));
		}
	}
}
