package com.huangzj.simplewheelview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.huangzj.simplewheelview.dialog.SelectDateDialog;
import com.huangzj.simplewheelview.dialog.SelectTimeDialog;
import com.huangzj.simplewheelview.dialog.SelectWeekDialog;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button selectDate;
    private Button selectTime;
    private Button selectLightTime;

    private String[] weekString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
        initValue();
    }

    private void initValue() {
        weekString = getResources().getStringArray(R.array.weeks);
    }


    private void initView() {
        selectDate = (Button) findViewById(R.id.show_select_date);
        selectTime = (Button) findViewById(R.id.show_select_time);
        selectLightTime = (Button) findViewById(R.id.show_select_week);

        selectDate.setOnClickListener(this);
        selectTime.setOnClickListener(this);
        selectLightTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_select_date:
                showSelectDateDialog();
                break;
            case R.id.show_select_time:
                showSelectTimeDialog();
                break;
            case R.id.show_select_week:
                showSelectWeekDialog();
                break;
            default:
                break;
        }

    }

    private void showSelectWeekDialog() {
        SelectWeekDialog mSelectWeekDialog = new SelectWeekDialog(this, new SelectWeekDialog.OnClickListener() {
            @Override
            public boolean onSure(int item, int setTimeType) {
                Toast.makeText(MainActivity.this, weekString[item], Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onCancel() {
                return false;
            }
        });
        mSelectWeekDialog.show(3);
    }

    private void showSelectTimeDialog() {
        SelectTimeDialog mSelectTimeDialog = new SelectTimeDialog(this, new SelectTimeDialog.OnClickListener() {
            @Override
            public boolean onSure(int hour, int minute, int setTimeType) {
                String result = String.format("%02d:%02d", hour, minute);
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onCancel() {
                return false;
            }
        });
        mSelectTimeDialog.show(12, 30, 1);
    }

    private void showSelectDateDialog() {
        SelectDateDialog mSelectDateDialog = new SelectDateDialog(this);
        mSelectDateDialog.setOnClickListener(new SelectDateDialog.OnClickListener() {
            @Override
            public boolean onSure(int mYear, int mMonth, int mDay, long time) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Toast.makeText(MainActivity.this, dateFormat.format(time), Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onCancel() {
                return false;
            }
        });
        mSelectDateDialog.show(2013, 11, 15);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
