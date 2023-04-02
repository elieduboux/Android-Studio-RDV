package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RdvDetails extends AppCompatActivity {

    private TextView  txtTitle;
    private CheckBox  cbOver;
    private ImageView imDate;
    private TextView  txtDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rdv_details);

        Intent intent= getIntent();
        Bundle b = intent.getExtras();
        Rdv rdv = b.getParcelable("rdv");

        String title = rdv.getTitle();
        Boolean state = rdv.getState();
        String date = rdv.getDate();

        TextView txtTitle = findViewById(R.id.rdv_details_title);
        CheckBox cbOver   = findViewById(R.id.rdv_details_over);
        ImageView imDate  = findViewById(R.id.rdv_details_date_icon);
        TextView txtDate  = findViewById(R.id.rdv_details_date);

        txtTitle.setText(title);
        cbOver.setChecked(state);
        imDate.setImageResource(R.drawable.day_calendar);
        txtDate.setText(date);
    }

    public void onValidate(View v)
    {
        Intent intent= new Intent(this, RdvList.class);
        Rdv rdv = new Rdv();
        rdv.setTitle(txtTitle.getText().toString());
        rdv.setDate(txtDate.getText().toString());
        rdv.setState(cbOver.isChecked());

        intent.putExtra("rdv", rdv);
        this.startActivity(intent);
    }

}
