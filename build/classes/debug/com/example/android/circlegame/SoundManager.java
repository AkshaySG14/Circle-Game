package com.example.android.circlegame;

import android.graphics.drawable.BitmapDrawable;

public class SoundManager {

public int findSound(BitmapDrawable circle)
{
   if (circle.getBitmap().sameAs(BitmapManager.red))
       return R.raw.ai_sound1;
    if (circle.getBitmap().sameAs(BitmapManager.blue))
        return R.raw.ai_sound2;
    if (circle.getBitmap().sameAs(BitmapManager.black))
        return R.raw.ai_sound3;
    if (circle.getBitmap().sameAs(BitmapManager.darkgreen))
        return R.raw.ai_sound4;
    if (circle.getBitmap().sameAs(BitmapManager.gray))
        return R.raw.ai_sound5;
    if (circle.getBitmap().sameAs(BitmapManager.lavender))
        return R.raw.ai_sound6;
    if (circle.getBitmap().sameAs(BitmapManager.magenta))
        return R.raw.ai_sound7;
    if (circle.getBitmap().sameAs(BitmapManager.maroon))
        return R.raw.ai_sound8;
    if (circle.getBitmap().sameAs(BitmapManager.midnight))
        return R.raw.ai_sound9;
    if (circle.getBitmap().sameAs(BitmapManager.orange))
        return R.raw.ai_sound10;
    if (circle.getBitmap().sameAs(BitmapManager.plum))
        return R.raw.ai_sound11;
    if (circle.getBitmap().sameAs(BitmapManager.purple))
        return R.raw.ai_sound12;
    if (circle.getBitmap().sameAs(BitmapManager.teal))
        return R.raw.ai_sound13;
    if (circle.getBitmap().sameAs(BitmapManager.turquoise))
        return R.raw.ai_sound14;
    if (circle.getBitmap().sameAs(BitmapManager.yellow))
        return R.raw.ai_sound15;
    if (circle.getBitmap().sameAs(BitmapManager.white))
        return R.raw.ai_sound16;
    if (circle.getBitmap().sameAs(BitmapManager.seafoam))
        return R.raw.ai_sound17;
    return R.raw.ai_sound18;
}


}


