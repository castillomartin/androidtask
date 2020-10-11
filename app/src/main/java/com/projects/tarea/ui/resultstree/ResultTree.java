package com.projects.tarea.ui.resultstree;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.projects.tarea.R;
import com.projects.tarea.ui.cards.Card;
import com.projects.tarea.ui.cards.CardArrayAdapter;
import com.projects.tarea.ui.tree1.Tree1Fragment;

public class ResultTree extends AppCompatActivity {


    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;
    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_tree);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listView = (ListView) findViewById(R.id.card_listView1);
        textView = (TextView) findViewById(R.id.textView2);
        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));
        cardArrayAdapter = new CardArrayAdapter(this, R.layout.list_item_card);

        Bundle extras = getIntent().getExtras();
        String value1 = extras.getString("EXTRA_MESSAGE");
        int value2 = extras.getInt("TOTAL");

        String[] parts = value1.split("~");
        String [][] subparts = new String[parts.length][];

        for (int i=0;i<parts.length;i++){
            subparts[i] = parts[i].split("'");

            for (int j = 0; j < subparts[i].length; j++) {
                Card card = new Card(String.valueOf(j), subparts[i][j]);
                cardArrayAdapter.add(card);
            }
        }


        listView.setAdapter(cardArrayAdapter);
        String s = "Current Url: "+ subparts[parts.length-1][0]+ "\n\n"+
                "Number of visited Site: " + String.valueOf(parts.length) + "\n\n"+
                "Total of links found: " + String.valueOf(value2) + "\n\n"+
                "Avg number of link in each: "+ String.valueOf(value2/parts.length) + " ";


        textView.setText(s);


    }
}