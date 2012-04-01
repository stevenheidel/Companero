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

public class Article 
{	
	private String source;
	
	private Date dateWritten;
	
	private String city;
	
	private String country;
	
	private String articleText;
	
	public Article (String source, Date dateWritten, String city, String country, String articleText)
	{
		this.source = source;
		this.dateWritten = dateWritten;
		this.city = city;
		this.country = country;
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
	
	public String getCity()
	{
		return city;
	}
	
	public void setCity(String newCity)
	{
		city = newCity;
	}
	
	public String getCountry()
	{
		return country;
	}
	
	public void setCountry(String newCountry)
	{
		country = newCountry;
	}
	
	public String getArticleText()
	{
		return articleText;
	}
	
	public void setArticleText(String newArticleText)
	{
		articleText = newArticleText;
	}
}
