package solver;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import utilities.Place;

import entities.Answer;
import entities.Article;
import entities.Corpus;
import entities.Question;

public class Solver {
	
	public static Answer solve(Question question, Corpus corpus)
	{
		LinkedList<Article> articles = corpus.getArticlesWithText(question.getText());
		
		if(question.getType().equals("WHO"))
		{
			return whoAnswer(articles, question.getText(), question.getPlace(), question.getTime());
		}
		else if(question.getType().equals("WHAT"))
		{
			return whatAnswer(articles, question.getText(), question.getPlace(), question.getTime());
		}
		else if(question.getType().equals("WHERE"))
		{
			return whereAnswer(articles, question.getText(), question.getTime());
		}
		else if(question.getType().equals("WHEN"))
		{
			return whenAnswer(articles, question.getText(), question.getPlace());
		}
		
		return new Answer();
	}
	
	private static Answer whoAnswer(LinkedList<Article> articles, String text, String place, String time)
	{
		
		
		return new Answer();
	}
	
	private static Answer whatAnswer(LinkedList<Article> articles, String text, String place, String time)
	{
		String toReturn = null;
		Place questionPlace = new Place(place);
		
		for (Article a : articles)
		{	
			if (a.containsPlace(questionPlace))
			{				
				// Get the rest of the sentence after the text
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
				
				// We found a correct answer, so break
				break;
			}
		}
		
		Answer answer = new Answer();
		
		if (toReturn != null)
			answer.add(toReturn, 1);
		
		return answer;
	}
	
	private static Place getPlaceForText(Article art, String text)
	{
		String[] cities = null;
		String[] countries = null;
		String closestPlace = "";
		if(art.getCities().equals("") && art.getCountries().equals(""))
		{
			return art.getLocationWritten();
		}
		else
		{
			if(art.getCities() != null)
			{
				cities = art.getCities().split("\\|");
			}
			if(art.getCountries() != null)
			{
				countries = art.getCountries().split("\\|");
			}
			
			int minDistance = Integer.MAX_VALUE;
			if(cities != null)
			{
				for(String s : cities)
				{
					if(!s.equals(""))
					{
						int tempDistance = art.closenessOfPlaceToText(s, text);
						if (tempDistance < minDistance && tempDistance != -1)
						{
							minDistance = tempDistance;
							closestPlace = s; 
						}
					}
				}
			}
			if(countries != null)
			{
				for(String s : countries)
				{
					if(!s.equals(""))
					{
						int tempDistance = art.closenessOfPlaceToText(s, text);
						if (tempDistance < minDistance)
						{
							minDistance = tempDistance;
							closestPlace = s; 
						}
					}
				}
			}
		}
		
		return new Place(closestPlace);
	}
	
	private static Answer whereAnswer(LinkedList<Article> articles, String text, String time)
	{
		Answer answer = new Answer();
		
		for (Article a : articles)
		{
			if (a.containsDate(time))
			{
				Place articlePlace = getPlaceForText(a, text);
				
				answer.add(articlePlace.toString(), 1);
			}
		}
		
		return answer;
	}
	
	private static Answer whenAnswer(LinkedList<Article> articles, String text, String place)
	{
		String returnDate = null;
		
		Place questionPlace = new Place(place);
		for(Article a : articles)
		{			
			// We only need to get the first country from questionPlace, as only one country will be entered per question
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
				
				// If we didn't find a date in the text, give the date the article was written
				if (returnDate == null)
				{
					StringBuffer sb = new StringBuffer();
					SimpleDateFormat df = new SimpleDateFormat("dd MMM, yyyy");
					df.format(a.getDateWritten(), sb, new FieldPosition(0));
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
}