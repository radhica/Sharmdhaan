package com.example.rsampath.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rsampath on 3/7/15.
 */
public class DeleteItemAdapter extends ArrayAdapter<ItemObject> {

    Context context;

    public DeleteItemAdapter(Context context, int resourceId,
                                 List<ItemObject> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtId;
        TextView txtName;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ItemObject rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.delete_item_fragment, null);
            holder = new ViewHolder();
            holder.txtId = (TextView) convertView.findViewById(R.id.item_id);
            holder.txtName = (TextView) convertView.findViewById(R.id.item_name);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtId.setText(String.valueOf(rowItem.getId()));
        holder.txtName.setText(rowItem.getName());

        return convertView;
    }
}
