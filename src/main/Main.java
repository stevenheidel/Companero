package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import entities.Article;
import entities.Corpus;
import entities.Question;

import solver.Solver;
import utilities.Place;

public class Main 
{
	public static String readTextFile(String fullPathFilename) throws IOException 
	{
		String toReturn = "";
		
		FileInputStream fis;
		BufferedReader br;
		
		try
		{
			fis = new FileInputStream(fullPathFilename);
			br = new BufferedReader(new InputStreamReader(fis));
			
			String nextLine = br.readLine();
			while (nextLine != null)
			{
				toReturn += nextLine;
				toReturn += "\n";
				nextLine = br.readLine();
			}
		}
		catch (FileNotFoundException e)
		{
			throw new IOException("File not found");
		}
		
		return toReturn;
	}
	
	public static void main(String[] args)
	{	
		Corpus corpus = null;
		try 
		{
			corpus = new Corpus(readTextFile("corpus.txt"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		Scanner scanner = new Scanner(System.in);
		
		while (scanner.hasNextLine())
		{
			Question question = new Question(scanner.nextLine());
			
			System.out.println("\t" + Solver.solve(question, corpus));
		}
	}
}
