package com.example.projet;

import android.app.DatePickerDialog;
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
    private int year,month,day;
    private EditText etDate;
    private Rdv rdv;
    private int idValue;
    private String titleValue;
    private String dateValue;
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

            String title  = rdv.getTitle();
            String date   = rdv.getDate();
            Boolean state = rdv.getState();

            etTitle.setHint(title);
            cbOver.setChecked(state);
            etDate.setText(date);
        };
    }

    public void onValidate(View v)
    {
        String  title = etTitle.getText().toString();
        String  date  = etDate.getText().toString();
        Boolean state = cbOver.isChecked();
        if (title.equals(""))
        {
            title = titleValue;
        }
        if (date.equals(""))
        {
            date = dateValue;
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
            Rdv rdv = new Rdv(title,date,state);
            myHelper.add(rdv);

            Intent main = new Intent(this,RdvList.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }
        else {
            Rdv rdv = new Rdv(idValue,title,date,state);
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
