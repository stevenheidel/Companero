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
import utilities.Time;
import entities.Answer;
import entities.Article;

/**
 * Solve when questions by:
 * 1. Finding the text in the article
 * 2. Checking how close it is to the time
 * 
 * @author Jamie Gaultois
 *
 */
public class When {
	/**
	 * A method to solve "when" questions.
	 * @param articles the list of answers
	 * @param text the important text
	 * @param place the place of the question
	 * @return zero or more possible answers
	 */
	protected static Answer answer(LinkedList<Article> articles, String text, String place)
	{
		Answer answer = new Answer();
		
		for (Article a : articles)
		{
			double confidence = 0.0;
			confidence += Heuristics.articleConfidence(a, new Place(place));
			confidence *= Heuristics.get("when.article_likelihood");
			answer.add(a.getTimeWritten().toString(), confidence);
			
			for (Time t : a.getTimes())
			{
				confidence = 0.0;
				confidence += Heuristics.articleConfidence(a, new Place(place)) * Heuristics.get("when.weight.article");
				confidence += (1 - Heuristics.minDistance(a, t, text) * Heuristics.get("when.distance_reduction")) * Heuristics.get("when.weight.closeness");
								
				answer.add(t.toString(), confidence);
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
		Main.main(new String[]{"-test", "when"});
	}
}