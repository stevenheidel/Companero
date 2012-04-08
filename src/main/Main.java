/*
 * Jamie Gaultois
 * jpg627
 * 11066502
 * 
 * Steven Heidel
 * sdh951
 * 11078053
 */

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import entities.Corpus;
import entities.Question;

import solver.Solver;
import utilities.FileReader;

/**
 * Main driver class. Can be enabled for testing.
 * 
 * @author Steven Heidel
 *
 */
public class Main 
{
	/**
	 * If true, will read from file of test questions
	 * If false, will read line separated questions from stdin
	 */
	private static final boolean TESTING = false;
	
	/**
	 * The driver itself.
	 * @param args
	 */
	public static void main(String[] args)
	{
		// the corpus takes a few seconds to initialize
		System.out.println("Processing the corpus...");
		Corpus corpus = new Corpus(FileReader.convertToString("data/structures/corpus.txt"));
		System.out.println("\t\t\t...ready for question answering!\n");
		
		// if testing, read from the file, otherwise System.in
		Scanner scanner = null;
		if (TESTING)
		{
			try 
			{
				scanner = new Scanner(new File("data/test/questions.txt"));
			} catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		}
		else
			scanner = new Scanner(System.in);
		
		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			if (! line.equals(""))
			{
				// get the question
				Question question = new Question(line);
				System.out.println("[QUESTION] " + line);
				
				// come up with an answer and output it
				System.out.println(Solver.solve(question, corpus));
			}
		}
	}
}
