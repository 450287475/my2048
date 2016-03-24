package com.example.mumuseng.my2014;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

public class AdvertisementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);

        AdManager.getInstance(this).init("c6234a9dd7c7d2a5", "9df6dd272c88ec13", true);
        SpotManager.getInstance(this).loadSpotAds();
        SpotManager.getInstance(this).setSpotOrientation(SpotManager.ORIENTATION_PORTRAIT);
        SpotManager.getInstance(this).setAnimationType(SpotManager.ANIM_SIMPLE);


    }
    public void shouAdv(View v){
        //SpotManager.getInstance(this ).showSpotAds(this);
        SpotManager.getInstance(this).showSpotAds(this, new SpotDialogListener() {
            @Override
            public void onShowSuccess() {
                System.out.println("展示成功");
            }

            @Override
            public void onShowFailed() {
                System.out.println("展示失败");

            }

            @Override
            public void onSpotClosed() {
                System.out.println("插屏被关闭");

            }

            @Override
            public void onSpotClick(boolean b) {
                System.out.println("插屏被点击");

            }
        });
    }
    public void go(View v){
        Intent intent = new Intent(AdvertisementActivity.this, Home.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        // 如果有需要，可以点击后退关闭插播广告。
        if (!SpotManager.getInstance(this).disMiss()) {
            // 弹出退出窗口，可以使用自定义退屏弹出和回退动画,参照demo,若不使用动画，传入-1
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        SpotManager.getInstance(this).onDestroy();
        super.onDestroy();
    }
}
