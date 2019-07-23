package predictive;

/**
 * This class pairs the numeric signatures with words.
 * 
 * @author Dimitrios Simopoulos
 *
 */
public class WordSig implements Comparable<WordSig> {

	private String words;
	private String signature;

	/**
	 * This is the Constructor for the WordSig Objects.
	 * 
	 * @param words
	 *            are the words of the dictionary
	 * @param signature
	 *            is the signature of the inputed word
	 */
	public WordSig(String words, String signature) {
		this.words = words;
		this.signature = signature;
	}

	/**
	 * This is a getter for the Signature of a WordSig Object.
	 * 
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * This is a getter for the words of a WordSig Object.
	 * 
	 * @return the words
	 */
	public String getWords() {
		return words;
	}

	/**
	 * This method searches efficiently for signatures.
	 * 
	 * @param ws
	 *            is the inputed WordSig Object that will compare the signatures
	 * @return -1,0,1 depending the result of the compareTo method
	 */
	public int compareTo(WordSig ws) {
		if (this.getSignature().compareTo(ws.getSignature()) > 0) {
			return 1;
		} else if (this.getSignature().compareTo(ws.getSignature()) < 0) {
			return -1;
		} else {
			return 0;
		}
	}
}