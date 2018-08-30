package com.hongjin.minipermission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

/**
 * 初衷：基于谷歌官方文档，最佳运行时权限做法，兼容国内手机，简单易用的封装。
 * https://developer.android.com/training/permissions/requesting
 *
 * if you have some question , please @hongjin
 * */
public class MiniPermissionActivity extends Activity{

    private static final int MINI_PERMISSIONS_REQUEST_CODE = 9527;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] permissions = getIntent().getExtras().getStringArray("permissions");
        if(Build.VERSION.SDK_INT < 23){
            permissionGranted(permissions,new String[]{});
        }else {
            //判断那些权限未授予,并对未授予的权限进行运行时请求
            miniCheckAndRequestPermissions(permissions);
        }
    }

    private void miniCheckAndRequestPermissions(String[] permissions) {
        //未授予权限列表
        List<String> unGrantPermissions = new ArrayList<>();
        for(String permission:permissions){
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED) {
                //  是不是要对请求这个权限做一个解释。
                showPermissionTip(permission);

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    //  如果用户操作过不再提示，则进行友好提示。然后弹出一个对话框让用户选择是否要请求权限。
                    showPermissionDialog(permission);
                    return;
                } else {
                    unGrantPermissions.add(permission);
                }
            }
        }
        //判断是否都授予了
        if (unGrantPermissions.isEmpty()) {
            //未授予的权限为空，表示都授予了
            permissionGranted(permissions,new String[]{});
        } else {
            String[] unpermissions = unGrantPermissions.toArray(new String[unGrantPermissions.size()]);//将List转为数组
            //不需要解释的话，我们可以直接请求该权限。
            ActivityCompat.requestPermissions(this, unpermissions, MINI_PERMISSIONS_REQUEST_CODE);
            // MINI_PERMISSIONS_REQUEST_CODE 权限处理操作码
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MINI_PERMISSIONS_REQUEST_CODE: {
                // 如果请求被取消了 ， grantResults 是空的，所以这里可以依据 grantResults 的长度和授予常量位来判断权限是否被授予。
                if (grantResults.length > 0) {
                    List<String> grantedPermissions = new ArrayList<>();
                    List<String> deniedPermissions = new ArrayList<>();
                    for(int i=0;i<grantResults.length;i++){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            grantedPermissions.add(permissions[i]);
                        }else if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                            deniedPermissions.add(permissions[i]);
                        }
                    }
                    permissionGranted(grantedPermissions.toArray(new String[grantedPermissions.size()]),
                            deniedPermissions.toArray(new String[deniedPermissions.size()]));
                }else {
                    //容错处理
                    finish();
                }
                return;
            }
        }
    }

    /**
          * 权限操作结束，处理回调
     * */
    private void permissionGranted(String[] grantedPermissions,String[] deniedPermissions){
        MiniPermission.getInstance().handleCallback(grantedPermissions,deniedPermissions);
        finish();
    }
    /**
     * 曾经拒绝过，进行一个友好提示
     * 用接口对外扩展
     * */
    private void showPermissionDialog(String permission){
        MiniDialogInterface miniDialog = MiniPermission.getInstance().getMiniDialog();
        if(miniDialog == null){
            miniDialog = new MiniDialog();
        }
        miniDialog.showPermissionDialog(this,permission);
    }
    /**
     * 展示一个权限被请求前的 提示说明
     * */
    private void showPermissionTip(String permission){
        MiniTipInterface miniTip = MiniPermission.getInstance().getPreRequestTip();
        if(miniTip == null){
            miniTip = new MiniTip();
        }
        miniTip.showTip(this,permission);
    }
}
