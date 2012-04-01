/**
 * Jamie Gaultois
 * jpg627
 * 11066502
 * 
 * Steven Heidel
 * sdh951
 * 11078053
 * 
 * The class which will hold all of the articles given in the corpus.
 */

package corpus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Date;

public class Corpus 
{
	HashMap<Integer, Article> articleMap;
	
	/**
	 * Constructor for the Corpus class. Takes in a String of corpusText, splitting it into articles.
	 * @param corpusText - The text with the articles
	 * @throws IllegalArgumentException if the text is formatted incorrectly.
	 */
	public Corpus (String corpusText) throws IllegalArgumentException
	{
		// Create the articleMap
		articleMap = new HashMap<Integer, Article>();
		
		// Split the text into articles
		String[] articles = corpusText.split("DEV-MUC3-");
		
		// Create a new Article object for each article and add it to the HashMap
		for (String article : articles)
		{
			// Split out the article id
			String[] idSplit = article.split("(NOSC)", 2);
			
			// Split out the city
			String[] citySplit = idSplit[1].split(",", 2);
			
			// Find out if the country is given in the article
			String[] countrySplit = null;
			if(citySplit[0].split("(").length > 1)
			{
				citySplit = idSplit[1].split("(", 2);
				countrySplit = citySplit[1].split(")", 2);
			}
			
			// Split out the date written (source given)
			String[] dateWrittenSplit;
			if(countrySplit == null)
			{
				dateWrittenSplit = citySplit[1].split("(", 2);
			}
			else
			{
				dateWrittenSplit = countrySplit[1].split("),", 2);
			}
			
			// Try to parse this date, and if it doesn't parse, split on --
			Date articleDate;
			Boolean sourceExists;
			String dateFormatString = "dd MMM yy";
			SimpleDateFormat df = new SimpleDateFormat(dateFormatString);
			try
			{
				articleDate = df.parse(dateWrittenSplit[0]);
				sourceExists = true;
			}
			catch(ParseException e)
			{
				sourceExists = false;
				dateWrittenSplit = citySplit[1].split("--", 2);
				try
				{
					articleDate = df.parse(dateWrittenSplit[0]);
				}
				catch(ParseException e2)
				{
					throw new IllegalArgumentException("Article was not in correct format.");
				}
			}
			
			// Split out the source if it exists
			String[] sourceSplit = null;
			if(sourceExists)
			{
				sourceSplit = dateWrittenSplit[1].split(") --", 2);
			}
			
			// Create the Article and add it to the HashMap
			if(sourceExists)
			{
				Article newArticle;
				if(countrySplit != null)
				{
					newArticle = new Article(sourceSplit[0], articleDate, citySplit[0], countrySplit[0], sourceSplit[1]);
				}
				else
				{
					newArticle = new Article(sourceSplit[0], articleDate, citySplit[0], "Japan", sourceSplit[1]);
				}
				
				int id;
				try
				{
					id = Integer.parseInt(idSplit[0]);
				}
				catch(NumberFormatException e)
				{
					throw new IllegalArgumentException("Article was not in correct format.");
				}
				articleMap.put(id, newArticle);
			}
		}
	}
}
