package com.example.android.circlegame;

public class LevelSelect
{
    private int xValue[] = new int[55];
    private int yValue[] = new int[55];
    private int xGrid[] = new int[100];
    private int yGrid[] = new int[100];

    public LevelSelect (double hRatio, double wRatio)
    {
            for (int i = 0; i < 100; i ++)
            {
                xGrid[i] = (int) (i * 10 * wRatio);
                yGrid[i] = (int) (i * 10 * hRatio);
            }
    }

    public void levelOne()
    {
        xValue[0] = xGrid[30];
        yValue[0] = yGrid[40];

        xValue[1] = xGrid[30];
        yValue[1] = yGrid[60];

        xValue[2] = xGrid[10];
        yValue[2] = yGrid[40];

        xValue[3] = xGrid[50];
        yValue[3] = yGrid[40];

        xValue[4] = xGrid[30];
        yValue[4] = yGrid[20];
    }

    public void levelTwo()
    {
        xValue[0] = xGrid[20];
        yValue[0] = yGrid[30];

        xValue[1] = xGrid[20];
        yValue[1] = yGrid[50];

        xValue[2] = xGrid[40];
        yValue[2] = yGrid[30];

        xValue[3] = xGrid[40];
        yValue[3] = yGrid[50];
    }

    public void levelThree()
    {
        xValue[0] = xGrid[10];
        yValue[0] = yGrid[30];

        xValue[1] = xGrid[10];
        yValue[1] = yGrid[45];

        xValue[2] = xGrid[25];
        yValue[2] = yGrid[30];

        xValue[3] = xGrid[25];
        yValue[3] = yGrid[45];

        xValue[4] = xGrid[40];
        yValue[4] = yGrid[30];

        xValue[5] = xGrid[40];
        yValue[5] = yGrid[45];

        xValue[6] = xGrid[55];
        yValue[6] = yGrid[30];

        xValue[7] = xGrid[55];
        yValue[7] = yGrid[45];
    }

    public void levelFour()
    {
        xValue[0] = xGrid[20];
        yValue[0] = yGrid[20];

        xValue[1] = xGrid[40];
        yValue[1] = yGrid[20];

        xValue[2] = xGrid[20];
        yValue[2] = yGrid[35];

        xValue[3] = xGrid[40];
        yValue[3] = yGrid[35];

        xValue[4] = xGrid[20];
        yValue[4] = yGrid[50];

        xValue[5] = xGrid[40];
        yValue[5] = yGrid[50];
    }

    public void levelFive()
    {
        xValue[0] = xGrid[30];
        yValue[0] = yGrid[35];

        xValue[1] = xGrid[40];
        yValue[1] = yGrid[55];

        xValue[2] = xGrid[20];
        yValue[2] = yGrid[55];
    }

    public void levelSix()
    {
        xValue[0] = xGrid[10];
        yValue[0] = yGrid[30];

        xValue[1] = xGrid[30];
        yValue[1] = yGrid[10];

        xValue[2] = xGrid[50];
        yValue[2] = yGrid[30];

        xValue[3] = xGrid[10];
        yValue[3] = yGrid[50];

        xValue[4] = xGrid[50];
        yValue[4] = yGrid[50];

        xValue[5] = xGrid[30];
        yValue[5] = yGrid[70];
    }

    public void levelSeven()
    {
        xValue[0] = xGrid[30];
        yValue[0] = yGrid[45];

        xValue[1] = xGrid[15];
        yValue[1] = yGrid[60];

        xValue[2] = xGrid[45];
        yValue[2] = yGrid[60];

        xValue[3] = xGrid[45];
        yValue[3] = yGrid[30];

        xValue[4] = xGrid[15];
        yValue[4] = yGrid[30];

        xValue[5] = xGrid[30];
        yValue[5] = yGrid[30];

        xValue[6] = xGrid[30];
        yValue[6] = yGrid[60];
    }

    public void levelEight()
    {
        xValue[0] = xGrid[30];
        yValue[0] = yGrid[30];

        xValue[1] = xGrid[30];
        yValue[1] = yGrid[50];

        xValue[2] = xGrid[40];
        yValue[2] = yGrid[40];

        xValue[3] = xGrid[50];
        yValue[3] = yGrid[50];

        xValue[4] = xGrid[40];
        yValue[4] = yGrid[60];

        xValue[5] = xGrid[30];
        yValue[5] = yGrid[70];

        xValue[6] = xGrid[20];
        yValue[6] = yGrid[60];

        xValue[7] = xGrid[10];
        yValue[7] = yGrid[50];

        xValue[8] = xGrid[20];
        yValue[8] = yGrid[40];
    }

    public void levelNine()
    {
        xValue[0] = xGrid[5];
        yValue[0] = yGrid[25];

        xValue[1] = xGrid[20];
        yValue[1] = yGrid[25];

        xValue[2] = xGrid[5];
        yValue[2] = yGrid[40];

        xValue[3] = xGrid[20];
        yValue[3] = yGrid[40];

        xValue[4] = xGrid[40];
        yValue[4] = yGrid[25];

        xValue[5] = xGrid[55];
        yValue[5] = yGrid[25];

        xValue[6] = xGrid[40];
        yValue[6] = yGrid[40];

        xValue[7] = xGrid[55];
        yValue[7] = yGrid[40];

        xValue[8] = xGrid[20];
        yValue[8] = yGrid[55];

        xValue[9] = xGrid[40];
        yValue[9] = yGrid[55];

        xValue[10] = xGrid[20];
        yValue[10] = yGrid[70];

        xValue[11] = xGrid[40];
        yValue[11] = yGrid[70];


    }

    public void levelTen()
    {
        xValue[0] = xGrid[15];
        yValue[0] = yGrid[10];

        xValue[1] = xGrid[30];
        yValue[1] = yGrid[10];

        xValue[2] = xGrid[45];
        yValue[2] = yGrid[10];

        xValue[3] = xGrid[20];
        yValue[3] = yGrid[25];

        xValue[4] = xGrid[40];
        yValue[4] = yGrid[25];

        xValue[5] = xGrid[30];
        yValue[5] = yGrid[40];

        xValue[6] = xGrid[20];
        yValue[6] = yGrid[55];

        xValue[7] = xGrid[40];
        yValue[7] = yGrid[55];

        xValue[8] = xGrid[15];
        yValue[8] = yGrid[70];

        xValue[9] = xGrid[30];
        yValue[9] = yGrid[70];

        xValue[10] = xGrid[45];
        yValue[10] = yGrid[70];
    }

    public void levelEleven()
    {
        xValue[0] = xGrid[30];
        yValue[0] = yGrid[40];

        xValue[1] = xGrid[30];
        yValue[1] = yGrid[60];

        xValue[2] = xGrid[10];
        yValue[2] = yGrid[40];

        xValue[3] = xGrid[50];
        yValue[3] = yGrid[40];

        xValue[4] = xGrid[30];
        yValue[4] = yGrid[20];

        xValue[5] = xGrid[50];
        yValue[5] = yGrid[20];

        xValue[6] = xGrid[50];
        yValue[6] = yGrid[60];

        xValue[7] = xGrid[10];
        yValue[7] = yGrid[20];

        xValue[8] = xGrid[10];
        yValue[8] = yGrid[60];
    }

    public void levelTwelve()
    {
        xValue[0] = xGrid[30];
        yValue[0] = yGrid[45];

        xValue[1] = xGrid[30];
        yValue[1] = yGrid[15];

        xValue[2] = xGrid[40];
        yValue[2] = yGrid[30];

        xValue[3] = xGrid[50];
        yValue[3] = yGrid[45];

        xValue[4] = xGrid[40];
        yValue[4] = yGrid[60];

        xValue[5] = xGrid[30];
        yValue[5] = yGrid[75];

        xValue[6] = xGrid[20];
        yValue[6] = yGrid[60];

        xValue[7] = xGrid[10];
        yValue[7] = yGrid[45];

        xValue[8] = xGrid[20];
        yValue[8] = yGrid[30];
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
