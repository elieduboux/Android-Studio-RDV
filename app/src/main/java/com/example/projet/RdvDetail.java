package com.example.projet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class RdvDetail extends AppCompatActivity {

    private DataBaseHelper myHelper;
    private TextView  txtTitle;
    private EditText  etTitle;
    private CheckBox  cbOver;
    private ImageView imDate;
    private ImageView imTime;
    private int year,month,day;

    private int hours, minutes;
    private EditText etDate;
    private EditText etTime;
    private EditText etPerson;
    private ImageView imPerson;
    private Rdv rdv;
    private int idValue;
    private String titleValue;
    private String dateValue;
    private String timeValue;
    private String personValue;
    private boolean fromAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rdv_details);

        txtTitle = findViewById(R.id.rdv_details_tv_title);
        etTitle  = findViewById(R.id.rdv_details_ed_title);
        cbOver   = findViewById(R.id.rdv_details_over);
        etDate   = findViewById(R.id.editTextDate);
        imDate   = findViewById(R.id.rdv_details_date_icon);
        imDate.setImageResource(R.drawable.day_calendar);

        etTime = findViewById(R.id.editTextTime);
        imTime = findViewById(R.id.rdv_details_time_icon);
        imTime.setImageResource(R.drawable.clock_calendar);

        etPerson = findViewById(R.id.editTextPerson);
        imPerson = findViewById(R.id.rdv_details_person_icon);
        imPerson.setImageResource(R.drawable.person);

        myHelper = new DataBaseHelper(this);
        myHelper.open();

        Intent intent= getIntent();
        fromAdd= intent.getBooleanExtra("fromAdd",false);

        if (!fromAdd) {
            Bundle rdvReceived = intent.getExtras();
            rdv = rdvReceived.getParcelable("rdv");

            idValue = rdv.getId();
            titleValue = rdv.getTitle();
            dateValue = rdv.getDate();
            timeValue = rdv.getTime();
            personValue = rdv.getPerson();


            String title  = rdv.getTitle();
            String date   = rdv.getDate();
            String time = rdv.getTime();
            String person = rdv.getPerson();
            Boolean state = rdv.getState();

            etTitle.setHint(title);
            cbOver.setChecked(state);
            etDate.setText(date);
            etTime.setText(time);
            etPerson.setText(person);
        };
    }

    public void onValidate(View v)
    {
        String  title = etTitle.getText().toString();
        String  date  = etDate.getText().toString();
        String  time  = etTime.getText().toString();
        String person = etPerson.getText().toString();
        Boolean state = cbOver.isChecked();
        if (title.equals(""))
        {
            title = titleValue;
        }
        if (date.equals(""))
        {
            date = dateValue;
        }
        if (time.equals(""))
        {
            time = timeValue;
        }
        if (time.equals(""))
        {
            person = personValue;
        }
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
//        LocalDate rdvDate = LocalDate.parse(date);
//        LocalDate localDate = LocalDate.now();
//        if (rdvDate.compareTo(localDate) >= 0){
//            state = false;
//        }
//        else{
//            state = true;
//        }

        if(fromAdd) {
            Rdv rdv = new Rdv(title,date,time,person,state);
            myHelper.add(rdv);

            Intent main = new Intent(this,RdvList.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }
        else {
            Rdv rdv = new Rdv(idValue,title,date,time,person,state);
            myHelper.update(rdv); //Can return the "count" of the update but not useful here.

            Intent main = new Intent(this,RdvList.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }
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

    public void pickDateDetails(View view){
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
        inflater.inflate(R.menu.rdv_details_menu,menu);
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

}
