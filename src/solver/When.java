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
			answer.add(a.getTimeWritten().toString(), Heuristics.articleConfidence(a, new Place(place)));
			
			for (Time t : a.getTimes())
			{
				double confidence = 1.0 - (Heuristics.minDistance(a, t, text) * 0.1);
				
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