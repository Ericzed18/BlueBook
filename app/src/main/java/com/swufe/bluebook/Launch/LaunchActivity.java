package com.swufe.bluebook.Launch;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.swufe.bluebook.Backstage.User;
import com.swufe.bluebook.CityChooseSlide.AddressActivity;
import com.swufe.bluebook.LoginOrRegister.LoginActivity;
import com.swufe.bluebook.MainActivity;
import com.swufe.bluebook.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class LaunchActivity extends Activity implements OnRequestPermissionsResultCallback{

    private static String TAG = "LaunchActivity";
    Handler handler;
    public final static int REQUEST_READ_PHONE_STATE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);//设置开机图

        Bmob.initialize(this, "9b90c2d2f64466ed4e8c1bef6c11099a");

        User bmobUser = BmobUser.getCurrentUser(User.class);
        handler=new Handler();
        if(bmobUser != null){
            // 允许用户使用

            if(BmobUser.getObjectByKey("city")==null){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LaunchActivity.this, AddressActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },5000);

            }else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },5000);
            }


        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            },5000);

        }

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            //TODO
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                }
                break;

            default:
                break;
        }
    }

}

