package solver;

import java.util.LinkedList;

import utilities.Place;
import entities.Answer;
import entities.Article;

/**
 * TODO: Description of implementation
 * 
 * @author Jami Gaultois
 *
 */
public class What {
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
		String toReturn = null;
		Place questionPlace = new Place(place);
		
		for (Article a : articles)
		{	
			if (a.containsPlace(questionPlace))
			{				
				// get the rest of the sentence after the text
				int index = a.getArticleText().indexOf(text) + text.length();
				
				toReturn = a.getArticleText().substring(index);
				
				String tempAnswer = toReturn.split("\\.")[0];
				if (tempAnswer.contains("\""))
				{
					String[] answerSplit = toReturn.split("\"");
					toReturn = answerSplit[0] + "\"" + answerSplit[1] + "\"";
					if (a.getArticleText().indexOf(answerSplit[1]) + answerSplit[1].length() < index + tempAnswer.length())
					{
						toReturn = a.getArticleText().substring(index).split("\\.")[0];
					}
				}
				else
				{
					toReturn = toReturn.split("\\.")[0];
				}
				
				// we found a correct answer, so break
				break;
			}
		}
		
		Answer answer = new Answer();
		
		if (toReturn != null)
			answer.add(toReturn, 1);
		
		return answer;
	}
}
