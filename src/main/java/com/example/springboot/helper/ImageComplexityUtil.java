package com.example.springboot.helper;

public class ImageComplexityUtil {
	
	public static String getImageComplexity(String file) {
		
		//Extracting Text from Image
		String extractedText = OcrUtil.extractTextFromImage(file);
		
		//Get Number of Words
		int numberOfWords = wordcount(extractedText);
		
		//Determining Complexity
		String complexity = null;
		if(numberOfWords<10) {
			complexity = "Unable to Determine";
		}
		else if(numberOfWords>=10 & numberOfWords<200) {
			complexity = "Low";
		}
		else if(numberOfWords>=200 & numberOfWords<500) {
			complexity = "Medium";
		}
		else if(numberOfWords>=500) {
			complexity = "High";
		}

		return complexity;
	}
	
	
	public static int wordcount(String string) {		
		int count = 0;
		char ch[] = new char[string.length()];
		for (int i = 0; i < string.length(); i++) {
			ch[i] = string.charAt(i);
			if (((i > 0) && (ch[i] != ' ') && (ch[i - 1] == ' ')) || ((ch[0] != ' ') && (i == 0)))
				count++;
		}
		return count;
	}

}