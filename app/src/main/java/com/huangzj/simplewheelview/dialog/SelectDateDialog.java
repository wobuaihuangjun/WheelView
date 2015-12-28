package com.huangzj.simplewheelview.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


import com.huangzj.simplewheelview.R;
import com.huangzj.simplewheelview.view.WheelStyle;
import com.huangzj.simplewheelview.view.WheelView;

import java.util.Calendar;

/**
 * 日期选择对话框
 *
 * Created by huangzj on 2015/10/25.
 */
public class SelectDateDialog extends BaseDialog {

    private View dialogView;
    private WheelView yearWheel;
    private WheelView monthWheel;
    private WheelView dayWheel;

    int selectYear;
    int selectMonth;

    private OnClickListener updateDateListener;

    boolean cyclicable = true;

    /**
     * 创建一个日期选择对话框
     *
     * @param mContext
     */
    public SelectDateDialog(Context mContext) {
        this.context = mContext;
        create();
    }

    /**
     * 创建一个日期选择对话框
     *
     * @param mContext
     */
    public SelectDateDialog(Context mContext, OnClickListener listener) {
        this.context = mContext;
        updateDateListener = listener;
        create();
    }

    /**
     * 创建选择日期对话框
     */
    private void create() {

        if (dialog != null) {
            return;
        }

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        dialogView = layoutInflater.inflate(R.layout.dialog_wheel_select_date, null);
        yearWheel = (WheelView) dialogView.findViewById(R.id.select_date_wheel_year_wheel);
        monthWheel = (WheelView) dialogView.findViewById(R.id.select_date_month_wheel);
        dayWheel = (WheelView) dialogView.findViewById(R.id.select_date_day_wheel);

        yearWheel.setWheelStyle(WheelStyle.STYLE_YEAR);
        yearWheel.setOnSelectListener(new WheelView.onSelectListener() {
            @Override
            public void onSelect(int index, String text) {
                selectYear = index + 1980;
                dayWheel.setWheelItemList(WheelStyle.createDayString(selectYear, selectMonth));
            }
        });

        monthWheel.setWheelStyle(WheelStyle.STYLE_MONTH);
        monthWheel.setOnSelectListener(new WheelView.onSelectListener() {
            @Override
            public void onSelect(int index, String text) {
                selectMonth = index + 1;
                dayWheel.setWheelItemList(WheelStyle.createDayString(selectYear, selectMonth));
            }
        });

        Button cancelBt = (Button) dialogView.findViewById(R.id.select_date_cancel);
        cancelBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (updateDateListener != null) {
                    updateDateListener.onCancel();
                }
            }
        });
        Button sureBt = (Button) dialogView.findViewById(R.id.select_date_sure);
        sureBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                int year = yearWheel.getCurrentItem() + 1980;
                int month = monthWheel.getCurrentItem();
                int day = dayWheel.getCurrentItem() + 1;

                int daySize = dayWheel.getItemCount();

                if (day > daySize) {
                    day = day - daySize;
                }

                Calendar ca = Calendar.getInstance();
                ca.set(Calendar.YEAR, year);
                ca.set(Calendar.MONTH, month);
                ca.set(Calendar.DATE, day);

                long setTime = ca.getTimeInMillis();

                if (updateDateListener != null) {
                    updateDateListener.onSure(year, month, day, setTime);
                }
            }
        });
        dialog = new AlertDialog.Builder(context).setView(dialogView).create();


    }

    /**
     * 显示选择日期对话框
     *
     * @param year  默认显示的年
     * @param month 默认月
     * @param day   默认日
     */
    public void show(int year, int month, int day) {
        if (dialog == null || dialog.isShowing()) {
            return;
        }
        dayWheel.setWheelItemList(WheelStyle.createDayString(year - 1980, month + 1));
        yearWheel.setCurrentItem(year - 1980);
        monthWheel.setCurrentItem(month);
        dayWheel.setCurrentItem(day - 1);
        dialog.show();
    }


    /**
     * 选择日期对话框回调
     *
     * @param listener
     */
    public void setOnClickListener(OnClickListener listener) {
        updateDateListener = listener;
    }

    /**
     * 选择日期对话框回调接口，调用者实现
     *
     * @author huangzj
     */
    public interface OnClickListener {
        void onSure(int year, int month, int day, long time);

        void onCancel();
    }
}
