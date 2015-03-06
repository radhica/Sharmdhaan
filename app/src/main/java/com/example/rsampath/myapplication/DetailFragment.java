package com.example.rsampath.myapplication;

/**
 * Created by rsampath on 3/5/15.
 */
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    public interface onSomeEventListener {
        public void someEvent(ArrayList<ItemObject> newObj);
    }

    onSomeEventListener someEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }



    EditText itemEdit;
    EditText priceEdit;
    Button buttonAdd;
    Button close;
     LinearLayout containerF;
    public ArrayList<ItemObject> newItems = new ArrayList<>();

    @Override
    public View onCreateView(final LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.menu_detail_fragment, container, false);
        buttonAdd = (Button) view.findViewById(R.id.addButton);
        close = (Button) view.findViewById(R.id.Close);
        containerF = (LinearLayout)view.findViewById(R.id.container);
        itemEdit = (EditText) view.findViewById(R.id.item);
        priceEdit = (EditText) view.findViewById(R.id.price);

        buttonAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                final View addView = inflater.inflate(R.layout.fragment_add_row, null);
                TextView item = (TextView) addView.findViewById(R.id.item_text);
                item.setText(itemEdit.getText().toString());
                itemEdit.setText("");

                TextView price = (TextView) addView.findViewById(R.id.price_text);
                price.setText(priceEdit.getText().toString());
                priceEdit.setText("");

                newItems.add(new ItemObject(item.getText().toString(),0,Double.valueOf(price.getText().toString())));


                containerF.addView(addView);
            }});

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                someEventListener.someEvent(newItems);
                FragmentManager fm = getFragmentManager();
                    fm.popBackStack();
            }
        });


    return view;
    }
}