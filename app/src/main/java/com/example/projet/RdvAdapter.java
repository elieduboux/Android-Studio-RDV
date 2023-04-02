package com.example.projet;

import android.annotation.SuppressLint;
import android.content.Context;
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
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        ViewHolder holder;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rdv_list_item,null, false);
            TextView txtTitle = convertView.findViewById(R.id.rdv_list_item_title);
            CheckBox cbOver   = convertView.findViewById(R.id.rdv_list_item_over);
            ImageView imDate  = convertView.findViewById(R.id.rdv_list_item_date_icon);
            TextView txtDate  = convertView.findViewById(R.id.rdv_list_item_date);

            holder = new ViewHolder();
            holder.txtTitle = txtTitle;
            holder.cbOver = cbOver;
            holder.imDate = imDate;
            holder.txtDate = txtDate;
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        Rdv rdv = getItem(position);
        if (rdv != null)
        {
            holder.txtTitle.setText(rdv.title);
            holder.cbOver.setChecked(rdv.state);
            holder.imDate.setImageResource(R.drawable.day_calendar);
            holder.txtDate.setText(rdv.date);
        }
        return convertView;
    }
}
