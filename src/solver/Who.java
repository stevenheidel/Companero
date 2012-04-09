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

import parser.Parser;
import utilities.Place;
import utilities.Time;

import main.Main;

import entities.Answer;
import entities.Article;

/**
 * Solve who questions by:
 * 1. Finding the text in the article
 * 2. Finding nouns in the article
 * 3. Checking each noun to see how close it is to the text, how likely it is
 * 		a name, and whether the article parameters match the question
 * 
 * @author Steven Heidel
 *
 */
public class Who {
	/**
	 * A method to solve "who" questions.
	 * @param articles the list of answers
	 * @param text the important text
	 * @param place the place of the question
	 * @param time the time of the question
	 * @return zero or more possible answers
	 */
	protected static Answer answer(LinkedList<Article> articles, String text, String place, String time)
	{
		Answer answer = new Answer();
		
		for (Article a : articles)
		{
			LinkedList<String> nouns = Parser.getNounPhrases(a.getID());
			
			if (articles.size() > 2 && 
					Heuristics.articleConfidence(a, new Place(place), new Time(time)) == 0)
				continue;
			
			for (String noun : nouns)
			{
				// check if the entire noun is in the text
				if (text.contains(noun))
					continue;
				
				double confidence = 0.0;
				confidence += Heuristics.articleConfidence(a, new Place(place), new Time(time)) * Heuristics.get("who.weight.article");
				confidence += Heuristics.personNameConfidence(noun) * Heuristics.get("who.weight.person_name");
				confidence += (1 - Heuristics.minDistance(a, noun, text) * Heuristics.get("who.distance_reduction")) * Heuristics.get("who.weight.closeness");
				
				answer.add(noun, confidence);
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
		Main.main(new String[]{"-test", "who"});
	}
}