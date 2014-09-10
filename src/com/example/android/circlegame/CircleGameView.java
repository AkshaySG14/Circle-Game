package com.example.android.circlegame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

@SuppressLint("HandlerLeak")
public class CircleGameView extends SurfaceView implements SurfaceHolder.Callback {
    class CircleGameThread extends Thread {
        private int xValue[] = new int[55];
        private int yValue[] = new int[55];
        private int chosen[];

        private SoundPool sp;

        private int position = 0;
        private int levelSet = 0;
        private int levelType = 0;
        private int repeats;
        private int sLevels;
        private int gSize;
        private int circles;

        private double rate = 1;
        private final long MILLI = 1000;

        private final BitmapManager manager;
        private final SoundManager sm;
        private Bitmap background;

        private final SurfaceHolder mSurfaceHolder;
        private Handler mHandler;

        private boolean running = true;
        private boolean selected[] = new boolean[54];
        private boolean isReady = true;
        private boolean checkable = true;

        public CircleGameThread(SurfaceHolder surfaceHolder, Context context,
                                Handler handler) {

            mSurfaceHolder = surfaceHolder;
            mHandler = handler;
            mContext = context;

            sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            sm = new SoundManager();
            //Gets the size of the display to properly scale
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            //Gets the width, height, and area of the display
            CANVASWIDTH = size.x;
            CANVASHEIGHT = size.y;
            CANVASAREA = CANVASWIDTH * CANVASHEIGHT;
            HEIGHTRATIO = CANVASHEIGHT / SOURCEHEIGHT;
            WIDTHRATIO = CANVASWIDTH / SOURCEWIDTH;
            AREARATIO = CANVASAREA / SOURCEAREA;
            RADIUS = (int) Math.sqrt(4900 * AREARATIO);
            DIAMETER = RADIUS * 2;

            for (int i = 0; i < 51; i ++)
            {
                //Sets the score of all levels to 0
                gScore[i] = 0;
            }

            manager = new BitmapManager(mContext, CANVASWIDTH, CANVASHEIGHT);
            background = manager.getTitleScreen();
        }

        protected void doDraw(Canvas canvas) {
            //Renders the background
            canvas.drawBitmap(background, 0, 0, null);

            //Renders all the circles
            for (int i = 0; i < order.size(); i++)
            {
                BitmapDrawable circle = order.get(i);
                circle.setBounds(xValue[i], yValue[i], xValue[i] + DIAMETER, yValue[i] + DIAMETER);
                circle.draw(canvas);
            }
        }

        protected void titleScreen() {
            //Serves as a transition to the game by setting isReady to true, which prevents multiple intializations
            background = manager.getTitleScreen();
            isReady = true;
            setState(INITIALIZING);
        }

        protected void beginGame() {
            //Begins the game and initializes important variables
            if (!isReady)
                return;
            setIsReady(false);
            endGame();
            setState(STARTING);
            setFresh(false);
            won = false;
        }

        protected void gameStart(int level) {
            //This is where the game actually starts
            background = manager.getBackground();
            score = 100;
            circles = 0;

            for (int i = 0; i < 18; i++) {
                xValue[i] = 0;
                yValue[i] = 0;
                selected[i] = false;
            }

            //Initializes important variables by using the initializer class
            Initializer init = new Initializer(HEIGHTRATIO, WIDTHRATIO);
            init.setLevel(level);
            xValue = init.getX();
            yValue = init.getY();
            int[] values = init.getValues();
            levelType = values[0];
            repeats = values[1];
            sLevels = values[2];
            rate = values[3];
            mScore = values[4];
            dScore = values[5];
            circles = values[6];
            order = getRandomOrder(values[7]);
            setState(RUNNING);

        }

        private ArrayList<BitmapDrawable> getRandomOrder(int limit) {
            //Conjures random circle from list of circles
            final ArrayList<BitmapDrawable> determine = new ArrayList<BitmapDrawable>();
            int i = 1;
            boolean unequal;
            BitmapDrawable test;
            final Resources res = mContext.getResources();

            test = new BitmapDrawable(res, manager.getCircle());

            for (int p = 0; p < limit; p++)
                determine.add(test);

            while (i < limit) {
                unequal = true;
                test = new BitmapDrawable(res, manager.getCircle());

                for (BitmapDrawable circle : determine)
                {
                    if (test.getBitmap().sameAs(circle.getBitmap()))
                    {
                        unequal = false;
                    }
                }

                 determine.set(i, test);
                 if (unequal)
                 i++;

                            }
            return determine;
        }


        @SuppressWarnings("unchecked")
        protected void gameRun() {
            //Starts the game by randomly selecting circles
            int base = 1000;
            setState(BUSY);
            selectOrder = (ArrayList<BitmapDrawable>) order.clone();
            gameOrder = new ArrayList<BitmapDrawable>();
            chosen = new int[order.size()];

            for (int i = 0; i < order.size(); i ++)
            {
                chosen[i] = 0;
            }

            checkable = true;
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    circleRun();
                }
            }, base);

        }

        private void circleRun() {
            //Chooses a certain amount of circles based on the level
            final int size = circles + repeats;
            gSize = size;
            long base = 500;
            int count = 1;
            for (int i = size; i > 0; i--) {
                final int p = count;
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        chooseCircle();
                        if (p == size) {
                            mHandler.postDelayed(new Runnable() {
                                public void run() {
                                    setState(INTERIM);
                                }
                            }, MILLI);
                            mHandler.postDelayed(new Runnable() {
                                public void run() {
                                    setState(WAITING);
                                    position = 0;
                                }
                            },(int) (MILLI * 1.5));
                        }
                    }
                }, base);
                base += (long) ((MILLI / rate));
                count++;
            }
        }

        private void chooseCircle() {
            //Randomly selects a circle
            BitmapDrawable circle = selectOrder.get((int) (Math.random() * selectOrder.size()));
            sp.play(sp.load(mContext, sm.findSound(circle), 1), 1, 1, 0, 0, 1);
            doSelected(circle);
            gameOrder.add(circle);
            int i = findCircle(circle);
            //Ensures that the same circle is not selected more times than its allocated repeats
            if (chosen[i] <= (levelType + 1) && repeats > 0)
            {
                if (chosen[i] > 0)
                {
                repeats --;
                }
                chosen[i] ++;
            }
            if (chosen[i] == (levelType + 1) || repeats == 0)
            {
            selectOrder.remove(circle);
            }
        }

        private void doSelected(final BitmapDrawable circle) {
            //Highlights the circle for half a second
            changeCircle(circle, false);
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    changeCircle(circle, false);
                }
            }, 500);

        }

        private void changeCircle(BitmapDrawable circle, boolean isCorrect) {
            //This is the method that actually changes the color of the circle
            for (int i = 0; i < order.size(); i++)
            {
                if (circle.equals(order.get(i)))
                {
                    if (selected[i])
                    {
                        circle.clearColorFilter();
                        selected[i] = false;
                    }
                    else
                    {
                        //Changes color based on whether the circle is correct, or whether the player has selected it
                        if (mMode == BUSY)
                        {
                            circle.setColorFilter(0xAA00FFFF, PorterDuff.Mode.SRC_ATOP);
                        }
                        else if (isCorrect)
                        {
                            circle.setColorFilter(0xAA00FF00, PorterDuff.Mode.SRC_ATOP);
                        }
                        else
                        {
                            circle.setColorFilter(0xAAFF0000, PorterDuff.Mode.SRC_ATOP);
                        }

                        selected[i] = true;
                    }
                }
            }
        }

        public int findCircle(BitmapDrawable circle)
        {
            //Checks if the selected circle is chosen correctly
            for (int i = 0; i < order.size(); i ++)
                if (circle.getBitmap().sameAs(order.get(i).getBitmap()))
                    return i;
            return -500;
        }

        public void isInCircle(MotionEvent event) {
            int xCoordinate = (int) event.getX();
            int yCoordinate = (int) event.getY();
            int xCenter;
            int yCenter;
            double distance;

            if (mMode != WAITING)
                return;

            //Using the distance formula, this detects whether the clicked point is inside the circle
            for (int i = 0; i < order.size(); i++) {
                xCenter = xValue[i] + RADIUS;
                yCenter = yValue[i] + RADIUS;
                distance = Math.sqrt(Math.pow(xCoordinate - xCenter, 2) + Math.pow(yCoordinate - yCenter, 2));

                    if (distance < RADIUS)
                    {
                        checkCircle(order.get(i));
                        break;
                    }
                 }
            }

        public void checkCircle(final BitmapDrawable circle) {
            if (!checkable)
                return;
            if (circle.equals(gameOrder.get(position)))
            {
                /* If the circle is the correct one, the game checks for the next circle, and it highlights the circle
                and plays the correct sound
                 */
                position++;
                sp.play(sp.load(mContext, sm.findSound(circle), 1), 1, 1, 0, 0, 1);
                changeCircle(circle, true);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        changeCircle(circle, true);
                    }
                }, 200);
                if (position == gSize)
                {
                    checkable = false;
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            if (sLevels > 0)
                            {
                                gameRun();
                                sLevels --;
                            }
                            else
                            {
                            sp.play(sp.load(mContext, R.raw.victory_sound, 1), 1, 1, 0, 0, 1);
                            if (levelSet + 1 == maxLevel)
                            maxLevel++;
                            won = true;
                            if (gScore[levelSet] < score)
                                gScore[levelSet] = score;
                            setState(FINISHED);
                            }
                        }
                    }, (int) (MILLI*0.25));
                }
            }

            else
            {
                //If the circle selected is the wrong one, an incorrect sound will play
                sp.play(sp.load(mContext, R.raw.wrong_sound, 1), 1, 1, 0, 0, 1);
                changeCircle(circle, false);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        changeCircle(circle, false);
                    }
                }, 200);
                setScore(mScore);
            }

        }

        public void setScore(int i)
        {
            //This decreases the score every interval
            if (!checkable)
                return;

                score -= i;

            if (score <= 0)
            {
                //If the score reaches or is below zero, the game ends. This also prevents negative scores
                checkable = false;
                setState(FINISHED);
                sp.play(sp.load(mContext, R.raw.defeat_sound, 1), 1, 1, 0, 0, 1);
                score = 0;
                won = false;
            }
            else
            {
                setState(WAITING);
            }

        }

        public void nextLevel() {
            //Increases the level, as well as playing a basic sound
            sp.play(sp.load(mContext, R.raw.leave_game_sounds, 1), 1, 1, 0, 0, 1);
            endGame();
            levelSet++;
            setIsReady(true);
            beginGame();
        }

        public void tryAgain()
        {
            //Activates methods necessary for reinitialization
            setIsReady(true);
            beginGame();
        }

        public void returnMenu()
        {
            //Returns to the menu by clearing the game and then setting the mode to initializing
            if (won)
                levelSet++;
            setState(CLEAR);
            endGame();
            background = manager.getTitleScreen();
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    setState(INITIALIZING);
                }
            }, (int) (MILLI * 0.25));
        }

        public void levelSelect(int i)
        {
            //When a level is clicked on the levelselect screen, this method sets the game to the level
            levelSet = i + cPage;
            beginGame();
        }

        public void credits() {
            //Simply goes to the credits
            if (!isReady)
                return;
            setIsReady(false);
            setState(CREDITS);
        }

        protected void highScores() {
            //Goes to the highscore screen
            if (!isReady)
                return;
            setIsReady(false);
            setState(HIGHSCORES);
        }

        public void endGame()
        {
            //This resets the order to ensure that the old game instance is completely cleared
            order = new ArrayList<BitmapDrawable>();
            gameOrder = new ArrayList<BitmapDrawable>();
        }

        public void changeCPage(boolean way)
        {
            //If the levelselect page is increased, each level button increases or decreases by 10
            if (way)
                cPage+=10;
            else
                cPage -=10;
            setState(CHOOSING);
        }

        public void changeSPage(boolean way)
        {
            //Same as the levelselect method except it modifies scores
            if (way)
                sPage+=10;
            else
                sPage -=10;
            setState(HIGHSCORES);
        }

        public void chooseLevel()
        {
            //Checks if the game is ready to reinitialize, and goes to the chooselevel screen if it is.
            if (!isReady)
                return;
            setIsReady(false);
            setState(CHOOSING);
        }

        public void play()
        {
            //Sets level to the max level (maxLevel - 1 as maxLevel starts at 0) and begins the game
            levelSet = maxLevel - 1;
            beginGame();
        }

        public int getMode()
        {
            return mMode;
        }

        public void setState(int mode) {
            synchronized (mSurfaceHolder) {
                mMode = mode;
                Message msg = mHandler.obtainMessage();
                mHandler.sendMessage(msg);
            }
        }

        public void setIsReady(boolean ready)
        {
            isReady = ready;
        }

        public void setRunning(boolean b) {
            running = b;
        }

        //This method, which runs every interval, is the method that drives the game. It does this based on the game state.
        @Override
        public void run() {
            while (running) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    doDraw(c);
                    synchronized (mSurfaceHolder) {
                        switch (mMode) {
                            case INITIALIZING:
                                titleScreen();
                                break;
                            case STARTING:
                                gameStart(levelSet);
                                break;
                            case RUNNING:
                                gameRun();
                                break;
                            case WAITING:
                                sCount ++;
                                if (sCount == 20)
                                {
                                    setScore(dScore);
                                    sCount = 0;
                                }
                                break;
                        }
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }


        }

        public void setSurfaceSize(int width, int height) {
            // synchronized to make sure these all change automically
            synchronized (mSurfaceHolder) {
                background = Bitmap.createScaledBitmap(background, width, height, true);
            }
        }
    }


    private CircleGameThread thread;
    private TextView gameOver;
    private TextView gameBegins;
    private TextView vScore;
    private TextView chooseLevel;
    private TextView highScoreList;
    private TextView creditTitle;
    private TextView creditList;
    private TextView title;

    private Context mContext;
    private Button returnMenu;
    private Button nextLevel;
    private Button play;
    private Button levelSelect;
    private Button credits;
    private Button highScores;
    private Button tryAgain;
    private Button back;
    private Button nextPage;
    private Button prevPage;

    private Button[] levels;
    private TextView[] scores;

    private boolean fresh = false;
    private int mMode;
    private int score = 0;
    private int sCount = 0;
    private int mScore = 0;
    private int maxLevel = 12;
    private int cPage = 0;
    private int sPage = 0;
    private int dScore = 0;
    private int[] gScore = new int[51];
    private boolean won = false;

    private ArrayList<BitmapDrawable> order = new ArrayList<BitmapDrawable>();
    private ArrayList<BitmapDrawable> gameOrder = new ArrayList<BitmapDrawable>();
    private ArrayList<BitmapDrawable> selectOrder = new ArrayList<BitmapDrawable>();

    public static final int INITIALIZING = 0;
    public static final int STARTING = 1;
    public static final int CLEAR = 2;
    public static final int CHOOSING = 3;
    public static final int HIGHSCORES = 4;
    public static final int CREDITS = 5;
    public static final int RUNNING = 6;
    public static final int BUSY = 7;
    public static final int INTERIM = 8;
    public static final int WAITING = 9;
    public static final int PAUSED = 10;
    public static final int FINISHED = 11;

    private final double SOURCEHEIGHT = 1135;
    private final double SOURCEWIDTH = 720;
    private final double SOURCEAREA = SOURCEHEIGHT * SOURCEWIDTH;
    private double CANVASHEIGHT;
    private double CANVASWIDTH;
    private double CANVASAREA;
    private double HEIGHTRATIO;
    private double WIDTHRATIO;
    private double AREARATIO;
    private int DIAMETER;
    private int RADIUS;

    public CircleGameThread getThread() {
        return thread;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        thread.setSurfaceSize(width, height);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // start the thread here so that we don't busy-wait in run()
        // waiting for the surface to be created
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void setGameOver(TextView view) {
        gameOver = view;
    }

    public void setGameBegins(TextView view) {
        gameBegins = view;
    }

    public void setScoreView(TextView view)
    {
       vScore = view;
    }

    public void setChooseLevel(TextView view)
    {
        chooseLevel = view;
    }

    public void setHighScoreList(TextView view)
    {
        highScoreList = view;
    }

    public void setCreditTitle(TextView view)
    {
        creditTitle = view;
    }

    public void setCreditList(TextView view)
    {
        creditList = view;
    }

    public void setTitle(TextView view)
    {
        title = view;
    }

    public void setReturnMenu(Button button) {
        returnMenu = button;
    }

    public void setNextLevel(Button button) {
        nextLevel = button;
    }

    public void setTryAgain(Button button)
    {
        tryAgain = button;
    }

    public void setPlay(Button button) {
        play = button;
    }

    public void setLevelSelect(Button button) {
        levelSelect = button;
    }

    public void setCredits(Button button) {
        credits = button;
    }

    public void setHighScores(Button button) {
        highScores = button;
    }

    public void setBack(Button button) {
        back = button;
    }

    public void setNextPage(Button button) {
        nextPage = button;
    }

    public void setPrevPage(Button button) {
        prevPage = button;
    }

    public void setLevels(Button[] buttons)
    {
        levels = buttons;
    }

    public void setScores(TextView[] textviews)
    {
        scores = textviews;
    }

    public void setFresh(boolean isFresh) {
        fresh = isFresh;
    }

    public CircleGameView(Context context) {
        super(context);
    }

    public CircleGameView(Context context, AttributeSet attrs) {
        super(context, attrs);


        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // create thread only; it's started in surfaceCreated()
        thread = new CircleGameThread(holder, context, new Handler() {
            @Override
            public void handleMessage(Message m) {
                //Makes all the buttons invisible until they are needed
                returnMenu.setVisibility(View.GONE);
                gameOver.setVisibility(View.GONE);
                tryAgain.setVisibility(View.GONE);
                vScore.setVisibility(View.GONE);
                play.setVisibility(View.GONE);
                highScores.setVisibility(View.GONE);
                levelSelect.setVisibility(View.GONE);
                credits.setVisibility(View.GONE);
                nextLevel.setVisibility(View.GONE);
                returnMenu.setVisibility(View.GONE);
                gameOver.setVisibility(View.GONE);
                tryAgain.setVisibility(View.GONE);
                vScore.setVisibility(View.GONE);
                chooseLevel.setVisibility(View.GONE);
                back.setVisibility(View.GONE);
                nextPage.setVisibility(View.GONE);
                prevPage.setVisibility(View.GONE);
                gameBegins.setVisibility(View.GONE);
                highScoreList.setVisibility(View.GONE);
                creditTitle.setVisibility(View.GONE);
                creditList.setVisibility(View.GONE);
                title.setVisibility(View.GONE);

                for (int i = 0; i < 10; i ++)
                {
                    levels[i].setVisibility(View.GONE);
                    scores[i].setVisibility(View.GONE);
                }

                //Creates the object in charge of positioning the buttons on the screen
                RelativeLayout.LayoutParams params[] = new RelativeLayout.LayoutParams[10];
                for (int i = 0; i < 10; i ++)
                    params[i] = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                int wBase = 164;
                int hBase = 250;

                //Sets up the three rows of buttons in the level select page
                for (int i = 0; i < 3; i ++)
                    params[i].topMargin = (int) (hBase * HEIGHTRATIO + 0.5);
                for (int i = 3; i < 6; i ++)
                    params[i].topMargin = (int) ((hBase+150) * HEIGHTRATIO + 0.5);
                for (int i = 6; i < 9; i ++)
                    params[i].topMargin = (int) ((hBase+300) * HEIGHTRATIO + 0.5);

                for (int i = 0; i < 9; i += 3)
                {
                    params[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    params[i].leftMargin = (int) (wBase * WIDTHRATIO + 0.5);
                    params[i].rightMargin = (int) (wBase * WIDTHRATIO + 0.5);
                }
                for (int i = 1; i < 9; i += 3)
                    params[i].addRule(RelativeLayout.CENTER_HORIZONTAL);
                for (int i = 2; i < 9; i += 3)
                {
                    params[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params[i].rightMargin = (int) (wBase * WIDTHRATIO + 0.5);
                }

                params[9].topMargin = (int) ((hBase+450) * HEIGHTRATIO + 0.5);
                params[9].addRule(RelativeLayout.CENTER_HORIZONTAL);

                //Sets each button for the levels to the position given by the index of the param array
                for (int i = 0; i < 10; i ++)
                    levels[i].setLayoutParams(params[i]);

                //Creates a new relativelayout params for title buttons
                RelativeLayout.LayoutParams params2[] = new RelativeLayout.LayoutParams[4];
                for (int i = 0; i < 4; i ++)
                {
                    params2[i] = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    params2[i].addRule(RelativeLayout.CENTER_HORIZONTAL);
                }

                double hBase2 = 42.610864;
                params2[0].topMargin = (int) ((hBase2 + 200) * HEIGHTRATIO + 0.5);
                params2[1].topMargin = (int) ((hBase2 + 400) * HEIGHTRATIO + 0.5);
                params2[2].topMargin = (int) ((hBase2 + 600) * HEIGHTRATIO + 0.5);
                params2[3].topMargin = (int) ((hBase2 + 800) * HEIGHTRATIO + 0.5);

                //Sets the positioning of the title buttons
                play.setLayoutParams(params2[0]);
                levelSelect.setLayoutParams(params2[1]);
                highScores.setLayoutParams(params2[2]);
                credits.setLayoutParams(params2[3]);

                //Creates a new layoutparams for the score texts
                RelativeLayout.LayoutParams params3[] = new RelativeLayout.LayoutParams[10];
                for (int i = 0; i < 10; i ++)
                {
                    params3[i] = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    params3[i].addRule(RelativeLayout.CENTER_HORIZONTAL);
                }

                int hBase3 = 200;

                for (int i = 0; i < 10; i ++)
                {
                    params3[i].topMargin = (int) ((hBase3 * HEIGHTRATIO + 0.5));
                    hBase3 += 70;
                }

                for (int i = 0; i < 10; i ++)
                    scores[i].setLayoutParams(params3[i]);

                //Sets button visibility based on current status of the game, as well as text colors
                switch (mMode) {
                    case INITIALIZING:
                       play.setVisibility(View.VISIBLE);
                       levelSelect.setVisibility(View.VISIBLE);
                       highScores.setVisibility(View.VISIBLE);
                       credits.setVisibility(View.VISIBLE);
                       title.setVisibility(View.VISIBLE);
                       break;
                    case STARTING:
                        tryAgain.setClickable(true);
                        returnMenu.setClickable(true);
                        nextLevel.setClickable(true);
                        break;
                    case CHOOSING:
                    for (int i = 0; i < 10; i ++)
                        {
                            levels[i].setVisibility(View.VISIBLE);
                            if ((i + cPage + 1) < 10)
                                levels[i].setText(Integer.toString(0)+Integer.toString(i+1));
                            else
                            levels[i].setText(Integer.toString(i + cPage + 1));
                            if (i + cPage < maxLevel)
                            {
                                levels[i].setTextColor(0xFF0000FF);
                                levels[i].setClickable(true);
                            }
                            else
                            {
                                levels[i].setTextColor(Color.GRAY);
                                levels[i].setClickable(false);
                            }
                        }

                        if (cPage == 0)
                        {
                            prevPage.setClickable(false);
                            prevPage.setTextColor(Color.GRAY);
                        }
                        else
                        {
                            prevPage.setClickable(true);
                            prevPage.setTextColor(0xFFFFFFFF);
                        }
                        if (cPage == 40)
                        {
                            nextPage.setClickable(false);
                            nextPage.setTextColor(Color.GRAY);
                        }
                        else
                        {
                            nextPage.setClickable(true);
                            nextPage.setTextColor(0xFFFFFFFF);
                        }
                        back.setVisibility(View.VISIBLE);
                        prevPage.setVisibility(View.VISIBLE);
                        nextPage.setVisibility(View.VISIBLE);
                        chooseLevel.setVisibility(View.VISIBLE);
                        break;
                    case HIGHSCORES:
                        for (int i = 0; i < 10; i ++)
                        {
                            scores[i].setVisibility(View.VISIBLE);
                            scores[i].setText("Level " + Integer.toString(i + sPage + 1) + ": " + Integer.toString(gScore[i+sPage]));
                        }

                        if (sPage == 0)
                        {
                            prevPage.setClickable(false);
                            prevPage.setTextColor(Color.GRAY);
                        }
                        else
                        {
                            prevPage.setClickable(true);
                            prevPage.setTextColor(0xFFFFFFFF);
                        }

                        if (sPage == 40)
                        {
                            nextPage.setClickable(false);
                            nextPage.setTextColor(Color.GRAY);
                        }
                        else
                        {
                            nextPage.setClickable(true);
                            nextPage.setTextColor(0xFFFFFFFF);
                        }

                        back.setVisibility(View.VISIBLE);
                        prevPage.setVisibility(View.VISIBLE);
                        nextPage.setVisibility(View.VISIBLE);
                        highScoreList.setVisibility(View.VISIBLE);
                        break;
                    case CREDITS:
                        creditTitle.setVisibility(View.VISIBLE);
                        creditList.setVisibility(View.VISIBLE);
                        back.setVisibility(View.VISIBLE);
                        break;
                    case BUSY:
                        gameBegins.setTextColor(0xFFFF0077);
                        gameBegins.setText("READY!");
                        gameBegins.setVisibility(View.VISIBLE);
                        vScore.setVisibility(View.VISIBLE);
                        vScore.setText(Integer.toString(score));
                        break;
                    case INTERIM:
                        gameBegins.setTextColor(0xAAFF00C3);
                        gameBegins.setText("GO!");
                        gameBegins.setVisibility(View.VISIBLE);
                        vScore.setVisibility(View.VISIBLE);
                        break;
                    case WAITING:
                        vScore.setVisibility(View.VISIBLE);
                        vScore.setText(Integer.toString(score));
                        break;
                    case FINISHED:
                        if (won)
                        {
                            gameOver.setText("You Won");
                            gameOver.setTextColor(0xFF00FF00);
                            nextLevel.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            gameOver.setText("You Lost");
                            gameOver.setTextColor(0xFFFF0000);
                        }
                        vScore.setText(Integer.toString(score));
                        vScore.setVisibility(View.VISIBLE);
                        gameOver.setVisibility(View.VISIBLE);
                        returnMenu.setVisibility(View.VISIBLE);
                        tryAgain.setVisibility(View.VISIBLE);
                        score = 0;
                        break;
                }

            }


        });
        setFocusable(true);
    }


}





