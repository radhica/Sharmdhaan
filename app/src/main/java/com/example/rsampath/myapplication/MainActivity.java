package com.example.rsampath.myapplication;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements DetailFragment.onSomeEventListener {

    private static final String TAG = MainActivity.class.getCanonicalName();
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    protected TextView total;
    protected EditText given;
    protected TextView change;

    public ItemListAdapter adapter;
    ArrayList<ItemObject> arrayListOfPayment = new ArrayList<>();

    private ItemOperations itemDBoperation;



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

        itemDBoperation = new ItemOperations(this);
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
        String[] osArray = { "Add Items" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawers();
                DetailFragment detail = new DetailFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, detail).addToBackStack(null).commit();
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
        List values = itemDBoperation.getAll();
        Log.d(TAG,""+values.size());

        if(values.size()==0) {
            arrayListOfPayment.add(0, itemDBoperation.addItem(new ItemObject("Nachos", 0, 5.00)));
            arrayListOfPayment.add(1, itemDBoperation.addItem(new ItemObject("Popcorn", 0, 3.5)));
            arrayListOfPayment.add(2, itemDBoperation.addItem(new ItemObject("Drink", 0, 3)));
            arrayListOfPayment.add(3, itemDBoperation.addItem(new ItemObject("Fountain", 0, 4.5)));
        }
        else{
            arrayListOfPayment.clear();
            arrayListOfPayment.addAll(values);

        }
        setupListViewAdapter();
    }

    private void setupListViewAdapter() {
        adapter = new ItemListAdapter(MainActivity.this, R.layout.list_item_row, arrayListOfPayment,total,given,change);
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
                ItemListAdapter.total = 0.0;
                for(int i = 0; i<adapter.items.size();i++)
                    adapter.items.get(i).setQuantity(0.0);
                return true;

            }
            if(mDrawerToggle.onOptionsItemSelected(item))
                return true;

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void someEvent(ArrayList<ItemObject> newObj) {
        Log.d(TAG, "New" + newObj.size());
        Log.d(TAG, "Cur" + arrayListOfPayment.size());
        for(int i = arrayListOfPayment.size(), j = 0; i <= (arrayListOfPayment.size()+newObj.size()) -1; i++,j++) {
            if (j < newObj.size()) {
                arrayListOfPayment.add(i, itemDBoperation.addItem(newObj.get(j)));
            }
        }
        Log.d(TAG, "NewCur" + arrayListOfPayment.size());
        Log.d(TAG,"DB"+itemDBoperation.getAll().size());
        adapter.notifyDataSetChanged();
    }




}