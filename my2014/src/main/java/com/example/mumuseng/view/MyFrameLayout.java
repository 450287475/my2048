package com.example.mumuseng.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;

/**
 * Created by Mumuseng on 2016/3/21.
 */
public class MyFrameLayout extends FrameLayout {
    int num;
    private MyTextView myTextView;
    private String mBgColor;

    public MyFrameLayout(Context context) {
        super(context);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrameLayout(Context context, int num) {
        super(context);
        this.num = num;
        init(num);
    }

    private void init(int num) {
        setBackgroundColor(0xff898989);
        myTextView = new MyTextView(getContext());
        setNumberAndBackgroundColor(num);
        myTextView.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams
                (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.setMargins(5,5,5,5);
        addView(myTextView,params);
    }

    public int getNum() {
        return num;
    }

    public void setNumberAndBackgroundColor(int num) {
        this.num=num;
        if(num==0){
            myTextView.setText("");
            myTextView.setBackgroundColor(0x00110000);
        }else {
            myTextView.setText(num + "");
            //myTextView.setBackgroundColor(0xff110000*num);
            switch (num){
                case 0:
                    mBgColor = "#CCC0B3";
                    break;
                case 2:
                    mBgColor = "#EEE4DA";
                    break;
                case 4:
                    mBgColor = "#EDE0C8";
                    break;
                case 8:
                    mBgColor = "#F2B179";// #F2B179
                    break;
                case 16:
                    mBgColor = "#F49563";
                    break;
                case 32:
                    mBgColor = "#F5794D";
                    break;
                case 64:
                    mBgColor = "#F55D37";
                    break;
                case 128:
                    mBgColor = "#EEE863";
                    break;
                case 256:
                    mBgColor = "#EDB04D";
                    break;
                case 512:
                    mBgColor = "#ECB04D";
                    break;
                case 1024:
                    mBgColor = "#EB9437";
                    break;
                case 2048:
                    mBgColor = "#EA7821";
                    break;
                default:
                    mBgColor = "#EA7821";
                    break;
            }
            myTextView.setBackgroundColor(Color.parseColor(mBgColor));
        }
    }

}
