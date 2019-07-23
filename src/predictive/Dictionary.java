package predictive;

import java.util.Set;

/**
 * 
 * @author Dimitrios Simopoulos
 *
 */
public interface Dictionary {
	public Set<String> signatureToWords(String signature);
}