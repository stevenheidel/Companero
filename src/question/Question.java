package question;

import java.util.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Main;

import utilities.Place;

import corpus.Article;
import corpus.Corpus;

public class Question
{
	private String type;
	
	private String text;
		
	private String place;
	
	private String time;
	
	public Question(String input)
	{
		input = input.toUpperCase();
		
		type = input.split(" ")[0];
		
		Matcher m = Pattern.compile("\\[(.*?)\\]").matcher(input);
		
		m.find();
		text = m.group(1);
				
		m.find();
		if (type.equals("WHO") || type.equals("WHAT"))
		{
			place = m.group(1);
			m.find();
			time = m.group(1);
		}
		else if (type.equals("WHERE"))
		{
			place = null;
			time = m.group(1);
		}
		else if (type.equals("WHEN"))
		{
			place = m.group(1);
			time = null;
		}
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getText()
	{
		return text;
	}
	
	public String getPlace()
	{
		return place;
	}
	
	public String getTime()
	{
		return time;
	}
	
	public Place getPlaceForText(Article art)
	{
		String[] places;
		String closestPlace = "";
		if(art.getCities().equals("") && art.getCountries().equals(""))
		{
			return art.getLocationWritten();
		}
		else
		{
			if(art.getCities() != null)
			{
				places = art.getCities().split("\\|");
			}
			else
			{
				places = art.getCountries().split("\\|");
			}
			
			int minDistance = Integer.MAX_VALUE;
			for(String s : places)
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
		
		return new Place(closestPlace);
	}
	
	public String whenAnswer(Corpus corpus)
	{
		// Get the list of articles with the text required
		LinkedList<Article> articles = corpus.getArticlesWithText(text);
		
		String returnDate = "No date found";
		
		Place questionPlace = new Place(place);
		for(Article a : articles)
		{
			Place articlePlace  = getPlaceForText(a);
			
			// We only need to get the first country from questionPlace, as only one country will be entered per question
			if(articlePlace.getCountry().contains(questionPlace.getCountry().getFirst()))
			{
				if(questionPlace.hasCity()
						&& !questionPlace.getCity().equals(articlePlace.getCity()))
				{
					continue;
				}
				
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
				if(returnDate.equals("No date found"))
				{
					returnDate = a.getDateWritten().toString();
				}
				
				break;
			}
		}
		
		return returnDate;
	}
	
	public String whatAnswer(Corpus corpus)
	{
		String answer = "Text not found";
		
		// Get the list of articles with the text required
		LinkedList<Article> articles = corpus.getArticlesWithText(text);
		
		Place questionPlace = new Place(place);
		for(Article a : articles)
		{
			Place articlePlace = getPlaceForText(a);			
			
			// We only need to get the first country from questionPlace, as only one country will be entered per question
			if(articlePlace.getCountry().contains(questionPlace.getCountry().getFirst()))
			{
				if(questionPlace.hasCity()
						&& !questionPlace.getCity().equals(articlePlace.getCity()))
				{
					continue;
				}
				
				// Get the rest of the sentence after the text
				int index = a.getArticleText().indexOf(text) + text.length();
				
				answer = a.getArticleText().substring(index);
				
				String tempAnswer = answer.split("\\.")[0];
				if(tempAnswer.contains("\""))
				{
					String[] answerSplit = answer.split("\"");
					answer = answerSplit[0] + "\"" + answerSplit[1] + "\"";
				}
				else
				{
					answer = answer.split("\\.")[0];
				}
				
				// We found a correct answer, so break
				break;
			}
		}
		
		return answer;		
	}
	
	public String whereAnswer(Corpus corpus)
	{
		String answer = "Text not found";
		
		// Get the list of articles with the text required
		LinkedList<Article> articles = corpus.getArticlesWithText(text);
		
		for(Article a : articles)
		{
			Place articlePlace = getPlaceForText(a);
			
			if (articlePlace.hasCity())
			{
				answer = articlePlace.toString();
			}
			else
			{
				answer = articlePlace.toString();
			}
		}
		
		return answer;
	}
	
	public String answer(Corpus corpus)
	{
		if(type.equals("WHEN"))
		{
			return whenAnswer(corpus);
		}
		else if(type.equals("WHAT"))
		{
			return whatAnswer(corpus);
		}
		else if(type.equals("WHERE"))
		{
			return whereAnswer(corpus);
		}
		return "";
	}
	
	public static void main(String[] args)
	{
		Corpus corpus = null;
		try
		{
			corpus = new Corpus(Main.readTextFile("corpus.txt"));
		} 
		catch (Exception e)
		{
			System.out.println("Error reading the corpus");
			System.exit(-1);
		}
		
		Question who = new Question("Who was [the army judge advocate general] of [Chile] in [1990]?");
		Question what = new Question("What is [an example Torres cited] in [Chile] in [1990]?");
		Question where = new Question("Where is it that [the government junta yesterday approved a draft bill] on [5 Jan 90]?");
		Question when = new Question("When did a Chilean spokesman say that [more than 1,000 prisoners might benefit from the modifications] to the bill approved in [Chile]?");
		
		System.out.println(who.getType());
		System.out.println(who.getText());
		System.out.println(who.getPlace());
		System.out.println(who.getTime());
		System.out.println(who.answer(corpus));
		
		System.out.println(what.getType());
		System.out.println(what.getText());
		System.out.println(what.getPlace());
		System.out.println(what.getTime());
		System.out.println(what.answer(corpus));
		
		System.out.println(where.getType());
		System.out.println(where.getText());
		System.out.println(where.getPlace());
		System.out.println(where.getTime());
		System.out.println(where.answer(corpus));
		
		System.out.println(when.getType());
		System.out.println(when.getText());
		System.out.println(when.getPlace());
		System.out.println(when.getTime());
		System.out.println(when.answer(corpus));
	}
}
