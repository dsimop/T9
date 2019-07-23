package predictive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author Dimitrios Simopoulos
 *
 */
public class TreeDictionary implements Dictionary {
	// The Tree has up to 8 branches/children, one for each number (2-9)
	int numberOfNodes = 8;

	protected Set<String> words;
	protected TreeDictionary[] treeDictionary = new TreeDictionary[numberOfNodes];

	/**
	 * This constructor creates the empty trees and an empty HashSet.
	 */
	private TreeDictionary() {
		for (int i = 0; i < treeDictionary.length; i++) {
			this.treeDictionary[i] = null;
		}
		this.words = new HashSet<>();
	}

	/**
	 * This constructor creates/manipulates the trees and the HashSet where the
	 * words are going to be stored. If each parsed word of the dictionary is valid,
	 * we get its signature. By finding the first number of the signature of the
	 * word, we see in which branch fits. Note: The numpad is from 2 to 9, but the
	 * array is from 0 to 7.
	 * 
	 * @param pathOfTheDictionary
	 *            is the path of the dictionary
	 */
	public TreeDictionary(String pathOfTheDictionary) {
		for (int i = 0; i < treeDictionary.length; i++) {
			this.treeDictionary[i] = new TreeDictionary();
		}
		this.words = new HashSet<>();
		try {
			Scanner wordDict = new Scanner(new File(pathOfTheDictionary));
			while (wordDict.hasNextLine()) {
				String currentWord = wordDict.nextLine().toLowerCase();
				if (PredictivePrototype.isValidWord(currentWord)) {
					String signature = PredictivePrototype.wordToSignature(currentWord);
					int numberPressed = Integer.parseInt(signature.substring(0, 1));// the first number of the signature
					// numberPressed - 2, because the tree is between 0-7, not between 2-9
					this.treeDictionary[numberPressed - 2].addingToTree(currentWord, signature, 1);
				}
			}
			wordDict.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Please ensure that the directory file exists.");
		}
	}

	/**
	 * This is a helper method which creates new trees, whenever it is needed from
	 * the TreeDictionary. It actually builds recursively the final tree by parsing
	 * character by character the inputed word.
	 * 
	 * @param word
	 *            is the word we want to add to the Tree
	 * @param signature
	 *            is the signature of the inputed word
	 * @param position
	 *            is the current character of the word that is parsed
	 */
	public void addingToTree(String word, String signature, int position) {
		this.words.add(word);
		if (position < signature.length()) {
			int index = Integer.parseInt(signature.substring(position, position + 1));
			position++; // go on to the next letter
			if (this.treeDictionary[index - 2] == null) {
				this.treeDictionary[index - 2] = new TreeDictionary();// re-creating a new Tree, if the specific node
																		// is
																		// empty
			}
			// calling recursively - the currentParsedCharacter is incremented now
			this.treeDictionary[index - 2].addingToTree(word, signature, position);
		}
	}

	/**
	 * This method returns a HashSet of the prefix-matches of the inputed signature.
	 * A helper method is used for this purpose that trims all the words in a given
	 * list to produce the output of the signatureToWords method.
	 * 
	 * @param signature
	 *            is the inputed signature
	 * @return a HashSet of the prefix-matches
	 */
	public Set<String> signatureToWords(String signature) {
		if (+signature.length() == 0) {
			Set<String> currentNodeWords = new TreeSet<String>(this.words);
			currentNodeWords.addAll(this.signatureToWordsHelper());
			return currentNodeWords;
		} else {
			int position = Integer.parseInt(signature.substring(0, 1)) - 2;
			if (this.treeDictionary[position] == null) {
				return new TreeSet<>();
			}
			TreeDictionary possiblePrefixesTree = this.treeDictionary[position];
			return possiblePrefixesTree.signatureToWords(signature.substring(1));
		}
	}

	/**
	 * This helper method is recursive and returns a TreeSet of the prefix-matching
	 * words. This method is called from the signatureToWords method.
	 * 
	 * @return a TreeSet of the prefix-matching words
	 */
	private Set<String> signatureToWordsHelper() {
		Set<String> words = new TreeSet<String>();
		// parsing the whole treeDictionary TreeSet
		for (int i = 0; i < this.treeDictionary.length; i++) {
			if (this.treeDictionary[i] != null) {
				words.addAll(this.treeDictionary[i].words);
				words.addAll(this.treeDictionary[i].signatureToWordsHelper());
			}
		}
		return words;
	}
}