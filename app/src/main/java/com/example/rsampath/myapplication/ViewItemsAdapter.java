package com.example.rsampath.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rsampath on 3/10/15.
 */
public class ViewItemsAdapter extends ArrayAdapter {
    Context context;
    private int resourceId;

    public ViewItemsAdapter(android.content.Context context, int resourceId,
                             ArrayList<ItemObject> items) {
        super(context, resourceId, items);
        this.context = context;
        this.resourceId = resourceId;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtName;
        TextView txtPrice;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final ItemObject rowItem = (ItemObject) getItem(position);


        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(resourceId, parent,false);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.title);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.price);

            convertView.setTag(holder);
            convertView.setTag(R.id.title, holder.txtName);
            convertView.setTag(R.id.price, holder.txtPrice);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtName.setText(rowItem.getName());
        holder.txtPrice.setText(String.valueOf(rowItem.getValue()));

        return convertView;
    }
}