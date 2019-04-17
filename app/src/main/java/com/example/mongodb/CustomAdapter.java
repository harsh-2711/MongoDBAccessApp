package com.example.mongodb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Result> {

    private ArrayList<Result> data;
    Context context;

    private static class ViewHolder {
        TextView place;
        TextView desc;
    }

    public CustomAdapter(ArrayList<Result> data, Context context) {
        super(context, R.layout.adapter_content, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Result result = getItem(position);

        ViewHolder viewHolder;

        final View view;

        if(convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());

            convertView = layoutInflater.inflate(R.layout.adapter_content, parent, false);

            viewHolder.place = (TextView) convertView.findViewById(R.id.place_name);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.description);

            view = convertView;

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        viewHolder.place.setText(result.getPlaceName());
        viewHolder.desc.setText(result.getDescription());

        return convertView;
    }
}
