package com.example.c301_w16_g5.c301_w16_g5;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/* the following two tutorials were used to produce this class:
   - http://developer.android.com/guide/topics/search/search-dialog.html
   - http://developer.android.com/training/search/setup.html
   accessed March 18, 2016 by Hailey Musselman
 */


/**
 * Activity that allows a user to perform a keyword search for chickens
 * in the database, displaying a list of the results.
 *
 * @author  Hailey
 * @version 1.5, 03/19/2016
 * @see     SearchController
 * @see     Chicken
 */
public class SearchesActivity extends AppCompatActivity {
    private ArrayList<Chicken> chickens = new ArrayList<Chicken>();
    private ArrayAdapter<Chicken> adapter;
    private ListView searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searches);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchResults = (ListView) findViewById(R.id.searchResults);
        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Chicken chicken = adapter.getItem(i);

                Toast.makeText(getApplicationContext(), "Clicked on " + chicken.getName(), Toast.LENGTH_SHORT).show();
                // TODO: make the intent, start the viewing activity
            }
        });

        handleIntent(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChickBidsApplication.getChickenController().popupNotificationToast(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    public void handleIntent(Intent intent) {
        // Get the intent, verify the action and get the query
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    public void doMySearch(String query) {
        chickens = ChickBidsApplication.getSearchController().searchByKeyword(query);
        if (chickens.size() == 0) {
            Toast.makeText(getApplicationContext(), "No Results", Toast.LENGTH_SHORT).show();
        }

        adapter = new ChickenAdapter(this, chickens);
        searchResults.setAdapter(adapter);
    }
}