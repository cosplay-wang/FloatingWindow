package com.demo.wang.floatingwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by zhiwei.wang on 2017/6/22.
 */

public class FloatingView extends View {

    private static FloatingView instance;
    public static FloatingView getInstance(Context mContext) {
        if (null == instance) {
            synchronized (FloatingView.class) {
                if (null == instance) {
                    instance = new FloatingView(mContext);
                }
            }
        }
        return instance;
    }

    // 定义浮动窗口布局
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    // 创建浮动窗口设置布局参数的对象
    WindowManager mWindowManager;
    Context context;

    public FloatingView(Context context) {
        super(context);
        this.context = context;
    }

    public void createFloatView() {
        wmParams = new WindowManager.LayoutParams();
        // 获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) context.getSystemService(
                context.WINDOW_SERVICE);
        // 设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        // 设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        // 设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = 900;
        wmParams.y = 0;

        // 设置悬浮窗口长宽数据
        wmParams.width = 200;
        wmParams.height = 100;

        LayoutInflater inflater = LayoutInflater.from(context);
        // 获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.floating, null);
        // 添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);

        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));


        Button float_back = (Button) mFloatLayout.findViewById(R.id.float_back);

        float_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 命令：3 "KEYCODE_HOME" 4 "KEYCODE_BACK" 1"KEYCODE_MENU"
                //execShell("input keyevent 4");
                Toast.makeText(context,"返回按钮",Toast.LENGTH_SHORT).show();
            }
        });

        // 设置监听浮动窗口的触摸移动
        float_back.setOnTouchListener(new View.OnTouchListener() {
            float[] temp = new float[]{0f, 0f};

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        temp[0] = event.getX();
                        temp[1] = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        wmParams.x = (int) (event.getRawX() - temp[0]);
                        wmParams.y = (int) (event.getRawY() - temp[1]);
                        // 刷新
                        mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                        break;
                }
                return false;
            }
        });
    }

}
