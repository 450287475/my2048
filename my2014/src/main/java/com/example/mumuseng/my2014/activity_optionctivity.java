package com.example.mumuseng.my2014;

import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;

public class activity_optionctivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_option_line;
    private Button bt_option_targetGoal;
    private Button bt_option_back;
    private Button bt_option_done;
    private String[] items;
    private String[] scores;
    private int line;
    private int score;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optionctivity);
        items = new String[]{"3","4","5","6"};
        scores=new String[]{"16","1024","2048","4096"};
        bt_option_line = (Button) findViewById(R.id.bt_option_line);
        bt_option_targetGoal = (Button) findViewById(R.id.bt_option_targetGoal);
        bt_option_back = (Button) findViewById(R.id.bt_option_back);
        bt_option_done = (Button) findViewById(R.id.bt_option_done);

        application = (MyApplication) getApplication();
        line = application.getGameLines();
        score = application.getTargetScore();
        bt_option_line.setText(line+"");
        bt_option_targetGoal.setText(score+"");

        bt_option_line.setOnClickListener(this);
        bt_option_targetGoal.setOnClickListener(this);
        bt_option_back.setOnClickListener(this);
        bt_option_done.setOnClickListener(this);

        AdView adView = new AdView(this, AdSize.FIT_SCREEN);
        LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
        adLayout.addView(adView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_option_back:
                finish();
                break;
            case R.id.bt_option_done:
                done();
                break;
            case R.id.bt_option_line:
                setLine();
                break;
            case R.id.bt_option_targetGoal:
                setTargetGoal();
                break;
        }
    }

    private void done() {
        application.setTargetScore(score);
        application.setGameLines(line);
        setResult(RESULT_OK);
        finish();
    }

    private void setTargetGoal() {
        new AlertDialog.Builder(this).setTitle("选择目标分数")
                .setItems(scores, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        score = Integer.parseInt( scores[which]);
                        bt_option_targetGoal.setText(score+"");
                    }
                }).show();
    }

    private void setLine() {
        new AlertDialog.Builder(this).setTitle("选择棋盘格数")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        line =Integer.parseInt( items[which]);
                        bt_option_line.setText(line+"");
                    }
                }).show();
    }
}
