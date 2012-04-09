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

import java.util.HashMap;
import java.util.LinkedList;

import entities.Article;
import utilities.FileReader;
import utilities.Place;
import utilities.Time;
import utilities.WordDictionary;

/**
 * Description of class
 * 
 * @author Steven Heidel
 *
 */
public class Heuristics {
	private static HashMap<String, Double> heuristics;
	
	public static double get(String name)
	{
		buildHeuristics();
		
		return heuristics.get(name);
	}
	
	/**
	 * Can be adjusted during runtime because it's read each time
	 */
	private static void buildHeuristics()
	{
		String[] lines = FileReader.convertToStringArrayOfLines("data/structures/heuristics.txt");
		
		for (String line : lines)
		{
			// ignore comments and things
			if (!line.contains("|"))
				continue;
			
			heuristics.put(line.split("|")[0].trim(), Double.parseDouble(line.split("|")[1].trim()));
		}
	}
	
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
			return Integer.MAX_VALUE;
		
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
	
	public static int minDistance(Article article, Time time, String text)
	{
		return minDistance(article, time.getOriginal(), text);
	}
	
	private static double articleConfidence(Article article, Place place, Time time,
			double articlePlace, double articleHasPlace, double articleTime, double articleHasTime)
	{
		double confidence = 0.0;
		
		if (place != null)
			// if article place matches, add confidence
			if (article.getLocationWritten().equals(place))
				confidence += articlePlace;
			// otherwise if there's mention of the place in the article,
			// add a little less confidence
			else if (article.containsPlace(place))
				confidence += articleHasPlace;
		
		if (time != null)
			// if article time matches, add confidence
			if (article.getTimeWritten().equals(time))
				confidence += articleTime;
			// otherwise if there's mention of the time in the article,
			// add a little less confidence
			else if (article.containsTime(time))
				confidence += articleHasTime;
			
			return confidence;
	}
	
	public static double articleConfidence(Article article, Place place, Time time)
	{
		return articleConfidence(article, place, time, 0.5, 0.4, 0.5, 0.3);
	}
	
	public static double articleConfidence(Article article, Time time)
	{
		return articleConfidence(article, null, time, 0.5, 0.4, 0.5, 0.3);
	}
	
	public static double articleConfidence(Article article, Place place)
	{
		return articleConfidence(article, place, null, 0.5, 0.4, 0.5, 0.3);
	}
	
	/**
	 * How likely is it that a string of text is a person's name
	 * @param name
	 * @return
	 */
	public static double personNameConfidence(String name)
	{
		int numWords = name.split(" ").length;
		double lengthConfidence = 0.1;
		
		switch (numWords)
		{
			case 1: lengthConfidence = 0.5; break;
			case 2: lengthConfidence = 1; break;
			case 3: lengthConfidence = 0.8; break;
			case 4: lengthConfidence = 0.6; break;
			case 5: lengthConfidence = 0.4; break;
			case 6: lengthConfidence = 0.3; break;
			case 7: lengthConfidence = 0.2; break;
		}
		
		double punctuationConfidence = 1;
		if (name.contains(",") || name.contains("-") || name.contains("`")
				|| name.contains("'") || name.contains("(") || name.contains(")"))
			punctuationConfidence = 0.1;
		
		
		int numRealWords = 0;
		for (String w : name.split(" "))
			if (WordDictionary.contains(w))
				numRealWords += 1;
		
		double englishConfidence = 1.0 - (numRealWords / numWords);
				
		/*System.out.print("\n");
		System.out.println(lengthConfidence);
		System.out.println(punctuationConfidence);
		System.out.println(englishConfidence);*/
		
		return ((lengthConfidence * 0.4) + (englishConfidence * 0.6)) * punctuationConfidence;
	}
	
	public static void main(String[] args)
	{
		Article article = new Article(0, "Source", new Place("Saskatoon"), new Time("5 Jan 90"), 
				"THIS IS THE ARTICLE TEXT THAT I WILL TEST FROM. THIS AND THAT.\nTHIS");
		
		System.out.println(minDistance(article, "THIS", "THAT"));
		
		System.out.println(personNameConfidence("Steven Heidel"));
		System.out.println(personNameConfidence("RETIRED GENERAL FERNANDO TORRES SILVA"));
		System.out.println(personNameConfidence("GENERAL FERNANDO TORRES SILVA"));
		System.out.println(personNameConfidence("FERNANDO TORRES SILVA"));
		System.out.println(personNameConfidence("Some arbitrary text"));
	}
}
