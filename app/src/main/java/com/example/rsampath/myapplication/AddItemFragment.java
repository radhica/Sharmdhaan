package com.example.rsampath.myapplication;

/**
 * Created by rsampath on 3/5/15.
 */
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddItemFragment extends Fragment {

    private static final String TAG = AddItemFragment.class.getCanonicalName();

    public interface addItemEventListener {
        public void addItemEvent(ArrayList<ItemObject> newObj);
    }

    addItemEventListener addItemEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addItemEventListener = (addItemEventListener) activity;
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
        View view = inflater.inflate(R.layout.add_item_fragment, container, false);
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

                String itemNewName = item.getText().toString();
                String itemNewPrice = price.getText().toString();

                if(!isEmpty(itemNewName) && !isEmpty(itemNewPrice) && isNumeric(itemNewPrice)) {
                    Log.d(TAG," "+isEmpty(itemNewName)+""+isEmpty(itemNewPrice)+""+isNumeric(itemNewPrice) );
                    Double itemNewPriceDouble = Double.parseDouble(itemNewPrice);
                    newItems.add(new ItemObject(itemNewName, 0, itemNewPriceDouble));
                    containerF.addView(addView);
                }
                else Toast.makeText(getActivity(),"Enter valid details",Toast.LENGTH_LONG).show();

            }});

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemEventListener.addItemEvent(newItems);
                FragmentManager fm = getFragmentManager();
                    fm.popBackStack();
            }
        });


    return view;
    }

    private boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isEmpty(String str)
    {
        return str.length()==0;
    }
}