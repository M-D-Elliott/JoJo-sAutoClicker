package extensions;

import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import Utils.StringUtils;

public class JIntegerFieldMinMax extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int minValue;
	public JIntegerFieldMinMax(String text, int columns, int minValue, int maxValue) {
		super(text, columns);
		this.minValue = minValue;
		((PlainDocument) this.getDocument()).setDocumentFilter(new IntegerFilterMinMax(minValue, maxValue));
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getText() {
		return validate(super.getText());
	}
	
	public String validate(String text) {
		if(StringUtils.empty(text)) {
			text = Integer.toString(minValue);
			super.setText(text);
		}
		return text;
	}
}
