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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

public class WordDictionary 
{
	private HashSet<String> dictionary = null;
	
	private void initialize()
	{
		// If the dictionary is already initialized for some reason, return
		if(dictionary != null)
		{
			return;
		}
		
		FileInputStream fis;
		BufferedReader br;
		
		// Read all of the words from the file, ignoring words starting with capital letters
		try
		{
			fis = new FileInputStream("words");
			br = new BufferedReader(new InputStreamReader(fis));
			
			String nextLine = br.readLine();
			int i = 0;
			while (nextLine != null)
			{
				if(!nextLine.equals("") && !Character.isUpperCase(nextLine.charAt(0)))
				{
					// Add the word in all upper case, as that is how the corpus is represented
					dictionary.add(nextLine.toUpperCase());
				}
				nextLine = br.readLine();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getStackTrace());
		}
	}
	
	public boolean contains(String givenWord)
	{
		if(dictionary == null)
		{
			initialize();
		}
		
		return dictionary.contains(givenWord);
	}
}