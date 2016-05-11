package cz.koto.misak.dbshowcase.android.mobile.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.PermissionChecker;

import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.entity.AppPermissionEnum;


/**
 * Utility class that wraps access to the runtime permissions API in M and provides basic helper
 * methods.
 */
public abstract class PermissionUtils {

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * method that will return whether the permission is accepted. By default it is true if the user is using a device below
     * version 23
     * @param appPermissionEnumList
     * @return
     */
    public static boolean hasPermission(Context context, List<AppPermissionEnum> appPermissionEnumList) {
        if (isApi23orHigher()) {
            if (appPermissionEnumList==null) return true;
            for(AppPermissionEnum permission:appPermissionEnumList) {
                if (PermissionChecker.checkSelfPermission(context, permission.getPermissionArray()[0]) != PackageManager.PERMISSION_GRANTED){
                    return false;
                };
            }
            return true;
        }
        return true;
    }

    private static boolean isApi23orHigher() {
        return(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1);
    }

}
