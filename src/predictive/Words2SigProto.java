package predictive;

/**
 * This class is a command-line program to run the wordToSignature method.
 * 
 * @author Dimitrios Simopoulos
 *
 */
public class Words2SigProto {
	public static void main(String[] args) {
		for (String arg : args) {
			System.out.println(PredictivePrototype.wordToSignature(arg));
		}
	}
}