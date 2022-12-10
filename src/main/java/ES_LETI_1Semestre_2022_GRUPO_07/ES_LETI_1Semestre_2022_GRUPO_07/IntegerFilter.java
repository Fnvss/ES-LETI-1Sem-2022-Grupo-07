package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

class IntegerFilter extends DocumentFilter {
	@Override
	public void insertString(FilterBypass fb, int offset, String string,
			AttributeSet attr) throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);

		if (verify(sb.toString())) {
			super.insertString(fb, offset, string, attr);
		} else {
			// warn the user and don't allow the insert
		}
	}

	private boolean verify(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

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
			// warn the user and don't allow the insert
		}

	}



}
