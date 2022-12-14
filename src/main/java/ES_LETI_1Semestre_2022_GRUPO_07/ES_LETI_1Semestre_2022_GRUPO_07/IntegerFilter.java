package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * A document filter that restricts the text that can be entered into a text component
 * to only integer values.
 */
class IntegerFilter extends DocumentFilter {

	/**
	 * Called whenever text is inserted into the document. This method first gets the current
	 * text of the document and appends the newly inserted text to it. It then calls the
	 * {@link #verify(String)} method to check if the resulting string is a valid integer.
	 * If it is, the method calls the {@link DocumentFilter#insertString(FilterBypass, int, String, AttributeSet)}
	 * method of its superclass to insert the text into the document. Otherwise, it warns the user
	 * and does not allow the insertion.
	 *
	 * @param fb     The filter bypass that can be used to insert the text directly into the document
	 * @param offset The offset at which the text is to be inserted
	 * @param string The string to be inserted
	 * @param attr   The attributes for the inserted content
	 * @throws BadLocationException If the given offset is invalid
	 */
	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);

		if (verify(sb.toString())) {
			super.insertString(fb, offset, string, attr);
		} else {
			// Warn the user and don't allow the insert
		}
	}


	/**
	 * Verifies that the given input string represents a valid integer.
	 *
	 * @param input The input string to verify.
	 * @return true if the input string is a valid integer, false otherwise.
	 */
	private boolean verify(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}


	/**
	 * Replaces the given range in the document with the given text, if the resulting
	 * document would contain a valid integer.
	 *
	 * @param fb The filter bypass to use for replacing the text.
	 * @param offset The offset at which to replace the text.
	 * @param length The length of the text to replace.
	 * @param text The text to insert into the document.
	 * @param attrs The attributes to associate with the inserted text.
	 * @throws BadLocationException if the given range is not a valid range in the document.
	 */
	@Override
	public void replace(FilterBypass fb, int offset, int length, String text,
			AttributeSet attrs) throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);

		if (verify(sb.toString())) {
			super.replace(fb, offset, length, text, attrs);
		} else {
			// Warn the user and don't allow the insert
		}
	}
}
