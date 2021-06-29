package edu.wm.cs.cs301.nickwilson.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;

import edu.wm.cs.cs301.nickwilson.R;


public class MazePanel extends View implements P5Panel {
    /**
     * A bitmap to draw in.
     */
    private Bitmap myBitmap;
    /**
     * The paint object the MazePanel uses to determine how to draw
     * lines on the screen.
     */
    private Paint myLinePaint;
    /**
     * The paint object the MazePanel uses to draw filled shapes.
     */
    private Paint myFilledPaint;
    /**
     * The canvas object the MazePanel uses to actually draw things.
     */
    private Canvas myCanvas;
    /**
     * A Path object to draw certain things like filled polygons.
     */
    private Path myPath;
    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     *
     * <p>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public MazePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        myLinePaint = new Paint();
        myFilledPaint = new Paint();
        myFilledPaint.setStyle(Paint.Style.FILL);
        myBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        myCanvas = new Canvas(myBitmap);
        myPath = new Path();
    }
    /** Takes in color integer values [0-255], returns corresponding color-int
     value. @param integer color values for red green and blue
     */
    public static int getColorEncoding(int red, int green, int blue) {
        int rgb = (red << 16 | green << 8 | blue);
        return rgb;
    }
    /**
     * Commits all accumulated drawings to the UI.
     * Substitute for MazePanel.update method.
     */
    @Override
    public void commit() {
        myCanvas.drawBitmap(myBitmap, 0, 0,myFilledPaint);
    }

    /**
     * Tells if instance is able to draw. This ability depends on the
     * context, for instance, in a testing environment, drawing
     * may be not possible and not desired.
     * Substitute for code that checks if graphics object for drawing is not null.
     *
     * @return true if drawing is possible, false if not.
     */
    @Override
    public boolean isOperational() {
        return myLinePaint != null && myCanvas != null && myFilledPaint != null;
    }

    /**
     * Sets the color for future drawing requests. The color setting
     * will remain in effect till this method is called again and
     * with a different color.
     * Substitute for Graphics.setColor method.
     *
     * @param rgb gives the red green and blue encoded value of the color
     */
    @Override
    public void setColor(int rgb) {
        myFilledPaint.setColor(rgb);
        myLinePaint.setColor(rgb);
    }

    /**
     * Returns the RGB value for the current color setting.
     *
     * @return integer RGB value
     */
    @Override
    public int getColor() {
        return myFilledPaint.getColor();
    }

    /**
     * Draws two solid rectangles to provide a background.
     * Note that this also erases any previous drawings.
     * The color setting adjusts to the distance to the exit to
     * provide an additional clue for the user.
     * Colors transition from black to gold and from grey to green.
     * Substitute for FirstPersonView.drawBackground method.
     *
     * @param percentToExit gives the distance to exit
     */
    @Override
    public void addBackground(float percentToExit) {
        //Color the top rectangle
        setColor(getBackgroundColor(percentToExit, true));
        //Draw the top rectangle
        addFilledRectangle(0, 0, getWidth(), getHeight() / 2);
        //Color the bottom rectangle
        setColor(getBackgroundColor(percentToExit, false));
        addFilledRectangle(0, getHeight() / 2, getWidth(), getHeight() / 2);
    }
    /**
     * Determine the background color for the top and bottom
     * rectangle as a blend between starting color settings
     * of black and grey towards gold and green as final
     * color settings close to the exit
     * @param percentToExit describes how far it is to the exit as a percentage value
     * @param top is true for the top rectangle, false for the bottom
     * @return the color to use for the background rectangle
     */
    private int getBackgroundColor(float percentToExit, boolean top){
        if(top){
            return ColorUtils.blendARGB(Color.BLACK, getResources().getColor(R.color.Gold), percentToExit);
        }
        else{
            return ColorUtils.blendARGB(Color.GRAY, Color.GREEN, percentToExit);
        }
    }
    /**
     * Adds a filled rectangle.
     * The rectangle is specified with the {@code (x,y)} coordinates
     * of the upper left corner and then its width for the
     * x-axis and the height for the y-axis.
     * Substitute for Graphics.fillRect() method
     *
     * @param x      is the x-coordinate of the top left corner
     * @param y      is the y-coordinate of the top left corner
     * @param width  is the width of the rectangle
     * @param height is the height of the rectangle
     */
    @Override
    public void addFilledRectangle(int x, int y, int width, int height) {
        RectF rectCoords = new RectF(x, y, x +width, y+height);
        myCanvas.drawRect(rectCoords, myFilledPaint);
    }

    /**
     * Adds a filled polygon.
     * The polygon is specified with {@code (x,y)} coordinates
     * for the n points it consists of. All x-coordinates
     * are given in a single array, all y-coordinates are
     * given in a separate array. Both arrays must have
     * same length n. The order of points in the arrays
     * matter as lines will be drawn from one point to the next
     * as given by the order in the array.
     * Substitute for Graphics.fillPolygon() method
     *
     * @param xPoints are the x-coordinates of points for the polygon
     * @param yPoints are the y-coordinates of points for the polygon
     * @param nPoints is the number of points, the length of the arrays
     */
    @Override
    public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        if(nPoints < 2){
            return;//To avoid errors
        }
        myPath.reset();//Reset myPath to avoid carrying over data from previous draws
        for(int i = 0; i < nPoints; i++){ //Add every point to the path
            myPath.moveTo(xPoints[i], yPoints[i]);
        }
        myPath.moveTo(xPoints[0], yPoints[0]); //Close out the path by returning to the start
        myCanvas.drawPath(myPath, myFilledPaint);
    }

    /**
     * Adds a polygon.
     * The polygon is not filled.
     * The polygon is specified with {@code (x,y)} coordinates
     * for the n points it consists of. All x-coordinates
     * are given in a single array, all y-coordinates are
     * given in a separate array. Both arrays must have
     * same length n. The order of points in the arrays
     * matter as lines will be drawn from one point to the next
     * as given by the order in the array.
     * Substitute for Graphics.drawPolygon method
     *
     * @param xPoints are the x-coordinates of points for the polygon
     * @param yPoints are the y-coordinates of points for the polygon
     * @param nPoints is the number of points, the length of the arrays
     */
    @Override
    public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        if(nPoints < 2){ //To avoid IndexOutOfBoundsExceptions
            return;
        }
        addLine(xPoints[nPoints - 1], yPoints[nPoints - 1], xPoints[0], yPoints[0]);
        //Draw a line between the last point and the first point
        for(int i = 0; i < nPoints - 1; i++){ //Draw lines between the other
            //pairs of adjacent points
            addLine(xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1]);
        }
    }

    /**
     * Adds a line.
     * A line is described by {@code (x,y)} coordinates for its
     * starting point and its end point.
     * Substitute for Graphics.drawLine method
     *
     * @param startX is the x-coordinate of the starting point
     * @param startY is the y-coordinate of the starting point
     * @param endX   is the x-coordinate of the end point
     * @param endY   is the y-coordinate of the end point
     */
    @Override
    public void addLine(int startX, int startY, int endX, int endY) {
        myCanvas.drawLine(startX, startY, endX, endY, myLinePaint);
    }

    /**
     * Adds a filled oval.
     * The oval is specified with the {@code (x,y)} coordinates
     * of the upper left corner and then its width for the
     * x-axis and the height for the y-axis. An oval is
     * described like a rectangle.
     * Substitute for Graphics.fillOval method
     *
     * @param x      is the x-coordinate of the top left corner
     * @param y      is the y-coordinate of the top left corner
     * @param width  is the width of the oval
     * @param height is the height of the oval
     */
    @Override
    public void addFilledOval(int x, int y, int width, int height) {
        RectF ovalCoords = new RectF(x, y, x+width, y+height);
        myCanvas.drawOval(ovalCoords, myFilledPaint);
    }

    /**
     * Adds the outline of a circular or elliptical arc covering the specified rectangle.
     * The resulting arc begins at startAngle and extends for arcAngle degrees,
     * using the current color. Angles are interpreted such that 0 degrees
     * is at the 3 o'clock position. A positive value indicates a counter-clockwise
     * rotation while a negative value indicates a clockwise rotation.
     * The center of the arc is the center of the rectangle whose origin is
     * (x, y) and whose size is specified by the width and height arguments.
     * The resulting arc covers an area width + 1 pixels wide
     * by height + 1 pixels tall.
     * The angles are specified relative to the non-square extents of
     * the bounding rectangle such that 45 degrees always falls on the
     * line from the center of the ellipse to the upper right corner of
     * the bounding rectangle. As a result, if the bounding rectangle is
     * noticeably longer in one axis than the other, the angles to the start
     * and end of the arc segment will be skewed farther along the longer
     * axis of the bounds.
     * Substitute for Graphics.drawArc method
     *
     * @param x          the x coordinate of the upper-left corner of the arc to be drawn.
     * @param y          the y coordinate of the upper-left corner of the arc to be drawn.
     * @param width      the width of the arc to be drawn.
     * @param height     the height of the arc to be drawn.
     * @param startAngle the beginning angle.
     * @param arcAngle   the angular extent of the arc, relative to the start angle.
     */
    @Override
    public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
         RectF arcCoords = new RectF(x, y, x+width, y+height);
         myCanvas.drawArc(arcCoords, startAngle, arcAngle, false, myLinePaint);
    }

    /**
     * Adds a string at the given position.
     * Substitute for CompassRose.drawMarker method
     *
     * @param x   the x coordinate
     * @param y   the y coordinate
     * @param str the string
     */
    @Override
    public void addMarker(float x, float y, String str) {
       myCanvas.drawText(str, x, y, myLinePaint);
    }
}
