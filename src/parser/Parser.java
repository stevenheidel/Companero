/*
 * Jamie Gaultois
 * jpg627
 * 11066502
 * 
 * Steven Heidel
 * sdh951
 * 11078053
 */

package parser;

import java.util.LinkedList;

import utilities.FileReader;

/**
 * Description of class
 * 
 * @author Steven Heidel
 *
 */
public class Parser 
{
	public static LinkedList<String> getNounPhrases(int articleID)
	{
		String factored = FileReader.convertToString("data/parser/factored/" + articleID + ".txt");
		// DEBUG: fall back to PCFG
		// String pcfg = FileReader.convertToString("data/parser/pcfg/" + articleID + ".txt");
		
		// put a space between double right brackets
		factored = factored.replaceAll("\\)\\)", "\\) \\)").replaceAll("\\)\\)", "\\) \\)");
		
		String[] tokens = factored.split("\\s+");
		
		LinkedList<String> nounPhrases = new LinkedList<String>();
		
		for (int i = 0; i < tokens.length; i++)
		{
			if (tokens[i].equals("(NP"))
			{
				int bracketCount = 1;
				String temp = "";
				
				for (int j = i + 1; bracketCount > 0; j++)
				{
					if (tokens[j].startsWith("("))
					{
						bracketCount += 1;
					}
					else if (tokens[j].endsWith(")"))
					{
						bracketCount -= 1;
						
						if (tokens[j].length() > 1)
						{
							temp += tokens[j].replaceAll("\\)", " ");
						}
					}
				}
				
				nounPhrases.add(temp.trim());
			}
		}
		
		return nounPhrases;
	}
	
	/**
	 * Test method
	 * @param args
	 */
	public static void main(String[] args)
	{
		for (String s : getNounPhrases(100))
		{
			System.out.println(s);
		}
	}
}
