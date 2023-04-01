package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.Date;
import java.util.ArrayList;

public class RdvList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Rdv> arrayOfRdv= new ArrayList<>();
        Rdv test1 = new Rdv(1, "one", "02-04-2023", true);
        Rdv test2 = new Rdv(2, "two", "02-05-2023", false);
        Rdv test3 = new Rdv(3, "three", "02-06-2023", true);
        arrayOfRdv.add(test1);
        arrayOfRdv.add(test2);
        arrayOfRdv.add(test3);

        ActorAdapter adapter = new ActorAdapter(this,R.layout.simple_item_list,arrayOfRdv);
        ListView myListView =(ListView)findViewById(R.id.myListView);
        myListView.setAdapter(adapter);
    }
}