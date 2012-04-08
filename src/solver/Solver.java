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

import entities.Answer;
import entities.Article;
import entities.Corpus;
import entities.Question;

/**
 * A static class used to determine the answer to a particular question based
 * on the information found in the corpus. This is somewhat general, such that
 * you could technically implement a different question/corpus class for many
 * corpuses and question types provided they implemented the same methods.
 * 
 * @author Steven Heidel
 *
 */
public class Solver 
{
	/**
	 * Gets a list of all articles that contain the text, and then delegates
	 * the work of finding the answer to a class depending on the type of
	 * question.
	 * @param question The question that should be answered
	 * @param corpus The corpus of text to search
	 * @return zero or more answers weighted by their potential
	 */
	public static Answer solve(Question question, Corpus corpus)
	{
		// get a list of all articles which could possibly contain the answer
		LinkedList<Article> articles = corpus.getArticlesWithText(question.getText());
		
		if(question.getType().equals("WHO"))
		{
			return Who.answer(articles, question.getText(), question.getPlace(), question.getTime());
		}
		else if(question.getType().equals("WHAT"))
		{
			return What.answer(articles, question.getText(), question.getPlace(), question.getTime());
		}
		else if(question.getType().equals("WHERE"))
		{
			return Where.answer(articles, question.getText(), question.getTime());
		}
		else if(question.getType().equals("WHEN"))
		{
			return When.answer(articles, question.getText(), question.getPlace());
		}
		
		return new Answer();
	}
}