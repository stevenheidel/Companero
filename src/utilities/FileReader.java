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
import java.io.InputStream;
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
	 * Overly complex method due to the weird way in which Java/Eclipse
	 * handle internal resources.
	 * @param filename The *relative path* to the file
	 * @return a BufferedReader to read through the file line by line
	 * @throws FileNotFoundException 
	 */
	private static BufferedReader getBufferedReader(String filename)
	{
		BufferedReader br = null;
		
		try
		{
			// this will work if we have exported to a .jar file
			InputStream is = null;
			try 
			{
				is = ClassLoader.getSystemResource(filename).openStream();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			br = new BufferedReader(new InputStreamReader(is));
		}
		catch (NullPointerException e)
		{
			// otherwise this will work in Eclipse
			FileInputStream fis = null;
			try 
			{
				 fis = new FileInputStream(filename);
			} 
			catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			br = new BufferedReader(new InputStreamReader(fis));
		}
		
		return br;
	}
	
	/**
	 * Read the entire file and convert it to a String
	 * @param filename the relative filename to read
	 * @return the entire contents of the file as a string
	 */
	public static String convertToString(String filename)
	{
		String toReturn = "";
				
		try
		{
			BufferedReader br = getBufferedReader(filename);
			
			String nextLine = br.readLine();
			while (nextLine != null)
			{
				toReturn += nextLine;
				toReturn += "\n";
				nextLine = br.readLine();
			}
		}
		catch (IOException e) 
		{
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
		
		try
		{
			BufferedReader br = getBufferedReader(filename);
			
			String nextLine = br.readLine();
			while (nextLine != null)
			{
				toReturn.add(nextLine);
				nextLine = br.readLine();
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return toReturn.toArray(new String[1]);
	}
}
