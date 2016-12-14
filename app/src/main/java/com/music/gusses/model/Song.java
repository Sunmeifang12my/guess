package com.music.gusses.model;

/** 歌曲类
 * 难点：歌曲名称和
 * Created by Administrator on 2016/12/14.
 */
public class Song {
    private String mSongName;
    private String mSongFileName;
    private int mSongNameLength;
    public char[] getNameCharacters(){
        return mSongName.toCharArray();
    }
    public String getmSongName() {
        return mSongName;
    }

    public void setmSongName(String songName) {
        this.mSongName = songName;
        this.mSongNameLength = songName.length();
    }

    public String getmSongFileName() {
        return mSongFileName;
    }

    public void setmSongFileName(String songFileName) {
        this.mSongFileName = songFileName;
    }

    public int getSongNameLength() {
        return this.mSongNameLength;
    }
}
