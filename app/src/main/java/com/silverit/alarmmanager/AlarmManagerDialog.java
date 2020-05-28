package com.silverit.alarmmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.ToggleButton;


public class AlarmManagerDialog extends Dialog implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    Activity activity;
    Button btnTime, btnOK, btnCancel;
    LinearLayout layoutWeekday;
    ToggleButton[] weekdays;
    CheckBox checkRepeat;

    int mHourOfDay, mMinute;

    public AlarmManagerDialog(Activity activity) {

        super(activity);
        // TODO Auto-generated constructor stub
        this.activity = activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.custom_dialog);

        Window window = getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        btnTime = (Button) findViewById(R.id.btnTime);

        checkRepeat = (CheckBox) findViewById(R.id.checkRepeat);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnCancel = (Button) findViewById(R.id.btnCancel);


        btnTime.setOnClickListener(this);
        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        checkRepeat.setOnClickListener(this);


        layoutWeekday = (LinearLayout) findViewById(R.id.layoutWeekday);

        weekdays = new ToggleButton[7];
        weekdays[0]     = (ToggleButton) findViewById(R.id.sunday);
        weekdays[1]     = (ToggleButton) findViewById(R.id.monday);
        weekdays[2]     = (ToggleButton) findViewById(R.id.tuesday);
        weekdays[3]     = (ToggleButton) findViewById(R.id.wednesday);
        weekdays[4]     = (ToggleButton) findViewById(R.id.thursday);
        weekdays[5]     = (ToggleButton) findViewById(R.id.friday);
        weekdays[6]     = (ToggleButton) findViewById(R.id.saturday);

        init();
    }

    private void init(){

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        setTimeText(hour, minute);
    }

    private void setTimeText(int hourOfDay, int minute){

        /*SpannableString ss1=  new SpannableString(strTime);
        ss1.setSpan(new RelativeSizeSpan(2f), 0,2, 0); // set size*/

        btnTime.setText( hourOfDay + ":" + String.format("%02d", minute));
        this.mHourOfDay = hourOfDay;
        this.mMinute = minute;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.btnTime:

                // TODO Auto-generated method stub

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(activity, this, mHourOfDay, mMinute, true);//Yes 24 hour time

                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;

            case R.id.checkRepeat :

                if(checkRepeat.isChecked()){
                    layoutWeekday.setVisibility(View.VISIBLE);
                }else{
                    layoutWeekday.setVisibility(View.GONE);
                }
                break;

            case R.id.btnOK:

                AlarmManager alarmMgr = (AlarmManager)activity.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(activity, MyAlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, intent, 0);

                Calendar alarmCalendar = Calendar.getInstance();
                alarmCalendar.set(Calendar.HOUR_OF_DAY, mHourOfDay);
                alarmCalendar.set(Calendar.MINUTE, mMinute);
                alarmCalendar.set(Calendar.SECOND, 0);
                alarmCalendar.set(Calendar.MILLISECOND, 0);


                if(!checkRepeat.isChecked()){
                    alarmMgr.set(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), pendingIntent);
                }else{

                    for(int i = 0; i < 7; i++){

                        alarmCalendar.set(Calendar.DAY_OF_WEEK, i + 1);
                        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                                alarmCalendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
                    }
                }


                dismiss();
                break;

            case R.id.btnCancel :
                dismiss();
                break;
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        setTimeText(hourOfDay, minute);
    }
}
