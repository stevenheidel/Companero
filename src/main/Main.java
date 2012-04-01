package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import question.Question;

public class Main 
{
	public static String readTextFile(String fullPathFilename) throws IOException 
	{
		StringBuffer sb = new StringBuffer(1024);
		BufferedReader reader = new BufferedReader(new FileReader(fullPathFilename));
				
		char[] chars = new char[1024];
		int numRead = 0;
		while( (numRead = reader.read(chars)) > -1)
		{
			sb.append(String.valueOf(chars));	
		}

		reader.close();

		return sb.toString();
	}
	
	public static void main(String[] args)
	{	
		/*
		try {
			Corpus corpus = new Corpus(readTextFile("corpus.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		Scanner scanner = new Scanner(System.in);
		
		while (scanner.hasNextLine())
		{
			Question question = new Question(scanner.nextLine());
			
			// solve(corpus, question)
		}
	}
}
