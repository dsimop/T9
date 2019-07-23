package predictive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * This class stores the inputed Dictionary more efficiently by implementing the
 * HashMap from the Java Collections API. It also implements the Dictionary
 * Interface. The signatures are the keys of the HashMap and the respective
 * words are the values of them.
 * 
 * @author Dimitrios Simopoulos
 *
 */
public class MapDictionary implements Dictionary {
	protected HashMap<String, Set<String>> myHashDictionary;

	/**
	 * This is the constructor of the MapDictionary Objects. It creates a HashMap
	 * that has as keys the signatures and as values a set of words that fit to the
	 * corresponding key/signature.
	 * 
	 * @param pathOfTheDictionary
	 *            is the path of the dictionary file we want to read from
	 */
	public MapDictionary(String pathOfTheDictionary) {
		this.myHashDictionary = new HashMap<>();
		try {
			Scanner wordDict = new Scanner(new File(pathOfTheDictionary));
			while (wordDict.hasNextLine()) {
				String currentWord = wordDict.nextLine().toLowerCase();
				String signature = PredictivePrototype.wordToSignature(currentWord);
				if (PredictivePrototype.isValidWord(currentWord)) {
					myHashDictionary.put(signature, getPredictiveWords(currentWord, signature));
				}
			}
			wordDict.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Please ensure that the directory file exists.");
		}
	}

	/**
	 * This helper method adds all the appropriate words to a HashSet according to
	 * their signatures. If the signature of each word is not already in the Set, it
	 * adds it along with the first word. Otherwise the new word is just added as a
	 * new value to the appropriate key. This method can be considered as a helper
	 * method.
	 * 
	 * @param word
	 *            is the word to be added to the existing HashSet
	 * @param signature
	 *            is the signature of the word that has to be checked if it already
	 *            exists or not
	 * @return a HashSet of words that is going to be used as the values of the
	 *         appropriate key in the HashMap
	 */
	public Set<String> getPredictiveWords(String word, String signature) {
		Set<String> potentialWordsSet = new HashSet<>();
		if (!myHashDictionary.containsKey(signature)) { // if the key does not already exist
			potentialWordsSet.add(word);
			myHashDictionary.put(signature, potentialWordsSet);
		} else {
			potentialWordsSet = myHashDictionary.get(signature);
			potentialWordsSet.add(word);
			myHashDictionary.put(signature, potentialWordsSet);
		}
		return potentialWordsSet;
	}

	/**
	 * This method converts the signature of a word to a word looking into the
	 * HashMap.
	 *
	 * @param signature
	 *            is the numeric signature of the word
	 * @return returns a set of the predictive words, based on theit signature
	 */
	public Set<String> signatureToWords(String signature) {
		if (myHashDictionary.containsKey(signature)) {
			return myHashDictionary.get(signature);
		} else {
			return Collections.emptySet();
		}
	}
}