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
 * Solve what questions by:
 * 1. Finding the text in the article
 * 2. Returning the rest of the sentence
 * 
 * @author Jamie Gaultois
 *
 */
public class What {
	/**
	 * Get remainder of sentence after a string.
	 * @param articleText the article where the sentence can be found
	 * @param text the string of text in the sentence
	 * @return the remainder of the sentence after that string
	 */
	private static String getRestOfSentence(String articleText, String text)
	{
		String toReturn = "";
		
		// get the rest of the sentence after the text
		int index = articleText.indexOf(text) + text.length();
		
		toReturn = articleText.substring(index);
		
		String tempAnswer = toReturn.split("\\.")[0];
		if (tempAnswer.contains("\""))
		{
			String[] answerSplit = toReturn.split("\"");
			toReturn = answerSplit[0] + "\"" + answerSplit[1] + "\"";
			if (articleText.indexOf(answerSplit[1]) + answerSplit[1].length() < index + tempAnswer.length())
			{
				toReturn = articleText.substring(index).split("\\.")[0];
			}
		}
		else
		{
			toReturn = toReturn.split("\\.")[0];
		}
		
		// remove leading and trailing whitespace
		return toReturn.trim();
	}
	
	/**
	 * A method to solve "what" questions.
	 * @param articles the list of answers
	 * @param text the important text
	 * @param place the place of the question
	 * @param time the time of the question
	 * @return zero or more possible answers
	 */
	protected static Answer answer(LinkedList<Article> articles, String text, String place, String time)
	{
		Answer answer = new Answer();
		
		// only need to do article confidence because it's always just the
		// remainder of the string
		for (Article a : articles)
			answer.add(getRestOfSentence(a.getArticleText(), text), 
					Heuristics.articleConfidence(a, new Place(place), new Time(time)));
		
		return answer;
	}
	
	/**
	 * Convenience test method
	 * @param args
	 */
	public static void main(String[] args)
	{
		Main.main(new String[]{"-test", "what"});
	}
}