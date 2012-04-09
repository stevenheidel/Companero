/*
 * Jamie Gaultois
 * jpg627
 * 11066502
 * 
 * Steven Heidel
 * sdh951
 * 11078053
 */

package entities;

import java.util.HashMap;
import java.util.LinkedList;

import utilities.FileReader;
import utilities.Place;
import utilities.Time;

/**
 * The class which will hold all of the articles given in the corpus.
 * 
 * @author Jamie Gaultois
 *
 */
public class Corpus 
{
	/**
	 * The list of all the articles in the corpus
	 */
	HashMap<Integer, Article> articleMap;
	
	/**
	 * Constructor for the Corpus class. Takes in a String of corpusText, 
	 * splitting it into articles.
	 * @param corpusText - The text with the articles
	 * @throws IllegalArgumentException if the text is formatted incorrectly.
	 */
	public Corpus(String corpusText) throws IllegalArgumentException
	{
		// create the articleMap
		articleMap = new HashMap<Integer, Article>();
		
		// get rid of newlines in the corpus text
		corpusText = corpusText.replace("\n", " ");
		
		// five spaces however mean a new paragraph
		corpusText = corpusText.replace("     ", "\n");
		
		// split the text into articles
		String[] articles = corpusText.split("DEV-MUC3-");
		
		// create a new Article object for each article and add it to the HashMap
		for (String article : articles)
		{
			if (article.equals(""))
			{
				continue;
			}
			
			// split out the article id
			String[] idSplit = article.split("\\(NOSC\\)", 2);
			
			// split out the city
			String[] citySplit = idSplit[1].split(",", 2);
			
			// find out if the country is given in the article
			String[] countrySplit = null;
			if (citySplit[0].split("\\(").length > 1)
			{
				citySplit = idSplit[1].split("\\(", 2);
				countrySplit = citySplit[1].split("\\)", 2);
			}
			
			// split out the date written (source given)
			String[] dateWrittenSplit;
			if (countrySplit == null)
			{
				dateWrittenSplit = citySplit[1].split("--", 2);
			}
			else
			{
				String[] tempSplit = countrySplit[1].split(",", 2);
				dateWrittenSplit = tempSplit[1].split("--", 2);
			}
			
			// parse out the date and determine if the source exists
			Time articleTime = null;
			Boolean sourceExists = false;
			
			articleTime = new Time(dateWrittenSplit[0]);
			if (dateWrittenSplit[0].split("\\(").length > 1)
			{
				sourceExists = true;
			}
			
			// split out the source if it exists
			String[] sourceSplit = null;
			if(sourceExists)
			{
				String[] tempSplit = dateWrittenSplit[0].split("\\(");
				sourceSplit = tempSplit[1].split("\\)", 2);
			}
			
			// create the Article and add it to the HashMap
			Article newArticle;
			
			String source, articleText;
			if (sourceExists)
			{
				source = sourceSplit[0].trim();
				articleText = dateWrittenSplit[1].trim();
			}
			else
			{
				source = "";
				articleText = dateWrittenSplit[1].trim();
			}
			Place place;
			if (countrySplit != null)
			{
				place = new Place(citySplit[0].trim(), countrySplit[0].trim());
				newArticle = new Article(source, articleTime, place, articleText);
			}
			else
			{
				place = new Place(citySplit[0].trim(), null);
				newArticle = new Article(source, articleTime, place, articleText);
			}
			
			int id;
			try
			{
				id = Integer.parseInt(idSplit[0].trim());
			}
			catch (NumberFormatException e)
			{
				throw new IllegalArgumentException("Article was not in correct format.");
			}
			articleMap.put(id, newArticle);
		}
	}
	
	/**
	 * Return the list of articles
	 * @return the list of articles
	 */
	public HashMap<Integer, Article> getArticles()
	{
		return articleMap;
	}
	
	/**
	 * A method to return all of the articles contained in the corpus that contain the given text.
	 * @param text - The text to search the articles for.
	 * @return All of the articles containing the given text.
	 */
	public LinkedList<Article> getArticlesWithText(String text)
	{
		LinkedList<Article> articleList = new LinkedList<Article>();
		
		// iterate through all of the articles in the HashMap and check if they contain the given text
		for (Article a : articleMap.values())
		{
			if (a.containsText(text))
			{
				articleList.add(a);
			}
		}
		
		return articleList;
	}
	
	/**
	 * Test method
	 * @param args
	 */
	public static void main(String[] args)
	{		
		Corpus corpus = new Corpus(FileReader.convertToString("data/structures/corpus.txt"));
		
		for (Article a : corpus.getArticlesWithText("ACCORDING TO RETIRED GENERAL FERNANDO TORRES SILVA"))
		{
			System.out.println(a);
			System.out.println(a.getLocationWritten());
			System.out.println(a.getTimeWritten());
		}
	}
}
