package com.example.rsampath.myapplication;

/**
 * Created by rsampath on 3/5/15.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;


public class AddItemAdapter extends ArrayAdapter<ItemObject> {

    protected static final String LOG_TAG = AddItemAdapter.class.getSimpleName();

    public List<ItemObject> items;
    private int layoutResourceId;
    private Context context;
    public static double total = 0.0;
    TextView totalamount = null;
    TextView changeAmount = null;
    EditText givenAmount = null;

    public AddItemAdapter(Context context, int layoutResourceId, List<ItemObject> items, TextView total, EditText given, TextView change) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
        this.totalamount = total;
        this.givenAmount = given;
        this.changeAmount = change;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final AtomPaymentHolder holder = new AtomPaymentHolder();

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder.atomPayment = items.get(position);
        holder.add = (ImageButton) row.findViewById(R.id.add);
        holder.remove = (ImageButton) row.findViewById(R.id.remove);
        holder.add.setTag(holder.atomPayment);
        holder.remove.setTag(holder.atomPayment);

        holder.name = (TextView) row.findViewById(R.id.atomPay_name);
        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/Square.ttf");
        holder.name.setTypeface(typeFace);
        holder.quantity = (TextView) row.findViewById(R.id.atomPay_quantity);

        row.setTag(holder);

        setupItem(holder);


        ImageButton add = (ImageButton) row.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double quantity = holder.atomPayment.increment();
                holder.quantity.setText(String.valueOf(quantity));
                total += holder.atomPayment.getValue();
                totalamount.setText(String.valueOf(total));
                Log.d(LOG_TAG, " " + quantity + "  " + total + " " + holder.atomPayment.getValue() + " " + holder.atomPayment.getName());

            }
        });


        ImageButton remove = (ImageButton) row.findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double quantity = holder.atomPayment.decrement();
                if (quantity >= 0) {
                    total -= holder.atomPayment.getValue();
                    holder.quantity.setText(String.valueOf(quantity));
                } else {
                    holder.atomPayment.setQuantity(0);
                }
                totalamount.setText(String.valueOf(total));
                Log.d(LOG_TAG, " " + quantity + " " + total + " " + holder.atomPayment.getValue() + " " + holder.atomPayment.getName());
            }
        });

        givenAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Double amtGiven = 0.0;
                Double amtTotal = Double.valueOf(totalamount.getText().toString());
                if(Double.valueOf(givenAmount.getText().toString()).equals(""))
                    amtGiven = 0.0;
                else
                amtGiven = Double.valueOf(givenAmount.getText().toString());
                if (amtGiven > 0.0)
                    changeAmount.setText(String.valueOf(amtGiven-amtTotal));
            }
        });

        totalamount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Double amtGiven = 0.0;
                Double amtTotal = Double.valueOf(totalamount.getText().toString());
                if(Double.valueOf(givenAmount.getText().toString()).equals(""))
                    amtGiven = 0.0;
                if (amtGiven > 0.0)
                    changeAmount.setText(String.valueOf(amtGiven-amtTotal));
            }
        });


        return row;
    }

    private void setupItem(AtomPaymentHolder holder) {
        holder.name.setText(String.valueOf(holder.atomPayment.getName()));
        holder.quantity.setText(String.valueOf(holder.atomPayment.getQuantity()));
    }

    public static class AtomPaymentHolder {
        ItemObject atomPayment;
        TextView name;
        TextView quantity;
        ImageButton add;
        ImageButton remove;
    }

}