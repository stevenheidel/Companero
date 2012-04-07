/**
 * Jamie Gaultois
 * jpg627
 * 11066502
 * 
 * Steven Heidel
 * sdh951
 * 11078053
 * 
 * The class which will hold the HashSet of words which will serve as our dictionary.
 */

package utilities;

import java.util.HashSet;

public class WordDictionary 
{
	private static HashSet<String> dictionary = null;
	
	private static void initialize()
	{
		// If the dictionary is already initialized for some reason, return
		if (dictionary != null)
			return;
		
		dictionary = new HashSet<String>();
				
		// Read all of the words from the file, ignoring words starting with capital letters
		for (String line : FileReader.convertToStringArrayOfLines("words"))
		{
			if (!line.equals("") && !Character.isUpperCase(line.charAt(0)))
			{
				// Add the word in all upper case, as that is how the corpus is represented
				dictionary.add(line.toUpperCase());
			}
		}
	}
	
	public static boolean contains(String word)
	{
		if (dictionary == null)
			initialize();
		
		return dictionary.contains(word.toUpperCase());
	}
	
	public static void main(String[] args)
	{
		System.out.println(WordDictionary.contains("Test"));
		System.out.println(WordDictionary.contains("Steven"));
	}
}