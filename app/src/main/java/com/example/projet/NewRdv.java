package com.example.projet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class NewRdv extends AppCompatActivity {

    private DataBaseHelper myHelper;
    private EditText etTitle;

    private EditText etDate;
    int year,month,day;
    Button btnPickDate;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_rdv);

        etTitle  = findViewById(R.id.new_rdv_title_et);
        etDate   = findViewById(R.id.new_rdv_date_ed);
        btnPickDate=(Button)findViewById(R.id.new_rdv_btnPickDate);

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
            etDate.setText(new StringBuilder().append(month +1). append("-").append(day).append("-").append(year).append(" "));
        }
    };

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
//                Intent intent = new Intent(this,NewRdv.class);
//                startActivity(intent);
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
        String title = etTitle.getText().toString();
        String date  = etDate.getText().toString();

        Rdv rdv = new Rdv(title,date,false);

        myHelper.add(rdv);
        Intent intent = new Intent(this,RdvList.class).
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void pickDate(View view){
        showDatePicker();
    }

    private void showDatePicker() {
        DatePickerFragment date= new DatePickerFragment();
        final Calendar c = Calendar.getInstance(); year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        Bundle args = new Bundle(); args.putInt("year",year); args.putInt("month",month); args.putInt("day",day);
        date.setArguments(args);
        date.setCallBack(onDate); date.show(getSupportFragmentManager(),"Date Picker");
    }
}