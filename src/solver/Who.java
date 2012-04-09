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
 * TODO: Description of implementation
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
			
			for (String noun : nouns)
			{
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