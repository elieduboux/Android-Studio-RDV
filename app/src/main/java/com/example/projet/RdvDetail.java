package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RdvDetail extends AppCompatActivity {

    private DataBaseHelper myHelper;
    private TextView  txtTitle;
    private EditText  edTitle;
    private CheckBox  cbOver;
    private ImageView imDate;
    private EditText edDate;
    private TextView  txtDate;
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
        edTitle  = findViewById(R.id.rdv_details_ed_title);
        cbOver   = findViewById(R.id.rdv_details_over);
        edDate   = findViewById(R.id.editTextDate);
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

            edTitle.setHint(title);
            cbOver.setChecked(state);
            edDate.setText(date);
        };
    }

    public void onValidate(View v)
    {
        String  title = edTitle.getText().toString();
        String  date  = edDate.getText().toString();
        Boolean state = cbOver.isChecked();
        if (title.equals(""))
        {
            title = titleValue;
        }
        if (date.equals(""))
        {
            date = dateValue;
        }

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

}
