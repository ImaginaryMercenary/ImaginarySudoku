package allan.pichardo;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;

public class SudokuBoard extends TableLayout{

	private Field[][] fields; //fields hold numbers
	private Solution solution;
	private TableRow[] boardRows; //a row of 9 fields
	private Context theContext;
	private TableRow.LayoutParams layout;

	public static final int EASY = 0;
	public static final int MEDIUM = 1;
	public static final int HARD = 2;

	public SudokuBoard(Context context, Solution solution, int difficulty, int width) {
		super(context);
		theContext = context;
		setStretchAllColumns(true);
		
		//setShrinkAllColumns(false);
		this.solution = solution;

		boardRows = new TableRow[9];
		fields = new Field[9][9];
		
		//set the width/height of cells
		layout = new TableRow.LayoutParams((width/10),(width/10));

		//populate the board
		switch(difficulty){
		case EASY:
			init(35);
			break;
		case MEDIUM:
			init(25);
			break;
		case HARD:
			init(16);
			break;
		}
		
	}

	private void init(int free){
		/* Initiate the sudoku board by
		 * instantiating the 9 rows with
		 * nine fields and providing
		 * the appropriate number of free
		 * tiles
		 */
		int freebies = free;

		for(int i=0;i<9;++i){
			boardRows[i] = new TableRow(theContext);
			layout.gravity = Gravity.CENTER_VERTICAL;
			for(int j=0;j<9;++j){
				int yesNo = (int)(Math.random()*2);
				if(yesNo != 0 && freebies > 0){
					//this one is a freebie
					fields[i][j] = new Field(theContext,solution.getSolution()[i][j],setColor(i,j),solution.getSolution()[i][j]);
					//fields[i][j].setBackgroundColor();
					boardRows[i].addView(fields[i][j],layout);
					--freebies;
				}
				else{
					//blank
					fields[i][j] = new Field(theContext,setColor(i,j),solution.getSolution()[i][j]);
					//fields[i][j].setBackgroundColor(setColor(i,j));
					boardRows[i].addView(fields[i][j],layout);
				}
			}
			addView(boardRows[i]);
			
		}

	}
	
	public void checkAndHighlightAll(){
		//check all fields and highlight wrong ones
		for(int i = 0; i < 9; ++i){
			for(int j = 0; j < 9; ++j){
				if(!fields[i][j].isEmpty() && !fields[i][j].isNote()){
					fields[i][j].checkAndHighlight();
				}
			}
		}
	}
	
	public void unhighlightAll(){
		for(int i = 0; i < 9; ++i){
			for(int j = 0; j < 9; ++j){
				if(!fields[i][j].isNote())
					fields[i][j].returnToNormal();
			}
		}
	}
	
	private int setColor(int row, int col){
		int sg = 1;
		int color = 0;
		
		if(row >= 0 && row <= 2){
			if(col >= 0 && col <= 2){
				sg = 1;
			}
			if(col >= 3 && col <= 5){
				sg = 2;
			}
			if(col >= 6 && col <= 8){
				sg = 3;
			}
		}
		if(row >= 3 && row <= 5){
			if(col >= 0 && col <= 2){
				sg = 4;
			}
			if(col >= 3 && col <= 5){
				sg = 5;
			}
			if(col >= 6 && col <= 8){
				sg = 6;
			}
		}
		if(row >= 6 && row <= 8){
			if(col >= 0 && col <= 2){
				sg = 7;
			}
			if(col >= 3 && col <= 5){
				sg = 8;
			}
			if(col >= 6 && col <= 8){
				sg = 9;
			}
		}
		
		if(sg%2 == 0){
			color = Color.WHITE;
		}
		else{
			color = Color.LTGRAY;
		}
		
		return color;
	}
	

}
