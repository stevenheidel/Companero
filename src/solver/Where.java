/*
 * Jamie Gaultois
 * jpg627
 * 11066502
 * 
 * Steven Heidel
 * sdh951
 * 11078053
 */

package solver;

import java.util.LinkedList;

import main.Main;

import utilities.Place;
import entities.Answer;
import entities.Article;

/**
 * TODO: Description of implementation
 * 
 * @author Jamie Gaultois
 *
 */
public class Where {
	/**
	 * Description of method
	 * @param art
	 * @param text
	 * @return
	 */
	private static Place getPlaceForText(Article art, String text)
	{
		String[] cities = null;
		String[] countries = null;
		String closestPlace = "";
		if(art.getCities().equals("") && art.getCountries().equals(""))
		{
			return art.getLocationWritten();
		}
		else
		{
			if(art.getCities() != null)
			{
				cities = art.getCities().split("\\|");
			}
			if(art.getCountries() != null)
			{
				countries = art.getCountries().split("\\|");
			}
			
			int minDistance = Integer.MAX_VALUE;
			if(cities != null)
			{
				for(String s : cities)
				{
					if(!s.equals(""))
					{
						int tempDistance = art.closenessOfPlaceToText(s, text);
						if (tempDistance < minDistance && tempDistance != -1)
						{
							minDistance = tempDistance;
							closestPlace = s; 
						}
					}
				}
			}
			if(countries != null)
			{
				for(String s : countries)
				{
					if(!s.equals(""))
					{
						int tempDistance = art.closenessOfPlaceToText(s, text);
						if (tempDistance < minDistance)
						{
							minDistance = tempDistance;
							closestPlace = s; 
						}
					}
				}
			}
		}
		
		return new Place(closestPlace);
	}
	
	/**
	 * A method to solve "where" questions.
	 * @param articles the list of answers
	 * @param text the important text
	 * @param time the time of the question
	 * @return zero or more possible answers
	 */
	protected static Answer answer(LinkedList<Article> articles, String text, String time)
	{
		Answer answer = new Answer();
		
		for (Article a : articles)
		{
			if (a.containsDate(time))
			{
				Place articlePlace = getPlaceForText(a, text);
				
				answer.add(articlePlace.toString(), 1);
			}
		}
		
		return answer;
	}
	
	/**
	 * Convenience test method
	 * @param args
	 */
	public static void main(String[] args)
	{
		Main.main(null);
	}
}