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
 * Solve where questions by:
 * 1. Finding the text in the article
 * 2. Checking how close it is to the place
 * 
 * @author Steven Heidel
 *
 */
public class Where {
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
			double confidence = 0.0;
			confidence += Heuristics.articleConfidence(a, new Time(time));
			confidence *= Heuristics.get("where.article_likelihood");
			answer.add(a.getLocationWritten().toString(), Heuristics.articleConfidence(a, new Time(time)));
			
			for (Place p : a.getPlaces())
			{
				confidence = 0.0;
				confidence += Heuristics.articleConfidence(a, new Time(time)) * Heuristics.get("where.weight.article");
				confidence += (1 - Heuristics.minDistance(a, p, text) * Heuristics.get("where.distance_reduction")) * Heuristics.get("where.weight.closeness");
								
				answer.add(p.toString(), confidence);
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
		Main.main(new String[]{"-test", "where"});
	}
}