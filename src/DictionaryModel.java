import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import predictive.TreeDictionary;

/**
 * This class is the Model class. It implements the DictionaryModelInterface and
 * extends the Observable class. The DictionaryModel uses the dictionary from
 * the given path and predicts the possible words according to the typed
 * signature of the user.
 * 
 * @author Dimitrios Simopoulos
 *
 */
public class DictionaryModel extends Observable implements DictionaryModelInterface {
	private TreeDictionary dict;
	private String currentSignature;
	private List<String> currentMatches;
	private List<String> message;
	private int index;

	/**
	 * This is the first constructor.
	 * 
	 * @param pathOfDict
	 *            is the path of the given dictionary
	 * @throws IOException
	 */
	public DictionaryModel(String pathOfDict) throws IOException {
		this.dict = new TreeDictionary(pathOfDict);
		this.message = new ArrayList<String>();
		startNewWordEntry();
	}

	/**
	 * This is the second constructor which uses a default dictionary path.
	 * 
	 * @throws IOException
	 */
	public DictionaryModel() throws IOException {
		this("/usr/share/dict/words");
	}

	/**
	 * This method is called whenever a new word is typed (after pressing the "0").
	 */
	private void startNewWordEntry() {
		this.currentMatches = new ArrayList<String>();
		this.currentMatches.add("");
		this.currentSignature = "";
		this.index = 0;
	}

	/**
	 * This method returns the current word that is displayed.
	 * 
	 * @return the current word
	 */
	private String getCurrentWord() {
		if (this.currentMatches.size() > 0) {
			return (String) this.currentMatches.get(this.index).substring(0, currentSignature.length());
		}
		return "";
	}

	/**
	 * This method returns the displayed text in the GUI.
	 * 
	 * @return an ArrayList where the typed words are stored
	 */
	@Override
	public List<String> getMessage() {
		ArrayList<String> myArrayList = new ArrayList<String>(this.message);
		myArrayList.add(getCurrentWord());
		return myArrayList;
	}

	/**
	 * This method adds a numeric key that has been typed in by the user. It also
	 * extends the current word (or prefix) with possible matches for the new key.
	 */
	@Override
	public void addCharacter(char key) {
		if (key == '0') {
			this.message.add(getCurrentWord());
			startNewWordEntry();
		} else if (key == '*') {
			nextMatch();
		} else if (key == 'C') {
			if (this.currentSignature.length() > 0) {
				removeLastCharacter();
			}
		}
		ArrayList<String> localArrayList = new ArrayList<String>(
				this.dict.signatureToWords(this.currentSignature + key));
		if (localArrayList.size() > 0) {
			this.currentSignature += key;
			this.currentMatches = localArrayList;
			this.index = 0;
			if (this.index >= this.currentMatches.size()) {
				this.index = 0;
			}
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * This method removes the last character typed in. It also removes the last
	 * character from the current match, but in general it enlarges the possible
	 * matches for the current word.
	 */
	@Override
	public void removeLastCharacter() {
		if (this.currentSignature.length() > 0) {
			this.currentSignature = this.currentSignature.substring(0, this.currentSignature.length() - 1);
			if (currentSignature.isEmpty()) {
				currentMatches = new ArrayList<>();
			} else {
				this.currentMatches = new ArrayList<String>(this.dict.signatureToWords(this.currentSignature));

				this.index = getCurrentWord().length();
			}
		} else {
			this.currentMatches = new ArrayList<String>();
			this.currentMatches.add("");
			this.index = 0;
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * This method cycles through the possible matches for the current word, i.e.,
	 * changes the current word to the next match if there is one, or goes back to
	 * the first match otherwise.
	 */
	@Override
	public void nextMatch() {
		// Editing the currentMatches list to have only the distinct prefixes of the
		// typed signature. This is done by using the HashSet.
		for (int i = 0; i < currentMatches.size(); i++) {
			currentMatches.set(i, currentMatches.get(i).substring(0, currentSignature.length()));
		}
		Set<String> helpSet = new HashSet<String>();
		helpSet.addAll(currentMatches);
		currentMatches.clear();
		currentMatches.addAll(helpSet);
		Collections.sort(currentMatches);
		if (index == currentMatches.size() - 1) {
			index = -1;
		}
		currentMatches.get(++index);
		setChanged();
		notifyObservers();
	}

	/**
	 * This method accepts the current matched word and adds it to the composed
	 * message.
	 */
	@Override
	public void acceptWord() {
		this.message.add(getCurrentWord() + " ");
		startNewWordEntry();
	}
}