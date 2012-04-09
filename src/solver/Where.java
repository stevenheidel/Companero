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
	 * @param a
	 * @param text
	 * @return
	 */
	private static Place getPlaceForText(Article a, String text)
	{
		String closestPlace = null;
		int tempDistance = 0;
		
		if (a.getPlaces().size() == 0)
		{
			return a.getLocationWritten();
		}
		else
		{
			int minDistance = Integer.MAX_VALUE;
			
			for (Place p : a.getPlaces())
			{
				if (p.hasCity())
				{
					tempDistance = a.closenessOfPlaceToText(p.getCity(), text);
					if (tempDistance < minDistance && tempDistance != -1)
					{
						minDistance = tempDistance;
						closestPlace = p.getCity(); 
					}
				}
				
				for (String s : p.getCountry())
				{
					tempDistance = a.closenessOfPlaceToText(s, text);
					if (tempDistance < minDistance)
					{
						minDistance = tempDistance;
						closestPlace = s; 
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
			//if (a.containsDate(time))
			//{
				Place articlePlace = getPlaceForText(a, text);
				
				if (articlePlace.getCountry().size() > 1)
					answer.add(articlePlace.getCity(), 1);
				else
					answer.add(articlePlace.toString(), 1);
			//}
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