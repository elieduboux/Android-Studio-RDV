package com.example.projet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
    private ImageView imPhone;
    private ImageView imAddress;
    private int year,month,day;

    private int hours, minutes;
    private EditText etDate;
    private EditText etTime;
    private EditText etPerson;
    private EditText etPhone;
    private EditText etAddress;
    private ImageView imPerson;
    private Rdv rdv;
    private int idValue;
    private String titleValue;
    private String dateValue;
    private String timeValue;
    private String personValue;
    private String phoneValue;
    private String addressValue;
    private boolean fromAdd;
    private static final int REQUEST_CALL =1;

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

        etPhone = findViewById(R.id.editPhonePerson);
        imPhone = findViewById(R.id.rdv_details_phone_icon);
        imPhone.setImageResource(R.drawable.call);

        etAddress = findViewById(R.id.editAddressPerson);
        imAddress = findViewById(R.id.rdv_details_address_icon);
        imAddress.setImageResource(R.drawable.address);

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
            phoneValue = rdv.getPhone();
            addressValue = rdv.getAddress();


            String title  = rdv.getTitle();
            String date   = rdv.getDate();
            String time = rdv.getTime();
            String person = rdv.getPerson();
            String phone = rdv.getPhone();
            String address = rdv.getAddress();
            Boolean state = rdv.getState();

            etTitle.setHint(title);
            cbOver.setChecked(state);
            etDate.setText(date);
            etTime.setText(time);
            etPerson.setText(person);
            etPhone.setText(phone);
            etAddress.setText(address);
        };
    }

    public void onValidate(View v)
    {
        String  title = etTitle.getText().toString();
        String  date  = etDate.getText().toString();
        String  time  = etTime.getText().toString();
        String person = etPerson.getText().toString();
        String phone = etPhone.getText().toString();
        String address = etAddress.getText().toString();
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
        if (person.equals(""))
        {
            person = personValue;
        }
        if (phone.equals(""))
        {
            phone = phoneValue;
        }
        if (address.equals(""))
        {
            address = addressValue;
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
            Rdv rdv = new Rdv(title,date,time,person,phone, address,state);
            myHelper.add(rdv);

            Intent main = new Intent(this,RdvList.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }
        else {
            Rdv rdv = new Rdv(idValue,title,date,time,person,phone, address,state);
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
    public void launchMaps(View view) {
        String map = "http://maps.google.co.in/maps?q=" + etAddress.getText() ; Uri gmmIntentUri = Uri.parse(map);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps"); startActivity(mapIntent);
    }

    public void phoneCall(View v){
        makePhoneCall();
    }
    private  void makePhoneCall(){
        String phone = etPhone.getText().toString();
        if (phone.trim().length()>0){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(RdvDetail.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }else{
                String dial = "tel:" + phone;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        }else{

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String [] permissions, @NonNull int [] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
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
