import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.math.*;

/**
 * Singleton adaptation of simplified English dictionary
 */
public class Dictionary
{
	private static final Dictionary dict = new Dictionary(); 
    private Map<String,Double> wordImportance; //map to word and frequency
    private final int maxLength;
    
    private Dictionary() 
    {
    	int maxLength = 0;
    	try{
	        Path path = Paths.get("C:\\Users\\Miranda McClellan\\Dropbox (MIT)\\workspace\\Ciphers\\src\\words.txt");
	        byte[] readBytes = Files.readAllBytes(path);
	        String wordListContents = new String(readBytes, "UTF-8");
	        String[] words = wordListContents.split("\n");
	        wordImportance = new HashMap<String,Double>();
	        int index = 0;
	        for(String word : words){
	        	wordImportance.put(word,Math.log10((index+1)*Math.log10(words.length)));
	        	maxLength = (word.length() > maxLength) ? word.length(): maxLength;
	        }
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    	this.maxLength = maxLength;
    }
    
    /**
     * Allows others to use singleton Dictionary
     * @return singleton Dictionary instance
     */
    public static Dictionary getInstance(){
    	return dict;
    }

    /**
     * If word is a word, return its relative frequency
     * @param word to look up
     * @return frequency of word
     */
    public double importance(String word)
    {
        return wordImportance.containsKey(word) ? wordImportance.get(word): Double.MAX_VALUE ;
    }
    
    public int longestLength(){
    	return maxLength;
    }
}
