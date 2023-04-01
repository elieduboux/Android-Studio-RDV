package com.example.projet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        ImageView image;
        TextView txtLastName;
        TextView txtFirstName;
        TextView date;
        TextView domain;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        ViewHolder holder = null;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item,null, false);
            ImageView image = (ImageView) convertView.findViewById(R.id.logo);
            TextView txtFirstName = (TextView) convertView.findViewById(R.id.label);
            TextView txtLastName  = (TextView) convertView.findViewById(R.id.label2);
            TextView date = (TextView) convertView.findViewById(R.id.dateOfBirth);
            TextView domain = (TextView) convertView.findViewById(R.id.domain);

            holder = new ViewHolder();
            holder.image = image;
            holder.txtLastName = txtLastName;
            holder.txtFirstName = txtFirstName;
            holder.date = date;
            holder.domain = domain;
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        Actor actor = getItem(position);
        if (actor != null)
        {
            holder.txtLastName.setText(actor.lastName);
            holder.txtFirstName.setText(actor.firstName);
            holder.image.setImageResource((actor.photo));
            holder.date.setText(actor.dateOfBirth);
            holder.domain.setText(actor.domain);
        }
        return convertView;
    }
}
