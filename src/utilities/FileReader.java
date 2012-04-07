package utilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader 
{
	public static String convertToString(String filename) throws IOException
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
			throw new IOException("File not found");
		}
		
		return toReturn;
	}
}
