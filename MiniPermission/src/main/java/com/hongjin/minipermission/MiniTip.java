package com.hongjin.minipermission;

import android.app.Activity;
import android.widget.Toast;

public class MiniTip implements MiniTipInterface {
    @Override
    public void showTip(Activity activity, String permission) {
//        Toast.makeText(activity,activity.getResources().getString(R.string.mini_tip_please) + permission,Toast.LENGTH_SHORT).show();
    }
}
