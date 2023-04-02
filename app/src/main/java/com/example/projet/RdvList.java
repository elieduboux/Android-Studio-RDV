package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
        Rdv test4 = new Rdv(4, "three", "02-06-2023", true);
        Rdv test5 = new Rdv(5, "three", "02-06-2023", false);
        Rdv test6 = new Rdv(6, "three", "02-06-2023", true);
        Rdv test7 = new Rdv(7, "three", "02-06-2023", true);

        arrayOfRdv.add(test1);
        arrayOfRdv.add(test2);
        arrayOfRdv.add(test3);
        arrayOfRdv.add(test4);
        arrayOfRdv.add(test5);
        arrayOfRdv.add(test6);
        arrayOfRdv.add(test7);

        RdvAdapter adapter  = new RdvAdapter(this,R.layout.simple_item_list,arrayOfRdv);
        ListView myListView = findViewById(R.id.myListView);
        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
//                Toast.makeText(RdvList.this,"Item "+position+" selected",Toast.LENGTH_LONG).show();
                Intent intent   = new Intent(RdvList.this, RdvDetail.class);
                Rdv rdvTmp1 = new Rdv();
                Rdv rdvTmp2 = arrayOfRdv.get(position);

                rdvTmp1.setTitle(rdvTmp2.getTitle());
                rdvTmp1.setDate(rdvTmp2.getDate());
                rdvTmp1.setState(rdvTmp2.getState());

                intent.putExtra("rdv", rdvTmp1);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.rdv_list_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_new_rdv: {
                Intent intent = new Intent(this,NewRdv.class);
                startActivity(intent);
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