package com.example.projet;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class NewRdv extends AppCompatActivity {

    private DataBaseHelper myHelper;
    private EditText etTitle;
    private ImageView imDate;
    private int year,month,day;
    private EditText etDate;
    private ImageView imTime;
    private EditText etTime;
    private int hours, minutes;
    private EditText etPerson;
    private ImageView imPerson;




    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_rdv);

        etTitle  = findViewById(R.id.new_rdv_title_et);
        etDate   = findViewById(R.id.new_rdv_date_ed);
        etTime= findViewById(R.id.new_rdv_time_et);
        etPerson = findViewById(R.id.new_rdv_person_ep);

        imDate   = findViewById(R.id.new_rdv_date_iv);
        imDate.setImageResource(R.drawable.day_calendar);
        imTime = findViewById(R.id.new_rdv_time_iv);
        imTime.setImageResource(R.drawable.clock_calendar);
        imPerson = findViewById(R.id.new_rdv_person_iv);
        imPerson.setImageResource(R.drawable.person);

        myHelper = new DataBaseHelper(this);
        myHelper.open();
    }

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay)
        {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            etDate.setText(new StringBuilder().append(month +1).
                    append("-").append(day).append("-").append(year).append(" "));
        }
    };

    TimePickerDialog.OnTimeSetListener onTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) { hours = hour;
            minutes = minute;
            etTime.setText(new StringBuilder().append(hours).append(":").append(minutes)); }
    };

    public void pickDateNewRdv(View view){
        DatePickerFragment date= new DatePickerFragment();
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        Bundle args = new Bundle();
        args.putInt("year",year);
        args.putInt("month",month);
        args.putInt("day",day);
        date.setArguments(args);
        date.setCallBack(onDate);
        date.show(getSupportFragmentManager(),"Date Picker");
    }

    public void showTimePicker(View view) {
        TimePickerFragment time= new TimePickerFragment();
        final Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        Bundle args = new Bundle();
        args.putInt("hours",hours);
        args.putInt("minutes",minutes);
        time.setArguments(args);
        time.setCallBack(onTime);
        time.show(getSupportFragmentManager(),"Time Picker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.new_rdv_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_back: {
                finish();
                return true;
            }
            case R.id.menu_settings: {
                Intent intent = new Intent(this,Settings.class);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveRdv(View view){
        String title  = etTitle.getText().toString();
        String date   = etDate.getText().toString();
        String time   = etTime.getText().toString();
        String person = etPerson.getText().toString();

        Rdv rdv = new Rdv(title,date,time,person,false);

        myHelper.add(rdv);
        Intent intent = new Intent(this,RdvList.class).
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}