package com.hongjin.minipermission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 *  MiniPermission
 *  @author hongjin at 20180821
 *
 *  使用步骤：
 *  1，清单文件中注明需要的权限；
 *  2，调用响应方法请求权限；
 * */
public class MiniPermission {

    private static volatile MiniPermission instance = null;

    private MiniPermission(){
    }
    public static MiniPermission getInstance(){
        if(instance == null){
            synchronized (MiniPermission.class){
                if(instance == null){
                    instance = new MiniPermission();
                }
            }
        }
        return instance;
    }

    private String[] basePermissions ;

    public String[] getBasePermissions() {
        return basePermissions;
    }

    public void setBasePermissions(String[] basePermissions) {
        this.basePermissions = basePermissions;
    }

    private MiniDialogInterface miniDialog;

    public MiniDialogInterface getMiniDialog() {
        return miniDialog;
    }

    /**
     * 设置你的 用户拒绝过后的对话框提示
     * */
    public void setMiniDialog(MiniDialogInterface miniDialog) {
        this.miniDialog = miniDialog;
    }

    private MiniTipInterface miniTip;

    public MiniTipInterface getPreRequestTip() {
        return miniTip;
    }

    /**
     * 设置你的 用户拒绝过后的对话框提示
     * */
    public void setPreRequestTip(MiniTipInterface miniTip) {
        this.miniTip = miniTip;
    }

    /**
     * 请求 app 运行所需要的基本权限（出于业务方便调用考虑，加的这个）
     * */
    public void requestBasePermission(Context context, MiniPcallback miniPcallback){
        mMiniPcallback = miniPcallback;
        Intent intent = new Intent(context,MiniPermissionActivity.class);
        intent.putExtra("permissions",basePermissions);
        if(!(context instanceof Activity)){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public boolean hasBasePermission(Context context){
        String[] unGrantPermissions = this.checkPermissions(context,basePermissions);
        return unGrantPermissions.length == 0;
    }

    /**
     * 请求 运行时需要的权限
     * */
    public void requestPermission(Context context,String[] permissions, MiniPcallback miniPcallback){
        mMiniPcallback = miniPcallback;
        Intent intent = new Intent(context,MiniPermissionActivity.class);
        intent.putExtra("permissions",permissions);
        if(!(context instanceof Activity)){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /***
     *  检测单个权限
     * */
    public boolean checkPermission(Context context,String permission ){
        return ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 检查 权限是否 授予的方法
     * @return 未授予列表
     * */
    public String[] checkPermissions(Context context,String[] permissions ){
        List<String> unGrantPermissions = new ArrayList<>();
        if(permissions == null){
            return new String[]{};
        }
        for(String permission:permissions){
            if (ContextCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED) {
                unGrantPermissions.add(permission);
            }
        }
        return unGrantPermissions.toArray(new String[unGrantPermissions.size()]);
    }

    /**
     *  用户是否勾选了不再提示并且拒绝了权限。
     * @return true 拒绝过，需要到设置页面处理
     * */
    public boolean hasAlwaysDeniedPermission(Activity activity, String permission ){
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }



    private MiniPcallback mMiniPcallback;
    public void handleCallback(String[] grantedPermissions,String[] deniedPermissions) {
        if (mMiniPcallback!=null){
            MiniResult result = new MiniResult();
            result.setGrantedPermissions(grantedPermissions);
            result.setDeniedPermissions(deniedPermissions);
            mMiniPcallback.callback(result);
        }
    }
}
