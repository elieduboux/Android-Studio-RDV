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



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_rdv);

        etTitle  = findViewById(R.id.new_rdv_title_et);
        etDate   = findViewById(R.id.new_rdv_date_ed);


        myHelper = new DataBaseHelper(this);
        myHelper.open();
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

}