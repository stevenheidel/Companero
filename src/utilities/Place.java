package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Place
{
	private String city;
	
	private String country;
	
	private static HashMap<String, String> cities = null;
	
	private static HashSet<String> countries = null;
	
	public Place(String theCity, String theCountry)
	{
		initialize(theCity, theCountry);
	}
	
	public Place(String unknown)
	{
		buildStructures();
		
		unknown = unknown.toUpperCase();
		
		if (countries.contains(unknown))
			initialize(null, unknown);
		else
			initialize(unknown, null);
	}
	
	private void initialize(String theCity, String theCountry)
	{
		if (theCity == null)
			city = null;
		else
			city = theCity.toUpperCase();
		
		if (theCountry == null)
			country = findCountry(city);
		else
			country = theCountry.toUpperCase();
	}
	
	private String findCountry(String theCity)
	{
		buildStructures();
		
		return cities.get(theCity);
	}
	
	private void buildStructures()
	{
		if (cities != null && countries != null)
			return;
		
		cities = new HashMap<String, String>();
		countries = new HashSet<String>();
		
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(new File("cities_countries.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while (scanner.hasNextLine())
		{
			String[] line = scanner.nextLine().split("\\|");
			
			cities.put(line[0].toUpperCase(), line[1].toUpperCase());
			countries.add(line[1].toUpperCase());
		}
	}
	
	public static HashMap<String,String> getCities()
	{
		if(cities == null)
		{
			Place place = new Place("Saskatoon");
		}
		return cities;
	}
	
	public static HashSet<String> getCountries()
	{
		if(countries == null)
		{
			Place place = new Place("Saskatoon");
		}
		return countries;
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
	
	public String toString()
	{
		if (hasCity())
			return city + ", " + country;
		else
			return country;
	}
	
	public static void main(String[] args)
	{
		Place both = new Place("Saskatoon", "Canada");
		Place city = new Place("Saskatoon", null);
		Place country = new Place(null, "Canada");
		Place unknown1 = new Place("Saskatoon");
		Place unknown2 = new Place("Canada");
		Place fake = new Place("Fake");
		
		System.out.println(both);
		System.out.println(city);
		System.out.println(country);
		System.out.println(unknown1);
		System.out.println(unknown2);
		System.out.println(fake);
	}
}