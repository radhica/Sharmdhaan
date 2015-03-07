package com.example.rsampath.myapplication;

/**
 * Created by rsampath on 3/6/15.
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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DeleteItemFragment extends Fragment {

    ArrayList<ItemObject> itemObjectList = new ArrayList<>();

    public interface onSomeEventListener {
        public void someEvent(ArrayList<ItemObject> newObj);
    }

    onSomeEventListener someEventListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemObjectList = getArguments().getParcelableArrayList("arraylist");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.delete_item_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.list_items);
        DeleteItemFragment adapter = new DeleteItemFragment(this,
                R.layout.fragment_delete_row, rowItems);
        listView.setAdapter(adapter);

        return view;
    }
}