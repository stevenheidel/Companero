/*
 * Jamie Gaultois
 * jpg627
 * 11066502
 * 
 * Steven Heidel
 * sdh951
 * 11078053
 */

package utilities;

import java.util.HashSet;

/**
 * A static class which will hold the HashSet of words which will serve as our 
 * dictionary. The words list comes from /usr/local/dict/words on Ubuntu
 * 
 * @author Jamie Gaultois
 *
 */
public class WordDictionary 
{
	/**
	 * A set to store all the words
	 */
	private static HashSet<String> dictionary = null;
	
	/**
	 * Initialize the dictionary by reading in the file and storing all the 
	 * words in the set. This is surprisingly fast.
	 */
	private static void initialize()
	{
		// if the dictionary is already initialized for some reason, return
		if (dictionary != null)
			return;
		
		dictionary = new HashSet<String>();
				
		// read all of the words from the file
		// ignore words starting with capital letters, they are proper nouns
		for (String line : FileReader.convertToStringArrayOfLines("data/words.txt"))
		{
			if (!line.equals("") && !Character.isUpperCase(line.charAt(0)))
			{
				// add the word in all upper case, as that is how the corpus is represented
				dictionary.add(line.toUpperCase());
			}
		}
	}
	
	/**
	 * Check if the dictionary contains a particular word
	 * @param word the case insensitive word to look up
	 * @return whether or not the word is in the dictionary
	 */
	public static boolean contains(String word)
	{
		if (dictionary == null)
			initialize();
		
		return dictionary.contains(word.toUpperCase());
	}
	
	/**
	 * Test method
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println(WordDictionary.contains("Test"));
		System.out.println(WordDictionary.contains("Steven"));
	}
}