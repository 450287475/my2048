package com.example.mumuseng.view;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.mumuseng.my2014.Home;
import com.example.mumuseng.my2014.MyApplication;

import java.util.ArrayList;

/**
 * Created by Mumuseng on 2016/3/21.
 */
public class MyGridlayout extends GridLayout{
    int mRowNumber;
    int mColumnNumber;
    private ArrayList<Point> points;
    private MyFrameLayout[][] myFrameLayouts;
    double startX;
    double startY;
    double stopX;
    double stopY;
    private ArrayList<Integer> tempList;
    private int temp;
    private Home home;
    private int mScore;
    private int targetScore;
    private int[][] history;
    MyApplication application;

    public MyGridlayout(Context context) {
        super(context);
        home = Home.getHome();
        init();
    }

    public MyGridlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        home = Home.getHome();
        init();
    }

   /* public MyGridlayout(Context context, int mRowNumber, int mColumnNumber) {
        super(context);
        this.mRowNumber = mRowNumber;
        this.mColumnNumber = mColumnNumber;
        home = Home.getHome();
        init();
    }*/

    private void init() {
        //初始化分数栏
        mScore=0;
        application = (MyApplication) home.getApplication();
        int gameLines = application.getGameLines();
        mRowNumber=gameLines;
        mColumnNumber=gameLines;
        targetScore = application.getTargetScore();
        home.updateRecord(application.getRecordScore());
        home.updateTarget(targetScore);
        home.updateScore(mScore);
        System.out.println("==========init");

        //初始化返回一步的棋盘
        history = new int[mRowNumber][mColumnNumber];
        // 取得Gridlayout的长和宽
        WindowManager windowManager = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int widthPixels = metrics.widthPixels;

        points = new ArrayList<>();
        myFrameLayouts = new MyFrameLayout[mRowNumber][mColumnNumber];

        setRowCount(mRowNumber);
        setColumnCount(mColumnNumber);

        for(int i=0;i<mRowNumber;i++){
            for(int j=0;j<mColumnNumber;j++){
                MyFrameLayout myFrameLayout = new MyFrameLayout(getContext(), 0);
                addView(myFrameLayout,widthPixels/mColumnNumber,widthPixels/mRowNumber);
                myFrameLayouts[i][j]=myFrameLayout;
                Point point = new Point();
                point.x=i;
                point.y=j;
                points.add(point);
            }
        }
        addRandomNumber();
        addRandomNumber();
        addRandomNumber();
        addRandomNumber();
        savehistory();

    }

    private void addRandomNumber() {
        updatePointsList();
        if(points.size()==0){
            System.out.println(points.size());
            return;
        }
        System.out.println(points.size());
        int v = (int) Math.floor(Math.random() * points.size());
        Point point = points.get(v);
        MyFrameLayout myFrameLayout = myFrameLayouts[point.x][point.y];
        myFrameLayout.setNumberAndBackgroundColor(2);
    }

    private void updatePointsList() {
        points.clear();
        for(int i=0;i<mRowNumber;i++){
            for(int j=0;j<mColumnNumber;j++){
                MyFrameLayout myFrameLayout = myFrameLayouts[i][j];
                int num = myFrameLayout.getNum();
                if(num==0)
                {
                    points.add(new Point(i,j));
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                savehistory();
                startX=event.getX();
                startY=event.getY();
                break;
            case MotionEvent.ACTION_MOVE :
                break;
            case MotionEvent.ACTION_UP :
                stopX=event.getX();
                stopY=event.getY();
                if(Math.abs(stopX-startX)>10||Math.abs(stopY-startY)>10){
                    judgeDerection(startX, startY, stopX, stopY);
                    addRandomNumber();
                    home.updateScore(mScore);
                    //0代表通关,1代表失败,2代表继续玩
                    handleResult(isOver());
                }
                break;
        }


        return true;
    }

    private void handleResult(int over) {
        switch (over){
            case 0:
                new AlertDialog.Builder(getContext())
                        .setTitle("闯关成功!")
                        .setMessage("你已经完成了游戏!")
                        .setPositiveButton("挑战更难!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    application.setTargetScore(targetScore*2);
                                    restart();
                            }
                        }).setNegativeButton("重新开始!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restart();
                    }
                }).show();
                break;
            case 1:
                new AlertDialog.Builder(getContext()).setTitle("闯关失败!")
                        .setMessage("你已经死了!咋这菜!")
                        .setPositiveButton("重新开始!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                restart();
                            }
                        }).setNegativeButton("退出游戏!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        application.setRecordScore(mScore);
                        home.finish();
                    }
                }).show();
                break;
            case 2:
                break;
        }
    }

    public void restart() {
        application.setRecordScore(mScore);
        removeAllViews();
        init();
    }

    public void revert(){
        for(int i=0;i<mRowNumber;i++){
            for(int j=0;j<mColumnNumber;j++){
                myFrameLayouts[i][j].setNumberAndBackgroundColor(history[i][j]);
            }
        }
    }

    private void savehistory() {
        for(int i=0;i<mRowNumber;i++){
            for(int j=0;j<mColumnNumber;j++){
                history[i][j]=myFrameLayouts[i][j].getNum();
            }
        }
    }

    private int isOver() {
        boolean flag=false;

        int tempLeft=-1;
        int tempUp=-1;
        for(int i=0;i<mRowNumber;i++){
            for(int j=0;j<mColumnNumber;j++){
                if(i==0){
                    tempUp=-1;
                }else {
                    tempUp= myFrameLayouts[i-1][j].getNum();
                }
                if(j==0){
                    tempLeft=-1;
                }else {
                    tempLeft=myFrameLayouts[i][j-1].getNum();
                }
                int num = myFrameLayouts[i][j].getNum();
                if(num>=targetScore){
                    //闯关成功
                    return 0;
                }
                //必须所有的格子都不能等于0
                    if(num==tempUp||num==tempLeft){
                        //格子没有满,继续
                        flag=true;
                    }
            }
        }
        if(flag){
            return 2;
        }
        //闯关失败
        if(points.size()==0){
            return 1;
        }
        return 2;
    }


    private void judgeDerection(double startX, double startY, double stopX, double stopY) {
        boolean flag = Math.abs(stopX - startX) > Math.abs(stopY - startY) ? true : false;
        if(flag){
            if(stopX>startX){
                slipRight();
            }else {
                slipLeft();

            }
        }else {
            if(stopY>startY){
                slipDown();

            }else {

                slipUp();

            }
        }
    }

    private void slipUp() {
        for(int j=0;j<mRowNumber;j++){
            temp = 0;
            tempList = new ArrayList<>();
            for (int i=0;i<mColumnNumber;i++){
                MyFrameLayout myFrameLayout = myFrameLayouts[i][j];
                if(myFrameLayout.getNum()!=0){
                    if(temp ==myFrameLayout.getNum()){
                        tempList.add(temp*2);
                        mScore+=temp;
                        temp=0;
                    }else{
                        if(temp!=0){
                            tempList.add(temp);
                        }
                        temp=myFrameLayout.getNum();
                    }
                    myFrameLayout.setNumberAndBackgroundColor(0);
                }
            }
            if(temp!=0){
                tempList.add(temp);
            }
            for(int i=0;i<tempList.size();i++){
                myFrameLayouts[i][j].setNumberAndBackgroundColor(tempList.get(i));
            }
        }
        System.out.println("up");
    }

    private void slipDown() {
        for(int j=0;j<mRowNumber;j++){
            temp = 0;
            tempList = new ArrayList<>();
            for (int i=mColumnNumber-1;i>=0;i--){
                MyFrameLayout myFrameLayout = myFrameLayouts[i][j];
                if(myFrameLayout.getNum()!=0){
                    if(temp ==myFrameLayout.getNum()){
                        tempList.add(temp*2);
                        mScore+=temp;
                        temp=0;
                    }else{
                        if(temp!=0){
                            tempList.add(temp);
                        }
                        temp=myFrameLayout.getNum();
                    }
                    myFrameLayout.setNumberAndBackgroundColor(0);
                }
            }
            if(temp!=0){
                tempList.add(temp);
            }
            for(int i=0;i<tempList.size();i++){
                myFrameLayouts[mRowNumber-1-i][j].setNumberAndBackgroundColor(tempList.get(i));
            }
        }
        System.out.println("down");
    }

    private void slipRight() {
        for(int i=0;i<mRowNumber;i++){
            temp = 0;
            tempList = new ArrayList<>();
            for (int j=mColumnNumber-1;j>=0;j--){
                MyFrameLayout myFrameLayout = myFrameLayouts[i][j];
                if(myFrameLayout.getNum()!=0){
                    if(temp ==myFrameLayout.getNum()){
                        tempList.add(temp*2);
                        mScore+=temp;
                        temp=0;
                    }else{
                        if(temp!=0){
                            tempList.add(temp);
                        }
                        temp=myFrameLayout.getNum();
                    }
                    myFrameLayout.setNumberAndBackgroundColor(0);
                }
            }
            if(temp!=0){
                tempList.add(temp);
            }
            for(int j=0;j<tempList.size();j++){
                myFrameLayouts[i][mColumnNumber-1-j].setNumberAndBackgroundColor(tempList.get(j));
            }
        }
        System.out.println("right");
    }

    private void slipLeft() {
        for(int i=0;i<mRowNumber;i++){
            temp = 0;
            tempList = new ArrayList<>();
            for (int j=0;j<mColumnNumber;j++){
                MyFrameLayout myFrameLayout = myFrameLayouts[i][j];
                if(myFrameLayout.getNum()!=0){
                    if(temp ==myFrameLayout.getNum()){
                        tempList.add(temp*2);
                        mScore+=temp;
                        temp=0;
                    }else{
                        if(temp!=0){
                            tempList.add(temp);
                        }
                        temp=myFrameLayout.getNum();
                    }
                    myFrameLayout.setNumberAndBackgroundColor(0);
                }
            }
            if(temp!=0){
                tempList.add(temp);
            }
            for(int j=0;j<tempList.size();j++){
                myFrameLayouts[i][j].setNumberAndBackgroundColor(tempList.get(j));
            }
        }

        System.out.println("left");
    }


}
