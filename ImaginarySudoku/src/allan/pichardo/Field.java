package allan.pichardo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

import com.imaginarymercenary.sudoku.BoardActivity;

public class Field extends EditText{

	/*this is a square field. the user can touch
	 * it and select a number to fill in
	 */

	private boolean isPreset = false;
	private boolean isNote = false;
	private int color; //background color
	private int textColor; //text color
	private int localSolution; //what is the actual solution here

	public Field(Context context,int bgColor, int lSolution){
		super(context);
		setBackgroundColor(bgColor);
		color = bgColor;
		textColor = Color.BLACK;
		setTextColor(textColor);
		isPreset = false;
		localSolution = lSolution;
		init();
	}

	public Field(Context context, int value, int bgColor, int lSolution){
		super(context);
		setBackgroundColor(bgColor);
		color = bgColor;
		textColor = Color.RED;
		setTextColor(textColor);
		isPreset = true;
		setText(String.valueOf(value));
		localSolution = lSolution;
		init();
	}
	@Override
	protected void onTextChanged(CharSequence text, int start,
			int lengthBefore, int lengthAfter) {
		// trigger the auto-checking to provide hints
		//make background red if wrong.
		
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
		
		if(!isEmpty() && BoardActivity.hintEnabled()){
			if(!isCorrect())
				setBackgroundColor(Color.RED);
			else
				setBackgroundColor(color);
		}
		
		if(BoardActivity.noteEnabled()){
			isNote = true;
			Log.d("note","set note");
			setTextColor(Color.BLUE);
			setTextSize(10);
			
		}
		else{
			setTextSize(13);
			setTextColor(textColor);
			isNote = false;
		}
		
		if(isEmpty())
			returnToNormal();
		
		//Log.d("Text Changed text",text.toString());
		//Log.d("text changed start",String.valueOf(start));
		//Log.d("text changed len before",String.valueOf(lengthBefore));
		//Log.d("text changed len after",String.valueOf(lengthAfter));
		
		if(text.length() > 1){
			text = text.subSequence(text.length()-1, length());
			setText(text);
		}
		if(text.toString().equalsIgnoreCase(String.valueOf(0))){
			setText("");
		}
	}
	
	public boolean isCorrect(){
		//return true if this square is correct
		//Log.d(getText().toString(),String.valueOf(localSolution));
		return getText().toString().equalsIgnoreCase(String.valueOf(localSolution));
	}
	
	public void checkAndHighlight(){
		//check if correct and highlight
		if(!isCorrect())
			setBackgroundColor(Color.RED);
	}
	
	public void returnToNormal(){
		setBackgroundColor(color);
		setTextSize(13);
		setTextColor(textColor);
	}
	
	public boolean isEmpty(){
		return getText().toString().equalsIgnoreCase("");
	}
	
	public boolean isNote(){
		return isNote;
	}
	
	private void init(){
		if(!isPreset){
			//set up focus change listener (indicate square selected)
			setOnFocusChangeListener(new OnFocusChangeListener(){

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if(hasFocus){
						v.setBackgroundColor(Color.YELLOW);
						
					}
					else{
						if(!isCorrect() && BoardActivity.hintEnabled() && !isEmpty()){
							//do nothing
							//Log.d("focus","lose focus do nothing");
						}
						else
							v.setBackgroundColor(color);
						
						if(!isEmpty()){
							
						}
					}
				}

			});
			
			setTextColor(Color.BLACK);
			
		}
		else{
			setTextColor(Color.RED);
			setKeyListener(null);
			setEnabled(false);
		}
		setGravity(Gravity.BOTTOM);
		setTextSize(13);
		setInputType(InputType.TYPE_CLASS_NUMBER);
		setFocusableInTouchMode(true);
		setCursorVisible(false);
	}

	@Override
	public InputConnection onCreateInputConnection(final EditorInfo outAttrs)
	{
		outAttrs.imeOptions |= EditorInfo.IME_ACTION_DONE;
		return (super.onCreateInputConnection(outAttrs));
	}


	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Rect rect = new Rect();
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(3);
		getLocalVisibleRect(rect);
		canvas.drawRect(rect, paint);       
	}

}
