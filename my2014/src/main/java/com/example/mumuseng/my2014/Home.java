package com.example.mumuseng.my2014;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mumuseng.view.MyGridlayout;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_home_content;
    private static Home mHome;
    private TextView tv_home_target;
    private TextView tv_home_score;
    private TextView tv_home_record;
    private Button bt_home_revert;
    private Button bt_home_options;
    private Button bt_home_restart;
    private MyGridlayout myGridlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mHome = this;
        tv_home_target = (TextView) findViewById(R.id.tv_home_target);
        tv_home_score = (TextView) findViewById(R.id.tv_home_score);
        tv_home_record = (TextView) findViewById(R.id.tv_home_record);
        bt_home_revert = (Button) findViewById(R.id.bt_home_revert);
        bt_home_options = (Button) findViewById(R.id.bt_home_options);
        bt_home_restart = (Button) findViewById(R.id.bt_home_restart);
        bt_home_revert.setOnClickListener(this);
        bt_home_options.setOnClickListener(this);
        bt_home_restart.setOnClickListener(this);
        rl_home_content = (RelativeLayout) findViewById(R.id.rl_home_content);
        myGridlayout = new MyGridlayout(this);
        rl_home_content.addView(myGridlayout);

    }

    public static Home getHome(){
        return mHome;
    }
    public void updateTarget(int score){
        tv_home_target.setText(score+"");
    }
    public void updateScore(int score){
        tv_home_score.setText(score + "");
    }
    public void updateRecord(int score){
        tv_home_record.setText(score+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_home_restart:
                myGridlayout.restart();
                break;
            case R.id.bt_home_revert:
                myGridlayout.revert();
                break;
            case R.id.bt_home_options:
                Intent intent = new Intent(this, activity_optionctivity.class);
                startActivityForResult(intent,100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==RESULT_OK){
            myGridlayout.restart();
        }
    }
}
