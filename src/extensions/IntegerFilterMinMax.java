package extensions;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

import Utils.IntUtils;
import Utils.StringUtils;

public class IntegerFilterMinMax extends DocumentFilter {
	private int minValue;
	private int maxValue;
//	private int minLength;
	private int maxLength;
	
	public IntegerFilterMinMax(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		if(maxValue < minValue) {
			maxValue = minValue;
		}
//		if(minValue == 0) {
//			this.minLength = minValue;
//		} else {
//			this.minLength = IntUtils.length(minValue);
//		}
		this.maxLength = IntUtils.length(maxValue);
	}

	@Override
	public void insertString(FilterBypass fb, int offset, String string,
	     AttributeSet attr) throws BadLocationException {
			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.insert(offset, string);
		  if (test(sb.toString())) {
		     super.insertString(fb, offset, string, attr);
		  } else {
		     // warn the user and don't allow the insert
		  }
	}

    private boolean test(String text) {
		try {
			Integer.parseInt(text);
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
	   if (test(text) && text != null) {
		   if(text.length() + doc.getLength() <= maxLength) {
			   super.replace(fb, offset, length, text, attrs);
		   } else {
			   int firstDigit = Integer.parseInt(sb.toString().substring(0, 1));
			   if(firstDigit >= 1 && offset + length >= maxLength) {
				   super.replace(fb, 0, doc.getLength(), Integer.toString(maxValue), attrs);
			   }
		   }

	   } else {
	      // warn the user and don't allow the insert
	   }
   }

   @Override
   public void remove(FilterBypass fb, int offset, int length)
         throws BadLocationException {
      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.delete(offset, offset + length);
      if(test(sb.toString())) {
          if(Integer.parseInt(sb.toString()) < minValue) { 
        	  super.replace(fb, 0, doc.getLength(), Integer.toString(minValue), null); 
    	  } else {  
        	  super.remove(fb, offset, length);
    	  }
      } else if(minValue < 1 && StringUtils.empty(sb.toString())) {
    	  super.remove(fb, offset, length);
      }
   }
}
