package com.example.rsampath.myapplication;

import android.app.FragmentManager;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AddItemFragment.addItemEventListener, DeleteItemFragment.deleteItemEventListener {

    private static final String TAG = MainActivity.class.getCanonicalName();
    public AddItemAdapter adapter;
    protected TextView total;
    protected EditText given;
    protected TextView change;
    ArrayList<ItemObject> arrayListOfPayment = new ArrayList<>();
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private DatabaseItemOperations itemDBoperation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


// trying to style action bar
//        LayoutInflater inflator = LayoutInflater.from(this);
//        View v = inflator.inflate(R.layout.title_view, null);
//
//        ((CustomTextView)v.findViewById(R.id.title)).setText("Shaarm");
//
//        this.getActionBar().setCustomView(v);


        itemDBoperation = new DatabaseItemOperations(this);
        itemDBoperation.open();

        setUpTextViews();
        createList();



        Log.d(TAG, total.getText().toString());

    }

    private void setUpTextViews() {
        total = (TextView) findViewById(R.id.total);
        given = (EditText) findViewById(R.id.given);
        change = (TextView) findViewById(R.id.change);

        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/Square.ttf");
        total.setTypeface(typeFace);
        given.setTypeface(typeFace);
        change.setTypeface(typeFace);
    }

    private void addDrawerItems() {
        String[] osArray = { "Add Items", "Delete Items" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawers();
                FragmentManager fragmentManager = getFragmentManager();

                switch(position) {
                    case 0:   AddItemFragment addItem = new AddItemFragment();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, addItem).addToBackStack(null).commit();
                        break;
                    case 1:
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("arraylist", arrayListOfPayment);
                        DeleteItemFragment deleteItem = new DeleteItemFragment();
                        deleteItem.setArguments(bundle);
                        fragmentManager.beginTransaction().replace(R.id.content_frame, deleteItem).addToBackStack(null).commit();
                        break;
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



    private void createList(){

//        List values = itemDBoperation.getAll();
        List values = readList();

        if(values == null) {
            arrayListOfPayment.add(new ItemObject("Nachos", 0, 5.00));
            arrayListOfPayment.add(new ItemObject("Popcorn", 0, 3.5));
            arrayListOfPayment.add(new ItemObject("Drink", 0, 3));
            arrayListOfPayment.add(new ItemObject("Fountain", 0, 4.5));
            writeList();
            for(ItemObject itemObject : arrayListOfPayment)
                itemDBoperation.addItem(itemObject);
        }
        else{
            arrayListOfPayment.clear();
            arrayListOfPayment.addAll(values);

            for(ItemObject newItemObj : arrayListOfPayment)
                Log.d(TAG,""+newItemObj.getId());

        }
        setupListViewAdapter();
    }

    private void setupListViewAdapter() {
        adapter = new AddItemAdapter(MainActivity.this, R.layout.list_item_row, arrayListOfPayment,total,given,change);
        ListView atomPaysListView = (ListView)findViewById(R.id.EnterPays_atomPaysList);
        atomPaysListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
            // action with ID action_refresh was selected
            if(id == R.id.action_refresh) {
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                total.setText("0.0");
                given.setText("0.0");
                change.setText("");
                adapter.givenAmount.setText("0.0");
                adapter.totalamount.setText("0.0");
                adapter.changeAmount.setText("0.0");
                AddItemAdapter.total = 0.0;
                for(int i = 0; i<adapter.items.size();i++)
                    adapter.items.get(i).setQuantity(0);
                for(ItemObject itemObject : arrayListOfPayment)
                    itemObject.setQuantity(0);
                adapter.notifyDataSetChanged();
                return true;


            }
            if(mDrawerToggle.onOptionsItemSelected(item))
                return true;

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void addItemEvent(ArrayList<ItemObject> newObj) {
        Log.d(TAG, "New" + newObj.size());
        Log.d(TAG, "Cur" + arrayListOfPayment.size());
        int success = itemDBoperation.deleteAll();
        arrayListOfPayment.addAll(newObj);
        for(ItemObject newItemObj : arrayListOfPayment)
               itemDBoperation.addItem(newItemObj);
        writeList();
        Log.d(TAG,"SP: "+readList().size());

        Log.d(TAG, "NewCur" + arrayListOfPayment.size());
        Log.d(TAG,"DB"+itemDBoperation.getAll().size());
        Log.d(TAG,"Delete"+success);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        itemDBoperation.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        itemDBoperation.close();
        super.onPause();
    }


    @Override
    public void deleteItemEvent(ArrayList<ItemObject> idList) {
        Log.d(TAG,""+idList.size());
        for(ItemObject newItemObj : idList) {
            itemDBoperation.deleteItem(newItemObj.getId());
            arrayListOfPayment.remove(newItemObj);
            Log.d(TAG, "" + newItemObj.getId());
        }
        writeList();
        Log.d(TAG,"SP: "+readList().size());
        adapter.notifyDataSetChanged();
    }


    public void writeList()
    {
        SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        String json = new Gson().toJson(arrayListOfPayment);
        prefsEditor.putString("MyItemsArray", json);
        prefsEditor.commit();
    }

    public  List<ItemObject> readList()
    {
        SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
        String connectionsJSONString = mPrefs.getString("MyItemsArray", null);
        Type type = new TypeToken<List<ItemObject>>(){}.getType();
        List<ItemObject> data = new Gson().fromJson(connectionsJSONString, type);
        return data;
    }
}