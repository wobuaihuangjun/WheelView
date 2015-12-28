package com.huangzj.simplewheelview.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.huangzj.simplewheelview.R;
import com.huangzj.simplewheelview.view.WheelStyle;
import com.huangzj.simplewheelview.view.WheelView;


/**
 * Created by huangzj on 2015/10/25.
 * <p/>
 * 时间选择对话框
 */
public class SelectTimeDialog extends BaseDialog {

    private View dialogView;
    private WheelView leftWheel;
    private WheelView rightWheel;

    private int timeType = 0;
    private OnClickListener onClickListener;

    boolean cancelable = true;

    /**
     * 创建一个时间选择对话框
     *
     * @param mContext
     */
    public SelectTimeDialog(Context mContext, OnClickListener listener) {
        this.context = mContext;
        onClickListener = listener;
        create();
    }

    /**
     * 创建选择时间对话框
     */
    private void create() {

        if (dialog != null) {
            return;
        }

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        dialogView = layoutInflater.inflate(R.layout.dialog_wheel_select_time, null);
        leftWheel = (WheelView) dialogView.findViewById(R.id.select_time_wheel_left);
        rightWheel = (WheelView) dialogView.findViewById(R.id.select_time_wheel_right);
        leftWheel.setWheelStyle(WheelStyle.STYLE_HOUR);
        rightWheel.setWheelStyle(WheelStyle.STYLE_MINUTE);

        dialog = new AlertDialog.Builder(context).setView(dialogView).create();

        dialog.setCancelable(cancelable);

        Button cancelBtn = (Button) dialogView.findViewById(R.id.select_time_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    if (!onClickListener.onCancel()) {
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }
            }
        });
        Button sureBtn = (Button) dialogView.findViewById(R.id.select_time_sure_btn);
        sureBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    if (!onClickListener.onSure(leftWheel.getCurrentItem(),
                            rightWheel.getCurrentItem(), timeType)) {
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }

            }
        });

    }

    /**
     * 显示选择时间对话框
     *
     * @param mHour    默认显示的小时
     * @param mMinute  默认小时的分钟
     * @param timeType 设置的时间标签，用于调用者识别回调
     */
    public void show(int mHour, int mMinute, int timeType) {
        if (dialog == null || dialog.isShowing()) {
            return;
        }
        this.timeType = timeType;
        leftWheel.setCurrentItem(mHour);
        rightWheel.setCurrentItem(mMinute);
        dialog.show();
    }

    /**
     * 显示选择时间对话框
     *
     * @param mHour   默认显示的小时
     * @param mMinute 默认小时的分钟
     */
    public void show(int mHour, int mMinute) {
        if (dialog == null || dialog.isShowing()) {
            return;
        }
        leftWheel.setCurrentItem(mHour);
        rightWheel.setCurrentItem(mMinute);
        dialog.show();
    }

    /**
     * 选择时间对话框回调
     *
     * @param listener
     */
    public void setOnUpdateTimeListener(OnClickListener listener) {
        onClickListener = listener;
    }

    /**
     * 选择时间对话框回调接口，调用者实现
     *
     * @author huangzj
     */
    public interface OnClickListener {
        boolean onSure(int hour, int minute, int setTimeType);

        boolean onCancel();
    }
}
