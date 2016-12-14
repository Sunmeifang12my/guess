package com.music.gusses.ui;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.music.gusses.data.Const;
import com.music.gusses.model.IWordButtonClickListener;
import com.music.gusses.model.R;
import com.music.gusses.model.Song;
import com.music.gusses.model.WordButton;
import com.music.gusses.myui.MyGridUi;
import com.music.gusses.util.Util;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements IWordButtonClickListener {
    public final static int COUNTS_BUTTON = 24;
    //唱片相关动画
    private Animation mPanAnim;
    private LinearInterpolator mPanLin;

    private Animation mBarInAnim;
    private LinearInterpolator mBarInLin;

    private Animation mBarOutAnim;
    private LinearInterpolator mBarOutLin;
    private Song mCurrentSong;
    //当前关的索引
    private int mCurrentStageIndex = 1;

    //唱片控件
    private ImageView mViewPan;
    //拨杆控件
    private ImageView mViewBar;
    //播放按钮点击
    private ImageButton mBtnPlayStart;
    // 当前动画是否正在运行，增加标志
    private boolean mIsRunning = false;

    //文字框容器
    private ArrayList<WordButton> mAllWords;

    public MyGridUi mMyGridUi;

    private ArrayList<WordButton> mSelectWords;

   //一选择文本框UI容器
    private LinearLayout mViewWordsContainer;
    //override
    public  void  onWordButtonClick(WordButton wordButton){
        Toast.makeText(this,wordButton.mIndex+"",Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewWordsContainer = (LinearLayout)findViewById(R.id.word_select_container);
        mMyGridUi = (MyGridUi)findViewById(R.id.gridview);
        mMyGridUi.OnWordButtonClick(this);
        mViewPan = (ImageView)findViewById(R.id.imageView1);
        mViewBar = (ImageView)findViewById(R.id.imageView2);

        mBtnPlayStart = (ImageButton)findViewById(R.id.btn_play_start);
        mBtnPlayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePlayButton();
            }
        });
        // 初始化动画
        mPanAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        mPanLin = new LinearInterpolator();
        mPanAnim.setInterpolator(mPanLin);
        mPanAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mViewBar.setAnimation(mBarOutAnim);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mBarInAnim  = AnimationUtils.loadAnimation(this,R.anim.rotate_45);
        mBarInLin = new LinearInterpolator();
        mBarInAnim.setInterpolator(mBarInLin);
        mBarInAnim.setFillAfter(true);//动画完后保持状态、位置
        mBarInAnim.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                mViewPan.startAnimation(mPanAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

        mBarOutAnim  = AnimationUtils.loadAnimation(this,R.anim.rotate_d_45);
        mBarOutLin = new LinearInterpolator();
        mBarOutAnim.setInterpolator(mBarOutLin);
       // mBarOutAnim.setFillAfter(true);//动画完后保持状态、位置
        mBarOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIsRunning = false;
                mBtnPlayStart.setVisibility(View.VISIBLE);//播放按钮显示
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        initCurrentStageData();

    }


   private void handlePlayButton(){
       if(mViewBar != null) {
           if (!mIsRunning) {
               mIsRunning = true;
               mViewBar.startAnimation(mBarInAnim);
               mBtnPlayStart.setVisibility(View.INVISIBLE);//播放按钮隐藏
           }
       }
   }

    @Override
    protected void onPause() {
        mViewPan.clearAnimation();//清除动画
        super.onPause();
    }
    private Song loadStageSongInfo(int stageIndex){
        Song song = new Song();
        String [] stage = Const.Song_Info[stageIndex];
        song.setmSongFileName(stage[Const.Index_FILE_NAME]);
        song.setmSongName(stage[Const.Index_SONG_NAME]);
        return  song;
    }
    public void initCurrentStageData(){
        //读取当前关的歌曲数据
        mCurrentSong = loadStageSongInfo(++mCurrentStageIndex);

        //初始化已经选择框
        mSelectWords = initWordSelect();
       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(140,140);
        for(int i=0;i< mSelectWords.size();i++){
            mViewWordsContainer.addView(mSelectWords.get(i).mViewButton,params);
        }
        //获得数据
        mAllWords = initAllWord();
      //  Log.i("d",mAllWords.toString());
        //更新数据
        mMyGridUi.updateData(mAllWords);
    }
    private ArrayList<WordButton> initAllWord(){
        ArrayList<WordButton> data = new ArrayList<WordButton>();
        //获得所有待选文字
        for(int i=0;i< COUNTS_BUTTON;i++){
            WordButton button = new WordButton();
            button.mWordString = "好";
            data.add(button);

        }
        return data;
    }

    /**
     *  初始化已选文本框
     */

    private ArrayList<WordButton> initWordSelect(){
        ArrayList<WordButton> data = new ArrayList<WordButton>();
        for(int i = 0;i < mCurrentSong.getSongNameLength();i++){
            View view = Util.getView(MainActivity.this,R.layout.self_ui_gridview_iten);
            WordButton holder = new WordButton();
            holder.mViewButton = (Button)view.findViewById(R.id.item_btn);
            holder.mViewButton.setTextColor(Color.WHITE);
            holder.mViewButton.setText("");
            holder.mIsVisible = false;
            holder.mViewButton.setBackgroundResource(R.drawable.game_wordblank);
            data.add(holder);
        }
        return data;
    }
}
