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
 * TODO: Description of implementation
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
			answer.add(a.getLocationWritten().toString(), Heuristics.articleConfidence(a, new Time(time)));
			
			for (Place p : a.getPlaces())
			{
				double confidence = 1 - (Heuristics.minDistance(a, p, text) * 0.01);
								
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