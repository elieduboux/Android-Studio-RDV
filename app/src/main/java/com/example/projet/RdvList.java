package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

public class RdvList extends AppCompatActivity {
    private DataBaseHelper myHelper;
    private ListView lvRdvs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rdv_list);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darkTheme); //when dark mode is enabled, we use the dark theme
        } else {
            setTheme(R.style.AppTheme);  //default app theme
        }

        myHelper = new DataBaseHelper(this);
        myHelper.open();

        lvRdvs = (ListView) findViewById(R.id.myListView);
        lvRdvs.setEmptyView(findViewById(R.id.tvEmpty));

        chargeData();
        registerForContextMenu(lvRdvs);

        lvRdvs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String idItem= ((TextView)view.findViewById(R.id.rdv_list_item_tv_id)).getText().toString();
                String titleItem= ((TextView)view.findViewById(R.id.rdv_list_item_title)).getText().toString();
                String dateItem= ((TextView)view.findViewById(R.id.rdv_list_item_date)).getText().toString();
                String timeItem = ((TextView) view.findViewById(R.id.rdv_list_item_time)).getText().toString();
                String personItem = ((TextView) view.findViewById(R.id.rdv_list_item_person)).getText().toString();
                CheckBox cbState = view.findViewById(R.id.rdv_list_item_state);
                Boolean stateItem = cbState.isChecked();

                Rdv rdv= new Rdv(Integer.parseInt(idItem),titleItem,dateItem,timeItem,personItem,stateItem);
                Intent intent = new Intent(getApplicationContext(), RdvDetail.class);
                intent.putExtra("rdv",rdv);

                intent.putExtra("fromAdd",false);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        if (item.getItemId()==R.id.delete){
            myHelper.delete((int)info.id);
            chargeData();
            return true;
        }
        return super.onContextItemSelected(item);
    }


    public void chargeData(){
        final String[] from = new String[]{DataBaseHelper._ID, DataBaseHelper.TITLE,
                DataBaseHelper.DATE,DataBaseHelper.TIME, DataBaseHelper.PERSON, DataBaseHelper.STATE};
        final int[]to= new int[]{
                R.id.rdv_list_item_tv_id,R.id.rdv_list_item_title, R.id.rdv_list_item_date,
                R.id.rdv_list_item_time, R.id.rdv_list_item_person,R.id.rdv_list_item_state};

        Cursor c = myHelper.getAllRdv();
        SimpleCursorAdapter adapter= new SimpleCursorAdapter(this,R.layout.rdv_list_item,c,from,to,0);
        adapter.notifyDataSetChanged();
        lvRdvs.setAdapter(adapter);
    }

}