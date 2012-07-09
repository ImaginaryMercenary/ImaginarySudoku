package com.imaginarymercenary.sudoku;

import allan.pichardo.Solution;
import allan.pichardo.SudokuBoard;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class BoardActivity extends Activity{

	private LinearLayout boardLayout;
	private Button saveButton;
	private Button evaluateButton;
	private static ToggleButton noteToggleButton;
	private static ToggleButton hintToggleButton;
	private TextView difficultyText;
	private SudokuBoard board;
	
	private int difficulty;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);

		difficultyText = (TextView)findViewById(R.id.difficultyTextView);
		saveButton = (Button)findViewById(R.id.saveButton);
		evaluateButton = (Button)findViewById(R.id.evaluateButton);
		noteToggleButton = (ToggleButton)findViewById(R.id.noteToggleButton);
		hintToggleButton = (ToggleButton)findViewById(R.id.hintToggleButton);
		boardLayout = (LinearLayout)findViewById(R.id.BoardLayout);
		
		Bundle extras = this.getIntent().getExtras();
		difficulty = extras.getInt("difficulty");
		
		init();
	}

	private void init(){
		Solution solution = new Solution();
		
		switch(difficulty){
		case SudokuBoard.EASY:
			difficultyText.setText("Easy");
			board = new SudokuBoard(this,solution,SudokuBoard.EASY,getScreenWidth());
			break;
		case SudokuBoard.MEDIUM:
			difficultyText.setText("Medium");
			board = new SudokuBoard(this,solution,SudokuBoard.MEDIUM,getScreenWidth());
			break;
		case SudokuBoard.HARD:
			difficultyText.setText("Hard");
			board = new SudokuBoard(this,solution,SudokuBoard.HARD,getScreenWidth());
			break;
		}
		
		boardLayout.addView(board);

		/*Don't allow both hint toggle and note toggle to be on
		 * at once. When one is set, the other is unset.
		 */
		hintToggleButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//disable notes
				noteToggleButton.setChecked(false);

				if(!hintToggleButton.isChecked()){
					//unhighlight all squares
					board.unhighlightAll();
				}
				else{
					//check all squares and highlight wrong ones
					board.checkAndHighlightAll();
				}
			}
		});
		noteToggleButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//disable hints
				hintToggleButton.setChecked(false);
			}
		});
	}

	public static boolean hintEnabled(){
		return hintToggleButton.isChecked();
	}

	public static boolean noteEnabled(){
		return noteToggleButton.isChecked();
	}


	private int getScreenWidth(){
		DisplayMetrics displaymetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int wwidth = displaymetrics.widthPixels;
		return wwidth;
	}

}
