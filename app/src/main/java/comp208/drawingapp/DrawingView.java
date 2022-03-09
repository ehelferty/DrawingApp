package comp208.drawingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

//A customer drawing view
public class DrawingView extends View {

    String TAG = "--DRAW";
    Paint paint = new Paint();
    ColouredPath path;

    //ArrayList to store list of paths
    //Path is a graphic object - a trail of mouse positions as we move the mouse around.
    //One path is a sequence of mouse positions(points) while left click is held.
    //This list will hold multiple paths that will make up the drawing.
    //Using Colour Paths, we can store different coloured paths.
    ArrayList<ColouredPath> paths = new ArrayList<>();

    /**
     * Constructor for the Drawing View. Automatically sets paint color and style.
     * @param context
     * @param attrs
     */
    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //Initialize paint object to be a specific colour and line width/style
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
    }


    /**
     * Called by MainActivity.
     * Start adding points to the path as mouse is moved around.
     *
     * @param x
     * @param y
     */
    public void addPointToPath(float x, float y)
    {
        if (path.isEmpty())
            path.moveTo(x,y);
        else
            path.lineTo(x,y);

        //This causes the onDraw function to be called.
        invalidate();
    }


    /**
     * Receives the x and y coordinates of the mouse and creates a new path which is then added
     * paths array list.
     * @param x
     * @param y
     */
    public void beginPath(float x, float y){
        path = new ColouredPath();
        path.colour = paint.getColor();
        path.lineWidth = paint.getStrokeWidth();
        path.moveTo(x,y);

        paths.add(path);
    }

    /**
     * Instructs the Canvas instance to draw out the paths being created
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ColouredPath path: paths)
        {
            paint.setColor(path.colour);
            paint.setStrokeWidth(path.lineWidth);
            canvas.drawPath(path, paint);
        }
    }

    /**
     * Receives a colour from MainActivity after a colour button is pressed and sets the current
     * paint colour to the selection.
     * @param colour
     */
    public void setDrawingColour(int colour){
        paint.setColor(colour);
    }


    /**
     * Receives the stroke width from the MainActivity after one of the 3 brush, crayon or pencil
     * buttons is pressed, and sets current paint stroke width to the selected size.
     * @param style
     */
    public void setDrawingStyle(int style){
        paint.setStrokeWidth(style);
    }

    /**
     * When clear button is pressed, this method is called to clear the paths array list,
     * and redraw the canvas.
     */
    public void clearScreen() {
        paths.clear();
        invalidate();
    }

    /**
     * When the undo button is pressed, this method is called to remove the most recent path added
     * to the paths array list, and redraw the canvas without it.
     * Does nothing if paths array list is empty.
     */
    public void undo()
    {
        if(!paths.isEmpty()) {
            paths.remove(paths.size() - 1);
            invalidate();
        }
    }
}
