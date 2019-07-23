package predictive;

import java.io.FileNotFoundException;

/**
 * 
 * @author Dimitrios Simopoulos
 *
 */
public class Sigs2WordsTree {
	public static void main(String[] args) throws FileNotFoundException {
		TreeDictionary myStoredTreeDictionary = new TreeDictionary("/usr/share/dict/words");
		for (String arg : args) {
			System.out.println(arg + " : " + myStoredTreeDictionary.signatureToWords(arg).toString().replace(",", "")
					.replace("[", "").replace("]", "").trim());
		}
	}
}