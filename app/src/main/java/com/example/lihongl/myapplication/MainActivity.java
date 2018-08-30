package com.example.lihongl.myapplication;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hongjin.minipermission.MiniPcallback;
import com.hongjin.minipermission.MiniPermission;
import com.hongjin.minipermission.MiniResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MiniPermission.getInstance().setBasePermissions(new String[]{
                Manifest.permission.WRITE_CALENDAR,
                Manifest.permission.READ_CONTACTS
        });
        MiniPermission.getInstance().requestBasePermission(this, new MiniPcallback() {
            @Override
            public void callback(MiniResult miniResult) {
                Toast.makeText(MainActivity.this, "未授权的权限是："+miniResult.getDeniedPermissions(), Toast.LENGTH_SHORT).show();
            }
        });

        TextView premissiontxt = findViewById(R.id.test_minipremission);
        premissiontxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                premissiontxtClick();
            }
        });
    }
    public void premissiontxtClick(){
        MiniPermission.getInstance().requestPermission(MainActivity.this,
                new String[]{
                        Manifest.permission.CAMERA
                },
                new MiniPcallback() {
                    @Override
                    public void callback(MiniResult miniResult) {
                        kk();
                    }
                });
    }

    public void kk(){
        Toast.makeText(MainActivity.this,"callback2",Toast.LENGTH_SHORT).show();
    }
}
