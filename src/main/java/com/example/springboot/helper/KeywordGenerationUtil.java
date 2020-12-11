package com.example.springboot.helper;

import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.*;
import java.util.*;

public class KeywordGenerationUtil {

	static String Keywords_PropertyFile = "src/main/java/com/example/springboot/resources/propertyFiles/keywords.properties";
	static String Stopwords_PropertyFile = "src/main/java/com/example/springboot/resources/propertyFiles/stopwords.properties";
	static String Stopwords_Key = "stopwords";

//	public static void main(String[] args) throws TesseractException, IOException, ConfigurationException {
//		generateKeywordsFromImage("DeathNotice", "src/main/java/com/example/springboot/resources/samples/DeathNotice.jpg");
//	}

	public static Hashtable<String, List<String>> generateKeywordsFromImage(String FileType, String FileName)
			throws TesseractException {

		// Getting Stopwords from Properties File as String
		String stopwords_String = readProperty(Stopwords_PropertyFile, Stopwords_Key);

        //Adding Stopwords to Set
		HashSet<String> stopwords_HashSet = convertStringToHashSet(stopwords_String);

		// Extracting Text from Image
		String ExtractedText = OcrUtil.extractTextFromImage(FileName);

		// Removing Empty Lines Spaces from Extracted Text
		ExtractedText = ExtractedText.replaceAll("\r\n", " ").replaceAll("\n", " ");

		// Removing Other Characters from Extracted Text
		ExtractedText = ExtractedText.replaceAll("[^a-zA-Z]", " ").toLowerCase();

		// Removing Less than 4 character Words from Extracted Text
		ExtractedText = ExtractedText.replaceAll("\\b\\w{1,4}\\b", "");

		// Removing Stopwords from Extracted Text
		Iterator Itr_1 = stopwords_HashSet.iterator();
		while (Itr_1.hasNext()) {
			String TextToReplace = Itr_1.next().toString();
			System.out.println(TextToReplace);
			ExtractedText = ExtractedText.replaceAll("(?i)\\b" + TextToReplace + "\\b", "").replaceAll("\\s+", " ");
		}
		System.out.println("ExtractedText - " + ExtractedText);

		// Count the frequency of words and add it to Keywords_HashSet set if the
		// frequency is more than 2 times
		HashSet<String> Keywords_HashSet = new HashSet<>();
		Hashtable<String, Integer> table = countWords(ExtractedText);
		for (Map.Entry entry : table.entrySet()) {
			if ((Integer) entry.getValue() > 2) {
				Keywords_HashSet.add(entry.getKey().toString());
			}
		}
		String Keywords = Keywords_HashSet.toString().replaceAll("\\[", "").replaceAll("\\]", "");

        //Converting File Type to Lower Case
		FileType = FileType.toLowerCase();

		// Update Property File
		Properties prop = loadPropertyFile(Keywords_PropertyFile);
		if (prop.containsKey(FileType)) {
			String Keywords_String = readProperty(Keywords_PropertyFile, FileType.toLowerCase());
			HashSet<String> Keywords_HashSet_New = convertStringToHashSet(Keywords_String);
			
			String[] NewKeywords_StringArray = Keywords.split(",");
			List<String> NewKeywords_List = Arrays.asList(NewKeywords_StringArray);
			Keywords_HashSet_New.addAll(NewKeywords_List);
			String NewKeywords = Keywords_HashSet_New.toString().replaceAll("\\[", "").replaceAll("\\]","");
			Keywords = NewKeywords;
			updatePropertyFile(Keywords_PropertyFile, FileType, Keywords);
		} else {
			updatePropertyFile(Keywords_PropertyFile, FileType, Keywords);
		}

		Hashtable<String, List<String>> FileType_Keywords = new Hashtable<String, List<String>>();
		String[] NewKeywordsStringArray = Keywords.split(",");
		List<String> NewKeywordsList = Arrays.asList(NewKeywordsStringArray);
		FileType_Keywords.put(FileType, NewKeywordsList);
		System.out.println(FileType_Keywords);
		return FileType_Keywords;
	}

	public static Hashtable countWords(String input) {
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

	public static Properties loadPropertyFile(String File) {
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(File);
			prop.load(fis);
		} catch (Exception e) {
			System.out.println("Exception - " + e);
		}
		return prop;
	}

	public static String readProperty(String File, String Key) {
		String Value = null;
		try {
			FileInputStream fis = new FileInputStream(File);
			Properties prop = new Properties();
			prop.load(fis);
			Value = prop.getProperty(Key).replaceAll("\\s", "");
			System.out.println("Value : " + Value);
			fis.close();
		} catch (Exception e) {
			System.out.println("Exception - " + e);
		}
		return Value;
	}

	public static HashSet convertStringToHashSet(String Text) {
		HashSet<String> TextHashSet = new HashSet<String>();
		if (Text.contains(",")) {
			String[] TextArray = Text.split(",");
			List<String> TextList = Arrays.asList(TextArray);
			TextHashSet.addAll(TextList);
			System.out.println("Hashset : " + TextHashSet);
		}
		return TextHashSet;
	}

	public static void updatePropertyFile(String File, String Key, String Value) {
		try {
			PropertiesConfiguration property = new PropertiesConfiguration(File);
			property.setProperty(Key, Value);
			property.save();
		} catch (Exception e) {
			System.out.println("Exception - " + e);
		}
	}

}
