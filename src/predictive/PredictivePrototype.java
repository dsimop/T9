package predictive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * @author Dimitrios Simopoulos
 *
 */
public class PredictivePrototype {

	/**
	 * This method takes a word and returns a numeric signature of it. If a
	 * character of the inputed String is not alphabetical, its signature is the
	 * space " ". Using a StringBuffer is more efficient than a plain String,
	 * because in that way we can have a mutable Object. In that way, we can append
	 * something new to that Object. So, this procedure is faster than creating
	 * every time new String Objects in the for-loop.
	 * 
	 * @param word
	 *            is the inputed String that represents one word
	 * @return
	 */
	public static String wordToSignature(String word) {
		StringBuffer buffer = new StringBuffer();
		word = word.toLowerCase();
		int letter;
		for (int i = 0; i < word.length(); i++) {
			// converting the letter to numeric value from a=0 to z=25
			letter = word.charAt(i) - 97;// 97 is the numeric value of the character 'a', according to ASCII
			if (letter < 0 || letter > 25) {
				buffer.append(" ");
			} else if (letter < 17) {// this if-statement works for the letters between a and r
				buffer.append(letter / 3 + 2);
			} else if (letter < 25) {
				buffer.append((letter - 1) / 3 + 2);// this if-statement works for the letters between s and y
			} else {
				buffer.append(9);// this is for the letter z
			}
		}
		return buffer.toString();
	}

	/**
	 * This method takes the given numeric signature and returns a set of the
	 * predictive matching words from the dictionary. It is inefficient because it
	 * opens the dictionary and parses all of the words in it one by one.
	 * 
	 * @param signature
	 *            is the inputed signature
	 * @return a set with the potential words from the dictionary
	 * @throws FileNotFoundException
	 *             when the dictionary cannot be found
	 */
	public static Set<String> signatureToWords(String signature) {
		Set<String> myPredictiveWords = new HashSet<String>();
		try {
			Scanner s = new Scanner(new File("/usr/share/dict/words"));
			while (s.hasNextLine()) {
				String currentWord = s.nextLine().toLowerCase();
				if (isValidWord(currentWord)) {
					if (Objects.equals(signature, wordToSignature(currentWord))) {
						myPredictiveWords.add(currentWord);
					}
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Please ensure that the directory file exists.");
		}
		return myPredictiveWords;
	}

	/**
	 * This is a helper method that ignores lines with non-alphabetical characters.
	 * 
	 * @param word
	 *            is the inputed word
	 * @return a boolean depending if the word contains alphabetical characters or
	 *         not
	 */
	static boolean isValidWord(String word) {
		word = word.toLowerCase();
		char charCheck;
		for (int i = 0; i < word.length(); i++) {
			charCheck = word.charAt(i);
			if ((charCheck < 97 || charCheck > 122)) {// 97 and 122 are the numeric values of a and z respectively,
														// according to ASCII
				return false;
			}
		}
		return true;
	}
}