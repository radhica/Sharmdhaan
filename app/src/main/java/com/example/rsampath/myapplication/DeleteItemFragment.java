package com.example.rsampath.myapplication;

/**
 * Created by rsampath on 3/6/15.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData;
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
    private DatabaseItemOperations itemDBoperation;
    private String id;
    public ArrayList<ItemObject> newItems = new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemObjectList = getArguments().getParcelableArrayList("arraylist");
    }

    public interface deleteItemEventListener {
        public void deleteItemEvent(ArrayList<ItemObject> idList);
    }

    deleteItemEventListener deleteItemEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            deleteItemEventListener = (deleteItemEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.delete_item_fragment, container, false);

        itemDBoperation = new DatabaseItemOperations(getActivity());
        itemDBoperation.open();


        close = (Button) view.findViewById(R.id.closeButton);
        listView = (ListView) view.findViewById(R.id.list_items);
        final DeleteItemAdapter adapter = new DeleteItemAdapter(getActivity(),
                R.layout.fragment_delete_row, itemObjectList);
        listView.setAdapter(adapter);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                for(ItemObject itemObject: itemObjectList){
                    if(itemObject.isSelected()){
                         newItems.add(itemObject);
                    }
                }
                deleteItemEventListener.deleteItemEvent(newItems);
                fm.popBackStack();
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