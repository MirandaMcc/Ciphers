import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class FourSquareCipher {

	private static final List<Character> alphabet = new ArrayList<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w', 'x','y','z'));
	private char[][] one, two, three, four;
	
	public FourSquareCipher(String keyOne, String keyTwo){
		this.one = new char[5][5];
		this.four = new char[5][5];
		fill(two, keyOne);
		fill(three, keyTwo);
	}
	
	private void fill(char[][] arr, String key){
		map = new HashMap<Character,int[]>();
		Set<Character> used = new HashSet<Character>();
		int n = 0;
		for(char c: key){
			c = (c == 'j') ? 'i' : c; //replace all i's with j's
			if(c >= 'a' && c <= 'z' && used.add(c)){
				int[] index = {n/5,n%5};
				
				map.put(c, index);
				arr[n/5][n%5] = c;
				n++;
			}
		}
		Set<Character> unused = setDiff(alphabet,used);
		for(Character c : unused){
			int[] index = {n/5,n%5};
			map.put(c, index);
			arr[n/5][n%5] = c;
			n++;
		}
	}
	
	private Character shift(char c, int i){
		int shift = (int) (Math.random()*26);
		this.key.add(shift); //shift for character at index i in msg stored at index i in key
		
		int m = alphabet.indexOf(c);
		return alphabet.get((m+shift)%alphabet.size());
	}
	
	private Character deshift(char c,int i){
		int e = alphabet.indexOf(c);
		int shift = key.get(i);
		int index = e-shift;
		index = (index >= 0) ? index : alphabet.size() + index ;
		return alphabet.get(index%alphabet.size());
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
			String encoding = cipher.encode(input);
			System.out.println(encoding);
			System.out.println(cipher.decode(encoding));
		}
	}

}
