package com.projects.tarea.ui.results;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.projects.tarea.R;
import com.projects.tarea.ui.cards.Card;
import com.projects.tarea.ui.cards.CardArrayAdapter;
import com.projects.tarea.ui.sites.SiteFragment;

public class ResultOne extends AppCompatActivity {



    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_one);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listView = (ListView) findViewById(R.id.card_listView);
        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));
        cardArrayAdapter = new CardArrayAdapter(this, R.layout.list_item_card);

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        String value1 = extras.getString("EXTRA_MESSAGE");

       String[] parts = value1.split("~");


        for (int i = 0; i < parts.length; i++) {
            Card card = new Card(parts[i], parts[i]);
            cardArrayAdapter.add(card);
        }
        listView.setAdapter(cardArrayAdapter);



    }
}