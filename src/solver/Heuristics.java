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

import entities.Article;
import utilities.Place;
import utilities.Time;

/**
 * Description of class
 * 
 * @author Steven Heidel
 *
 */
public class Heuristics {
	/**
	 * TODO: description
	 * Returns -1 if one or the other can't be found
	 * @param article
	 * @param one
	 * @param two
	 * @return
	 */
	public static int minDistance(Article article, String one, String two)
	{
		LinkedList<Integer> ones = new LinkedList<Integer>();
		LinkedList<Integer> twos = new LinkedList<Integer>();
				
		int location = -1;
		while ((location = article.getArticleText().indexOf(one, location+1)) >= 0)
			// make sure it matches the full word
			if ((location-1 < 0 || article.getArticleText().charAt(location-1) < 65)
					&& (location+one.length() >= article.getArticleText().length() || article.getArticleText().charAt(location+one.length()) < 65))
				ones.add(location);
		
		location = -1;
		while ((location = article.getArticleText().indexOf(two, location+1)) >= 0)
			// make sure it matches the full word
			if ((location-1 < 0 || article.getArticleText().charAt(location-1) < 65)
					&& (location+two.length() >= article.getArticleText().length() || article.getArticleText().charAt(location+two.length()) < 65))
				twos.add(location);
		
		if (ones.isEmpty() || twos.isEmpty())
			return -1;
		
		// Loop through the locations of both strings and try and find the 
		// minDistance between them
		int minDistance = Integer.MAX_VALUE;
		
		for (Integer i : ones)
		{
			for (Integer j : twos)
			{
				String substring = "";
				
				try
				{
					if (i < j)
					{
						substring = article.getArticleText().substring(i + one.length(), j);
					}
					else if (i > j)
					{
						substring = article.getArticleText().substring(j + two.length(), i);
					}
				}
				catch (Exception e)
				{
					// place is within the text itself
					continue;
				}
				
				int distance = substring.length();
				// +10 for sentences
				distance += 10 * (substring.replaceAll("[^.]", "").length());
				// +100 for paragraphs
				distance += 100 * (substring.replaceAll("[^\n]", "").length());

				minDistance = Math.min(minDistance, distance);
			}
		}
		
		return minDistance;
	}
	
	public static int minDistance(Article article, Place place, String text)
	{
		// Answers often given Salvador, Brazil when we want San Salvador, El Salvador
		// so here is a quick fix
		if (place.hasCity() && place.getCity().equals("SALVADOR") && 
				(article.containsText("SAN SALVADOR") || article.containsText("EL SALVADOR")))
			return Integer.MAX_VALUE;
		
		return minDistance(article, place.getOriginal(), text);
	}
	
	public static double articleConfidence(Article article, Place place, Time time)
	{
		double confidence = 0.0;
		
		if (place != null)
			// if article place matches, add confidence
			if (article.getLocationWritten().equals(place))
				confidence += 0.5;
			// otherwise if there's mention of the place in the article,
			// add a little less confidence
			else if (article.containsPlace(place))
				confidence += 0.4;
		
		if (time != null)
			// if article time matches, add confidence
			if (article.getTimeWritten().equals(time))
				confidence += 0.5;
			// otherwise if there's mention of the time in the article,
			// add a little less confidence
			// TODO: fix time.toString()
			else if (article.containsDate(time.toString()))
				confidence += 0.3;
			
			return confidence;
	}
	
	public static double articleConfidence(Article article, Time time)
	{
		return articleConfidence(article, null, time);
	}
	
	public static double articleConfidence(Article article, Place place)
	{
		return articleConfidence(article, place, null);
	}
	
	public static void main(String[] args)
	{
		Article article = new Article(0, "Source", new Place("Saskatoon"), new Time("5 Jan 90"), 
				"THIS IS THE ARTICLE TEXT THAT I WILL TEST FROM. THIS AND THAT.\nTHIS");
		
		System.out.println(minDistance(article, "THIS", "THAT"));
	}
}
