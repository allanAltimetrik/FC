package com.example.springboot.helper;

import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.configuration.ConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class FileClassifierUtil {

	public static void main(String[] args) throws TesseractException, IOException, ConfigurationException {
		//classifyForms("src/main/java/com/example/springboot/resources/samples/Kansas Birth Certificate.jpg");
		
		HashMap<String, String> fileType = new HashMap<String, String>();
		String directory = "src/main/java/com/example/springboot/resources/samples";
		List<File> fileList = iterateOverFiles(directory);
		Iterator<File> itr = fileList.iterator();
		while(itr.hasNext()) {
			File file = itr.next();			
			String filepath = file.toString();
			String fileName = file.getName().toString();
			String type = classifyForms(filepath);
			fileType.put(fileName, type);
		}
		System.out.println("File Types - " + fileType);		
		
	}

	public static String classifyForms(String file) throws TesseractException, IOException {

		// Read All Keywords from Keywords File
		Hashtable<String, List<String>> keywords = PropertyFileUtil.readAllKeywords();

		// Extracting Text from Image
		String extractedText = OcrUtil.extractTextFromImage(file);

		// Process Extracted Text
		String processedText = ExtractTextUtil.processExtractedText(extractedText);

		// Convert the Processed Text to List
		String[] processedTextArray = processedText.split("\\s+");
		List<String> processedTextList = Arrays.asList(processedTextArray);
		System.out.println("Processed Text List - " + processedTextList);

		// Comparing the Processed Text with Keywords
		Hashtable<String, Integer> matchingKeyWords = new Hashtable<>();
		Set<Map.Entry<String, List<String>>> entries = keywords.entrySet();
		Iterator<Map.Entry<String, List<String>>> itr = entries.iterator();
		while (itr.hasNext()) {
			Map.Entry<String, List<String>> entry = itr.next();
			String key = entry.getKey();
			List<String> value = new ArrayList<String>(entry.getValue());
			System.out.println(value);
			List<String> extractedTextList = new ArrayList<String>(processedTextList);
			System.out.println(extractedTextList);
			extractedTextList.retainAll(value);
			matchingKeyWords.put(key, extractedTextList.size());
		}
		System.out.println("File Name - Number of Keywords Matched : " + matchingKeyWords);

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
		System.out.println("GreatestValue - " + greatestValue);

		// Get the File Type for Value
		String fileType = null;
		if (greatestValue > 0) {
			fileType = getKeyFromValue(matchingKeyWords, greatestValue).toString();
		} else {
			fileType = "unknown file";
		}
		System.out.println("File Type - " + fileType);
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
		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
		    System.out.println("File - " + listOfFiles[i].getName());
		  } else if (listOfFiles[i].isDirectory()) {
		    System.out.println("Directory - " + listOfFiles[i].getName());
		  }
		}
		List<File> FileList = Arrays.asList(listOfFiles);		
		return FileList;
	}

}
