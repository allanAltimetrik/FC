package com.example.springboot.helper;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

public class FileClassifierUtil {
	
	public static HashMap<String, String> classifyForms(String directory) {
		
		//Create Hashmap to store the filenames and type
		HashMap<String, String> fileType = new HashMap<String, String>();
		
		//Iterate the Files in directory
		try {
			List<File> fileList = iterateOverFiles(directory);
			Iterator<File> itr = fileList.iterator();
			while(itr.hasNext()) {
				File file = itr.next();			
				String filepath = file.toString();
				String fileName = file.getName().toString();
				String type = classifyForm(filepath);
				fileType.put(fileName, type);
			}
		}
		catch(Exception e) {
			System.out.println("Exception - " + e);
		}
			
		return fileType;
	}
	

	public static String classifyForm(String file) {

		// Read All Keywords from Keywords File
		Hashtable<String, List<String>> keywords = PropertyFileUtil.readAllKeywords();

		// Extracting Text from Image
		String extractedText = OcrUtil.extractTextFromImage(file);

		// Process Extracted Text
		String processedText = ExtractTextUtil.processExtractedText(extractedText);
		
		// Initailize File Type Variable
		String fileType = null;
		
		if (!processedText.equals("") && !processedText.equals(null) && processedText.length() > 0) {

			// Convert the Processed Text to List
			String[] processedTextArray = processedText.split("\\s+");
			List<String> processedTextList = Arrays.asList(processedTextArray);

			// Comparing the Processed Text with Keywords
			Hashtable<String, Integer> matchingKeyWords = new Hashtable<>();
			Set<Map.Entry<String, List<String>>> entries = keywords.entrySet();
			Iterator<Map.Entry<String, List<String>>> itr = entries.iterator();
			while (itr.hasNext()) {
				Map.Entry<String, List<String>> entry = itr.next();
				String key = entry.getKey();
				List<String> value = new ArrayList<String>(entry.getValue());
				List<String> extractedTextList = new ArrayList<String>(processedTextList);
				extractedTextList.retainAll(value);
				matchingKeyWords.put(key, extractedTextList.size());
			}

			// Finding Greater Number of Matching Keyword
			List<Integer> numberOfMatchingWords = new ArrayList<Integer>();
			Set<Entry<String, Integer>> matchingText = matchingKeyWords.entrySet();
			Iterator<Entry<String, Integer>> itr1 = matchingText.iterator();
			while (itr1.hasNext()) {
				Entry<String, Integer> entry = itr1.next();
				int value = entry.getValue();
				numberOfMatchingWords.add(value);
			}
			Collections.sort(numberOfMatchingWords, Collections.reverseOrder());
			int greatestValue = numberOfMatchingWords.get(0);

			// Get the File Type for Value
			if (greatestValue > 0) {
				fileType = getKeyFromValue(matchingKeyWords, greatestValue).toString();
			} else {
				fileType = "!!! File Not Classified !!!";
			}

		}
		return fileType;
	}

	public static Object getKeyFromValue(Map<String, Integer> hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}
	
	
	public static List<File> iterateOverFiles(String directory) {
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		List<File> FileList = Arrays.asList(listOfFiles);		
		return FileList;
	}

}
