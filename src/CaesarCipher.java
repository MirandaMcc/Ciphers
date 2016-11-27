import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CaesarCipher {
	private static final List<Character> alphabet = new ArrayList<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w', 'x','y','z'));
	private final int offset;
	
	public CaesarCipher(int offset){
		int scaled = (offset % alphabet.size());
		if(scaled < 0){
			this.offset = alphabet.size() + scaled;
		}
		else
			this.offset = scaled;
	}
	
	private Character shift(char c){
		int index = alphabet.indexOf(c);
		return alphabet.get((index+offset)%alphabet.size());
	}
	
	private Character deshift(char c){
		int index = alphabet.indexOf(c);
		int shift = index - offset;
		shift = (shift >= 0) ? shift : alphabet.size() + shift ;
		return alphabet.get( shift%alphabet.size());
	}
	
	public String encode(String message){
		String encoding = "";
		message = message.toLowerCase();
		for(int i = 0; i < message.length(); i++){
			char c = message.charAt(i);
			if(c >= 'a' && c <= 'z')
				encoding += "" + shift(c);
		}
		return encoding;
	}
	
	public String decode(String message){
		String encoding = "";
		message = message.toLowerCase();
		for(int i = 0; i < message.length(); i++){
			char c = message.charAt(i);
			if(c >= 'a' && c <= 'z')
				encoding += "" + deshift(c);
		}
		
		return encoding;
	}
	
	public static void main(String[] args){
		System.out.print("Please choose a shift for the cipher: ");
		Scanner in = new Scanner(System.in);
		int shift = Integer.parseInt(in.nextLine());
		CaesarCipher cipher = new CaesarCipher(shift);
		
		while(true){
			System.out.print("Please enter new message: ");
			String input = in.nextLine();
			String encoding = cipher.encode(input);
			System.out.println(encoding);
			System.out.println(cipher.decode(encoding));
		}
	}
}