package com.imaginarymercenary.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainScreen extends Activity {
	
	RadioButton rbEasy, rbMedium, rbHard;
	RadioGroup difficultyGroup;
	Button newGameButton, resumeGameButton;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        rbEasy = (RadioButton)findViewById(R.id.radio0);
        rbMedium = (RadioButton)findViewById(R.id.radio1);
        rbHard = (RadioButton)findViewById(R.id.radio2);
        newGameButton = (Button)findViewById(R.id.new_game);
        resumeGameButton = (Button)findViewById(R.id.resume);
        difficultyGroup = (RadioGroup)findViewById(R.id.radioGroup1);
        
        newGameButton.setOnClickListener(new NewGameListener());
        resumeGameButton.setOnClickListener(new ResumeGameListener());
    }
    
    class NewGameListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			//check which radio button is selected, and start a new
			//bloard activity of the corresponding difficulty
			if(rbEasy.isChecked()){
				Intent game = new Intent(MainScreen.this,BoardActivity.class);
				game.putExtra("difficulty", 0);
				startActivity(game);
			}else if(rbMedium.isChecked()){
				Intent game = new Intent(MainScreen.this,BoardActivity.class);
				game.putExtra("difficulty", 1);
				startActivity(game);
			}else if(rbHard.isChecked()){
				Intent game = new Intent(MainScreen.this,BoardActivity.class);
				game.putExtra("difficulty", 2);
				startActivity(game);
			}
		}
    	
    }
    
    class ResumeGameListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
    	
    }
}