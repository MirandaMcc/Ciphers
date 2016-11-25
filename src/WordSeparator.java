import java.util.ArrayList;
import java.util.List;
import java.math.*;
/**
 * Singleton WordSeparator class adjusted from:
 * http://stackoverflow.com/questions/8870261/how-to-split-text-without-spaces-into-list-of-words
 */
public class WordSeparator {
	private static final Dictionary dict = Dictionary.getInstance();
	private static final WordSeparator separator = new WordSeparator();
	
	private WordSeparator(){}
	
	public static WordSeparator getInstance(){
		return separator;
	}
	
	private double[] bestMatch(String s, int n, List<Double> cost){
		 //tuple array of {cost,length} of best match
		List<Double> possible = new ArrayList<Double>();
		int length = Math.max(0,n-dict.longestLength());
		for(int i = n; i >= length; i--){
			possible.add(cost.get(i));
		}
		double min = Double.MAX_VALUE;
		int longest = 0;
		for(int j = 0; j < possible.size(); j++){
			double c = dict.importance(s.substring(Math.max(n-j-1,0),Math.max(n-1,0)));
			double val = Math.min(c + possible.get(j), j+1);
			if( val < min)
			{
				min = val;
				longest = j;
			}
		}
		double[] match = {min,longest};
		return match;
	}
	
	private List<Double> calculateCosts(String s){
		List<Double> cost = new ArrayList<Double>();
		cost.add(0.0);
		for(int i = 0; i < s.length(); i++){
			double[] match = bestMatch(s,i,cost);
			cost.add(match[0]);
		}
		return cost;
	}
	
	public String determineWords(String noSpaces){
		String withSpaces = "";
		char[] charsArr = noSpaces.toCharArray();
		List<String> chars = new ArrayList<String>();
		for(char c : charsArr){
			chars.add("" + c);
		}
		List<Double> cost = calculateCosts(noSpaces);
		for(int i = chars.size(); i > 0; i--){
			double[] match = bestMatch(noSpaces,i,cost);
			if(cost.get(i) == match[0])
			{
				String word = noSpaces.substring(i-(int)match[1],i);
				withSpaces = word + " " + withSpaces;
			}		
		}
		return withSpaces;
	}
}
