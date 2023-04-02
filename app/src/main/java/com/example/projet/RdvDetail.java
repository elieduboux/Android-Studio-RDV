package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RdvDetail extends AppCompatActivity {

    private TextView  txtTitle;
    private CheckBox  cbOver;
    private ImageView imDate;
    private TextView  txtDate;
    private Rdv rdv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rdv_details);

        Intent intent= getIntent();
        Bundle rdvReceived = intent.getExtras();
        rdv = rdvReceived.getParcelable("rdv");

        String title = rdv.getTitle();
        Boolean state = rdv.getState();
        String date = rdv.getDate();

        txtTitle = findViewById(R.id.rdv_details_title);
        cbOver   = findViewById(R.id.rdv_details_over);
        imDate  = findViewById(R.id.rdv_details_date_icon);
        txtDate  = findViewById(R.id.rdv_details_date);

        txtTitle.setText(title);
        cbOver.setChecked(state);
        imDate.setImageResource(R.drawable.day_calendar);
        txtDate.setText(date);
    }

    public void onValidate(View v)
    {
        Intent intent= new Intent(this, RdvList.class);
        Rdv rdvTmp = new Rdv();
        try {
            rdvTmp.setTitle(txtTitle.getText().toString());
            rdvTmp.setDate(txtDate.getText().toString());
            rdvTmp.setState(cbOver.isChecked());
            intent.putExtra("rdv", rdvTmp);

        }catch(Exception e) {
            Toast.makeText(RdvDetail.this,"Error, something probably null," +
                    "Passing the previous elements !",Toast.LENGTH_LONG).show();
            intent.putExtra("rdv", rdv);
        }

        this.startActivity(intent);
    }

}
