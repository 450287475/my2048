package com.example.mumuseng.my2014;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by Mumuseng on 2016/3/22.
 */
public class MyApplication extends Application {
    private int targetScore;
    private int RecordScore;
    private int gameLines;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public int getGameLines() {
        return gameLines;
    }

    public void setGameLines(int gameLines) {
        this.gameLines = gameLines;
        editor.putInt("gameLines",gameLines);
        editor.commit();
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
        editor.putInt("targetScore",targetScore);
        editor.commit();
    }

    public void setRecordScore(int recordScore) {
        if(recordScore>RecordScore){
            this.RecordScore = recordScore;
            editor.putInt("RecordScore",RecordScore);
            editor.commit();
        }
    }

    public int getTargetScore() {

        return targetScore;
    }

    public int getRecordScore() {
        return RecordScore;
    }

    public SharedPreferences getSp() {
        return sp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences("config", MODE_PRIVATE);
        editor = sp.edit();
        targetScore = sp.getInt("targetScore", 16);
        RecordScore = sp.getInt("RecordScore", 0);
        gameLines=sp.getInt("gameLines",3);
        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                ex.printStackTrace();
            }
        });

    }
}
