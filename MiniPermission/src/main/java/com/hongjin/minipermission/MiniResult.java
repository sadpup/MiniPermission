package com.hongjin.minipermission;

/**
 * 结果体封装，待完善。
 * */
public class MiniResult {

    private String[] grantedPermissions ;

    private String[] deniedPermissions ;

    public String[] getGrantedPermissions() {
        return grantedPermissions;
    }

    public void setGrantedPermissions(String[] grantedPermissions) {
        this.grantedPermissions = grantedPermissions;
    }

    public String[] getDeniedPermissions() {
        return deniedPermissions;
    }

    public void setDeniedPermissions(String[] deniedPermissions) {
        this.deniedPermissions = deniedPermissions;
    }
}
