package predictive;

/**
 * This class is a command-line program for testing the ListDictionary class.
 *
 * @author Dimitrios Simopoulos
 */
public class Sigs2WordsList {

	public static void main(String[] args) {
		ListDictionary myLD = new ListDictionary("/usr/share/dict/words");
		for (String arg : args) {
			System.out.println(arg + " : "
					+ myLD.signatureToWords(arg).toString().replace(",", "").replace("[", "").replace("]", "").trim());
		}
	}
}