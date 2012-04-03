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

import java.util.Date;
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
}
