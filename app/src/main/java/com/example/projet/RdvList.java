package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Date;
import java.util.ArrayList;

public class RdvList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rdv_list);
        ArrayList<Rdv> arrayOfRdv= new ArrayList<>();
        Rdv test1 = new Rdv(1, "one"  , "02-04-2023", false);
        Rdv test2 = new Rdv(2, "two"  , "02-05-2023", true);
        Rdv test3 = new Rdv(3, "three", "02-06-2023", true);

        arrayOfRdv.add(test1);
        arrayOfRdv.add(test2);
        arrayOfRdv.add(test3);

        RdvAdapter adapter = new RdvAdapter(this,R.layout.simple_item_list,arrayOfRdv);
        ListView myListView = findViewById(R.id.myListView);
        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener((parent, view, position, id) -> {
//                Toast.makeText(MainActivity.this,"Item "+position+" selected",Toast.LENGTH_LONG).show();
            Intent intent   = new Intent(RdvList.this, RdvDetails.class);
            Rdv rdvTmp1 = new Rdv();
            Rdv rdvTmp2 = arrayOfRdv.get(position);

            rdvTmp1.setTitle(rdvTmp2.getTitle());
            rdvTmp1.setDate(rdvTmp2.getDate());
            rdvTmp1.setState(rdvTmp2.getState());

            intent.putExtra("rdv", rdvTmp1);
            startActivity(intent);
        });
    }
}