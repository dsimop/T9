
import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * This class represents the view. Essentially here we create the JFrame
 * (top-level container), and all the classes which make up the complete GUI.
 *
 */

public class View {
	public static void main(String[] args) throws Exception {
		javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());

		DictionaryModel model = new DictionaryModel();
		KeypadPane keypad = new KeypadPane(model);
		MessagePane view = new MessagePane(model);

		JFrame gui = new JFrame();
		gui.setLayout(new BorderLayout());
		gui.add(keypad, BorderLayout.SOUTH);
		gui.add(view, BorderLayout.CENTER);

		gui.pack();
		gui.setTitle("Predictive Text");
		gui.setVisible(true);

		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
