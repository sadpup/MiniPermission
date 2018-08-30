# MiniPermission
mini permission 

权限MiniPermission

因觉得目前能接触到的权限处理都不够简单方便，故动手写了一份权限的sdk，希望做到简单方便，高可用，稳定；

一、使用：

1、androidmainfest.xml中对需要的权限进行配置；

<!--⬇️ only for test MiniPermission ⬇️-->

<uses-permission android:name="android.permission.READ_CONTACTS"/>

<uses-permission android:name="android.permission.WRITE_CALENDAR"/>

<uses-permission android:name="android.permission.CAMERA"/>

<!--⬆️ only for test MiniPermission ⬆️-->


2、调用

MiniPermission.getInstance().requestPermission(yourActivity.this,

        new String[]{
                Manifest.permission.CAMERA
        },
        new MiniPcallback() {
        
            @Override
            public void callback(MiniResult miniResult) {
            
                if(miniResult.getDeinepermissions().length>0)
                
                  Toast.makeText(TestMiniPermissionActivity.this,"未全部授权",Toast.LENGTH_SHORT).show();
            }
            
        });

就这么简单，无需初始化，无需activtiy操作，简单的接口回调形式足以。
