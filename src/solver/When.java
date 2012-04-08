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

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import main.Main;

import utilities.Place;
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
		String returnDate = null;
		
		Place questionPlace = new Place(place);
		for(Article a : articles)
		{			
			// we only need to get the first country from questionPlace, as only one country will be entered per question
			if(a.containsPlace(questionPlace))
			{				
				LinkedList<String> dates = a.getDatesFromText();
				
				int minDistance = Integer.MAX_VALUE;
				
				for(String s : dates)
				{
					int dist = a.closenessOfDateToText(s, text);
					if(dist < minDistance)
					{
						minDistance = dist;
						returnDate = s.replace("(yearadded)", "");
					}
				}
				
				// if we didn't find a date in the text, give the date the article was written
				if (returnDate == null)
				{
					StringBuffer sb = new StringBuffer();
					SimpleDateFormat df = new SimpleDateFormat("dd MMM, yyyy");
					df.format(a.getTimeWritten().getDate(), sb, new FieldPosition(0));
					returnDate = sb.toString();
				}
				
				break;
			}
		}
		
		Answer answer = new Answer();
		
		if (returnDate != null)
			answer.add(returnDate, 1);
		
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