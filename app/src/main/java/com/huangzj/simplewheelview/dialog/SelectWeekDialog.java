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
 * 选择亮屏时间对话框
 */
public class SelectWeekDialog extends BaseDialog {

    private View dialogView;
    private WheelView wheel;

    private int timeType = 0;
    private OnClickListener onClickListener;

    boolean cancelable = true;

    /**
     * 创建一个选择亮屏时间对话框
     *
     * @param mContext
     */
    public SelectWeekDialog(Context mContext, OnClickListener listener) {
        this.context = mContext;
        onClickListener = listener;
        create();
    }

    /**
     * 创建选择亮屏时间对话框
     */
    private void create() {

        if (dialog != null) {
            return;
        }

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        dialogView = layoutInflater.inflate(R.layout.dialog_wheel_select_week, null);
        wheel = (WheelView) dialogView.findViewById(R.id.wheel_week_wheel);

        wheel.setWheelStyle(WheelStyle.STYLE_LIGHT_TIME);

        dialog = new AlertDialog.Builder(context).setView(dialogView).create();
        dialog.setCancelable(cancelable);

        Button cancelBt = (Button) dialogView.findViewById(R.id.wheel_week_cancel_btn);
        cancelBt.setOnClickListener(new View.OnClickListener() {

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
        Button sureBt = (Button) dialogView.findViewById(R.id.week_wheel_sure_btn);
        sureBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int index = wheel.getCurrentItem();

                if (onClickListener != null) {
                    if (onClickListener.onSure(index, timeType)) {
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }
            }
        });
    }

    /**
     * 设置对话框类型
     *
     * @param style WheelAdapter类型
     */
    public void setDialogWheelStyle(int style) {
        wheel.setWheelStyle(style);
    }

    /**
     * 显示亮屏时间对话框
     *
     * @param selectItem 默认选中项
     */
    public void show(int selectItem) {
        if (dialog == null || dialog.isShowing()) {
            return;
        }
        wheel.setCurrentItem(selectItem);
        dialog.show();
    }

    /**
     * 显示选择时间对话框
     *
     * @param selectItme 默认选中项
     * @param timeType   设置的类型标签，用于调用者识别回调
     */
    public void show(int selectItme, int timeType) {
        if (dialog == null || dialog.isShowing()) {
            return;
        }
        this.timeType = timeType;
        wheel.setCurrentItem(selectItme);
        dialog.show();
    }

    /**
     * 选择时间对话框回调
     *
     * @param listener
     */
    public void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
    }

    /**
     * 选择时间对话框回调接口，调用者实现
     *
     * @author huangzj
     */
    public interface OnClickListener {
        boolean onSure(int item, int setTimeType);

        boolean onCancel();
    }
}
