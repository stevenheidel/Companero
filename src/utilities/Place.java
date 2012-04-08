/*
 * Jamie Gaultois
 * jpg627
 * 11066502
 * 
 * Steven Heidel
 * sdh951
 * 11078053
 */

package utilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Stores a place, which has a city and country. It can identify which city 
 * goes with which country, and even provides multiple options in some cases.
 * 
 * @author Jamie Gaultois and Steven Heidel
 *
 */
public class Place
{
	/**
	 * The stored city
	 */
	private String city;
	
	/**
	 * The stored country
	 */
	private LinkedList<String> country;
	
	/**
	 * List of all the cities in the world and their country/countries
	 */
	private static HashMap<String, LinkedList<String>> cities = null;

	/**
	 * List of all the countries in Latin America
	 */
	private static HashSet<String> laCountries = null;
	
	/**
	 * List of all the countries in the world
	 */
	private static HashSet<String> allCountries = null;
	
	/**
	 * Create a new place if you know the city and/or country
	 * @param theCity the city or null if not known
	 * @param theCountry the country or null if not known
	 */
	public Place(String theCity, String theCountry)
	{
		initialize(theCity, theCountry);
	}
	
	/**
	 * Create a new place just based on the name
	 * @param unknown a city or country
	 */
	public Place(String unknown)
	{
		buildCountries();
		buildCities();
		
		unknown = unknown.toUpperCase();
		
		if (allCountries.contains(unknown))
			initialize(null, unknown);
		else
			initialize(unknown, null);
	}
	
	/**
	 * Determine the country if not known
	 * @param theCity the city or null if not known
	 * @param theCountry the country or null if not known
	 */
	private void initialize(String theCity, String theCountry)
	{
		if (theCity == null)
			city = null;
		else
			city = theCity.toUpperCase();
		
		if (theCountry == null)
			country = findCountry(city);
		else
		{
			country = new LinkedList<String>();
			country.add(theCountry.toUpperCase());
		}
	}
	
	/**
	 * Find the country associated with a particular city
	 * @param theCity the city to search for
	 * @return the country that that city is in
	 */
	private LinkedList<String> findCountry(String theCity)
	{
		buildCountries();
		buildCities();
		
		return cities.get(theCity);
	}
	
	/**
	 * Build up a list of cities
	 */
	private void buildCities()
	{
		if(laCountries == null)
			buildCountries();
		if (cities != null)
			return;
		
		cities = new HashMap<String, LinkedList<String>>();
						
		for (String rawLine : FileReader.convertToStringArrayOfLines("data/structures/cities_countries.txt"))
		{
			String[] line = rawLine.split("\\|");
			
			if(line[0].equals("")) continue;
			
			if(laCountries.contains(line[1].toUpperCase()))
			{
				if(cities.containsKey(line[0].toUpperCase()))
				{
					LinkedList<String> temp = cities.get(line[0].toUpperCase());
					temp.add(line[1].toUpperCase());
					cities.put(line[0].toUpperCase(), temp);
				}
				else
				{
					LinkedList<String> temp = new LinkedList<String>();
					temp.add(line[1].toUpperCase());
					cities.put(line[0].toUpperCase(), temp);
				}
			}
			else
			{
				allCountries.add(line[1].toUpperCase());
			}
		}
	}
	
	/**
	 * Build up a list of countries
	 */
	private void buildCountries()
	{
		if (laCountries != null)
			return;
		
		laCountries = new HashSet<String>();
		allCountries = new HashSet<String>();
				
		for (String line : FileReader.convertToStringArrayOfLines("data/structures/latin_american_countries.txt"))
		{
			if (line.equals("")) 
				continue;
			
			laCountries.add(line.toUpperCase());
			allCountries.add(line.toUpperCase());
		}
	}
	
	/**
	 * Return the list of all cities
	 * @return all cities
	 */
	public static HashMap<String,LinkedList<String>> getCities()
	{
		return cities;
	}
	
	/**
	 * Return the list of all countries
	 * @return all countries
	 */
	public static HashSet<String> getCountries()
	{
		return laCountries;
	}
	
	/**
	 * Check if the place is a city or country
	 * @return whether or not the place has a city
	 */
	public boolean hasCity()
	{
		return (city != null);
	}
	
	/**
	 * Return the city
	 * @return the city
	 */
	public String getCity()
	{
		return city;
	}
	
	/**
	 * Check if the place is a city or country
	 * @return whether or not the place has a country
	 */
	public boolean hasCountry()
	{
		return (country != null);
	}
	
	/**
	 * Return the country/countries
	 * @return the country/countries
	 */
	public LinkedList<String> getCountry()
	{
		return country;
	}
	
	/**
	 * Print the city and country, or multiple separated by |
	 */
	public String toString()
	{
		String toReturn = "";
		
		if (country != null)
		{
			for (String s : country)
			{
				if (hasCity() && toReturn.equals(""))
				{
					toReturn += city + ", " + s;
				}
				else if(hasCity())
				{
					toReturn += " | " + city + ", " + s;
				}
				else
				{
					toReturn += s;
				}
			}
		}
		else if (hasCity()) 
			toReturn = city;
		
		return toReturn;
	}
	
	/**
	 * Test method
	 * @param args
	 */
	public static void main(String[] args)
	{
		Place both = new Place("San Salvador", "El Salvador");
		Place city = new Place("San Salvador", null);
		Place country = new Place(null, "El Salvador");
		Place unknown1 = new Place("Santiago");
		Place unknown2 = new Place("Chile");
		Place fake = new Place("Fake");
		
		System.out.println(both);
		System.out.println(city);
		System.out.println(country);
		System.out.println(unknown1);
		System.out.println(unknown2);
		System.out.println(fake);
	}
}