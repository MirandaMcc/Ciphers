import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Singleton version of dictionary from:
 * http://stackoverflow.com/questions/11607270/how-to-check-whether-given-string-is-a-word
 */
public class Dictionary
{
	private static final Dictionary dict = new Dictionary(); 
    private Set<String> wordsSet;

    private Dictionary() 
    {
    	try{
	        Path path = Paths.get("words.txt");
	        byte[] readBytes = Files.readAllBytes(path);
	        String wordListContents = new String(readBytes, "UTF-8");
	        String[] words = wordListContents.split("\n");
	        wordsSet = new HashSet<>();
	        Collections.addAll(wordsSet, words);
    	}
    	catch(IOException e){
    		System.err.println(e.getStackTrace());
    	}
    }
    
    public static Dictionary getInstance(){
    	return dict;
    }

    public boolean contains(String word)
    {
        return wordsSet.contains(word);
    }
}
