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

public class Parser 
{

	private static final String PATH_TO_STANFORD_PARSER = "/home/steven/stanford-parser-2012-03-09/";

	public static void main(String[] args)
	{
		Corpus corpus = new Corpus(FileReader.convertToString("corpus.txt"));

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
			for (String type : new String[]{"PCFG", "Factored"})
			{
				for (int i = 1; i <= corpus.getArticles().size(); i++)
				{
					ProcessBuilder pb = new ProcessBuilder("java", "-mx800m",
							"edu.stanford.nlp.parser.lexparser.LexicalizedParser",
							//"-outputFormat", "words,penn,typedDependencies",  
							"edu/stanford/nlp/models/lexparser/english" + type + ".ser.gz",
							"data/parser/original/" + i + ".txt");

					Map<String, String> env = pb.environment();
					env.put("CLASSPATH", PATH_TO_STANFORD_PARSER + "*:");

					Process p = pb.start();

					PrintWriter out = null;
					try {
						out = new PrintWriter("data/parser/" + type.toLowerCase() + "/" + i + ".txt");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

					String line;
					BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
					while ((line = bre.readLine()) != null) {
						System.out.println(line);
					}
					bre.close();

					BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while ((line = bri.readLine()) != null) 
					{
						out.println(line);
					}
					bri.close();
					out.close();

					p.waitFor();
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
