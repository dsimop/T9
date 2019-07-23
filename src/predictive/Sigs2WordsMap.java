package predictive;

import java.io.FileNotFoundException;

/**
 * This class is a command-line program that uses the MapDictionary class.
 * 
 * @author Dimitrios Simopoulos
 *
 */
public class Sigs2WordsMap {
	public static void main(String[] args) throws FileNotFoundException {
		MapDictionary myStoredMapDictionary = new MapDictionary("/usr/share/dict/words");
		for (String arg : args) {
			System.out.println(arg + " : " + myStoredMapDictionary.signatureToWords(arg).toString().replace(",", "")
					.replace("[", "").replace("]", "").trim());
		}
	}
}