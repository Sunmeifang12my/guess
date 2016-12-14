package com.music.gusses.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Administrator on 2016/12/13.
 */
public class Util{
    public static View getView(Context context,int layoutId){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout  = inflater.inflate(layoutId,null);
        return layout;
    }
}
