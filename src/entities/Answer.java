/*
 * Jamie Gaultois
 * jpg627
 * 11066502
 * 
 * Steven Heidel
 * sdh951
 * 11078053
 */

package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * A class to store possible answers for some question. These answers can be
 * weighted by values and then displayed in an attractive format.
 * 
 * @author Steven Heidel
 *
 */
public class Answer 
{
	/**
	 * How much space to allocate for the answers (default: 45)
	 */
	private static final int ANSWER_LENGTH = 45;
	
	/**
	 * The total width of the terminal (default: 80)
	 */
	private static final int TERMINAL_WIDTH = 80;
	
	/**
	 * Display only the top n answers (default: 5)
	 */
	private static final int MAX_ANSWERS = 5;
	
	/**
	 * A list of answers and their associated likelihoods
	 */
	private static HashMap<String, Double> answers;
	
	/**
	 * Simply create the list of answers
	 */
	public Answer()
	{
		answers = new HashMap<String, Double>();
	}
	
	/**
	 * Add a new answer
	 * @param answer String containing the possible answer
	 * @param weight Percentage from 0-1 indicating likelihood
	 */
	public void add(String answer, double weight)
	{
		if (weight > 1)
			weight = 1;
		
		answers.put(answer, weight);
	}
		
	/**
	 * Return the weight of a particular answer
	 * @param answer the answer to search for
	 * @return the weight for that particular answer
	 */
	public double getWeight(String answer)
	{
		return answers.get(answer);
	}
	
	/**
	 * Sort a hash map by its values, method taken from internet
	 * @param original the hash map to be sorted
	 * @return a linked hash map that is sorted descending by values
	 */
	public LinkedHashMap<String, Double> sortHashMapByValues(HashMap<String, Double> original) {
		@SuppressWarnings("unchecked")
		HashMap<String, Double> clone = (HashMap<String, Double>) original.clone();
		
	 	List<String> mapKeys = new ArrayList<String>(clone.keySet());
		List<Double> mapValues = new ArrayList<Double>(clone.values());
		Collections.sort(mapValues);	
		Collections.sort(mapKeys);	
	
		// .sort() does ascending, make it descending
		Collections.reverse(mapValues);
	
		LinkedHashMap<String, Double> sorted = new LinkedHashMap<String, Double>();
		Iterator<Double> valueIt = mapValues.iterator();
		
		// make a new linked hash map using the sorted values
		while (valueIt.hasNext()) 
		{
			Double val = valueIt.next();
			Iterator<String> keyIt = mapKeys.iterator();
			while (keyIt.hasNext()) 
			{
				String key = keyIt.next();
				if (clone.get(key).toString().equals(val.toString())) 
				{
					clone.remove(key);
					mapKeys.remove(key);
					sorted.put(key, val);
					break;
				}
			}
		}
		
		return sorted;
	}
	
	/**
	 * Display the answers in an attractive format. This was inspired by 
	 * Watson with the bars beside the answers indicating likelihood.
	 */
	public String toString()
	{
		if (answers.size() == 0)
			return "***No Answers Found***\n";
		
		String toReturn = "";
		int count = 0;
		
		for (Entry<String, Double> answer : sortHashMapByValues(answers).entrySet())
		{
			// right align the answer
			toReturn += String.format("%" + ANSWER_LENGTH + "s", answer.getKey());
			
			// put answer on its own line if it's too long
			if (answer.getKey().length() > ANSWER_LENGTH)
			{
				toReturn += "\n";
				toReturn += String.format("%" + ANSWER_LENGTH + "s", " ");
				
				// printing bar for just one long answer looks weird
				if (answers.size() == 1)
					break;
			}
			
			toReturn += " |";
			
			// display percentage likelihood as a bar
			for (int i = 0; i < (TERMINAL_WIDTH - ANSWER_LENGTH) * answer.getValue() - 2; i++)
				toReturn += "=";
			
			toReturn += "\n";
			
			count += 1;
			if (count >= MAX_ANSWERS)
				break;
		}
		
		return toReturn;
	}
	
	/**
	 * Test method
	 * @param args
	 */
	public static void main(String[] args)
	{
		Answer blank = new Answer();
		System.out.println(blank);
		
		Answer single = new Answer();
		single.add("SINGLE ANSWER", 1);
		System.out.println(single);
		
		Answer singleLong = new Answer();
		singleLong.add("SINGLE ANSWER THAT IS EXTERMELY LONG AND WILL WRAP OVER ONTO THE NEXT LINE PROBABLY", 1);
		System.out.println(singleLong);
		
		Answer answer = new Answer();
		answer.add("TEST 1", 0.27);
		answer.add("TEST WHERE THE LENGTH OF THE STRING IS MUCH LONGER THAN THE MAX CHARACTERS", 1);
		answer.add("TEST 2", 0.78);
		System.out.println(answer);
	}
}