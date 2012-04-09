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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import solver.Heuristics;
import utilities.FileReader;
import utilities.Place;
import utilities.Time;

/**
 * Class to hold an individual article. Will store the article's source, date, 
 * city, and country.
 * 
 * @author Jamie Gaultois and Steven Heidel
 *
 */
public class Article
{	
	/**
	 * The ID of the article
	 */
	private int id;
	
	/**
	 * The news source of the article, not everyone has one
	 */
	private String source;
	
	/**
	 * The date cited at the beginning of the article
	 */
	private Time timeWritten;
	
	/**
	 * The location cited at the beginning of the article
	 */
	private Place locationWritten;
	
	/**
	 * The body of the article
	 */
	private String articleText;
	
	/**
	 * The body of the article without punctuation
	 */
	private String articleTextNoPunct;
	
	/**
	 * Names of places contained within the article text
	 */
	private LinkedList<Place> places;
	
	/**
	 * Times contained within the article text
	 */
	private LinkedList<Time> times;
	
	/**
	 * Create a new Article
	 * @param source the news source
	 * @param location the location written
	 * @param articleText the article text
	 * @param dateWritten the date written
	 */
	public Article(int id, String source, Place location, Time timeWritten, String articleText)
	{
		this.id = id;
		this.source = source;
		this.timeWritten = timeWritten;
		this.locationWritten = location;
		this.articleText = articleText;
		this.articleTextNoPunct = this.articleText.replaceAll("[^A-Z0-9]", " ");
		
		places = new LinkedList<Place>();
		
		// TODO: correct for places within other places, like san salvador
		// look for cities and countries in the text, ignoring any blank ones
		for (String s : Place.getCities().keySet())
		{
			if (this.articleTextNoPunct.contains(" " + s + " ") && !s.equals(""))
			{
				places.add(new Place(s));
			}
		}
		
		for (String s : Place.getCountries())
		{
			if (this.articleTextNoPunct.contains(" " + s + " ") && !s.equals(""))
			{
				places.add(new Place(s));
			}
		}
	}
	
	/**
	 * Return the ID
	 * @return the ID
	 */
	public int getID()
	{
		return id;
	}
	
	/**
	 * Return the source
	 * @return the source
	 */
	public String getSource()
	{
		return source;
	}
	
	/**
	 * Return the time written
	 * @return the time written
	 */
	public Time getTimeWritten()
	{
		return timeWritten;
	}
	
	/**
	 * Return the location written
	 * @return the location written
	 */
	public Place getLocationWritten()
	{
		return locationWritten;
	}
	
	/**
	 * Return the article text
	 * @return the article text
	 */
	public String getArticleText()
	{
		return articleText;
	}
	
	/**
	 * Return the places within the article
	 * @return the places within the article
	 */
	public LinkedList<Place> getPlaces()
	{
		return places;
	}
	
	/**
	 * A method to determine whether this article contains a certain string of text.
	 * @param text - The text we're hoping to find in the article.
	 * @return True if the text is contained in the article, false otherwise.
	 */
	public boolean containsText(String text)
	{
		return articleText.contains(text);
	}
	
	/**
	 * A method to determine whether this article contains a certain date
	 * within the text of the article itself.
	 * @param date - The date that we want to find.
	 * @return	True if the article contains the date, false otherwise.
	 */
	public boolean containsDate(String date)
	{
		Time givenDate = new Time(date);
						
		LinkedList<String> dates = getDatesFromText();
		
		for (String s : dates)
		{
			Time dateFromText = new Time(s);

			if (dateFromText.equals(givenDate))
				return true;
		}
		
		return false;
	}
	
	/**
	 * A method to determine whether this article contains a certain place.
	 * @param toFind - The place that we want to find.
	 * @return	True if the article contains the place, false otherwise.
	 */
	public boolean containsPlace(Place toFind)
	{
		for (Place p : places)
			if (p.equals(toFind))
				return true;
				
		return false;
	}
	
	/**
	 * A method to extract all dates from the article text.
	 * @return A list of the dates in the article in String format.
	 */
	public LinkedList<String> getDatesFromText()
	{
		LinkedList<String> dateList = new LinkedList<String>();
		
		// regular expression to match dates in the article formatted like the below examples:
		// 5 JANUARY 89 or 5 JANUARY or JANUARY 89. Could also give 4 digit years 
		String pattern = "(\\b[0-9]|[1-2][0-9]|3[0-1])?(-[0-9]|[1-2][0-9]|3[0-1])?" +
				"[ ]+(JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER)" +
				"[ ]?+([1-2]?[0-9]?[8-9][0-9])?(.{9})";
		Matcher m = Pattern.compile(pattern).matcher(articleText);
		
		while (m.find())
		{
			String date = m.group(0);
			String[] dateSplit = date.split(" ");
			boolean hasYear = true;
			boolean hasDay = true;
			
			// check that we didn't parse 5 NOVEMBER DAM or 19 APRIL MOVEMENT
			if (date.contains("MOVEMENT") || date.contains("DAM"))
			{
				continue;
			}
			
			try
			{
				Integer.parseInt(dateSplit[0]);
			}
			catch (NumberFormatException e)
			{
				hasDay = false;
			}
			
			try
			{
				Integer.parseInt(dateSplit[2]);
				date = dateSplit[0] + " " + dateSplit[1] + " " + dateSplit[2];
			}
			catch (NumberFormatException e)
			{
				hasYear = false;
				date = dateSplit[0] + " " + dateSplit[1] + " ";
			}
			
			// ensure that the day or the year are present
			if (!(hasYear || hasDay))
			{
				continue;
			}
			
			// if there was no year in the article, add one based on which month was written about
			if (!hasYear)
			{
				String dateFormatString = "MMM";
				SimpleDateFormat df = new SimpleDateFormat(dateFormatString);
				Date findYear = null;
				try
				{
					findYear = df.parse(dateSplit[1]);
					
					Calendar findYearCal = new GregorianCalendar();
					findYearCal.setTime(findYear);
					
					Calendar timeWrittenCal = new GregorianCalendar();
					timeWrittenCal.setTime(timeWritten.getDate());
					
					if(findYearCal.get(Calendar.MONTH) > timeWrittenCal.get(Calendar.MONTH) + 2)
					{
						date += (timeWrittenCal.get(Calendar.YEAR) - 1) + "(yearadded)";
					}
					else
					{
						date += timeWrittenCal.get(Calendar.YEAR) + "(yearadded)";
					}
				}
				catch (ParseException e)
				{
					System.out.println("Error parsing the date.");
				}				
			}
			
			dateList.add(date);
		}
		
		return dateList;
	}
	
	/**
	 * Convert to a string, which is just the article text
	 */
	public String toString()
	{
		return articleText;
	}
	
	/**
	 * Test method
	 * @param args
	 */
	public static void main(String[] argv)
	{
		Corpus corpus = new Corpus(FileReader.convertToString("data/structures/corpus.txt"));
		
		LinkedList<Article> articles = corpus.getArticlesWithText("THE");
		
		System.out.println(articles.get(4));
		for (Place p : articles.get(4).getPlaces())
			System.out.println(p);
	}
}