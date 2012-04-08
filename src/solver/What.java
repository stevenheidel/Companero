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
		
		// get the question place
		Place questionPlace = new Place(place);
		
		// get the question date
		Time questionTime = new Time(time);
		
		for (Article a : articles)
		{	
			double confidence = 0.0;
			
			// if article place matches, add confidence
			if (a.getLocationWritten().equals(questionPlace))
				confidence += 0.5;
			// otherwise if there's mention of the place in the article,
			// add a little less confidence
			else if (a.containsPlace(questionPlace))
				confidence += 0.4;
			
			// if article time matches, add confidence
			if (a.getTimeWritten().equals(questionTime))
				confidence += 0.5;
			// otherwise if there's mention of the time in the article,
			// add a little less confidence
			else if (a.containsDate(time))
				confidence += 0.3;
			
			answer.add(getRestOfSentence(a.getArticleText(), text), confidence);
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