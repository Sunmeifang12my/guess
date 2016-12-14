package com.music.gusses.model;

import android.widget.Button;

/**
 * Created by Administrator on 2016/12/13.
 */
public class WordButton {
    public int mIndex;
    public boolean mIsVisible;
    public String mWordString;
    public Button mViewButton;

    public WordButton(){
       mIsVisible = true;
        mWordString = "";
    }
}
