import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Caesar {
	private static final List<Character> alphabet = new ArrayList<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','k','l','m','n','o','p','q','r','s','t','u','v','w', 'x','y','z'));
	private final int offset;
	
	public Caesar(int offset){
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
		for(int i = 0; i < message.length(); i++)
			encoding += "" + shift(message.charAt(i));
		
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
	
	public static void main(String[] args) {
		Caesar cae = new Caesar(4);
		String en = cae.encode("Hello");
		System.out.println(en);
		System.out.println(cae.decode(en));
	}
}