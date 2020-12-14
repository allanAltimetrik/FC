package com.example.springboot.helper;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class KeywordGenerationUtil {

//	public static void main(String[] args) {
//		generateKeywordsFromImage("deathrecord",
//				"src/main/java/com/example/springboot/resources/samples/DeathNotice.jpg", "      ");
//	}

	@SuppressWarnings("rawtypes")
	public static Hashtable<String, List<String>> generateKeywordsFromImage(String fileType, String file, String bias) {

		//Extracting Text from Image
		String extractedText = OcrUtil.extractTextFromImage(file);

		//Process Extracted Text
		String processedText = ExtractTextUtil.processExtractedText(extractedText);

		//Initailize Hashtable for storing File Type and Keywords
		Hashtable<String, List<String>> fileType_Keywords = new Hashtable<String, List<String>>();

		if (processedText != "" || processedText != null) {

			// Count the frequency of words and add it to Keywords_HashSet set
			HashSet<String> keywords_HashSet = new HashSet<>();
			Hashtable<String, Integer> words = ExtractTextUtil.countWords(processedText);
			for (Map.Entry singleWord : words.entrySet()) {
				if ((Integer) singleWord.getValue() > 1) {
					keywords_HashSet.add(singleWord.getKey().toString());
				}
			}			
			
			//Add bias to keywords
			if(bias != "" || bias != null || bias != "\\s+") {
				if (bias.contains(",")) {
					String[] TextArray = bias.split(",");
					List<String> TextList = Arrays.asList(TextArray);
					keywords_HashSet.addAll(TextList);
				}
				else {
					keywords_HashSet.add(bias);
				}
			}			
			
			String keywords = keywords_HashSet.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s+","");

			// Converting File Type to Lower Case
			fileType = fileType.toLowerCase();

			// Update Property File
			Properties prop = PropertyFileUtil.loadKeywordsPropertFile();
			if (prop.containsKey(fileType)) {
				String keywords_String = PropertyFileUtil.readPropertyFromKeywordsFile(fileType.toLowerCase());
				HashSet<String> keywords_HashSet_New = ExtractTextUtil.convertStringToHashSet(keywords_String);
				String[] newKeywords_StringArray = keywords.split(",");
				List<String> newKeywords_List = Arrays.asList(newKeywords_StringArray);
				keywords_HashSet_New.addAll(newKeywords_List);
				String newKeywords = keywords_HashSet_New.toString().replaceAll("\\[", "").replaceAll("\\]", "");
				PropertyFileUtil.updateKeywords(fileType, newKeywords);
			} else {
				PropertyFileUtil.updateKeywords(fileType, keywords);
			}

			//Store FileType and Keywords in Hashtable
			String[] newKeywordsStringArray = keywords.split(",");
			List<String> NewKeywordsList = Arrays.asList(newKeywordsStringArray);
			fileType_Keywords.put(fileType, NewKeywordsList);
			System.out.println(fileType_Keywords);
		}

		return fileType_Keywords;
	}

}