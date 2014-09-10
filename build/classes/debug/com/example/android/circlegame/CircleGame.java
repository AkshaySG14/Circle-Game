/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.circlegame;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.circlegame.CircleGameView.CircleGameThread;

public class CircleGame extends Activity {
    private static final int MENU_EASY = 1;

    private static final int MENU_HARD = 2;

    private static final int MENU_MEDIUM = 3;

    private static final int MENU_PAUSE = 4;

    private static final int MENU_RESUME = 5;

    private static final int MENU_START = 6;

    private static final int MENU_STOP = 7;

    Button nextLevel;
    Button returnMenu;
    Button tryAgain;
    Button play;
    Button levelSelect;
    Button credits;
    Button highScores;
    Button back;
    Button nextPage;
    Button prevPage;
    Button[] levels;


    /**
     * A handle to the thread that's actually running the animation.
     */
    private CircleGameThread mCircleGameThread;

    /**
     * Invoked during init to give the Activity a chance to set up its Menu.
     *
     * @param menu the Menu to which entries may be added
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);


        return true;
    }

    /**
     * Invoked when the user selects an item from the Menu.
     *
     * @param item the Menu entry which was selected
     * @return true if the Menu item was legit (and we consumed it), false
     * otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_START:
                mCircleGameThread.doDraw(null);
                return true;
            case MENU_STOP:
                mCircleGameThread.doDraw(null);
                return true;
            case MENU_PAUSE:
                mCircleGameThread.doDraw(null);
                return true;
            case MENU_RESUME:
                return true;
            case MENU_EASY:
                return true;
            case MENU_MEDIUM:
                return true;
            case MENU_HARD:
                return true;
        }

        return false;
    }

    /**
     * Invoked when the Activity is created.
     *
     * @param savedInstanceState a Bundle containing state saved from a previous
     *                           execution, or null if this is a new execution
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        levels = new Button[10];
        final SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        CircleGameView mCircleGameView;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.circle_game);

        mCircleGameView = (CircleGameView) findViewById(R.id.circle);
        mCircleGameThread = mCircleGameView.getThread();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Typeface sText = Typeface.createFromAsset(this.getAssets(), "fonts/pixel_coleco.otf");
        Typeface lText = Typeface.createFromAsset(this.getAssets(), "fonts/ringmatrix3d.ttf");
        Typeface mText = Typeface.createFromAsset(this.getAssets(), "fonts/ringmatrix.ttf");
        Typeface altText = Typeface.createFromAsset(this.getAssets(), "fonts/lcd_solid.ttf");
        Typeface altText2 = Typeface.createFromAsset(this.getAssets(), "fonts/glasstown_nbp.ttf");
        Typeface altText3 = Typeface.createFromAsset(this.getAssets(), "fonts/cymopxl.ttf");
        Typeface altText4 = Typeface.createFromAsset(this.getAssets(), "fonts/pixel_nes.otf");
        Typeface altText5 = Typeface.createFromAsset(this.getAssets(), "fonts/prstartk.ttf");


        play = (Button) findViewById(R.id.play);
        play.setTypeface(lText);
        levelSelect = (Button) findViewById(R.id.levelSelect);
        levelSelect.setTypeface(lText);
        credits = (Button) findViewById(R.id.credits);
        credits.setTypeface(lText);
        highScores = (Button) findViewById(R.id.highScores);
        highScores.setTypeface(lText);
        TextView gameBegins = (TextView) findViewById(R.id.gameBegins);
        TextView gameOver = (TextView) findViewById(R.id.gameOver);
        gameBegins.setTypeface(altText3);
        nextLevel = (Button) findViewById(R.id.nextLevel);
        nextLevel.setTypeface(altText2);
        returnMenu = (Button) findViewById(R.id.returnMenu);
        returnMenu.setTypeface(altText2);
        tryAgain = (Button) findViewById(R.id.tryAgain);
        tryAgain.setTypeface(altText2);
        back = (Button) findViewById(R.id.back);
        back.setTypeface(altText);
        nextPage = (Button) findViewById(R.id.nextPage);
        nextPage.setTypeface(altText);
        prevPage = (Button) findViewById(R.id.prevPage);
        prevPage.setTypeface(altText);
        gameOver.setTypeface(altText4);

        levels[0] = (Button) findViewById(R.id.level1);
        levels[1] = (Button) findViewById(R.id.level2);
        levels[2] = (Button) findViewById(R.id.level3);
        levels[3] = (Button) findViewById(R.id.level4);
        levels[4] = (Button) findViewById(R.id.level5);
        levels[5] = (Button) findViewById(R.id.level6);
        levels[6] = (Button) findViewById(R.id.level7);
        levels[7] = (Button) findViewById(R.id.level8);
        levels[8] = (Button) findViewById(R.id.level9);
        levels[9] = (Button) findViewById(R.id.level10);


        TextView screen = (TextView) findViewById(R.id.screen);
        TextView score = (TextView) findViewById(R.id.score);
        score.setTypeface(sText);
        TextView chooseLevel = (TextView) findViewById(R.id.chooseLevel);
        chooseLevel.setTypeface(altText5);
        TextView highScoreList = (TextView) findViewById(R.id.highScoreList);
        highScoreList.setTypeface(altText5);
        TextView scores[] = new TextView[10];
        TextView creditTitle = (TextView) findViewById(R.id.creditTitle);
        creditTitle.setTypeface(altText5);
        TextView creditList = (TextView) findViewById(R.id.creditList);
        creditList.setTypeface(mText);
        TextView title = (TextView) findViewById(R.id.memori);
        title.setTypeface(lText);

        scores[0] = (TextView) findViewById(R.id.score1);
        scores[1] = (TextView) findViewById(R.id.score2);
        scores[2] = (TextView) findViewById(R.id.score3);
        scores[3] = (TextView) findViewById(R.id.score4);
        scores[4] = (TextView) findViewById(R.id.score5);
        scores[5] = (TextView) findViewById(R.id.score6);
        scores[6] = (TextView) findViewById(R.id.score7);
        scores[7] = (TextView) findViewById(R.id.score8);
        scores[8] = (TextView) findViewById(R.id.score9);
        scores[9] = (TextView) findViewById(R.id.score10);

        for (int i = 0; i < 10; i ++)
        {
            levels[i].setTypeface(sText);
            scores[i].setTypeface(mText);
        }

        mCircleGameView.setGameOver((TextView) findViewById(R.id.gameOver));
        mCircleGameView.setGameBegins((TextView) findViewById(R.id.gameBegins));
        mCircleGameView.setReturnMenu(returnMenu);
        mCircleGameView.setNextLevel(nextLevel);
        mCircleGameView.setTryAgain(tryAgain);
        mCircleGameView.setPlay(play);
        mCircleGameView.setLevelSelect(levelSelect);
        mCircleGameView.setCredits(credits);
        mCircleGameView.setHighScores(highScores);
        mCircleGameView.setScoreView(score);
        mCircleGameView.setChooseLevel(chooseLevel);
        mCircleGameView.setLevels(levels);
        mCircleGameView.setBack(back);
        mCircleGameView.setNextPage(nextPage);
        mCircleGameView.setPrevPage(prevPage);
        mCircleGameView.setHighScoreList(highScoreList);
        mCircleGameView.setScores(scores);
        mCircleGameView.setCreditTitle(creditTitle);
        mCircleGameView.setCreditList(creditList);
        mCircleGameView.setTitle(title);

        screen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    mCircleGameThread.isInCircle(event);
                return true;
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                titleButtons();
                sp.play(sp.load(getApplicationContext(), R.raw.main_menu_sound, 1), 1, 1, 0, 0, 1);
                mCircleGameThread.play();            }
        });


        levelSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                titleButtons();
                sp.play(sp.load(getApplicationContext(), R.raw.main_menu_sound, 1), 1, 1, 0, 0, 1);
                mCircleGameThread.chooseLevel();
            }
        });

        credits.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                titleButtons();
                sp.play(sp.load(getApplicationContext(), R.raw.main_menu_sound, 1), 1, 1, 0, 0, 1);
                mCircleGameThread.credits();
            }
        });

        highScores.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                titleButtons();
                sp.play(sp.load(getApplicationContext(), R.raw.main_menu_sound, 1), 1, 1, 0, 0, 1);
                mCircleGameThread.highScores();
            }
        });

        returnMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                endButtons();
                sp.play(sp.load(getApplicationContext(), R.raw.leave_game_sounds, 1), 1, 1, 0, 0, 1);
                mCircleGameThread.returnMenu();
            }
        });

        nextLevel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                endButtons();
                sp.play(sp.load(getApplicationContext(), R.raw.leave_game_sounds, 1), 1, 1, 0, 0, 1);
                mCircleGameThread.nextLevel();
            }
        });


        tryAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                endButtons();
                sp.play(sp.load(getApplicationContext(), R.raw.leave_game_sounds, 1), 1, 1, 0, 0, 1);
                mCircleGameThread.tryAgain();
        }
        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insideButtons();
                sp.play(sp.load(getApplicationContext(), R.raw.button_sounds, 1), 1, 1, 0, 0, 1);
                mCircleGameThread.titleScreen();
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sp.load(getApplicationContext(), R.raw.button_sounds, 1), 1, 1, 0, 0, 1);
                if (mCircleGameThread.getMode() == 3)
                mCircleGameThread.changeCPage(true);
                else mCircleGameThread.changeSPage(true);

            }
        });

        prevPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp.play(sp.load(getApplicationContext(), R.raw.button_sounds, 1), 1, 1, 0, 0, 1);
                if (mCircleGameThread.getMode() == 3)
                    mCircleGameThread.changeCPage(false);
                else mCircleGameThread.changeSPage(false);            }
        });



        for (int i = 0; i < 10; i++)
        {
            final int o = i;
            levels[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < 10; i++)
                    {
                        levels[i].setClickable(false);
                    }
                    sp.play(sp.load(getApplicationContext(), R.raw.level_select, 1), 1, 1, 0, 0, 1);
                    mCircleGameThread.levelSelect(o);
                }
            });
        }

        if (savedInstanceState == null) {
            // we were just launched: set up a new game
            mCircleGameThread.titleScreen();
            mCircleGameView.setFresh(true);
            Log.w(this.getClass().getName(), "SIS is null");
        } else {
            // we are being restored: resume a previous game
            Log.w(this.getClass().getName(), "SIS is nonnull");
            mCircleGameView.setFresh(false);
        }

    }

    private void titleButtons()
    {
        play.setClickable(false);
        levelSelect.setClickable(false);
        credits.setClickable(false);
        highScores.setClickable(false);
        back.setClickable(true);
        nextPage.setClickable(true);
        prevPage.setClickable(true);
        back.setClickable(true);
        returnMenu.setClickable(true);
        nextLevel.setClickable(true);
        for (int i = 0; i < 10; i++)
        {
            levels[i].setClickable(true);
        }
    }

    private void insideButtons()
    {
        back.setClickable(false);
        nextPage.setClickable(false);
        prevPage.setClickable(false);
        play.setClickable(true);
        levelSelect.setClickable(true);
        credits.setClickable(true);
        highScores.setClickable(true);
    }

    private void endButtons()
    {
        tryAgain.setClickable(false);
        nextLevel.setClickable(false);
        returnMenu.setClickable(false);
        play.setClickable(true);
        levelSelect.setClickable(true);
        credits.setClickable(true);
        highScores.setClickable(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Notification that something is about to happen, to give the Activity a
     * chance to save state.
     *
     * @param outState a Bundle into which this Activity should save its state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // just have the View's thread save its state into our Bundle
        super.onSaveInstanceState(outState);
        Log.w(this.getClass().getName(), "SIS called");
    }

}
