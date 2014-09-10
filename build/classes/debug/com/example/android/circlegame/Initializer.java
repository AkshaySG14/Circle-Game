package com.example.android.circlegame;

public class Initializer {
    private int[] xValue;
    private int[] yValue;
    private int levelType;
    private int repeats;
    private int rate;
    private int sLevels;
    private int mScore;
    private int dScore;
    private int circles;
    private int order;
    LevelSelect selector;

    public Initializer(double hRatio, double wRatio)
    {
        selector = new LevelSelect(hRatio, wRatio);
    }

    public void setLevel(int level)
    {
        circles = 0;
        switch (level) {
            case 0:
                level1();
                break;
            case 1:
                level2();
                break;
            case 2:
                level3();
                break;
            case 3:
                level4();
                break;
            case 4:
                level5();
                break;
            case 5:
                level6();
                break;
            case 6:
                level7();
                break;
            case 7:
                level8();
                break;
            case 8:
                level9();
                break;
            case 9:
                level10();
                break;
            case 10:
                level11();
                break;
            case 11:
                level12();
                break;
        }
    }

    private void level1() {
        selector.levelOne();
        xValue = selector.getX();
        yValue = selector.getY();
        levelType = 0;
        repeats = 0;
        sLevels = 0;
        rate = 1;
        mScore = 10;
        dScore = 1;
        circles = 5;
        order = 5;
    }

    private void level2() {
        selector.levelTwo();
        xValue = selector.getX();
        yValue = selector.getY();
        levelType = 1;
        repeats = 4;
        sLevels = 0;
        rate = 2;
        mScore = 10;
        dScore = 1;
        circles = 4;
        order = 4;
    }

    private void level3() {
        selector.levelThree();
        xValue = selector.getX();
        yValue = selector.getY();
        levelType = 1;
        repeats = 2;
        sLevels = 0;
        rate = 1;
        mScore = 10;
        dScore = 1;
        circles = 6;
        order = 8;
    }

    private void level4() {
        selector.levelFour();
        xValue = selector.getX();
        yValue = selector.getY();
        levelType = 1;
        repeats = 3;
        sLevels = 0;
        rate = 2;
        mScore = 20;
        dScore = 2;
        circles = 4;
        order = 6;
    }

    private void level5() {
        selector.levelFive();
        xValue = selector.getX();
        yValue = selector.getY();
        levelType = 2;
        repeats = 4;
        sLevels = 5;
        rate = 2;
        mScore = 20;
        dScore = 1;
        circles = 3;
        order = 3;
    }

    private void level6() {
        selector.levelSix();
        xValue = selector.getX();
        yValue = selector.getY();
        levelType = 1;
        repeats = 4;
        sLevels = 1;
        rate = 2;
        mScore = 8;
        dScore = 2;
        circles = 4;
        order = 6;
    }

    private void level7() {
        selector.levelSeven();
        xValue = selector.getX();
        yValue = selector.getY();
        levelType = 1;
        repeats = 2;
        sLevels = 1;
        rate = 2;
        mScore = 10;
        dScore = 2;
        circles = 6;
        order = 7;
    }

    private void level8() {
        selector.levelEight();
        xValue = selector.getX();
        yValue = selector.getY();
        levelType = 2;
        repeats = 3;
        sLevels = 2;
        rate = 2;
        mScore = 15;
        dScore = 2;
        circles = 6;
        order = 9;
    }

    private void level9() {
        selector.levelNine();
        xValue = selector.getX();
        yValue = selector.getY();
        levelType = 1;
        repeats = 4;
        sLevels = 2;
        rate = 2;
        mScore = 10;
        dScore = 1;
        circles = 4;
        order = 12;
    }

    private void level10() {
        selector.levelTen();
        xValue = selector.getX();
        yValue = selector.getY();
        levelType = 1;
        repeats = 4;
        sLevels = 2;
        rate = 2;
        mScore = 20;
        dScore = 1;
        circles = 4;
        order = 11;
    }

    private void level11() {
        selector.levelEleven();
        xValue = selector.getX();
        yValue = selector.getY();
        levelType = 1;
        repeats = 4;
        sLevels = 1;
        rate = 2;
        mScore = 20;
        dScore = 1;
        circles = 9;
        order = 9;
    }

    private void level12() {
        selector.levelTwelve();
        xValue = selector.getX();
        yValue = selector.getY();
        levelType = 1;
        repeats = 6;
        sLevels = 1;
        rate = 2;
        mScore = 20;
        dScore = 1;
        circles = 5;
        order = 9;
    }

    public int[] getValues()
    {
        int[] values = new int[10];
        values[0] = levelType;
        values[1] = repeats;
        values[2] = sLevels;
        values[3] = rate;
        values[4] = mScore;
        values[5] = dScore;
        values[6] = circles;
        values[7] = order;
        return values;
    }

    public int[] getX()
    {
        return xValue;
    }

    public int[] getY()
    {
        return yValue;
    }
}
