package allan.pichardo;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.widget.TextView;

public class MyInputConnection extends BaseInputConnection{
	
	private SpannableStringBuilder _editable;
	TextView _textView;

	public MyInputConnection(View targetView, boolean fullEditor) {
		super(targetView, fullEditor);
		_textView = (TextView) targetView;
	}
	
	public Editable getEditable(){
		if(_editable == null){
			_editable = (SpannableStringBuilder)Editable.Factory.getInstance().newEditable("P");
		}
		return _editable;
	}
	
	public boolean commitText(CharSequence text, int newCursorPosition){
		_editable.append(text);
		_textView.setText(text);
		return true;
	}

}
