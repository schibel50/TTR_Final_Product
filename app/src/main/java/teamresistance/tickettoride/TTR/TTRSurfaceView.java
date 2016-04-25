package teamresistance.tickettoride.TTR;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.graphics.Path;
import android.graphics.Rect;
import java.io.Serializable;
import java.util.ArrayList;

import teamresistance.tickettoride.R;

/**
 * This is a surface view class that draws the game board/pieces onto the screen.
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version March 2016
 */

public class TTRSurfaceView extends SurfaceView implements Serializable {
    private static final long serialVersionUID = 388245561312013L;

    Bitmap boardImage = BitmapFactory.decodeResource(getResources(), R.drawable.game_board);
    /*
    * Booleans relating to the placement process for trains
    */
    private boolean[] highlights;
    private boolean[] selected;
    private boolean[] covered;
    private int[] playerIDs;
    private boolean[] highlights2;
    private boolean[] selected2;
    private boolean[] covered2;
    private int[] playerIDs2;
    protected int maxX = 1720;
    protected int maxY = 980;

    private Path pathTemp = new Path();
    private Path pathTemp2 = new Path();
    private Path blankPath = null;
    private Rect tempRect;
    /*
     * These ints correspond to hex colors used by this class
     */
    final int PLAYER1_COLOR = 0xFFFF0000;
    final int PLAYER2_COLOR = 0xFF0000FF;
    final int PLAYER3_COLOR = 0xFF00FF00;
    final int PLAYER4_COLOR = 0xFFFFFF00;
    final int HIGHLIGHT_COLOR = 0xFF00FF00;
    final int SELECTION_COLOR = 0xFF00ffff;
    final int OUTLINE_COLOR = 0xFF000000;

    private Paint paint = new Paint();

    //path initializations
    ArrayList<Path> paths = new ArrayList<Path>();
    ArrayList<Path> paths2 = new ArrayList<Path>();
    ArrayList<Rect> touchRects = new ArrayList<Rect>();

    // TicketToRideSurfaceView constructor
    public TTRSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotCacheDrawing(false);


        /**
         External Citation
         Date: 4/21/16
         Problem: paths would not be drawn when called from paths Arraylist
         Resource:
         http://stackoverflow.com/questions/20871589/adding-path-objects-to-arraylist
         Solution: In this post, someone recommended using Paths.add(new Path(path))
         instead of using Paths.add(path) because the second one only creates an
         address. Therefore, I implemented the suggestion after every path creation.
         */
        //creates a path and a rect for each track

        //0
        tempRect = new Rect((int) (maxX * .0), (int) (maxY * .290), (int) (maxX * .05), (int) (maxY * .8));
        pathTemp.moveTo(maxX * .028f, maxY * .286f);
        pathTemp.lineTo(maxX * .016f, maxY * .333f);
        pathTemp.lineTo(maxX * .026f, maxY * .341f);
        pathTemp.lineTo(maxX * .038f, maxY * .294f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .015f, maxY * .345f);
        pathTemp.lineTo(maxX * .009f, maxY * .398f);
        pathTemp.lineTo(maxX * .019f, maxY * .398f);
        pathTemp.lineTo(maxX * .024f, maxY * .35f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .008f, maxY * .408f);
        pathTemp.lineTo(maxX * .018f, maxY * .408f);
        pathTemp.lineTo(maxX * .017f, maxY * .459f);
        pathTemp.lineTo(maxX * .007f, maxY * .459f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .007f, maxY * .469f);
        pathTemp.lineTo(maxX * .0098f, maxY * .523f);
        pathTemp.lineTo(maxX * .0197f, maxY * .522f);
        pathTemp.lineTo(maxX * .0168f, maxY * .469f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .0105f, maxY * .5336f);
        pathTemp.lineTo(maxX * .0186f, maxY * .579f);
        pathTemp.lineTo(maxX * .029f, maxY * .578f);
        pathTemp.lineTo(maxX * .0215f, maxY * .5285f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .043f, maxY * .286f);
        pathTemp2.lineTo(maxX * .032f, maxY * .338f);
        pathTemp2.lineTo(maxX * .045f, maxY * .343f);
        pathTemp2.lineTo(maxX * .055f, maxY * .294f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .030f, maxY * .345f);
        pathTemp2.lineTo(maxX * .024f, maxY * .398f);
        pathTemp2.lineTo(maxX * .034f, maxY * .398f);
        pathTemp2.lineTo(maxX * .039f, maxY * .35f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .023f, maxY * .408f);
        pathTemp2.lineTo(maxX * .033f, maxY * .408f);
        pathTemp2.lineTo(maxX * .032f, maxY * .459f);
        pathTemp2.lineTo(maxX * .022f, maxY * .459f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .022f, maxY * .469f);
        pathTemp2.lineTo(maxX * .0248f, maxY * .523f);
        pathTemp2.lineTo(maxX * .0327f, maxY * .522f);
        pathTemp2.lineTo(maxX * .0318f, maxY * .469f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .0255f, maxY * .5336f);
        pathTemp2.lineTo(maxX * .0336f, maxY * .579f);
        pathTemp2.lineTo(maxX * .044f, maxY * .578f);
        pathTemp2.lineTo(maxX * .0365f, maxY * .5285f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //1
        tempRect = new Rect((int) (maxX * .0), (int) (maxY * .6), (int) (maxX * .1), (int) (maxY * .8));
        pathTemp.moveTo(maxX * .029f, maxY * .629f);
        pathTemp.lineTo(maxX * .043f, maxY * .680f);
        pathTemp.lineTo(maxX * .053f, maxY * .675f);
        pathTemp.lineTo(maxX * .038f, maxY * .623f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .043f, maxY * .690f);
        pathTemp.lineTo(maxX * .063f, maxY * .735f);
        pathTemp.lineTo(maxX * .072f, maxY * .725f);
        pathTemp.lineTo(maxX * .053f, maxY * .682f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .067f, maxY * .740f);
        pathTemp.lineTo(maxX * .090f, maxY * .775f);
        pathTemp.lineTo(maxX * .098f, maxY * .770f);
        pathTemp.lineTo(maxX * .076f, maxY * .732f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .039f, maxY * .624f);
        pathTemp2.lineTo(maxX * .053f, maxY * .675f);
        pathTemp2.lineTo(maxX * .063f, maxY * .670f);
        pathTemp2.lineTo(maxX * .048f, maxY * .617f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .055f, maxY * .681f);
        pathTemp2.lineTo(maxX * .075f, maxY * .726f);
        pathTemp2.lineTo(maxX * .084f, maxY * .718f);
        pathTemp2.lineTo(maxX * .065f, maxY * .673f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .078f, maxY * .73f);
        pathTemp2.lineTo(maxX * .101f, maxY * .765f);
        pathTemp2.lineTo(maxX * .109f, maxY * .76f);
        pathTemp2.lineTo(maxX * .087f, maxY * .722f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //2
        tempRect = new Rect((int) (maxX * .11), (int) (maxY * .67), (int) (maxX * .175), (int) (maxY * .755));
        pathTemp.moveTo(maxX * .127f, maxY * .702f);
        pathTemp.lineTo(maxX * .137f, maxY * .707f);
        pathTemp.lineTo(maxX * .124f, maxY * .754f);
        pathTemp.lineTo(maxX * .115f, maxY * .749f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .139f, maxY * .704f);
        pathTemp.lineTo(maxX * .137f, maxY * .685f);
        pathTemp.lineTo(maxX * .170f, maxY * .673f);
        pathTemp.lineTo(maxX * .172f, maxY * .694f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //3
        tempRect = new Rect((int) (maxX * .91), (int) (maxY * .06), (int) (maxX * .99), (int) (maxY * .18));
        pathTemp.moveTo(maxX * .929f, maxY * .0583f);
        pathTemp.lineTo(maxX * .9537f, maxY * .0951f);
        pathTemp.lineTo(maxX * .9456f, maxY * .10f);
        pathTemp.lineTo(maxX * .9235f, maxY * .0678f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .95f, maxY * .11f);
        pathTemp.lineTo(maxX * .9573f, maxY * .097f);
        pathTemp.lineTo(maxX * .9826f, maxY * .1298f);
        pathTemp.lineTo(maxX * .9762f, maxY * .1405f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .923f, maxY * .0683f);
        pathTemp2.lineTo(maxX * .9477f, maxY * .1051f);
        pathTemp2.lineTo(maxX * .94f, maxY * .115f);
        pathTemp2.lineTo(maxX * .917f, maxY * .078f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .944f, maxY * .12f);
        pathTemp2.lineTo(maxX * .9513f, maxY * .107f);
        pathTemp2.lineTo(maxX * .9766f, maxY * .1398f);
        pathTemp2.lineTo(maxX * .9702f, maxY * .1505f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //4
        tempRect = new Rect((int) (maxX * .895), (int) (maxY * .08), (int) (maxX * .93), (int) (maxY * .27));
        pathTemp.moveTo(maxX * .899f, maxY * .089f);
        pathTemp.lineTo(maxX * .91f, maxY * .087f);
        pathTemp.lineTo(maxX * .915f, maxY * .145f);
        pathTemp.lineTo(maxX * .904f, maxY * .146f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .9055f, maxY * .148f);
        pathTemp.lineTo(maxX * .916f, maxY * .146f);
        pathTemp.lineTo(maxX * .921f, maxY * .2f);
        pathTemp.lineTo(maxX * .911f, maxY * .2002f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .91f, maxY * .207f);
        pathTemp.lineTo(maxX * .921f, maxY * .2073f);
        pathTemp.lineTo(maxX * .927f, maxY * .26f);
        pathTemp.lineTo(maxX * .916f, maxY * .26f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //5
        tempRect = new Rect((int) (maxX * .95), (int) (maxY * .18), (int) (maxX * 1.0), (int) (maxY * .29));
        pathTemp.moveTo(maxX * .97f, maxY * .17f);
        pathTemp.lineTo(maxX * .98f, maxY * .175f);
        pathTemp.lineTo(maxX * .965f, maxY * .218f);
        pathTemp.lineTo(maxX * .952f, maxY * .21f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .95f, maxY * .218f);
        pathTemp.lineTo(maxX * .959f, maxY * .228f);
        pathTemp.lineTo(maxX * .943f, maxY * .272f);
        pathTemp.lineTo(maxX * .934f, maxY * .263f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .981f, maxY * .176f);
        pathTemp2.lineTo(maxX * .991f, maxY * .181f);
        pathTemp2.lineTo(maxX * .976f, maxY * .224f);
        pathTemp2.lineTo(maxX * .963f, maxY * .216f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .961f, maxY * .224f);
        pathTemp2.lineTo(maxX * .970f, maxY * .234f);
        pathTemp2.lineTo(maxX * .954f, maxY * .278f);
        pathTemp2.lineTo(maxX * .945f, maxY * .269f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //6
        tempRect = new Rect((int) (maxX * .93), (int) (maxY * .298), (int) (maxX * .96), (int) (maxY * .42));
        pathTemp.moveTo(maxX * .922f, maxY * .3f);
        pathTemp.lineTo(maxX * .935f, maxY * .3f);
        pathTemp.lineTo(maxX * .937f, maxY * .357f);
        pathTemp.lineTo(maxX * .923f, maxY * .354f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .925f, maxY * .36f);
        pathTemp.lineTo(maxX * .935f, maxY * .36f);
        pathTemp.lineTo(maxX * .939f, maxY * .417f);
        pathTemp.lineTo(maxX * .928f, maxY * .417f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .936f, maxY * .299f);
        pathTemp2.lineTo(maxX * .949f, maxY * .299f);
        pathTemp2.lineTo(maxX * .951f, maxY * .356f);
        pathTemp2.lineTo(maxX * .937f, maxY * .353f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .939f, maxY * .359f);
        pathTemp2.lineTo(maxX * .949f, maxY * .359f);
        pathTemp2.lineTo(maxX * .953f, maxY * .416f);
        pathTemp2.lineTo(maxX * .942f, maxY * .416f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //7
        tempRect = new Rect((int) (maxX * .87), (int) (maxY * .43), (int) (maxX * .95), (int) (maxY * .55));
        pathTemp.moveTo(maxX * .923f, maxY * .436f);
        pathTemp.lineTo(maxX * .934f, maxY * .448f);
        pathTemp.lineTo(maxX * .91f, maxY * .49f);
        pathTemp.lineTo(maxX * .9f, maxY * .48f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .897f, maxY * .487f);
        pathTemp.lineTo(maxX * .904f, maxY * .497f);
        pathTemp.lineTo(maxX * .887f, maxY * .534f);
        pathTemp.lineTo(maxX * .877f, maxY * .525f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .935f, maxY * .449f);
        pathTemp2.lineTo(maxX * .946f, maxY * .461f);
        pathTemp2.lineTo(maxX * .922f, maxY * .503f);
        pathTemp2.lineTo(maxX * .912f, maxY * .493f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .909f, maxY * .500f);
        pathTemp2.lineTo(maxX * .916f, maxY * .510f);
        pathTemp2.lineTo(maxX * .899f, maxY * .547f);
        pathTemp2.lineTo(maxX * .889f, maxY * .538f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //8
        tempRect = new Rect((int) (maxX * .875), (int) (maxY * .55), (int) (maxX * .94), (int) (maxY * .655));
        pathTemp.moveTo(maxX * .882f, maxY * .566f);
        pathTemp.lineTo(maxX * .889f, maxY * .551f);
        pathTemp.lineTo(maxX * .915f, maxY * .586f);
        pathTemp.lineTo(maxX * .91f, maxY * .6f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .918f, maxY * .59f);
        pathTemp.lineTo(maxX * .928f, maxY * .595f);
        pathTemp.lineTo(maxX * .915f, maxY * .65f);
        pathTemp.lineTo(maxX * .902f, maxY * .64f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //9
        tempRect = new Rect((int) (maxX * .89), (int) (maxY * .655), (int) (maxX * .955), (int) (maxY * .91));
        pathTemp.moveTo(maxX * .9f, maxY * .675f);
        pathTemp.lineTo(maxX * .912f, maxY * .675f);
        pathTemp.lineTo(maxX * .915f, maxY * .72f);
        pathTemp.lineTo(maxX * .9f, maxY * .72f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .902f, maxY * .73f);
        pathTemp.lineTo(maxX * .916f, maxY * .73f);
        pathTemp.lineTo(maxX * .92f, maxY * .79f);
        pathTemp.lineTo(maxX * .909f, maxY * .79f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .91f, maxY * .798f);
        pathTemp.lineTo(maxX * .922f, maxY * .796f);
        pathTemp.lineTo(maxX * .93f, maxY * .845f);
        pathTemp.lineTo(maxX * .92f, maxY * .848f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .92f, maxY * .852f);
        pathTemp.lineTo(maxX * .933f, maxY * .846f);
        pathTemp.lineTo(maxX * .951f, maxY * .9f);
        pathTemp.lineTo(maxX * .94f, maxY * .905f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //10
        tempRect = new Rect((int) (maxX * .8), (int) (maxY * .7), (int) (maxX * .9), (int) (maxY * .8));
        pathTemp.moveTo(maxX * .807f, maxY * .677f);
        pathTemp.lineTo(maxX * .817f, maxY * .67f);
        pathTemp.lineTo(maxX * .84f, maxY * .715f);
        pathTemp.lineTo(maxX * .828f, maxY * .725f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .832f, maxY * .73f);
        pathTemp.lineTo(maxX * .843f, maxY * .723f);
        pathTemp.lineTo(maxX * .86f, maxY * .76f);
        pathTemp.lineTo(maxX * .85f, maxY * .765f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .856f, maxY * .772f);
        pathTemp.lineTo(maxX * .867f, maxY * .7645f);
        pathTemp.lineTo(maxX * .8875f, maxY * .813f);
        pathTemp.lineTo(maxX * .875f, maxY * .818f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .88f, maxY * .82f);
        pathTemp.lineTo(maxX * .89f, maxY * .812f);
        pathTemp.lineTo(maxX * .912f, maxY * .853f);
        pathTemp.lineTo(maxX * .899f, maxY * .86f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .902f, maxY * .865f);
        pathTemp.lineTo(maxX * .91f, maxY * .856f);
        pathTemp.lineTo(maxX * .935f, maxY * .905f);
        pathTemp.lineTo(maxX * .925f, maxY * .91f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //11
        tempRect = new Rect((int) (maxX * .81), (int) (maxY * .64), (int) (maxX * .9), (int) (maxY * .685));
        pathTemp.moveTo(maxX * .82f, maxY * .65f);
        pathTemp.lineTo(maxX * .855f, maxY * .652f);
        pathTemp.lineTo(maxX * .855f, maxY * .671f);
        pathTemp.lineTo(maxX * .82f, maxY * .671f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .862f, maxY * .652f);
        pathTemp.lineTo(maxX * .895f, maxY * .653f);
        pathTemp.lineTo(maxX * .895f, maxY * .673f);
        pathTemp.lineTo(maxX * .862f, maxY * .673f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //12
        tempRect = new Rect((int) (maxX * .8), (int) (maxY * .54), (int) (maxX * .88), (int) (maxY * .65));
        pathTemp.moveTo(maxX * .862f, maxY * .545f);
        pathTemp.lineTo(maxX * .871f, maxY * .556f);
        pathTemp.lineTo(maxX * .844f, maxY * .6f);
        pathTemp.lineTo(maxX * .834f, maxY * .581f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .834f, maxY * .589f);
        pathTemp.lineTo(maxX * .843f, maxY * .6f);
        pathTemp.lineTo(maxX * .815f, maxY * .633f);
        pathTemp.lineTo(maxX * .808f, maxY * .619f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .872f, maxY * .557f);
        pathTemp2.lineTo(maxX * .881f, maxY * .568f);
        pathTemp2.lineTo(maxX * .854f, maxY * .612f);
        pathTemp2.lineTo(maxX * .844f, maxY * .593f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .844f, maxY * .601f);
        pathTemp2.lineTo(maxX * .853f, maxY * .612f);
        pathTemp2.lineTo(maxX * .825f, maxY * .645f);
        pathTemp2.lineTo(maxX * .818f, maxY * .631f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //13
        tempRect = new Rect((int) (maxX * .845), (int) (maxY * .39), (int) (maxX * .865), (int) (maxY * .51));
        pathTemp.moveTo(maxX * .843f, maxY * .393f);
        pathTemp.lineTo(maxX * .855f, maxY * .39f);
        pathTemp.lineTo(maxX * .864f, maxY * .448f);
        pathTemp.lineTo(maxX * .85f, maxY * .452f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .85f, maxY * .454f);
        pathTemp.lineTo(maxX * .864f, maxY * .45f);
        pathTemp.lineTo(maxX * .873f, maxY * .508f);
        pathTemp.lineTo(maxX * .86f, maxY * .51f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //14
        tempRect = new Rect((int) (maxX * .85), (int) (maxY * .35), (int) (maxX * .93), (int) (maxY * .43));
        pathTemp.moveTo(maxX * .855f, maxY * .38f);
        pathTemp.lineTo(maxX * .858f, maxY * .365f);
        pathTemp.lineTo(maxX * .892f, maxY * .393f);
        pathTemp.lineTo(maxX * .885f, maxY * .405f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .89f, maxY * .408f);
        pathTemp.lineTo(maxX * .895f, maxY * .396f);
        pathTemp.lineTo(maxX * .928f, maxY * .418f);
        pathTemp.lineTo(maxX * .919f, maxY * .434f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp2.reset();
        pathTemp.reset();

        //15
        tempRect = new Rect((int) (maxX * .845), (int) (maxY * .26), (int) (maxX * .925), (int) (maxY * .35));
        pathTemp.moveTo(maxX * .845f, maxY * .33f);
        pathTemp.lineTo(maxX * .875f, maxY * .3f);
        pathTemp.lineTo(maxX * .883f, maxY * .313f);
        pathTemp.lineTo(maxX * .853f, maxY * .34f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .879f, maxY * .295f);
        pathTemp.lineTo(maxX * .91f, maxY * .27f);
        pathTemp.lineTo(maxX * .915f, maxY * .285f);
        pathTemp.lineTo(maxX * .884f, maxY * .31f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .854f, maxY * .342f);
        pathTemp2.lineTo(maxX * .884f, maxY * .312f);
        pathTemp2.lineTo(maxX * .892f, maxY * .325f);
        pathTemp2.lineTo(maxX * .862f, maxY * .352f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .888f, maxY * .307f);
        pathTemp2.lineTo(maxX * .919f, maxY * .282f);
        pathTemp2.lineTo(maxX * .924f, maxY * .297f);
        pathTemp2.lineTo(maxX * .893f, maxY * .322f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //16
        tempRect = new Rect((int) (maxX * .815), (int) (maxY * .2), (int) (maxX * .835), (int) (maxY * .35));
        pathTemp.moveTo(maxX * .824f, maxY * .216f);
        pathTemp.lineTo(maxX * .835f, maxY * .215f);
        pathTemp.lineTo(maxX * .838f, maxY * .278f);
        pathTemp.lineTo(maxX * .826f, maxY * .279f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .827f, maxY * .283f);
        pathTemp.lineTo(maxX * .838f, maxY * .282f);
        pathTemp.lineTo(maxX * .84f, maxY * .335f);
        pathTemp.lineTo(maxX * .829f, maxY * .336f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //17
        tempRect = new Rect((int) (maxX * .805), (int) (maxY * .06), (int) (maxX * .905), (int) (maxY * .2));
        pathTemp.moveTo(maxX * .83f, maxY * .127f);
        pathTemp.lineTo(maxX * .841f, maxY * .137f);
        pathTemp.lineTo(maxX * .821f, maxY * .189f);
        pathTemp.lineTo(maxX * .812f, maxY * .182f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .86f, maxY * .09f);
        pathTemp.lineTo(maxX * .867f, maxY * .1f);
        pathTemp.lineTo(maxX * .842f, maxY * .14f);
        pathTemp.lineTo(maxX * .832f, maxY * .125f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .895f, maxY * .06f);
        pathTemp.lineTo(maxX * .901f, maxY * .075f);
        pathTemp.lineTo(maxX * .87f, maxY * .0999f);
        pathTemp.lineTo(maxX * .865f, maxY * .083f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //18
        tempRect = new Rect((int) (maxX * .7), (int) (maxY * .04), (int) (maxX * .9), (int) (maxY * .15));
        pathTemp.moveTo(maxX * .737f, maxY * .108f);
        pathTemp.lineTo(maxX * .747f, maxY * .12f);
        pathTemp.lineTo(maxX * .723f, maxY * .16f);
        pathTemp.lineTo(maxX * .713f, maxY * .15f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .775f, maxY * .075f);
        pathTemp.lineTo(maxX * .78f, maxY * .093f);
        pathTemp.lineTo(maxX * .75f, maxY * .12f);
        pathTemp.lineTo(maxX * .74f, maxY * .105f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .81f, maxY * .055f);
        pathTemp.lineTo(maxX * .815f, maxY * .075f);
        pathTemp.lineTo(maxX * .782f, maxY * .095f);
        pathTemp.lineTo(maxX * .778f, maxY * .075f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .85f, maxY * .045f);
        pathTemp.lineTo(maxX * .852f, maxY * .062f);
        pathTemp.lineTo(maxX * .82f, maxY * .075f);
        pathTemp.lineTo(maxX * .815f, maxY * .056f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .89f, maxY * .043f);
        pathTemp.lineTo(maxX * .89f, maxY * .06f);
        pathTemp.lineTo(maxX * .855f, maxY * .06f);
        pathTemp.lineTo(maxX * .855f, maxY * .043f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //19
        tempRect = new Rect((int) (maxX * .725), (int) (maxY * .8), (int) (maxX * .88), (int) (maxY * .88));
        pathTemp.moveTo(maxX * .755f, maxY * .83f);
        pathTemp.lineTo(maxX * .76f, maxY * .843f);
        pathTemp.lineTo(maxX * .730f, maxY * .870f);
        pathTemp.lineTo(maxX * .725f, maxY * .86f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .795f, maxY * .81f);
        pathTemp.lineTo(maxX * .799f, maxY * .825f);
        pathTemp.lineTo(maxX * .763f, maxY * .840f);
        pathTemp.lineTo(maxX * .76f, maxY * .825f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .799f, maxY * .81f);
        pathTemp.lineTo(maxX * .835f, maxY * .81f);
        pathTemp.lineTo(maxX * .835f, maxY * .825f);
        pathTemp.lineTo(maxX * .799f, maxY * .825f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .84f, maxY * .811f);
        pathTemp.lineTo(maxX * .872f, maxY * .838f);
        pathTemp.lineTo(maxX * .867f, maxY * .853f);
        pathTemp.lineTo(maxX * .835f, maxY * .83f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .876f, maxY * .845f);
        pathTemp.lineTo(maxX * .903f, maxY * .875f);
        pathTemp.lineTo(maxX * .895f, maxY * .89f);
        pathTemp.lineTo(maxX * .87f, maxY * .86f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .905f, maxY * .886f);
        pathTemp.lineTo(maxX * .93f, maxY * .926f);
        pathTemp.lineTo(maxX * .92f, maxY * .94f);
        pathTemp.lineTo(maxX * .899f, maxY * .899f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //20
        tempRect = new Rect((int) (maxX * .12), (int) (maxY * .79), (int) (maxX * .33), (int) (maxY * .9));
        pathTemp.moveTo(maxX * .130f, maxY * .790f);
        pathTemp.lineTo(maxX * .125f, maxY * .805f);
        pathTemp.lineTo(maxX * .150f, maxY * .832f);
        pathTemp.lineTo(maxX * .155f, maxY * .820f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .164f, maxY * .825f);
        pathTemp.lineTo(maxX * .162f, maxY * .839f);
        pathTemp.lineTo(maxX * .188f, maxY * .862f);
        pathTemp.lineTo(maxX * .190f, maxY * .848f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .195f, maxY * .849f);
        pathTemp.lineTo(maxX * .193f, maxY * .862f);
        pathTemp.lineTo(maxX * .227f, maxY * .874f);
        pathTemp.lineTo(maxX * .230f, maxY * .860f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .235f, maxY * .861f);
        pathTemp.lineTo(maxX * .233f, maxY * .876f);
        pathTemp.lineTo(maxX * .265f, maxY * .883f);
        pathTemp.lineTo(maxX * .267f, maxY * .872f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .271f, maxY * .872f);
        pathTemp.lineTo(maxX * .273f, maxY * .884f);
        pathTemp.lineTo(maxX * .303f, maxY * .879f);
        pathTemp.lineTo(maxX * .301f, maxY * .868f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .308f, maxY * .863f);
        pathTemp.lineTo(maxX * .310f, maxY * .875f);
        pathTemp.lineTo(maxX * .340f, maxY * .865f);
        pathTemp.lineTo(maxX * .338f, maxY * .855f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //21
        tempRect = new Rect((int) (maxX * .0), (int) (maxY * .1), (int) (maxX * .1), (int) (maxY * .2));
        pathTemp.moveTo(maxX * .072f, maxY * .115f);
        pathTemp.lineTo(maxX * .072f, maxY * .165f);
        pathTemp.lineTo(maxX * .085f, maxY * .165f);
        pathTemp.lineTo(maxX * .085f, maxY * .115f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .058f, maxY * .115f);
        pathTemp2.lineTo(maxX * .058f, maxY * .165f);
        pathTemp2.lineTo(maxX * .07f, maxY * .165f);
        pathTemp2.lineTo(maxX * .07f, maxY * .115f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //22
        tempRect = new Rect((int) (maxX * .0), (int) (maxY * .19), (int) (maxX * .075), (int) (maxY * .279));
        pathTemp.moveTo(maxX * .053f, maxY * .200f);
        pathTemp.lineTo(maxX * .040f, maxY * .252f);
        pathTemp.lineTo(maxX * .050f, maxY * .257f);
        pathTemp.lineTo(maxX * .060f, maxY * .205f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .066f, maxY * .206f);
        pathTemp2.lineTo(maxX * .053f, maxY * .256f);
        pathTemp2.lineTo(maxX * .060f, maxY * .263f);
        pathTemp2.lineTo(maxX * .075f, maxY * .212f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //23
        tempRect = new Rect((int) (maxX * .08), (int) (maxY * .0), (int) (maxX * .2), (int) (maxY * .1));
        pathTemp.moveTo(maxX * .09f, maxY * .08f);
        pathTemp.lineTo(maxX * .091f, maxY * .098f);
        pathTemp.lineTo(maxX * .122f, maxY * .09f);
        pathTemp.lineTo(maxX * .122f, maxY * .07f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .128f, maxY * .071f);
        pathTemp.lineTo(maxX * .129f, maxY * .088f);
        pathTemp.lineTo(maxX * .160f, maxY * .085f);
        pathTemp.lineTo(maxX * .159f, maxY * .068f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .163f, maxY * .066f);
        pathTemp.lineTo(maxX * .165f, maxY * .082f);
        pathTemp.lineTo(maxX * .200f, maxY * .080f);
        pathTemp.lineTo(maxX * .198f, maxY * .064f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //24
        tempRect = new Rect((int) (maxX * .08), (int) (maxY * .1), (int) (maxX * .22), (int) (maxY * .19));
        pathTemp.moveTo(maxX * .085f, maxY * .181f);
        pathTemp.lineTo(maxX * .085f, maxY * .195f);
        pathTemp.lineTo(maxX * .122f, maxY * .195f);
        pathTemp.lineTo(maxX * .122f, maxY * .180f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .125f, maxY * .175f);
        pathTemp.lineTo(maxX * .127f, maxY * .192f);
        pathTemp.lineTo(maxX * .160f, maxY * .187f);
        pathTemp.lineTo(maxX * .158f, maxY * .170f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .162f, maxY * .168f);
        pathTemp.lineTo(maxX * .164f, maxY * .179f);
        pathTemp.lineTo(maxX * .190f, maxY * .146f);
        pathTemp.lineTo(maxX * .183f, maxY * .140f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .186f, maxY * .135f);
        pathTemp.lineTo(maxX * .200f, maxY * .140f);
        pathTemp.lineTo(maxX * .215f, maxY * .091f);
        pathTemp.lineTo(maxX * .203f, maxY * .085f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //25
        tempRect = new Rect((int) (maxX * .11), (int) (maxY * .75), (int) (maxX * .23), (int) (maxY * .8));
        pathTemp.moveTo(maxX * .126f, maxY * .760f);
        pathTemp.lineTo(maxX * .128f, maxY * .779f);
        pathTemp.lineTo(maxX * .161f, maxY * .774f);
        pathTemp.lineTo(maxX * .159f, maxY * .756f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .165f, maxY * .753f);
        pathTemp.lineTo(maxX * .166f, maxY * .772f);
        pathTemp.lineTo(maxX * .198f, maxY * .772f);
        pathTemp.lineTo(maxX * .198f, maxY * .753f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .200f, maxY * .758f);
        pathTemp.lineTo(maxX * .200f, maxY * .773f);
        pathTemp.lineTo(maxX * .235f, maxY * .783f);
        pathTemp.lineTo(maxX * .238f, maxY * .770f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //26
        tempRect = new Rect((int) (maxX * .08), (int) (maxY * .2), (int) (maxX * .31), (int) (maxY * .3));
        pathTemp.moveTo(maxX * .082f, maxY * .205f);
        pathTemp.lineTo(maxX * .081f, maxY * .216f);
        pathTemp.lineTo(maxX * .117f, maxY * .228f);
        pathTemp.lineTo(maxX * .119f, maxY * .212f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .124f, maxY * .218f);
        pathTemp.lineTo(maxX * .122f, maxY * .230f);
        pathTemp.lineTo(maxX * .154f, maxY * .240f);
        pathTemp.lineTo(maxX * .154f, maxY * .229f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .159f, maxY * .231f);
        pathTemp.lineTo(maxX * .158f, maxY * .243f);
        pathTemp.lineTo(maxX * .189f, maxY * .250f);
        pathTemp.lineTo(maxX * .191f, maxY * .239f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .196f, maxY * .242f);
        pathTemp.lineTo(maxX * .197f, maxY * .257f);
        pathTemp.lineTo(maxX * .228f, maxY * .267f);
        pathTemp.lineTo(maxX * .229f, maxY * .251f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .234f, maxY * .257f);
        pathTemp.lineTo(maxX * .234f, maxY * .272f);
        pathTemp.lineTo(maxX * .266f, maxY * .283f);
        pathTemp.lineTo(maxX * .266f, maxY * .266f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .270f, maxY * .268f);
        pathTemp.lineTo(maxX * .269f, maxY * .282f);
        pathTemp.lineTo(maxX * .302f, maxY * .295f);
        pathTemp.lineTo(maxX * .304f, maxY * .278f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //27
        tempRect = new Rect((int) (maxX * .09), (int) (maxY * .28), (int) (maxX * .33), (int) (maxY * .43));
        pathTemp.moveTo(maxX * .062f, maxY * .270f);
        pathTemp.lineTo(maxX * .060f, maxY * .285f);
        pathTemp.lineTo(maxX * .092f, maxY * .293f);
        pathTemp.lineTo(maxX * .095f, maxY * .280f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .100f, maxY * .280f);
        pathTemp.lineTo(maxX * .098f, maxY * .300f);
        pathTemp.lineTo(maxX * .128f, maxY * .316f);
        pathTemp.lineTo(maxX * .131f, maxY * .300f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .136f, maxY * .303f);
        pathTemp.lineTo(maxX * .134f, maxY * .319f);
        pathTemp.lineTo(maxX * .161f, maxY * .344f);
        pathTemp.lineTo(maxX * .164f, maxY * .332f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .171f, maxY * .340f);
        pathTemp.lineTo(maxX * .168f, maxY * .351f);
        pathTemp.lineTo(maxX * .191f, maxY * .384f);
        pathTemp.lineTo(maxX * .195f, maxY * .374f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .200f, maxY * .380f);
        pathTemp.lineTo(maxX * .195f, maxY * .391f);
        pathTemp.lineTo(maxX * .216f, maxY * .428f);
        pathTemp.lineTo(maxX * .222f, maxY * .419f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .226f, maxY * .423f);
        pathTemp.lineTo(maxX * .219f, maxY * .432f);
        pathTemp.lineTo(maxX * .233f, maxY * .479f);
        pathTemp.lineTo(maxX * .241f, maxY * .471f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //28
        tempRect = new Rect((int) (maxX * .05), (int) (maxY * .48), (int) (maxX * .25), (int) (maxY * .61));
        pathTemp.moveTo(maxX * .050f, maxY * .575f);
        pathTemp.lineTo(maxX * .052f, maxY * .590f);
        pathTemp.lineTo(maxX * .082f, maxY * .578f);
        pathTemp.lineTo(maxX * .080f, maxY * .563f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .085f, maxY * .559f);
        pathTemp.lineTo(maxX * .088f, maxY * .573f);
        pathTemp.lineTo(maxX * .120f, maxY * .558f);
        pathTemp.lineTo(maxX * .117f, maxY * .542f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .122f, maxY * .538f);
        pathTemp.lineTo(maxX * .124f, maxY * .553f);
        pathTemp.lineTo(maxX * .154f, maxY * .540f);
        pathTemp.lineTo(maxX * .150f, maxY * .527f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .155f, maxY * .523f);
        pathTemp.lineTo(maxX * .158f, maxY * .535f);
        pathTemp.lineTo(maxX * .189f, maxY * .522f);
        pathTemp.lineTo(maxX * .185f, maxY * .505f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .193f, maxY * .501f);
        pathTemp.lineTo(maxX * .196f, maxY * .514f);
        pathTemp.lineTo(maxX * .225f, maxY * .502f);
        pathTemp.lineTo(maxX * .220f, maxY * .487f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .053f, maxY * .591f);
        pathTemp2.lineTo(maxX * .055f, maxY * .606f);
        pathTemp2.lineTo(maxX * .085f, maxY * .594f);
        pathTemp2.lineTo(maxX * .083f, maxY * .579f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .088f, maxY * .575f);
        pathTemp2.lineTo(maxX * .091f, maxY * .599f);
        pathTemp2.lineTo(maxX * .123f, maxY * .574f);
        pathTemp2.lineTo(maxX * .120f, maxY * .558f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .125f, maxY * .554f);
        pathTemp2.lineTo(maxX * .127f, maxY * .569f);
        pathTemp2.lineTo(maxX * .157f, maxY * .556f);
        pathTemp2.lineTo(maxX * .153f, maxY * .543f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .158f, maxY * .539f);
        pathTemp2.lineTo(maxX * .161f, maxY * .551f);
        pathTemp2.lineTo(maxX * .192f, maxY * .538f);
        pathTemp2.lineTo(maxX * .188f, maxY * .521f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .196f, maxY * .517f);
        pathTemp2.lineTo(maxX * .199f, maxY * .530f);
        pathTemp2.lineTo(maxX * .228f, maxY * .518f);
        pathTemp2.lineTo(maxX * .223f, maxY * .503f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //29
        tempRect = new Rect((int) (maxX * .19), (int) (maxY * .51), (int) (maxX * .24), (int) (maxY * .73));
        pathTemp.moveTo(maxX * .194f, maxY * .670f);
        pathTemp.lineTo(maxX * .201f, maxY * .680f);
        pathTemp.lineTo(maxX * .224f, maxY * .641f);
        pathTemp.lineTo(maxX * .217f, maxY * .631f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .219f, maxY * .626f);
        pathTemp.lineTo(maxX * .229f, maxY * .629f);
        pathTemp.lineTo(maxX * .239f, maxY * .585f);
        pathTemp.lineTo(maxX * .230f, maxY * .581f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .232f, maxY * .578f);
        pathTemp.lineTo(maxX * .241f, maxY * .579f);
        pathTemp.lineTo(maxX * .246f, maxY * .523f);
        pathTemp.lineTo(maxX * .237f, maxY * .521f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //30
        tempRect = new Rect((int) (maxX * .22), (int) (maxY * .0), (int) (maxX * .45), (int) (maxY * .1));
        pathTemp.moveTo(maxX * .224f, maxY * .05f);
        pathTemp.lineTo(maxX * .227f, maxY * .063f);
        pathTemp.lineTo(maxX * .258f, maxY * .045f);
        pathTemp.lineTo(maxX * .255f, maxY * .032f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .261f, maxY * .031f);
        pathTemp.lineTo(maxX * .263f, maxY * .044f);
        pathTemp.lineTo(maxX * .294f, maxY * .036f);
        pathTemp.lineTo(maxX * .291f, maxY * .021f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .297f, maxY * .019f);
        pathTemp.lineTo(maxX * .298f, maxY * .032f);
        pathTemp.lineTo(maxX * .332f, maxY * .032f);
        pathTemp.lineTo(maxX * .332f, maxY * .019f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .338f, maxY * .018f);
        pathTemp.lineTo(maxX * .337f, maxY * .031f);
        pathTemp.lineTo(maxX * .366f, maxY * .033f);
        pathTemp.lineTo(maxX * .367f, maxY * .021f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .376f, maxY * .022f);
        pathTemp.lineTo(maxX * .374f, maxY * .034f);
        pathTemp.lineTo(maxX * .405f, maxY * .048f);
        pathTemp.lineTo(maxX * .408f, maxY * .034f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .416f, maxY * .037f);
        pathTemp.lineTo(maxX * .414f, maxY * .052f);
        pathTemp.lineTo(maxX * .443f, maxY * .072f);
        pathTemp.lineTo(maxX * .445f, maxY * .063f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //31
        tempRect = new Rect((int) (maxX * .2), (int) (maxY * .09), (int) (maxX * .33), (int) (maxY * .27));
        pathTemp.moveTo(maxX * .228f, maxY * .082f);
        pathTemp.lineTo(maxX * .220f, maxY * .093f);
        pathTemp.lineTo(maxX * .241f, maxY * .132f);
        pathTemp.lineTo(maxX * .246f, maxY * .126f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .251f, maxY * .129f);
        pathTemp.lineTo(maxX * .243f, maxY * .135f);
        pathTemp.lineTo(maxX * .264f, maxY * .174f);
        pathTemp.lineTo(maxX * .271f, maxY * .168f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .276f, maxY * .171f);
        pathTemp.lineTo(maxX * .268f, maxY * .179f);
        pathTemp.lineTo(maxX * .289f, maxY * .221f);
        pathTemp.lineTo(maxX * .299f, maxY * .208f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .301f, maxY * .223f);
        pathTemp.lineTo(maxX * .295f, maxY * .231f);
        pathTemp.lineTo(maxX * .313f, maxY * .269f);
        pathTemp.lineTo(maxX * .323f, maxY * .263f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //32
        tempRect = new Rect((int) (maxX * .25), (int) (maxY * .3), (int) (maxX * .32), (int) (maxY * .46));
        pathTemp.moveTo(maxX * .251f, maxY * .458f);
        pathTemp.lineTo(maxX * .259f, maxY * .464f);
        pathTemp.lineTo(maxX * .275f, maxY * .421f);
        pathTemp.lineTo(maxX * .268f, maxY * .415f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .270f, maxY * .408f);
        pathTemp.lineTo(maxX * .277f, maxY * .412f);
        pathTemp.lineTo(maxX * .292f, maxY * .369f);
        pathTemp.lineTo(maxX * .285f, maxY * .360f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .287f, maxY * .354f);
        pathTemp.lineTo(maxX * .298f, maxY * .360f);
        pathTemp.lineTo(maxX * .318f, maxY * .312f);
        pathTemp.lineTo(maxX * .308f, maxY * .304f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //33
        tempRect = new Rect((int) (maxX * .26), (int) (maxY * .48), (int) (maxX * .37), (int) (maxY * .54));
        pathTemp.moveTo(maxX * .259f, maxY * .483f);
        pathTemp.lineTo(maxX * .258f, maxY * .497f);
        pathTemp.lineTo(maxX * .288f, maxY * .504f);
        pathTemp.lineTo(maxX * .291f, maxY * .491f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .294f, maxY * .491f);
        pathTemp.lineTo(maxX * .293f, maxY * .506f);
        pathTemp.lineTo(maxX * .325f, maxY * .519f);
        pathTemp.lineTo(maxX * .326f, maxY * .502f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .330f, maxY * .503f);
        pathTemp.lineTo(maxX * .329f, maxY * .518f);
        pathTemp.lineTo(maxX * .361f, maxY * .531f);
        pathTemp.lineTo(maxX * .362f, maxY * .514f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .257f, maxY * .498f);
        pathTemp2.lineTo(maxX * .256f, maxY * .512f);
        pathTemp2.lineTo(maxX * .286f, maxY * .519f);
        pathTemp2.lineTo(maxX * .289f, maxY * .506f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .292f, maxY * .506f);
        pathTemp2.lineTo(maxX * .291f, maxY * .521f);
        pathTemp2.lineTo(maxX * .323f, maxY * .534f);
        pathTemp2.lineTo(maxX * .324f, maxY * .517f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .328f, maxY * .518f);
        pathTemp2.lineTo(maxX * .327f, maxY * .533f);
        pathTemp2.lineTo(maxX * .359f, maxY * .546f);
        pathTemp2.lineTo(maxX * .360f, maxY * .529f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //34
        tempRect = new Rect((int) (maxX * .25), (int) (maxY * .55), (int) (maxX * .38), (int) (maxY * .76));
        pathTemp.moveTo(maxX * .243f, maxY * .773f);
        pathTemp.lineTo(maxX * .252f, maxY * .778f);
        pathTemp.lineTo(maxX * .263f, maxY * .723f);
        pathTemp.lineTo(maxX * .254f, maxY * .720f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .258f, maxY * .715f);
        pathTemp.lineTo(maxX * .265f, maxY * .717f);
        pathTemp.lineTo(maxX * .283f, maxY * .676f);
        pathTemp.lineTo(maxX * .276f, maxY * .666f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .275f, maxY * .658f);
        pathTemp.lineTo(maxX * .283f, maxY * .669f);
        pathTemp.lineTo(maxX * .303f, maxY * .628f);
        pathTemp.lineTo(maxX * .296f, maxY * .616f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .301f, maxY * .611f);
        pathTemp.lineTo(maxX * .309f, maxY * .619f);
        pathTemp.lineTo(maxX * .330f, maxY * .590f);
        pathTemp.lineTo(maxX * .326f, maxY * .58f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .334f, maxY * .572f);
        pathTemp.lineTo(maxX * .336f, maxY * .582f);
        pathTemp.lineTo(maxX * .369f, maxY * .566f);
        pathTemp.lineTo(maxX * .365f, maxY * .556f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //35
        tempRect = new Rect((int) (maxX * .25), (int) (maxY * .7), (int) (maxX * .35), (int) (maxY * .78));
        pathTemp.moveTo(maxX * .260f, maxY * .770f);
        pathTemp.lineTo(maxX * .263f, maxY * .781f);
        pathTemp.lineTo(maxX * .293f, maxY * .764f);
        pathTemp.lineTo(maxX * .290f, maxY * .751f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .293f, maxY * .748f);
        pathTemp.lineTo(maxX * .298f, maxY * .761f);
        pathTemp.lineTo(maxX * .329f, maxY * .738f);
        pathTemp.lineTo(maxX * .325f, maxY * .727f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .326f, maxY * .726f);
        pathTemp.lineTo(maxX * .332f, maxY * .735f);
        pathTemp.lineTo(maxX * .365f, maxY * .717f);
        pathTemp.lineTo(maxX * .360f, maxY * .7f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //36
        tempRect = new Rect((int) (maxX * .26), (int) (maxY * .79), (int) (maxX * .36), (int) (maxY * .86));
        pathTemp.moveTo(maxX * .255f, maxY * .789f);
        pathTemp.lineTo(maxX * .250f, maxY * .806f);
        pathTemp.lineTo(maxX * .283f, maxY * .816f);
        pathTemp.lineTo(maxX * .287f, maxY * .800f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .290f, maxY * .805f);
        pathTemp.lineTo(maxX * .286f, maxY * .821f);
        pathTemp.lineTo(maxX * .321f, maxY * .832f);
        pathTemp.lineTo(maxX * .325f, maxY * .816f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .328f, maxY * .818f);
        pathTemp.lineTo(maxX * .324f, maxY * .835f);
        pathTemp.lineTo(maxX * .356f, maxY * .849f);
        pathTemp.lineTo(maxX * .360f, maxY * .833f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //37
        tempRect = new Rect((int) (maxX * .33), (int) (maxY * .08), (int) (maxX * .44), (int) (maxY * .28));
        pathTemp.moveTo(maxX * .328f, maxY * .263f);
        pathTemp.lineTo(maxX * .337f, maxY * .271f);
        pathTemp.lineTo(maxX * .359f, maxY * .234f);
        pathTemp.lineTo(maxX * .35f, maxY * .224f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .355f, maxY * .219f);
        pathTemp.lineTo(maxX * .364f, maxY * .229f);
        pathTemp.lineTo(maxX * .386f, maxY * .190f);
        pathTemp.lineTo(maxX * .380f, maxY * .176f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .385f, maxY * .173f);
        pathTemp.lineTo(maxX * .391f, maxY * .183f);
        pathTemp.lineTo(maxX * .413f, maxY * .146f);
        pathTemp.lineTo(maxX * .407f, maxY * .132f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .412f, maxY * .129f);
        pathTemp.lineTo(maxX * .418f, maxY * .139f);
        pathTemp.lineTo(maxX * .440f, maxY * .103f);
        pathTemp.lineTo(maxX * .434f, maxY * .092f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //38
        tempRect = new Rect((int) (maxX * .32), (int) (maxY * .3), (int) (maxX * .39), (int) (maxY * .51));
        pathTemp.moveTo(maxX * .330f, maxY * .3f);
        pathTemp.lineTo(maxX * .320f, maxY * .307f);
        pathTemp.lineTo(maxX * .334f, maxY * .356f);
        pathTemp.lineTo(maxX * .341f, maxY * .351f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .342f, maxY * .354f);
        pathTemp.lineTo(maxX * .336f, maxY * .361f);
        pathTemp.lineTo(maxX * .347f, maxY * .410f);
        pathTemp.lineTo(maxX * .357f, maxY * .405f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .358f, maxY * .412f);
        pathTemp.lineTo(maxX * .349f, maxY * .418f);
        pathTemp.lineTo(maxX * .363f, maxY * .467f);
        pathTemp.lineTo(maxX * .370f, maxY * .465f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .371f, maxY * .468f);
        pathTemp.lineTo(maxX * .362f, maxY * .472f);
        pathTemp.lineTo(maxX * .376f, maxY * .521f);
        pathTemp.lineTo(maxX * .382f, maxY * .514f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //39
        tempRect = new Rect((int) (maxX * .36), (int) (maxY * .57), (int) (maxX * .39), (int) (maxY * .68));
        pathTemp.moveTo(maxX * .381f, maxY * .572f);
        pathTemp.lineTo(maxX * .370f, maxY * .570f);
        pathTemp.lineTo(maxX * .368f, maxY * .623f);
        pathTemp.lineTo(maxX * .379f, maxY * .624f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .381f, maxY * .629f);
        pathTemp.lineTo(maxX * .370f, maxY * .631f);
        pathTemp.lineTo(maxX * .367f, maxY * .684f);
        pathTemp.lineTo(maxX * .378f, maxY * .685f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //40
        tempRect = new Rect((int) (maxX * .36), (int) (maxY * .71), (int) (maxX * .39), (int) (maxY * .84));
        pathTemp.moveTo(maxX * .375f, maxY * .721f);
        pathTemp.lineTo(maxX * .365f, maxY * .720f);
        pathTemp.lineTo(maxX * .362f, maxY * .773f);
        pathTemp.lineTo(maxX * .374f, maxY * .774f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .374f, maxY * .781f);
        pathTemp.lineTo(maxX * .364f, maxY * .780f);
        pathTemp.lineTo(maxX * .361f, maxY * .831f);
        pathTemp.lineTo(maxX * .373f, maxY * .832f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //41
        tempRect = new Rect((int) (maxX * .33), (int) (maxY * .26), (int) (maxX * .56), (int) (maxY * .32));
        pathTemp.moveTo(maxX * .332f, maxY * .280f);
        pathTemp.lineTo(maxX * .332f, maxY * .297f);
        pathTemp.lineTo(maxX * .368f, maxY * .297f);
        pathTemp.lineTo(maxX * .368f, maxY * .279f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .372f, maxY * .279f);
        pathTemp.lineTo(maxX * .372f, maxY * .296f);
        pathTemp.lineTo(maxX * .406f, maxY * .296f);
        pathTemp.lineTo(maxX * .406f, maxY * .278f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .410f, maxY * .278f);
        pathTemp.lineTo(maxX * .410f, maxY * .296f);
        pathTemp.lineTo(maxX * .444f, maxY * .296f);
        pathTemp.lineTo(maxX * .444f, maxY * .278f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .448f, maxY * .276f);
        pathTemp.lineTo(maxX * .448f, maxY * .294f);
        pathTemp.lineTo(maxX * .483f, maxY * .294f);
        pathTemp.lineTo(maxX * .483f, maxY * .276f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .487f, maxY * .273f);
        pathTemp.lineTo(maxX * .487f, maxY * .291f);
        pathTemp.lineTo(maxX * .521f, maxY * .291f);
        pathTemp.lineTo(maxX * .521f, maxY * .272f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .525f, maxY * .272f);
        pathTemp.lineTo(maxX * .525f, maxY * .291f);
        pathTemp.lineTo(maxX * .559f, maxY * .290f);
        pathTemp.lineTo(maxX * .559f, maxY * .271f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //42
        tempRect = new Rect((int) (maxX * .7), (int) (maxY * .64), (int) (maxX * .81), (int) (maxY * .82));
        pathTemp.moveTo(maxX * .716f, maxY * .784f);
        pathTemp.lineTo(maxX * .726f, maxY * .793f);
        pathTemp.lineTo(maxX * .71f, maxY * .84f);
        pathTemp.lineTo(maxX * .7f, maxY * .835f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .737f, maxY * .73f);
        pathTemp.lineTo(maxX * .748f, maxY * .74f);
        pathTemp.lineTo(maxX * .727f, maxY * .787f);
        pathTemp.lineTo(maxX * .716f, maxY * .775f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .763f, maxY * .685f);
        pathTemp.lineTo(maxX * .77f, maxY * .695f);
        pathTemp.lineTo(maxX * .75f, maxY * .733f);
        pathTemp.lineTo(maxX * .74f, maxY * .725f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .792f, maxY * .638f);
        pathTemp.lineTo(maxX * .8f, maxY * .657f);
        pathTemp.lineTo(maxX * .775f, maxY * .693f);
        pathTemp.lineTo(maxX * .764f, maxY * .68f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .725f, maxY * .804f);
        pathTemp2.lineTo(maxX * .735f, maxY * .813f);
        pathTemp2.lineTo(maxX * .719f, maxY * .86f);
        pathTemp2.lineTo(maxX * .709f, maxY * .855f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .746f, maxY * .75f);
        pathTemp2.lineTo(maxX * .757f, maxY * .76f);
        pathTemp2.lineTo(maxX * .736f, maxY * .807f);
        pathTemp2.lineTo(maxX * .725f, maxY * .795f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .772f, maxY * .705f);
        pathTemp2.lineTo(maxX * .779f, maxY * .715f);
        pathTemp2.lineTo(maxX * .759f, maxY * .753f);
        pathTemp2.lineTo(maxX * .749f, maxY * .745f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .801f, maxY * .658f);
        pathTemp2.lineTo(maxX * .809f, maxY * .677f);
        pathTemp2.lineTo(maxX * .784f, maxY * .713f);
        pathTemp2.lineTo(maxX * .773f, maxY * .700f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //43
        tempRect = new Rect((int) (maxX * .755), (int) (maxY * .59), (int) (maxX * .8), (int) (maxY * .645));
        pathTemp.moveTo(maxX * .757f, maxY * .605f);
        pathTemp.lineTo(maxX * .764f, maxY * .586f);
        pathTemp.lineTo(maxX * .797f, maxY * .62f);
        pathTemp.lineTo(maxX * .788f, maxY * .637f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //44
        tempRect = new Rect((int) (maxX * .75), (int) (maxY * .5), (int) (maxX * .875), (int) (maxY * .56));
        pathTemp.moveTo(maxX * .756f, maxY * .565f);
        pathTemp.lineTo(maxX * .789f, maxY * .535f);
        pathTemp.lineTo(maxX * .794f, maxY * .553f);
        pathTemp.lineTo(maxX * .762f, maxY * .58f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .79f, maxY * .53f);
        pathTemp.lineTo(maxX * .83f, maxY * .52f);
        pathTemp.lineTo(maxX * .831f, maxY * .537f);
        pathTemp.lineTo(maxX * .795f, maxY * .55f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .834f, maxY * .52f);
        pathTemp.lineTo(maxX * .869f, maxY * .522f);
        pathTemp.lineTo(maxX * .868f, maxY * .542f);
        pathTemp.lineTo(maxX * .834f, maxY * .54f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //45
        tempRect = new Rect((int) (maxX * .75), (int) (maxY * .42), (int) (maxX * .83), (int) (maxY * .52));
        pathTemp.moveTo(maxX * .754f, maxY * .518f);
        pathTemp.lineTo(maxX * .763f, maxY * .527f);
        pathTemp.lineTo(maxX * .745f, maxY * .578f);
        pathTemp.lineTo(maxX * .734f, maxY * .572f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .775f, maxY * .471f);
        pathTemp.lineTo(maxX * .784f, maxY * .48f);
        pathTemp.lineTo(maxX * .764f, maxY * .523f);
        pathTemp.lineTo(maxX * .755f, maxY * .51f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .81f, maxY * .43f);
        pathTemp.lineTo(maxX * .818f, maxY * .447f);
        pathTemp.lineTo(maxX * .787f, maxY * .478f);
        pathTemp.lineTo(maxX * .783f, maxY * .467f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .833f, maxY * .388f);
        pathTemp.lineTo(maxX * .845f, maxY * .4f);
        pathTemp.lineTo(maxX * .822f, maxY * .45f);
        pathTemp.lineTo(maxX * .813f, maxY * .432f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //46
        tempRect = new Rect((int) (maxX * .7), (int) (maxY * .4), (int) (maxX * .8), (int) (maxY * .5));
        pathTemp.moveTo(maxX * .693f, maxY * .49f);
        pathTemp.lineTo(maxX * .7f, maxY * .505f);
        pathTemp.lineTo(maxX * .667f, maxY * .53f);
        pathTemp.lineTo(maxX * .663f, maxY * .518f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .725f, maxY * .457f);
        pathTemp.lineTo(maxX * .733f, maxY * .468f);
        pathTemp.lineTo(maxX * .7f, maxY * .504f);
        pathTemp.lineTo(maxX * .694f, maxY * .487f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .761f, maxY * .43f);
        pathTemp.lineTo(maxX * .766f, maxY * .443f);
        pathTemp.lineTo(maxX * .734f, maxY * .468f);
        pathTemp.lineTo(maxX * .73f, maxY * .455f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .79f, maxY * .4f);
        pathTemp.lineTo(maxX * .797f, maxY * .413f);
        pathTemp.lineTo(maxX * .767f, maxY * .44f);
        pathTemp.lineTo(maxX * .763f, maxY * .425f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .827f, maxY * .37f);
        pathTemp.lineTo(maxX * .833f, maxY * .385f);
        pathTemp.lineTo(maxX * .8f, maxY * .41f);
        pathTemp.lineTo(maxX * .793f, maxY * .396f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //47
        tempRect = new Rect((int) (maxX * .7), (int) (maxY * .32), (int) (maxX * .825), (int) (maxY * .37));
        pathTemp.moveTo(maxX * .708f, maxY * .345f);
        pathTemp.lineTo(maxX * .745f, maxY * .335f);
        pathTemp.lineTo(maxX * .745f, maxY * .354f);
        pathTemp.lineTo(maxX * .712f, maxY * .37f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .75f, maxY * .33f);
        pathTemp.lineTo(maxX * .78f, maxY * .324f);
        pathTemp.lineTo(maxX * .783f, maxY * .345f);
        pathTemp.lineTo(maxX * .752f, maxY * .355f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .785f, maxY * .325f);
        pathTemp.lineTo(maxX * .823f, maxY * .328f);
        pathTemp.lineTo(maxX * .821f, maxY * .348f);
        pathTemp.lineTo(maxX * .788f, maxY * .344f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .713f, maxY * .371f);
        pathTemp2.lineTo(maxX * .75f, maxY * .361f);
        pathTemp2.lineTo(maxX * .75f, maxY * .38f);
        pathTemp2.lineTo(maxX * .717f, maxY * .396f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .755f, maxY * .356f);
        pathTemp2.lineTo(maxX * .785f, maxY * .350f);
        pathTemp2.lineTo(maxX * .788f, maxY * .371f);
        pathTemp2.lineTo(maxX * .757f, maxY * .381f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .79f, maxY * .351f);
        pathTemp2.lineTo(maxX * .828f, maxY * .354f);
        pathTemp2.lineTo(maxX * .826f, maxY * .374f);
        pathTemp2.lineTo(maxX * .793f, maxY * .370f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //48
        tempRect = new Rect((int) (maxX * .715), (int) (maxY * .25), (int) (maxX * .8), (int) (maxY * .32));
        pathTemp.moveTo(maxX * .715f, maxY * .309f);
        pathTemp.lineTo(maxX * .724f, maxY * .32f);
        pathTemp.lineTo(maxX * .7f, maxY * .36f);
        pathTemp.lineTo(maxX * .692f, maxY * .348f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .748f, maxY * .27f);
        pathTemp.lineTo(maxX * .752f, maxY * .28f);
        pathTemp.lineTo(maxX * .725f, maxY * .317f);
        pathTemp.lineTo(maxX * .719f, maxY * .302f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .787f, maxY * .255f);
        pathTemp.lineTo(maxX * .79f, maxY * .265f);
        pathTemp.lineTo(maxX * .757f, maxY * .28f);
        pathTemp.lineTo(maxX * .752f, maxY * .267f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .816f, maxY * .215f);
        pathTemp.lineTo(maxX * .824f, maxY * .227f);
        pathTemp.lineTo(maxX * .795f, maxY * .264f);
        pathTemp.lineTo(maxX * .79f, maxY * .248f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //49
        tempRect = new Rect((int) (maxX * .72), (int) (maxY * .15), (int) (maxX * .81), (int) (maxY * .21));
        pathTemp.moveTo(maxX * .723f, maxY * .165f);
        pathTemp.lineTo(maxX * .76f, maxY * .176f);
        pathTemp.lineTo(maxX * .758f, maxY * .19f);
        pathTemp.lineTo(maxX * .72f, maxY * .18f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .765f, maxY * .178f);
        pathTemp.lineTo(maxX * .8f, maxY * .187f);
        pathTemp.lineTo(maxX * .797f, maxY * .203f);
        pathTemp.lineTo(maxX * .762f, maxY * .192f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //50
        tempRect = new Rect((int) (maxX * .34), (int) (maxY * .31), (int) (maxX * .51), (int) (maxY * .41));
        pathTemp.moveTo(maxX * .344f, maxY * .302f);
        pathTemp.lineTo(maxX * .340f, maxY * .316f);
        pathTemp.lineTo(maxX * .374f, maxY * .337f);
        pathTemp.lineTo(maxX * .378f, maxY * .323f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .381f, maxY * .326f);
        pathTemp.lineTo(maxX * .377f, maxY * .340f);
        pathTemp.lineTo(maxX * .410f, maxY * .361f);
        pathTemp.lineTo(maxX * .414f, maxY * .347f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .417f, maxY * .350f);
        pathTemp.lineTo(maxX * .413f, maxY * .364f);
        pathTemp.lineTo(maxX * .444f, maxY * .385f);
        pathTemp.lineTo(maxX * .448f, maxY * .371f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .452f, maxY * .372f);
        pathTemp.lineTo(maxX * .449f, maxY * .384f);
        pathTemp.lineTo(maxX * .480f, maxY * .403f);
        pathTemp.lineTo(maxX * .484f, maxY * .393f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .489f, maxY * .394f);
        pathTemp.lineTo(maxX * .484f, maxY * .413f);
        pathTemp.lineTo(maxX * .513f, maxY * .431f);
        pathTemp.lineTo(maxX * .518f, maxY * .416f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //51
        tempRect = new Rect((int) (maxX * .39), (int) (maxY * .43), (int) (maxX * .53), (int) (maxY * .52));
        pathTemp.moveTo(maxX * .389f, maxY * .524f);
        pathTemp.lineTo(maxX * .391f, maxY * .534f);
        pathTemp.lineTo(maxX * .420f, maxY * .503f);
        pathTemp.lineTo(maxX * .415f, maxY * .492f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .421f, maxY * .486f);
        pathTemp.lineTo(maxX * .425f, maxY * .496f);
        pathTemp.lineTo(maxX * .452f, maxY * .479f);
        pathTemp.lineTo(maxX * .448f, maxY * .468f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .452f, maxY * .461f);
        pathTemp.lineTo(maxX * .456f, maxY * .476f);
        pathTemp.lineTo(maxX * .487f, maxY * .460f);
        pathTemp.lineTo(maxX * .484f, maxY * .445f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .489f, maxY * .442f);
        pathTemp.lineTo(maxX * .493f, maxY * .456f);
        pathTemp.lineTo(maxX * .528f, maxY * .445f);
        pathTemp.lineTo(maxX * .527f, maxY * .431f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //52
        tempRect = new Rect((int) (maxX * .4), (int) (maxY * .51), (int) (maxX * .56), (int) (maxY * .6));
        pathTemp.moveTo(maxX * .400f, maxY * .541f);
        pathTemp.lineTo(maxX * .399f, maxY * .558f);
        pathTemp.lineTo(maxX * .434f, maxY * .560f);
        pathTemp.lineTo(maxX * .434f, maxY * .543f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .438f, maxY * .544f);
        pathTemp.lineTo(maxX * .439f, maxY * .561f);
        pathTemp.lineTo(maxX * .471f, maxY * .560f);
        pathTemp.lineTo(maxX * .472f, maxY * .541f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .475f, maxY * .541f);
        pathTemp.lineTo(maxX * .477f, maxY * .558f);
        pathTemp.lineTo(maxX * .511f, maxY * .548f);
        pathTemp.lineTo(maxX * .509f, maxY * .530f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .515f, maxY * .529f);
        pathTemp.lineTo(maxX * .518f, maxY * .544f);
        pathTemp.lineTo(maxX * .547f, maxY * .522f);
        pathTemp.lineTo(maxX * .543f, maxY * .504f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .400f, maxY * .559f);
        pathTemp2.lineTo(maxX * .399f, maxY * .576f);
        pathTemp2.lineTo(maxX * .434f, maxY * .578f);
        pathTemp2.lineTo(maxX * .434f, maxY * .561f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .438f, maxY * .562f);
        pathTemp2.lineTo(maxX * .439f, maxY * .579f);
        pathTemp2.lineTo(maxX * .471f, maxY * .578f);
        pathTemp2.lineTo(maxX * .472f, maxY * .559f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .475f, maxY * .559f);
        pathTemp2.lineTo(maxX * .477f, maxY * .576f);
        pathTemp2.lineTo(maxX * .511f, maxY * .566f);
        pathTemp2.lineTo(maxX * .509f, maxY * .548f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .515f, maxY * .547f);
        pathTemp2.lineTo(maxX * .518f, maxY * .562f);
        pathTemp2.lineTo(maxX * .547f, maxY * .540f);
        pathTemp2.lineTo(maxX * .543f, maxY * .522f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //53
        tempRect = new Rect((int) (maxX * .38), (int) (maxY * .58), (int) (maxX * .53), (int) (maxY * .67));
        pathTemp.moveTo(maxX * .391f, maxY * .577f);
        pathTemp.lineTo(maxX * .385f, maxY * .586f);
        pathTemp.lineTo(maxX * .410f, maxY * .622f);
        pathTemp.lineTo(maxX * .415f, maxY * .611f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .420f, maxY * .612f);
        pathTemp.lineTo(maxX * .416f, maxY * .629f);
        pathTemp.lineTo(maxX * .445f, maxY * .647f);
        pathTemp.lineTo(maxX * .448f, maxY * .633f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .455f, maxY * .635f);
        pathTemp.lineTo(maxX * .453f, maxY * .651f);
        pathTemp.lineTo(maxX * .484f, maxY * .663f);
        pathTemp.lineTo(maxX * .485f, maxY * .648f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .490f, maxY * .649f);
        pathTemp.lineTo(maxX * .489f, maxY * .665f);
        pathTemp.lineTo(maxX * .520f, maxY * .670f);
        pathTemp.lineTo(maxX * .521f, maxY * .654f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //54
        tempRect = new Rect((int) (maxX * .38), (int) (maxY * .67), (int) (maxX * .5), (int) (maxY * .71));
        pathTemp.moveTo(maxX * .388f, maxY * .695f);
        pathTemp.lineTo(maxX * .389f, maxY * .710f);
        pathTemp.lineTo(maxX * .421f, maxY * .703f);
        pathTemp.lineTo(maxX * .420f, maxY * .687f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .425f, maxY * .686f);
        pathTemp.lineTo(maxX * .426f, maxY * .701f);
        pathTemp.lineTo(maxX * .458f, maxY * .694f);
        pathTemp.lineTo(maxX * .457f, maxY * .678f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .462f, maxY * .679f);
        pathTemp.lineTo(maxX * .461f, maxY * .694f);
        pathTemp.lineTo(maxX * .498f, maxY * .687f);
        pathTemp.lineTo(maxX * .496f, maxY * .671f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //55
        tempRect = new Rect((int) (maxX * .37), (int) (maxY * .71), (int) (maxX * .53), (int) (maxY * .83));
        pathTemp.moveTo(maxX * .381f, maxY * .836f);
        pathTemp.lineTo(maxX * .382f, maxY * .849f);
        pathTemp.lineTo(maxX * .414f, maxY * .834f);
        pathTemp.lineTo(maxX * .413f, maxY * .824f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .418f, maxY * .820f);
        pathTemp.lineTo(maxX * .420f, maxY * .832f);
        pathTemp.lineTo(maxX * .450f, maxY * .803f);
        pathTemp.lineTo(maxX * .446f, maxY * .791f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .449f, maxY * .791f);
        pathTemp.lineTo(maxX * .451f, maxY * .802f);
        pathTemp.lineTo(maxX * .479f, maxY * .773f);
        pathTemp.lineTo(maxX * .472f, maxY * .760f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .478f, maxY * .755f);
        pathTemp.lineTo(maxX * .484f, maxY * .767f);
        pathTemp.lineTo(maxX * .510f, maxY * .735f);
        pathTemp.lineTo(maxX * .505f, maxY * .720f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .509f, maxY * .718f);
        pathTemp.lineTo(maxX * .513f, maxY * .726f);
        pathTemp.lineTo(maxX * .538f, maxY * .685f);
        pathTemp.lineTo(maxX * .528f, maxY * .678f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //56
        tempRect = new Rect((int) (maxX * .66), (int) (maxY * .52), (int) (maxX * .74), (int) (maxY * .6));
        pathTemp.moveTo(maxX * .665f, maxY * .542f);
        pathTemp.lineTo(maxX * .7f, maxY * .557f);
        pathTemp.lineTo(maxX * .697f, maxY * .57f);
        pathTemp.lineTo(maxX * .662f, maxY * .557f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .702f, maxY * .557f);
        pathTemp.lineTo(maxX * .735f, maxY * .575f);
        pathTemp.lineTo(maxX * .735f, maxY * .589f);
        pathTemp.lineTo(maxX * .699f, maxY * .575f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //57
        tempRect = new Rect((int) (maxX * .64), (int) (maxY * .59), (int) (maxX * .75), (int) (maxY * .69));
        pathTemp.moveTo(maxX * .646f, maxY * .662f);
        pathTemp.lineTo(maxX * .678f, maxY * .659f);
        pathTemp.lineTo(maxX * .683f, maxY * .675f);
        pathTemp.lineTo(maxX * .647f, maxY * .676f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .684f, maxY * .657f);
        pathTemp.lineTo(maxX * .715f, maxY * .631f);
        pathTemp.lineTo(maxX * .72f, maxY * .646f);
        pathTemp.lineTo(maxX * .684f, maxY * .67f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .72f, maxY * .628f);
        pathTemp.lineTo(maxX * .745f, maxY * .595f);
        pathTemp.lineTo(maxX * .752f, maxY * .606f);
        pathTemp.lineTo(maxX * .725f, maxY * .64f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //58
        tempRect = new Rect((int) (maxX * .64), (int) (maxY * .68), (int) (maxX * .7), (int) (maxY * .78));
        pathTemp.moveTo(maxX * .638f, maxY * .69f);
        pathTemp.lineTo(maxX * .648f, maxY * .685f);
        pathTemp.lineTo(maxX * .66f, maxY * .73f);
        pathTemp.lineTo(maxX * .653f, maxY * .737f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .654f, maxY * .742f);
        pathTemp.lineTo(maxX * .664f, maxY * .738f);
        pathTemp.lineTo(maxX * .681f, maxY * .79f);
        pathTemp.lineTo(maxX * .671f, maxY * .795f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .674f, maxY * .8f);
        pathTemp.lineTo(maxX * .682f, maxY * .792f);
        pathTemp.lineTo(maxX * .7f, maxY * .845f);
        pathTemp.lineTo(maxX * .69f, maxY * .85f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //59
        tempRect = new Rect((int) (maxX * .61), (int) (maxY * .85), (int) (maxX * .7), (int) (maxY * .9));
        pathTemp.moveTo(maxX * .615f, maxY * .867f);
        pathTemp.lineTo(maxX * .65f, maxY * .86f);
        pathTemp.lineTo(maxX * .655f, maxY * .88f);
        pathTemp.lineTo(maxX * .618f, maxY * .89f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .654f, maxY * .86f);
        pathTemp.lineTo(maxX * .69f, maxY * .85f);
        pathTemp.lineTo(maxX * .691f, maxY * .87f);
        pathTemp.lineTo(maxX * .655f, maxY * .878f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //60
        tempRect = new Rect((int) (maxX * .61), (int) (maxY * .52), (int) (maxX * .7), (int) (maxY * .6));
        pathTemp.moveTo(maxX * .65f, maxY * .53f);
        pathTemp.lineTo(maxX * .658f, maxY * .534f);
        pathTemp.lineTo(maxX * .652f, maxY * .595f);
        pathTemp.lineTo(maxX * .642f, maxY * .59f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .64f, maxY * .595f);
        pathTemp.lineTo(maxX * .651f, maxY * .6f);
        pathTemp.lineTo(maxX * .642f, maxY * .653f);
        pathTemp.lineTo(maxX * .63f, maxY * .65f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //61
        tempRect = new Rect((int) (maxX * .58), (int) (maxY * .68), (int) (maxX * .635), (int) (maxY * .8));
        pathTemp.moveTo(maxX * .573f, maxY * .778f);
        pathTemp.lineTo(maxX * .595f, maxY * .73f);
        pathTemp.lineTo(maxX * .607f, maxY * .74f);
        pathTemp.lineTo(maxX * .583f, maxY * .79f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .596f, maxY * .728f);
        pathTemp.lineTo(maxX * .618f, maxY * .678f);
        pathTemp.lineTo(maxX * .629f, maxY * .69f);
        pathTemp.lineTo(maxX * .61f, maxY * .73f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //62
        tempRect = new Rect((int) (maxX * .54), (int) (maxY * .65), (int) (maxX * .64), (int) (maxY * .685));
        pathTemp.moveTo(maxX * .551f, maxY * .659f);
        pathTemp.lineTo(maxX * .59f, maxY * .658f);
        pathTemp.lineTo(maxX * .591f, maxY * .67f);
        pathTemp.lineTo(maxX * .553f, maxY * .675f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .592f, maxY * .659f);
        pathTemp.lineTo(maxX * .625f, maxY * .655f);
        pathTemp.lineTo(maxX * .628f, maxY * .67f);
        pathTemp.lineTo(maxX * .591f, maxY * .675f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        //63
        tempRect = new Rect((int) (maxX * .555), (int) (maxY * .49), (int) (maxX * .65), (int) (maxY * .535));
        pathTemp.moveTo(maxX * .572f, maxY * .518f);
        pathTemp.lineTo(maxX * .605f, maxY * .518f);
        pathTemp.lineTo(maxX * .606f, maxY * .533f);
        pathTemp.lineTo(maxX * .574f, maxY * .533f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .61f, maxY * .518f);
        pathTemp.lineTo(maxX * .645f, maxY * .518f);
        pathTemp.lineTo(maxX * .645f, maxY * .534f);
        pathTemp.lineTo(maxX * .612f, maxY * .534f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .572f, maxY * .502f);
        pathTemp2.lineTo(maxX * .605f, maxY * .502f);
        pathTemp2.lineTo(maxX * .606f, maxY * .517f);
        pathTemp2.lineTo(maxX * .574f, maxY * .517f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .61f, maxY * .502f);
        pathTemp2.lineTo(maxX * .645f, maxY * .502f);
        pathTemp2.lineTo(maxX * .645f, maxY * .518f);
        pathTemp2.lineTo(maxX * .612f, maxY * .518f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        //64
        tempRect = new Rect((int) (maxX * .65), (int) (maxY * .39), (int) (maxX * .71), (int) (maxY * .51));
        pathTemp.moveTo(maxX * .682f, maxY * .395f);
        pathTemp.lineTo(maxX * .693f, maxY * .402f);
        pathTemp.lineTo(maxX * .673f, maxY * .45f);
        pathTemp.lineTo(maxX * .664f, maxY * .442f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .66f, maxY * .445f);
        pathTemp.lineTo(maxX * .672f, maxY * .454f);
        pathTemp.lineTo(maxX * .653f, maxY * .501f);
        pathTemp.lineTo(maxX * .642f, maxY * .494f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .694f, maxY * .403f);
        pathTemp2.lineTo(maxX * .705f, maxY * .410f);
        pathTemp2.lineTo(maxX * .685f, maxY * .458f);
        pathTemp2.lineTo(maxX * .676f, maxY * .450f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .672f, maxY * .453f);
        pathTemp2.lineTo(maxX * .685f, maxY * .462f);
        pathTemp2.lineTo(maxX * .665f, maxY * .509f);
        pathTemp2.lineTo(maxX * .654f, maxY * .502f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        tempRect = new Rect((int) (maxX * .545), (int) (maxY * .35), (int) (maxX * .7), (int) (maxY * .435));
        pathTemp.moveTo(maxX * .545f, maxY * .425f);
        pathTemp.lineTo(maxX * .575f, maxY * .395f);
        pathTemp.lineTo(maxX * .585f, maxY * .41f);
        pathTemp.lineTo(maxX * .555f, maxY * .436f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .576f, maxY * .39f);
        pathTemp.lineTo(maxX * .607f, maxY * .362f);
        pathTemp.lineTo(maxX * .615f, maxY * .378f);
        pathTemp.lineTo(maxX * .587f, maxY * .405f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .617f, maxY * .36f);
        pathTemp.lineTo(maxX * .653f, maxY * .367f);
        pathTemp.lineTo(maxX * .651f, maxY * .38f);
        pathTemp.lineTo(maxX * .616f, maxY * .372f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .656f, maxY * .37f);
        pathTemp.lineTo(maxX * .687f, maxY * .375f);
        pathTemp.lineTo(maxX * .688f, maxY * .39f);
        pathTemp.lineTo(maxX * .655f, maxY * .385f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        tempRect = new Rect((int) (maxX * .4), (int) (maxY * .82), (int) (maxX * .55), (int) (maxY * .86));
        pathTemp.moveTo(maxX * .399f, maxY * .850f);
        pathTemp.lineTo(maxX * .400f, maxY * .865f);
        pathTemp.lineTo(maxX * .435f, maxY * .860f);
        pathTemp.lineTo(maxX * .434f, maxY * .843f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .439f, maxY * .842f);
        pathTemp.lineTo(maxX * .440f, maxY * .857f);
        pathTemp.lineTo(maxX * .473f, maxY * .851f);
        pathTemp.lineTo(maxX * .472f, maxY * .832f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .478f, maxY * .831f);
        pathTemp.lineTo(maxX * .479f, maxY * .846f);
        pathTemp.lineTo(maxX * .508f, maxY * .840f);
        pathTemp.lineTo(maxX * .507f, maxY * .824f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .512f, maxY * .825f);
        pathTemp.lineTo(maxX * .513f, maxY * .839f);
        pathTemp.lineTo(maxX * .543f, maxY * .833f);
        pathTemp.lineTo(maxX * .542f, maxY * .817f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        tempRect = new Rect((int) (maxX * .38), (int) (maxY * .85), (int) (maxX * .59), (int) (maxY));
        pathTemp.moveTo(maxX * .380f, maxY * .867f);
        pathTemp.lineTo(maxX * .375f, maxY * .877f);
        pathTemp.lineTo(maxX * .403f, maxY * .904f);
        pathTemp.lineTo(maxX * .408f, maxY * .889f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .412f, maxY * .890f);
        pathTemp.lineTo(maxX * .408f, maxY * .902f);
        pathTemp.lineTo(maxX * .440f, maxY * .914f);
        pathTemp.lineTo(maxX * .443f, maxY * .903f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .448f, maxY * .905f);
        pathTemp.lineTo(maxX * .445f, maxY * .919f);
        pathTemp.lineTo(maxX * .482f, maxY * .926f);
        pathTemp.lineTo(maxX * .482f, maxY * .912f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .485f, maxY * .912f);
        pathTemp.lineTo(maxX * .485f, maxY * .928f);
        pathTemp.lineTo(maxX * .520f, maxY * .930f);
        pathTemp.lineTo(maxX * .521f, maxY * .914f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .524f, maxY * .914f);
        pathTemp.lineTo(maxX * .524f, maxY * .930f);
        pathTemp.lineTo(maxX * .556f, maxY * .927f);
        pathTemp.lineTo(maxX * .554f, maxY * .911f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .558f, maxY * .908f);
        pathTemp.lineTo(maxX * .560f, maxY * .925f);
        pathTemp.lineTo(maxX * .594f, maxY * .903f);
        pathTemp.lineTo(maxX * .587f, maxY * .889f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        tempRect = new Rect((int) (maxX * .45), (int) (maxY * .09), (int) (maxX * .56), (int) (maxY * .27));
        pathTemp.moveTo(maxX * .463f, maxY * .095f);
        pathTemp.lineTo(maxX * .456f, maxY * .107f);
        pathTemp.lineTo(maxX * .479f, maxY * .139f);
        pathTemp.lineTo(maxX * .484f, maxY * .131f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .488f, maxY * .138f);
        pathTemp.lineTo(maxX * .481f, maxY * .15f);
        pathTemp.lineTo(maxX * .507f, maxY * .182f);
        pathTemp.lineTo(maxX * .512f, maxY * .171f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .518f, maxY * .176f);
        pathTemp.lineTo(maxX * .512f, maxY * .187f);
        pathTemp.lineTo(maxX * .533f, maxY * .228f);
        pathTemp.lineTo(maxX * .540f, maxY * .215f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .545f, maxY * .216f);
        pathTemp.lineTo(maxX * .538f, maxY * .229f);
        pathTemp.lineTo(maxX * .559f, maxY * .270f);
        pathTemp.lineTo(maxX * .566f, maxY * .257f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        tempRect = new Rect((int) (maxX * .55), (int) (maxY * .8), (int) (maxX * .61), (int) (maxY * .87));
        pathTemp.moveTo(maxX * .568f, maxY * .828f);
        pathTemp.lineTo(maxX * .563f, maxY * .835f);
        pathTemp.lineTo(maxX * .583f, maxY * .876f);
        pathTemp.lineTo(maxX * .588f, maxY * .868f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .58f, maxY * .816f);
        pathTemp2.lineTo(maxX * .573f, maxY * .823f);
        pathTemp2.lineTo(maxX * .6f, maxY * .864f);
        pathTemp2.lineTo(maxX * .606f, maxY * .856f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        tempRect = new Rect((int) (maxX * .53), (int) (maxY * .68), (int) (maxX * .58), (int) (maxY * .8));
        pathTemp.moveTo(maxX * .564f, maxY * .680f);
        pathTemp.lineTo(maxX * .552f, maxY * .682f);
        pathTemp.lineTo(maxX * .556f, maxY * .735f);
        pathTemp.lineTo(maxX * .568f, maxY * .734f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .567f, maxY * .738f);
        pathTemp.lineTo(maxX * .555f, maxY * .740f);
        pathTemp.lineTo(maxX * .559f, maxY * .793f);
        pathTemp.lineTo(maxX * .571f, maxY * .792f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .551f, maxY * .682f);
        pathTemp2.lineTo(maxX * .539f, maxY * .684f);
        pathTemp2.lineTo(maxX * .543f, maxY * .737f);
        pathTemp2.lineTo(maxX * .555f, maxY * .736f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .554f, maxY * .74f);
        pathTemp2.lineTo(maxX * .542f, maxY * .742f);
        pathTemp2.lineTo(maxX * .546f, maxY * .795f);
        pathTemp2.lineTo(maxX * .558f, maxY * .794f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        tempRect = new Rect((int) (maxX * .53), (int) (maxY * .53), (int) (maxX * .58), (int) (maxY * .64));
        pathTemp.moveTo(maxX * .578f, maxY * .542f);
        pathTemp.lineTo(maxX * .568f, maxY * .538f);
        pathTemp.lineTo(maxX * .557f, maxY * .589f);
        pathTemp.lineTo(maxX * .566f, maxY * .593f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .567f, maxY * .601f);
        pathTemp.lineTo(maxX * .557f, maxY * .596f);
        pathTemp.lineTo(maxX * .546f, maxY * .647f);
        pathTemp.lineTo(maxX * .555f, maxY * .651f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .567f, maxY * .537f);
        pathTemp2.lineTo(maxX * .557f, maxY * .533f);
        pathTemp2.lineTo(maxX * .546f, maxY * .584f);
        pathTemp2.lineTo(maxX * .555f, maxY * .588f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .556f, maxY * .596f);
        pathTemp2.lineTo(maxX * .545f, maxY * .591f);
        pathTemp2.lineTo(maxX * .535f, maxY * .642f);
        pathTemp2.lineTo(maxX * .544f, maxY * .646f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        tempRect = new Rect((int) (maxX * .575), (int) (maxY * .285), (int) (maxX * .69), (int) (maxY * .38));
        pathTemp.moveTo(maxX * .579f, maxY * .29f);
        pathTemp.lineTo(maxX * .613f, maxY * .315f);
        pathTemp.lineTo(maxX * .61f, maxY * .326f);
        pathTemp.lineTo(maxX * .573f, maxY * .302f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .613f, maxY * .317f);
        pathTemp.lineTo(maxX * .648f, maxY * .335f);
        pathTemp.lineTo(maxX * .643f, maxY * .35f);
        pathTemp.lineTo(maxX * .61f, maxY * .333f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .652f, maxY * .341f);
        pathTemp.lineTo(maxX * .685f, maxY * .353f);
        pathTemp.lineTo(maxX * .682f, maxY * .364f);
        pathTemp.lineTo(maxX * .65f, maxY * .355f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        tempRect = new Rect((int) (maxX * .625), (int) (maxY * .21), (int) (maxX * .755), (int) (maxY * .275));
        pathTemp.moveTo(maxX * .582f, maxY * .267f);
        pathTemp.lineTo(maxX * .618f, maxY * .258f);
        pathTemp.lineTo(maxX * .62f, maxY * .278f);
        pathTemp.lineTo(maxX * .582f, maxY * .283f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .621f, maxY * .257f);
        pathTemp.lineTo(maxX * .657f, maxY * .248f);
        pathTemp.lineTo(maxX * .657f, maxY * .263f);
        pathTemp.lineTo(maxX * .622f, maxY * .275f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .66f, maxY * .246f);
        pathTemp.lineTo(maxX * .692f, maxY * .235f);
        pathTemp.lineTo(maxX * .695f, maxY * .255f);
        pathTemp.lineTo(maxX * .66f, maxY * .263f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .698f, maxY * .233f);
        pathTemp.lineTo(maxX * .732f, maxY * .225f);
        pathTemp.lineTo(maxX * .733f, maxY * .242f);
        pathTemp.lineTo(maxX * .7f, maxY * .255f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .73f, maxY * .223f);
        pathTemp.lineTo(maxX * .763f, maxY * .218f);
        pathTemp.lineTo(maxX * .768f, maxY * .235f);
        pathTemp.lineTo(maxX * .735f, maxY * .24f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .767f, maxY * .213f);
        pathTemp.lineTo(maxX * .806f, maxY * .205f);
        pathTemp.lineTo(maxX * .808f, maxY * .223f);
        pathTemp.lineTo(maxX * .773f, maxY * .232f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        tempRect = new Rect((int) (maxX * .59), (int) (maxY * .155), (int) (maxX * .7), (int) (maxY * .23));
        pathTemp.moveTo(maxX * .59f, maxY * .245f);
        pathTemp.lineTo(maxX * .62f, maxY * .22f);
        pathTemp.lineTo(maxX * .625f, maxY * .233f);
        pathTemp.lineTo(maxX * .592f, maxY * .26f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .625f, maxY * .218f);
        pathTemp.lineTo(maxX * .656f, maxY * .196f);
        pathTemp.lineTo(maxX * .66f, maxY * .21f);
        pathTemp.lineTo(maxX * .633f, maxY * .23f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .66f, maxY * .195f);
        pathTemp.lineTo(maxX * .693f, maxY * .174f);
        pathTemp.lineTo(maxX * .698f, maxY * .191f);
        pathTemp.lineTo(maxX * .663f, maxY * .21f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        tempRect = new Rect((int) (maxX * .51), (int) (maxY * .08), (int) (maxX * .67), (int) (maxY * .15));
        pathTemp.moveTo(maxX * .475f, maxY * .075f);
        pathTemp.lineTo(maxX * .508f, maxY * .087f);
        pathTemp.lineTo(maxX * .506f, maxY * .102f);
        pathTemp.lineTo(maxX * .471f, maxY * .087f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .514f, maxY * .088f);
        pathTemp.lineTo(maxX * .547f, maxY * .098f);
        pathTemp.lineTo(maxX * .545f, maxY * .112f);
        pathTemp.lineTo(maxX * .509f, maxY * .104f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .552f, maxY * .098f);
        pathTemp.lineTo(maxX * .58f, maxY * .108f);
        pathTemp.lineTo(maxX * .58f, maxY * .125f);
        pathTemp.lineTo(maxX * .55f, maxY * .114f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .587f, maxY * .106f);
        pathTemp.lineTo(maxX * .622f, maxY * .12f);
        pathTemp.lineTo(maxX * .618f, maxY * .132f);
        pathTemp.lineTo(maxX * .585f, maxY * .124f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .623f, maxY * .12f);
        pathTemp.lineTo(maxX * .66f, maxY * .13f);
        pathTemp.lineTo(maxX * .655f, maxY * .15f);
        pathTemp.lineTo(maxX * .62f, maxY * .138f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .66f, maxY * .135f);
        pathTemp.lineTo(maxX * .697f, maxY * .143f);
        pathTemp.lineTo(maxX * .692f, maxY * .162f);
        pathTemp.lineTo(maxX * .659f, maxY * .15f);
        pathTemp.close();
        paths.add(new Path(pathTemp));
        paths2.add(blankPath);
        touchRects.add(tempRect);
        pathTemp.reset();

        tempRect = new Rect((int) (maxX * .53), (int) (maxY * .3), (int) (maxX * .58), (int) (maxY * .41));
        pathTemp.moveTo(maxX * .565f, maxY * .303f);
        pathTemp.lineTo(maxX * .555f, maxY * .297f);
        pathTemp.lineTo(maxX * .542f, maxY * .345f);
        pathTemp.lineTo(maxX * .553f, maxY * .35f);
        pathTemp.close();
        pathTemp.moveTo(maxX * .552f, maxY * .357f);
        pathTemp.lineTo(maxX * .542f, maxY * .351f);
        pathTemp.lineTo(maxX * .529f, maxY * .402f);
        pathTemp.lineTo(maxX * .540f, maxY * .408f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .575f, maxY * .310f);
        pathTemp2.lineTo(maxX * .565f, maxY * .304f);
        pathTemp2.lineTo(maxX * .552f, maxY * .352f);
        pathTemp2.lineTo(maxX * .563f, maxY * .357f);
        pathTemp2.close();
        pathTemp2.moveTo(maxX * .562f, maxY * .364f);
        pathTemp2.lineTo(maxX * .552f, maxY * .358f);
        pathTemp2.lineTo(maxX * .539f, maxY * .409f);
        pathTemp2.lineTo(maxX * .550f, maxY * .415f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        tempRect = new Rect((int) (maxX * .54), (int) (maxY * .46), (int) (maxX * .59), (int) (maxY * .5));
        pathTemp.moveTo(maxX * .543f, maxY * .450f);
        pathTemp.lineTo(maxX * .533f, maxY * .463f);
        pathTemp.lineTo(maxX * .547f, maxY * .511f);
        pathTemp.lineTo(maxX * .558f, maxY * .499f);
        pathTemp.close();
        pathTemp2.moveTo(maxX * .554f, maxY * .436f);
        pathTemp2.lineTo(maxX * .544f, maxY * .449f);
        pathTemp2.lineTo(maxX * .558f, maxY * .497f);
        pathTemp2.lineTo(maxX * .569f, maxY * .485f);
        pathTemp2.close();
        paths.add(new Path(pathTemp));
        paths2.add(new Path(pathTemp2));
        touchRects.add(tempRect);
        pathTemp.reset();
        pathTemp2.reset();

        highlights = new boolean[paths.size()];
        for(int i = 0; i < highlights.length; i++){
            highlights[i] = false;
        }
        selected = new boolean[paths.size()];
        for(int i = 0; i < selected.length; i++){
            selected[i] = false;
        }
        covered = new boolean[paths.size()];
        for(int i = 0; i < covered.length; i++) {
            covered[i] = false;
        }
        playerIDs = new int[paths.size()];
        for(int i = 0; i < playerIDs.length; i++){
            playerIDs[i] = -1;
        }
        highlights2 = new boolean[paths.size()];
        for(int i = 0; i < highlights2.length; i++){
            highlights2[i] = false;
        }
        selected2 = new boolean[paths.size()];
        for(int i = 0; i < selected2.length; i++){
            selected2[i] = false;
        }
        covered2 = new boolean[paths.size()];
        for(int i = 0; i < covered2.length; i++) {
            covered2[i] = false;
        }
        playerIDs2 = new int[paths.size()];
        for(int i = 0; i < playerIDs2.length; i++){
            playerIDs2[i] = -1;
        }
    }

    /*
     * onDraw method handles canvas and drawing (like paint() in java)
     */
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        drawBoard(canvas);

        for(int i = 0; i < highlights.length; i++){
            if(highlights[i]){
                paint.setColor(HIGHLIGHT_COLOR);
                paint.setStrokeWidth(5);
                paint.setStyle(Paint.Style.STROKE);
                Path temp= paths.get(i);
                canvas.drawPath(temp, paint);
            }
            if(selected[i]){
                paint.setColor(SELECTION_COLOR);
                canvas.drawPath(paths.get(i),paint);
            }
            if(covered[i]) {
                if(playerIDs[i] == 0){paint.setColor(PLAYER1_COLOR);}
                else if(playerIDs[i] == 1){paint.setColor(PLAYER2_COLOR);}
                else if(playerIDs[i] == 2){paint.setColor(PLAYER3_COLOR);}
                else if(playerIDs[i] == 3){paint.setColor(PLAYER4_COLOR);}
                paint.setStyle(Paint.Style.FILL);
                canvas.drawPath(paths.get(i), paint);
                paint.setColor(OUTLINE_COLOR);
                paint.setStrokeWidth(5);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(paths.get(i),paint);
            }
            if(highlights2[i]){
                paint.setColor(HIGHLIGHT_COLOR);
                paint.setStrokeWidth(5);
                paint.setStyle(Paint.Style.STROKE);
                Path temp= paths2.get(i);
                canvas.drawPath(temp, paint);
            }
            if(selected2[i]){
                paint.setColor(SELECTION_COLOR);
                canvas.drawPath(paths2.get(i),paint);
            }
            if(covered2[i]) {
                if(playerIDs2[i] == 0){paint.setColor(PLAYER1_COLOR);}
                else if(playerIDs2[i] == 1){paint.setColor(PLAYER2_COLOR);}
                else if(playerIDs2[i] == 2){paint.setColor(PLAYER3_COLOR);}
                else if(playerIDs2[i] == 3){paint.setColor(PLAYER4_COLOR);}
                paint.setStyle(Paint.Style.FILL);
                canvas.drawPath(paths2.get(i), paint);
                paint.setColor(OUTLINE_COLOR);
                paint.setStrokeWidth(5);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(paths2.get(i),paint);
            }
        }
    }
    /*
     * Method which draws the board image onto the canvas
     */
    protected void drawBoard(Canvas canvas){
        boardImage = Bitmap.createScaledBitmap(boardImage, maxX, maxY, false);
        canvas.drawBitmap(boardImage, 0, 0, new Paint());
    }

    /** Returns the position of the track clicked by the user if within the track selection area
      @param xPos
      @param yPos
      @return position in myTracks array
     */
    public int clickedTrack(int xPos, int yPos){
        int pos = -1;
        Rect tempRect;
        for(int i = 0; i < touchRects.size(); i++){
            tempRect = touchRects.get(i);
            if(tempRect.contains(xPos, yPos)){
                pos = i;
            }
        }
        return pos;
    }

    public void setHighlights(boolean[] highlightsEnter){
        for(int i = 0; i < highlightsEnter.length; i++){
            this.highlights[i] = highlightsEnter[i];
        }
    }

    public void setSelected(boolean[] selectedEnter){
        for(int i = 0; i < selectedEnter.length; i++){
            this.selected[i] = selectedEnter[i];
        }
    }

    public void setCovered(boolean[] coveredEnter){
        for(int i = 0; i < coveredEnter.length; i++){
            this.covered[i] = coveredEnter[i];
        }
    }

    public void setPlayerIDs(int[] playerIDsEnter){
        for(int i = 0; i < playerIDsEnter.length; i++){
            this.playerIDs[i] = playerIDsEnter[i];
        }
    }

    public void setHighlights2(boolean[] highlightsEnter){
        for(int i = 0; i < highlightsEnter.length; i++){
            this.highlights2[i] = highlightsEnter[i];
        }
    }

    public void setSelected2(boolean[] selectedEnter){
        for(int i = 0; i < selectedEnter.length; i++){
            this.selected2[i] = selectedEnter[i];
        }
    }

    public void setCovered2(boolean[] coveredEnter){
        for(int i = 0; i < coveredEnter.length; i++){
            this.covered2[i] = coveredEnter[i];
        }
    }

    public void setPlayerIDs2(int[] playerIDsEnter){
        for(int i = 0; i < playerIDsEnter.length; i++){
            this.playerIDs2[i] = playerIDsEnter[i];
        }
    }
}
