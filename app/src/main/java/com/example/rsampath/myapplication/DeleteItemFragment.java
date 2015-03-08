package com.example.rsampath.myapplication;

/**
 * Created by rsampath on 3/6/15.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class DeleteItemFragment extends Fragment {

    private static final String TAG = DeleteItemFragment.class.getCanonicalName();
    ArrayList<ItemObject> itemObjectList = new ArrayList<>();
    ListView listView;
    private Button close;
    private Button delete;
    private EditText itemIdEntered;
    private ItemOperations itemDBoperation;
    private String id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemObjectList = getArguments().getParcelableArrayList("arraylist");
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            someEventListener = (onSomeEventListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
//        }
//    }


    @Override
    public View onCreateView(final LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.delete_item_fragment, container, false);

        itemDBoperation = new ItemOperations(getActivity());
        itemDBoperation.open();


        close = (Button) view.findViewById(R.id.closeButton);
        delete = (Button) view.findViewById(R.id.deleteButton);
        itemIdEntered = (EditText) view.findViewById(R.id.enteredId);

        listView = (ListView) view.findViewById(R.id.list_items);
        final DeleteItemAdapter adapter = new DeleteItemAdapter(getActivity(),
                R.layout.fragment_delete_row, itemObjectList);
        listView.setAdapter(adapter);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        itemIdEntered.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                id = itemIdEntered.getText().toString();
                Log.d(TAG,"!@#$%^&*"+ id);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"item id "+id);
             itemDBoperation.deleteItem(Long.parseLong(id));
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        itemDBoperation.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        itemDBoperation.close();
        super.onPause();
    }


}