/**
 * Jamie Gaultois
 * jpg627
 * 11066502
 * 
 * Steven Heidel
 * sdh951
 * 11078053
 * 
 * Class to hold an individual article. Will store the article's source, date, city,
 * and country.
 */

package corpus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.Main;

import utilities.Place;

public class Article 
{	
	private String source;
	
	private Date dateWritten;
	
	private Place locationWritten;
	
	private String articleText;
	
	public Article (String source, Date dateWritten, Place location, String articleText)
	{
		this.source = source;
		this.dateWritten = dateWritten;
		this.locationWritten = location;
		this.articleText = articleText;
	}
	
	public String getSource()
	{
		return source;
	}
	
	public void setSource(String newSource)
	{
		source = newSource;
	}
	
	public Date getDateWritten()
	{
		return dateWritten;
	}
	
	public void setDateWritten(Date newDateWritten)
	{
		dateWritten = newDateWritten;
	}
	
	public Place getLocationWritten()
	{
		return locationWritten;
	}
	
	public void setLocationWritten(Place newLocation)
	{
		locationWritten = newLocation;
	}
	
	public String getArticleText()
	{
		return articleText;
	}
	
	public void setArticleText(String newArticleText)
	{
		articleText = newArticleText;
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
	 * A method to extract all dates from the article text.
	 * @return A list of the dates in the article in String format.
	 */
	public LinkedList<String> getDatesFromText()
	{
		LinkedList<String> dateList = new LinkedList<String>();
		
		String pattern = "(\\b[0-9]|[1-2][0-9]|3[0-1])?" +
				"[ ]+(JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER)" +
				"[ ]+([1-2]?[0-9]?[0-9][0-9])?";
		Matcher m = Pattern.compile(pattern).matcher(articleText);
		
		while(m.find())
		{
			String date = m.group(0);
			String[] dateSplit = date.split(" ");
			boolean hasYear = true;
			
			try
			{
				Integer.parseInt(dateSplit[dateSplit.length-1]);
			}
			catch (NumberFormatException e)
			{
				hasYear = false;
			}
			
			// If there was no year in the article, add one based on which month was written about
			if(!hasYear)
			{
				String dateFormatString = "MMM";
				SimpleDateFormat df = new SimpleDateFormat(dateFormatString);
				Date findYear = null;
				try
				{
					findYear = df.parse(dateSplit[1]);
					if(findYear.getMonth() > dateWritten.getMonth())
					{
						date += (dateWritten.getYear() - 1) + "(yearadded)";
					}
					else
					{
						date += dateWritten.getYear() + "(yearadded)";
					}
				}
				catch(ParseException e)
				{
					System.out.println("Error parsing the date.");
				}				
			}
			
			dateList.add(date);
		}
		
		return dateList;
	}
	
	/**
	 * Test method
	 */
	public static void main(String[] argv)
	{
		String corpus = "";
		try
		{
			corpus = Main.readTextFile("corpus.txt");
		}
		catch (Exception e)
		{
			System.out.println("Error reading from the file");
		}
		
		Corpus corp = new Corpus(corpus);
		
		LinkedList<Article> articles = corp.getArticlesWithText("THE");
		
		for (Article art : articles)
		{
			LinkedList<String> dates = art.getDatesFromText();
			
			for (String s : dates)
			{
				System.out.println(s);
			}
		}
	}
}
