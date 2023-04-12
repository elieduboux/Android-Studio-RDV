package com.example.projet;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RdvAdapter extends ArrayAdapter<Rdv> {
    public RdvAdapter(Context context, int resource, ArrayList<Rdv> users )
    {
        super(context,resource, users);
    }

    static class ViewHolder {
        TextView  txtTitle;
        CheckBox  cbOver;
        ImageView imDate;
        TextView  txtDate;
        ImageView imTime;
        TextView txtTime;
        ImageView imPerson;
        TextView txtPerson;
        ImageView imShare;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rdv_list_item,null, false);
            TextView txtTitle  = convertView.findViewById(R.id.rdv_list_item_title);
            CheckBox cbOver    = convertView.findViewById(R.id.rdv_list_item_state);
            ImageView imDate   = convertView.findViewById(R.id.rdv_list_item_date_icon);
            TextView txtDate   = convertView.findViewById(R.id.rdv_list_item_date);
            ImageView imTime   = convertView.findViewById(R.id.rdv_list_item_time_icon);
            TextView txtTime   = convertView.findViewById(R.id.rdv_list_item_time);
            ImageView imPerson = convertView.findViewById(R.id.rdv_list_item_person_icon);
            TextView txtPerson = convertView.findViewById(R.id.rdv_list_item_person);
            ImageView imShare  = convertView.findViewById(R.id.rdv_list_share_iv);

            holder = new ViewHolder();
            holder.txtTitle  = txtTitle;
            holder.cbOver    = cbOver;
            holder.imDate    = imDate;
            holder.txtDate   = txtDate;
            holder.imTime    = imTime;
            holder.txtTime   = txtTime;
            holder.imPerson  = imPerson;
            holder.txtPerson = txtPerson;
            holder.imShare   = imShare;

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        Rdv rdv = getItem(position);
        if (rdv != null) {
            holder.txtTitle.setText(rdv.title);
            holder.cbOver.setChecked(rdv.state);
            holder.imDate.setImageResource(R.drawable.day_calendar);
            holder.txtDate.setText(rdv.date);
            holder.imTime.setImageResource(R.drawable.clock_calendar);
            holder.txtTime.setText(rdv.time);
            holder.imPerson.setImageResource(R.drawable.person);
            holder.txtPerson.setText(rdv.person);

            holder.imShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();

                    String textToShare = "Title : " + rdv.title + "\n" +
                            "Date : " + rdv.date + "\n" +
                            "Time : " + rdv.time + "\n" +
                            "Contact : " + rdv.person + "\n";
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
                    sendIntent.setType("text/plain");
//                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(Intent.createChooser(sendIntent, "Share App"));
                }

            });
        }

        return convertView;
    }
}
