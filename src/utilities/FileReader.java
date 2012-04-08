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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * Static class providing some utility methods for working with files and more
 * specifically converting them into Strings.
 * 
 * @author Steven Heidel
 *
 */
public class FileReader 
{
	/**
	 * Read the entire file and convert it to a String
	 * @param filename the relative filename to read
	 * @return the entire contents of the file as a string
	 */
	public static String convertToString(String filename)
	{
		String toReturn = "";
		
		FileInputStream fis;
		BufferedReader br;
		
		try
		{
			fis = new FileInputStream(filename);
			br = new BufferedReader(new InputStreamReader(fis));
			
			String nextLine = br.readLine();
			while (nextLine != null)
			{
				toReturn += nextLine;
				toReturn += "\n";
				nextLine = br.readLine();
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return toReturn;
	}
	
	/**
	 * Read the entire file and convert it to a String array with each entry
	 * being a line in the original file
	 * @param filename the relative filename to read
	 * @return the entire contents of the file as a string array
	 */
	public static String[] convertToStringArrayOfLines(String filename)
	{
		LinkedList<String> toReturn = new LinkedList<String>();
		
		FileInputStream fis;
		BufferedReader br;
		
		try
		{
			fis = new FileInputStream(filename);
			br = new BufferedReader(new InputStreamReader(fis));
			
			String nextLine = br.readLine();
			while (nextLine != null)
			{
				toReturn.add(nextLine);
				nextLine = br.readLine();
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return toReturn.toArray(new String[1]);
	}
}
