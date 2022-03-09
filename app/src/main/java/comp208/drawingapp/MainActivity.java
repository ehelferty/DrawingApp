package comp208.drawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * The Main Activity class the controls the OnTouchListeners and OnClickListeners, as well as
 * instantiates the drawing view for use.
 */
public class MainActivity extends AppCompatActivity {

    final int numColours = 8;
    final int numTools = 5;

    TableLayout tools;
    DrawingView drawingView;

    /**
     * Instructions on what to do when app instance is created.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tools = findViewById(R.id.tools);
        drawingView = findViewById(R.id.drawingView);

        init();

        /**
         * The onTouchListener that senses MotionEvents on the screen and calls the
         * DrawingView methods for creating and drawing paths using the x,y coordinates of the
         * contact locations.
         */
        drawingView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                //Want to get the type of action mouse was just used for
                int action = motionEvent.getActionMasked();

                switch(action)
                {
                    case MotionEvent.ACTION_DOWN:
                        drawingView.beginPath(motionEvent.getX(), motionEvent.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_UP:
                        drawingView.addPointToPath(motionEvent.getX(), motionEvent.getY());
                        break;
                }
                return true;
            }
        });



    }

    /**
     * OnClickListener for all the tool buttons. Identifies which button was clicked via the button
     * id, and calls the appropriate drawing view method.
     */
    View.OnClickListener btnListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View button)
        {
            Button btnClicked = (Button) button;

            //Find out which button was clicked and react accordingly
            switch (btnClicked.getId())
            {
                case R.id.btnRed:
                    drawingView.setDrawingColour(Color.RED);
                    break;
                case R.id.btnYellow:
                    drawingView.setDrawingColour(Color.YELLOW);
                    break;
                case R.id.btnGreen:
                    drawingView.setDrawingColour(Color.GREEN);
                    break;
                case R.id.btnCyan:
                    drawingView.setDrawingColour(Color.CYAN);
                    break;
                case R.id.btnBlue:
                    drawingView.setDrawingColour(Color.BLUE);
                    break;
                case R.id.btnPink:
                    drawingView.setDrawingColour(Color.MAGENTA);
                    break;
                case R.id.btnBlack:
                    drawingView.setDrawingColour(Color.BLACK);
                    break;
                case R.id.btnWhite:
                    drawingView.setDrawingColour(Color.WHITE);
                    break;
                case R.id.btnBrush:
                    drawingView.setDrawingStyle(30);
                    break;
                case R.id.btnCrayon:
                    drawingView.setDrawingStyle(15);
                    break;
                case R.id.btnPencil:
                    drawingView.setDrawingStyle(5);
                    break;
                case R.id.btnClear:
                    drawingView.clearScreen();
                    break;
                case R.id.btnUndo:
                    drawingView.undo();
                    break;
            }
        }
    };

    /**
     * Initiate drawing board and connect tool buttons to click listener. Iterates through
     * each column of each table row and assigns the buttons to the OnClickListener.
     */
    private void init()
    {

        //Iterate through first Table Row of tools table and connect colour buttons to the onClickListener
        for (int row = 0; row < 1; row++) {
            TableRow coloursRow = (TableRow) tools.getChildAt(row);

            for (int col = 0; col < numColours; col++) {
                Button colourBtn = (Button) coloursRow.getChildAt(col);

                colourBtn.setOnClickListener(btnListener);
            }
        }

        //Iterate through second Table row of tools and connect tool buttons to the onClickListener
        for (int row = 1; row < 2; row++) {
            TableRow toolsRow = (TableRow)  tools.getChildAt(row);

            for (int col = 0; col < numTools; col++) {
                Button toolBtn = (Button) toolsRow.getChildAt(col);

                toolBtn.setOnClickListener(btnListener);
            }
        }
    }

}