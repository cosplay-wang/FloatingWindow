package com.demo.wang.floatingwindow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int ALERT_WINDOW_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sdk23Permission();
    }

    /**
     * @description 安卓6.0下权限处理
     * @author ldm
     * @time 2017/3/20 15:00
     */
    public void sdk23Permission() {
        if (Build.VERSION.SDK_INT > 22) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(MainActivity.this, "当前无权限使用悬浮窗，请授权！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ALERT_WINDOW_PERMISSION_CODE);
            }
        } else {
            startService(new Intent(MainActivity.this, FloatingService.getInstance().getClass()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT > 22) {
            if (requestCode == ALERT_WINDOW_PERMISSION_CODE && Settings.canDrawOverlays(this)) {
                startService(new Intent(MainActivity.this, FloatingService.getInstance().getClass()));
            } else {
                Toast.makeText(MainActivity.this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(MainActivity.this, FloatingService.getInstance().getClass()));
    }
}
