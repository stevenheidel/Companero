package utilities;

public class Place
{
	private String city;
	
	private String country;
	
	public Place(String theCountry, String theCity)
	{
		city = theCity;
		
		if (country == null)
			country = findCountry(theCity);
		else
			country = theCountry;
	}
	
	public Place(String unknown)
	{
		// determine if city or country
	}
	
	private String findCountry(String theCity)
	{
		// search the CSV file
		
		return "Japan";
	}
	
	public boolean hasCity()
	{
		return (city != null);
	}
	
	public String getCity()
	{
		return city;
	}
	
	public String getCountry()
	{
		return country;
	}
}