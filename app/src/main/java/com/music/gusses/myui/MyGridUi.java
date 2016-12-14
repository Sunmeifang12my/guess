package com.music.gusses.myui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.music.gusses.model.IWordButtonClickListener;
import com.music.gusses.model.WordButton;
import com.music.gusses.ui.MainActivity;
import com.music.gusses.util.Util;
import com.music.gusses.model.R;

import java.util.ArrayList;

/**
 * 定义自己的gridView
 * Created by Administrator on 2016/12/13.
 */
public class MyGridUi extends GridView {
    private ArrayList<WordButton> mArray = new ArrayList<WordButton>();
    private MyGridUiAdatar myAdatar;
    private IWordButtonClickListener mWordButtonListener;
    private Context mContext;
    //定义一个动画
    private Animation scaleAnimation;

    public MyGridUi(Context context, AttributeSet attrs){
        super(context,attrs);
        myAdatar = new MyGridUiAdatar();
        mContext = context;
        this.setAdapter(myAdatar);
    }
    public void updateData(ArrayList<WordButton> list){
         mArray = list;
        setAdapter(myAdatar);

    }
    /**
     * 注册接听接口
     * @param listener
     */
       // 观察者模式的主题
    public void OnWordButtonClick(IWordButtonClickListener listener){
        mWordButtonListener =   listener;
    }
    //内部类，。 用适配器实现文字田村
    class MyGridUiAdatar extends BaseAdapter{
        @Override
        public int getCount(){
            return mArray.size();
        }
        @Override
        public View getView(int pos,View v,ViewGroup p){
           final WordButton holder;
            if(v == null){
               v = Util.getView(mContext,R.layout.self_ui_gridview_iten);
                //加载动画
               scaleAnimation  =  AnimationUtils.loadAnimation(mContext,R.anim.scale);
               scaleAnimation.setStartOffset(pos*100);//延迟动画播放
               holder  = mArray.get(pos);
                holder.mIndex = pos;
                holder.mViewButton = (Button)v.findViewById(R.id.item_btn);
                holder.mViewButton.setOnClickListener(new OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          mWordButtonListener.onWordButtonClick(holder);
                      }
                 }

                );
                v.setTag(holder);
            }else{
                holder =(WordButton) v.getTag();
            }
            holder.mViewButton.setText(holder.mWordString);
            //播放动画
            v.startAnimation(scaleAnimation);
            return v;
        }


        @Override
        public long getItemId(int pos){
            return pos;
        }
        public Object getItem(int pos){
            return mArray.get(pos);
        }
    }
}

