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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import entities.Article;
import entities.Corpus;
import utilities.FileReader;

/**
 * A class to do Natural Language Parsing.
 * 
 * @author Steven Heidel
 *
 */
public class Parser 
{
	/**
	 * Absolute path to the latest version of the Natural Language Parser
	 * created by Stanford University
	 */
	private static final String PATH_TO_STANFORD_PARSER = "/home/steven/stanford-parser-2012-03-09/";

	/**
	 * Loop through all the articles and create a separate file for them in 
	 * data/parser/original. Then, parse them in two separate ways, storing the
	 * results in two separate folders.
	 * @param args
	 */
	public static void main(String[] args)
	{
		Corpus corpus = new Corpus(FileReader.convertToString("data/structures/corpus.txt"));

		// make original files
		for (Entry<Integer, Article> a : corpus.getArticles().entrySet())
		{
			PrintWriter out = null;
			try {
				out = new PrintWriter("data/parser/original/" + a.getKey() + ".txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			out.print(a.getValue().getArticleText());
			out.close();
		}

		// parse with Stanford parser
		try 
		{
			// use both PCFG and Factored methods
			for (String type : new String[]{"PCFG", "Factored"})
			{
				// used to skip one or the other for debugging
				// if (type.equals("PCFG")) continue;
				
				for (int i = 1; i <= corpus.getArticles().size(); i++)
				{
					// create a new java process to run the Stanford NLP
					// make sure it has lots of memory!
					ProcessBuilder pb = new ProcessBuilder("java", "-mx2048m",
							"edu.stanford.nlp.parser.lexparser.LexicalizedParser",
							//"-outputFormat", "words,penn,typedDependencies",
							"-MAX_ITEMS", "2000000",
							"edu/stanford/nlp/models/lexparser/english" + type + ".ser.gz",
							"data/parser/original/" + i + ".txt");

					Map<String, String> env = pb.environment();
					env.put("CLASSPATH", PATH_TO_STANFORD_PARSER + "*:");

					Process p = pb.start();

					// write the error to the console
					String line;
					BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
					while ((line = bre.readLine()) != null) 
					{
						System.out.println(line);
					}
					bre.close();

					// write the regular output to a file
					PrintWriter out = null;
					try 
					{
						out = new PrintWriter("data/parser/" + type.toLowerCase() + "/" + i + ".txt");
					} 
					catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
					BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while ((line = bri.readLine()) != null) 
					{
						out.println(line);
					}
					bri.close();
					out.close();

					p.waitFor();
					
					// used to only process first article for debugging
					// break;
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
