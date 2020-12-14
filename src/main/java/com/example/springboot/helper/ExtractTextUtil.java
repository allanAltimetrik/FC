package com.example.springboot.helper;

import java.util.*;

public class ExtractTextUtil {

	public static String processExtractedText(String text) {

		String ProcessedText = text;

		if (ProcessedText != "" || ProcessedText != null) {

			// Removing Empty Lines Spaces from Extracted Text
			ProcessedText = ProcessedText.replaceAll("\r\n", " ").replaceAll("\n", " ");

			// Removing Other Characters from Extracted Text
			ProcessedText = ProcessedText.replaceAll("[^a-zA-Z]", " ").toLowerCase();

			// Removing Less than 4 character Words from Extracted Text
			ProcessedText = ProcessedText.replaceAll("\\b\\w{1,3}\\b", "");

			// Getting Stopwords from Properties File as String
			String stopwords_String = PropertyFileUtil.readStopWords();

			// Adding Stopwords to Set
			HashSet<String> stopwords_HashSet = convertStringToHashSet(stopwords_String);

			// Removing Stopwords from Extracted Text
			Iterator<String> Itr_1 = stopwords_HashSet.iterator();
			while (Itr_1.hasNext()) {
				String TextToReplace = Itr_1.next().toString();
				System.out.println(TextToReplace);
				ProcessedText = ProcessedText.replaceAll("(?i)\\b" + TextToReplace + "\\b", "").replaceAll("\\s+", " ");
			}
			System.out.println("Processed Text - " + ProcessedText);

		}

		return ProcessedText;
	}

	public static HashSet<String> convertStringToHashSet(String text) {
		HashSet<String> TextHashSet = new HashSet<String>();
		if (text.contains(",")) {
			String[] TextArray = text.split(",");
			List<String> TextList = Arrays.asList(TextArray);
			TextHashSet.addAll(TextList);
			System.out.println("Hashset : " + TextHashSet);
		}
		return TextHashSet;
	}

	public static Hashtable<String, Integer> countWords(String input) {
		Hashtable<String, Integer> map = new Hashtable<String, Integer>();
		if (input != null) {
			String[] separatedWords = input.split(" ");
			for (String str : separatedWords) {
				if (map.containsKey(str)) {
					int count = map.get(str);
					if (!str.equals("")) {
						map.put(str, count + 1);
					}
				} else {
					map.put(str, 1);
				}
			}
		}
		System.out.println("Count :- " + map);
		return map;
	}

}
