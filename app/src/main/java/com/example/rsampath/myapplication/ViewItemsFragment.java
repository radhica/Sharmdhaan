package com.example.rsampath.myapplication;

/**
 * Created by rsampath on 3/10/15.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewItemsFragment extends Fragment {

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



    @Override
    public View onCreateView(final LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.view_items_fragment, container, false);

        itemDBoperation = new DatabaseItemOperations(getActivity());
        itemDBoperation.open();


        close = (Button) view.findViewById(R.id.closeButton);
        listView = (ListView) view.findViewById(R.id.list_items);
        final ViewItemsAdapter adapter = new ViewItemsAdapter(getActivity(),
                R.layout.fragment_view_row, itemObjectList);
        listView.setAdapter(adapter);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
