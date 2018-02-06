package cn.leo.frame.utils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

/**
 * Created by JarryLeo on 2018/2/6.
 */

public class PermissionUtil {
    private static String tag = "fragmentRequestPermissionCallBack";
    private static final int REQUEST_CODE = 110;
    private FragmentCallback mFragmentCallback;

    public enum 权限 {
        联系人("联系人", Manifest.permission.READ_CONTACTS),
        电话("电话", Manifest.permission.READ_PHONE_STATE),
        日历("日历", Manifest.permission.READ_CALENDAR),
        相机("相机", Manifest.permission.CAMERA),
        传感器("传感器", Manifest.permission.BODY_SENSORS),
        定位("定位", Manifest.permission.ACCESS_FINE_LOCATION),
        存储("存储", Manifest.permission.READ_EXTERNAL_STORAGE),
        录音("录音", Manifest.permission.RECORD_AUDIO),
        短信("短信", Manifest.permission.READ_SMS);
        private String permissionCh;
        private String permission;

        权限(String permissionCh, String permission) {
            this.permissionCh = permissionCh;
            this.permission = permission;
        }

        public String getPermissionCh() {
            return permissionCh;
        }

        public String getPermission() {
            return permission;
        }
    }

    public interface PermissionsResult {
        void onSuccess();

        void onFailed();
    }

    public static class FragmentCallback extends Fragment {
        private PermissionsResult mResult;
        private 权限[] mPermissions;

        public void setResult(PermissionsResult result) {
            mResult = result;
        }

        public void setPermissions(权限[] permissions) {
            mPermissions = permissions;
        }

        @Override
        public void onRequestPermissionsResult(int requestCode,
                                               @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            boolean result = true;
            switch (requestCode) {
                case REQUEST_CODE:
                    for (int i = 0; i < permissions.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            result = false;
                            break;
                        }
                    }
                    break;
            }
            if (mResult != null) {
                if (result) {
                    mResult.onSuccess();
                } else {
                    mResult.onFailed();
                }
            }
            detach();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE) {
                if (mResult != null && mPermissions != null) {
                    boolean result = true;
                    for (int i = 0; i < mPermissions.length; i++) {
                        if (!checkPermission(getActivity(), mPermissions[i])) {
                            result = false;
                            break;
                        }
                    }
                    if (result) {
                        mResult.onSuccess();
                    } else {
                        mResult.onFailed();
                    }
                }
            }
            detach();
        }

        private void detach() {
            FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.detach(this);
            fragmentTransaction.remove(this);
            fragmentTransaction.commit();
        }
    }

    private FragmentActivity mActivity;
    private PermissionsResult mResult;

    private PermissionUtil(FragmentActivity activity, PermissionsResult result) {
        this.mActivity = activity;
        this.mResult = result;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(tag);
        if (fragmentByTag != null) {
            mFragmentCallback = (FragmentCallback) fragmentByTag;
            return;
        }
        mFragmentCallback = new FragmentCallback();
        mFragmentCallback.setResult(result);
        fragmentManager
                .beginTransaction()
                .add(mFragmentCallback, tag)
                .commit();
        fragmentManager.executePendingTransactions();
    }

    public static PermissionUtil getInstance(FragmentActivity activity, PermissionsResult result) {
        return new PermissionUtil(activity, result);
    }

    /**
     * 检查权限
     *
     * @param permission
     * @return
     */
    public boolean checkPermission(权限 permission) {
        //检查权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        int checkSelfPermission =
                ContextCompat
                        .checkSelfPermission(mActivity, permission.getPermission());
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 静态检查权限
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkPermission(Context context, 权限 permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        int checkSelfPermission =
                ContextCompat
                        .checkSelfPermission(context, permission.getPermission());
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 申请权限
     *
     * @param permissions
     */
    public void requestPermission(权限... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (mResult != null) {
                mResult.onSuccess();
            }
            return;
        }
        if (mActivity.getSupportFragmentManager().findFragmentByTag(tag) == null) {
            throw new PermissionRequestException("一个权限申请工具类对象只能申请一次权限");
        }
        if (mFragmentCallback != null && permissions != null) {
            mFragmentCallback.setPermissions(permissions);

            String[] per = new String[permissions.length];
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < permissions.length; i++) {
                per[i] = permissions[i].getPermission();
                if (!checkPermission(permissions[i])) {
                    sb.append(" [")
                            .append(permissions[i].getPermissionCh())
                            .append("] ");
                }
            }
            //如果用户点了不提示，我们主动提示用户
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[0].getPermission())) {
                openSettingActivity("需要" + sb.toString() + "权限,前往开启?");
            } else {
                //申请单个权限
                try {
                    mFragmentCallback.requestPermissions(per, REQUEST_CODE);
                } catch (Exception e) {
                    openSettingActivity("需要" + sb.toString() + "权限,前往开启?");
                }
            }
        }
    }

    /**
     * 打开应用权限设置界面
     */
    private void openSettingActivity(String message) {
        showMessageOKCancel(message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
                intent.setData(uri);
                if (mFragmentCallback != null) {
                    mFragmentCallback.startActivityForResult(intent, REQUEST_CODE);
                }
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mResult.onFailed();
            }
        });
    }

    /**
     * 弹出对话框
     *
     * @param message
     * @param okListener
     */
    private void showMessageOKCancel(String message,
                                     DialogInterface.OnClickListener okListener,
                                     DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(mActivity)
                .setMessage(message)
                .setPositiveButton("确定", okListener)
                .setNegativeButton("取消", cancelListener)
                .create()
                .show();
    }

    static class PermissionRequestException extends RuntimeException {
        public PermissionRequestException(String message) {
            super(message);
        }
    }
}

/*•	Normal Permissions如下 （不需要动态申请，只需要在清单文件注册即可）

ACCESS_LOCATION_EXTRA_COMMANDS
ACCESS_NETWORK_STATE
ACCESS_NOTIFICATION_POLICY
ACCESS_WIFI_STATE
BLUETOOTH
BLUETOOTH_ADMIN
BROADCAST_STICKY
CHANGE_NETWORK_STATE
CHANGE_WIFI_MULTICAST_STATE
CHANGE_WIFI_STATE
DISABLE_KEYGUARD
EXPAND_STATUS_BAR
GET_PACKAGE_SIZE
INSTALL_SHORTCUT
INTERNET
KILL_BACKGROUND_PROCESSES
MODIFY_AUDIO_SETTINGS
NFC
READ_SYNC_SETTINGS
READ_SYNC_STATS
RECEIVE_BOOT_COMPLETED
REORDER_TASKS
REQUEST_INSTALL_PACKAGES
SET_ALARM
SET_TIME_ZONE
SET_WALLPAPER
SET_WALLPAPER_HINTS
TRANSMIT_IR
UNINSTALL_SHORTCUT
USE_FINGERPRINT
VIBRATE
WAKE_LOCK
WRITE_SYNC_SETTINGS


•Dangerous Permissions: (需要动态申请，当然也要在清单文件声明)

group:android.permission-group.CONTACTS
  permission:android.permission.WRITE_CONTACTS
  permission:android.permission.GET_ACCOUNTS
  permission:android.permission.READ_CONTACTS

group:android.permission-group.PHONE
  permission:android.permission.READ_CALL_LOG
  permission:android.permission.READ_PHONE_STATE
  permission:android.permission.CALL_PHONE
  permission:android.permission.WRITE_CALL_LOG
  permission:android.permission.USE_SIP
  permission:android.permission.PROCESS_OUTGOING_CALLS
  permission:com.android.voicemail.permission.ADD_VOICEMAIL

group:android.permission-group.CALENDAR
  permission:android.permission.READ_CALENDAR
  permission:android.permission.WRITE_CALENDAR

group:android.permission-group.CAMERA
  permission:android.permission.CAMERA

group:android.permission-group.SENSORS
  permission:android.permission.BODY_SENSORS

group:android.permission-group.LOCATION
  permission:android.permission.ACCESS_FINE_LOCATION
  permission:android.permission.ACCESS_COARSE_LOCATION

group:android.permission-group.STORAGE
  permission:android.permission.READ_EXTERNAL_STORAGE
  permission:android.permission.WRITE_EXTERNAL_STORAGE

group:android.permission-group.MICROPHONE
  permission:android.permission.RECORD_AUDIO

group:android.permission-group.SMS
  permission:android.permission.READ_SMS
  permission:android.permission.RECEIVE_WAP_PUSH
  permission:android.permission.RECEIVE_MMS
  permission:android.permission.RECEIVE_SMS
  permission:android.permission.SEND_SMS
  permission:android.permission.READ_CELL_BROADCASTS

*/
