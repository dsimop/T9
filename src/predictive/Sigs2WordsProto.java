package predictive;

/**
 * This class is a command-line program to run/test the signatureToWords method.
 * 
 * @author Dimitrios Simopoulos
 *
 */
public class Sigs2WordsProto {
	public static void main(String[] args) {
		for (String arg : args) {
			System.out.println(arg + " : " + PredictivePrototype.signatureToWords(arg).toString().replace(",", "")
					.replace("[", "").replace("]", "").trim());// formatting the output of the print
																// result
		}
	}
}