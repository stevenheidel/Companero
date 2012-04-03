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
import java.util.LinkedList;

import utilities.Place;

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
		
		// Get rid of newlines in the corpus text
		corpusText = corpusText.replace("\n", " ");
		
		// Quintuple spaces however mean a new paragraph
		corpusText = corpusText.replace("     ", "\n");
		
		// Split the text into articles
		String[] articles = corpusText.split("DEV-MUC3-");
		
		// Create a new Article object for each article and add it to the HashMap
		for (String article : articles)
		{
			if(article.equals(""))
			{
				continue;
			}
			
			// Split out the article id
			String[] idSplit = article.split("\\(NOSC\\)", 2);
			
			// Split out the city
			String[] citySplit = idSplit[1].split(",", 2);
			
			// Find out if the country is given in the article
			String[] countrySplit = null;
			if(citySplit[0].split("\\(").length > 1)
			{
				citySplit = idSplit[1].split("\\(", 2);
				countrySplit = citySplit[1].split("\\)", 2);
			}
			
			// Split out the date written (source given)
			String[] dateWrittenSplit;
			if(countrySplit == null)
			{
				dateWrittenSplit = citySplit[1].split("--", 2);
			}
			else
			{
				String[] tempSplit = countrySplit[1].split(",", 2);
				dateWrittenSplit = tempSplit[1].split("--", 2);
			}
			
			// Parse out the date and determine if the source exists
			Date articleDate;
			Boolean sourceExists = false;
			String dateFormatString = "dd MMM yy";
			SimpleDateFormat df = new SimpleDateFormat(dateFormatString);
			try
			{
				articleDate = df.parse(dateWrittenSplit[0]);
				if (dateWrittenSplit[0].split("\\(").length > 1)
				{
					sourceExists = true;
				}
			}
			catch(ParseException e)
			{
				throw new IllegalArgumentException("Article was not in correct format.");
			}
			
			// Split out the source if it exists
			String[] sourceSplit = null;
			if(sourceExists)
			{
				String[] tempSplit = dateWrittenSplit[0].split("\\(");
				sourceSplit = tempSplit[1].split("\\)", 2);
			}
			
			// Create the Article and add it to the HashMap
			Article newArticle;
			
			String source, articleText;
			if(sourceExists)
			{
				source = sourceSplit[0].trim();
				articleText = dateWrittenSplit[1].trim();
			}
			else
			{
				source = "";
				articleText = dateWrittenSplit[1].trim();
			}
			
			if(countrySplit != null)
			{
				newArticle = new Article(source, articleDate, new Place(countrySplit[0].trim(), citySplit[0].trim()), articleText);
			}
			else
			{
				newArticle = new Article(source, articleDate, new Place(null, citySplit[0].trim()), articleText);
			}
			
			int id;
			try
			{
				id = Integer.parseInt(idSplit[0].trim());
			}
			catch(NumberFormatException e)
			{
				throw new IllegalArgumentException("Article was not in correct format.");
			}
			articleMap.put(id, newArticle);
		}
	}
	
	/**
	 * A method to return all of the articles contained in the corpus that contain the given text.
	 * @param text - The text to search the articles for.
	 * @return All of the articles containing the given text.
	 */
	public LinkedList<Article> getArticlesWithText (String text)
	{
		LinkedList<Article> articleList = new LinkedList<Article>();
		
		// Iterate through all of the articles in the HashMap and check if they contain the given text
		for(Article a : articleMap.values())
		{
			if(a.containsText(text))
			{
				articleList.add(a);
			}
		}
		
		return articleList;
	}
}
