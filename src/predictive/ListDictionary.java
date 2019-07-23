package predictive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * @author Dimitrios Simopoulos
 *
 */
public class ListDictionary {

	private ArrayList<WordSig> wordSignaturePairs;

	/**
	 * This is the Constructor of the class ListDirectory that takes a String path
	 * to the dictionary, reads stores it in an ArrayList.
	 * 
	 * @param pathToTheDictionary
	 *            is the String path to the dictionary
	 */
	public ListDictionary(String pathToTheDictionary) {
		wordSignaturePairs = new ArrayList<>();
		try {
			Scanner myfile = new Scanner(new File(pathToTheDictionary));// scanning the whole directory
			while (myfile.hasNextLine()) {
				String currentWord = myfile.nextLine().toLowerCase();
				if (PredictivePrototype.isValidWord(currentWord)) {
					WordSig w2s = new WordSig(currentWord, PredictivePrototype.wordToSignature(currentWord));
					wordSignaturePairs.add(w2s);
				}
			}
			myfile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Please ensure that the directory file exists.");
		} finally {
			Collections.sort(wordSignaturePairs); // sorting the dictionary in the ArrayList, according to the given
													// hint
		}
	}

	/**
	 * This method takes a signature as an input and searches in the dictionary for
	 * any words that can be predicted. The search is done with the help of the
	 * Collections.binarySearch as stated in the Hints. Binary search returns the
	 * index of the first match it finds. So, we have to scan above and below (the
	 * previous and the next respectively) the found index to collect all matching
	 * words.
	 * 
	 * @param signature
	 *            is the signature that we search in the stored dictionary
	 * @return a set with all the matching words with the signature
	 */

	public Set<String> signatureToWords(String signature) {
		Set<String> myPredictiveWords = new HashSet<>();
		WordSig findTheSignature = new WordSig("", signature);
		int index = Collections.binarySearch(wordSignaturePairs, findTheSignature);// according to the hint
		String foundSignature = wordSignaturePairs.get(Math.abs(index)).getSignature();
		int indexForNextWords = Math.abs(index);
		int indexForPreviousWords = indexForNextWords;
		int dictionarySize = wordSignaturePairs.size();
		myPredictiveWords.add(wordSignaturePairs.get(index).getWords()); // add first word found
		// Check for the next words with the same numeric signature
		while (Objects.equals(foundSignature, wordSignaturePairs.get(indexForNextWords).getSignature())) {
			myPredictiveWords.add(wordSignaturePairs.get(indexForNextWords).getWords());
			indexForNextWords++;
			if (!(indexForNextWords >= 0 && indexForNextWords < dictionarySize - 1)) {
				break;// No more words to parse: EOF (end of file)
			}
		}
		// Check for the previous words with the same numeric signature
		while (Objects.equals(foundSignature, wordSignaturePairs.get(indexForPreviousWords).getSignature())) {
			myPredictiveWords.add(wordSignaturePairs.get(indexForPreviousWords).getWords());
			indexForPreviousWords--;
			// checking if it is not in the range of the dictionary
			if (!(indexForPreviousWords >= 0 && indexForPreviousWords < dictionarySize - 1)) {
				break;// No more words to parse
			}
		}
		return myPredictiveWords;
	}
}