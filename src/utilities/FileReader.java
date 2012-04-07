package utilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class FileReader 
{
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
