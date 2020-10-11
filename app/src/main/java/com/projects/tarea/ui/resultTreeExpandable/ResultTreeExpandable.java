package com.projects.tarea.ui.resultTreeExpandable;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.SparseArray;
import android.view.View;
import android.widget.ExpandableListView;

import com.projects.tarea.R;
import com.projects.tarea.ui.cards.Card;

public class ResultTreeExpandable extends AppCompatActivity {

    SparseArray<Group> groups = new SparseArray<Group>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_tree_expandable);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle extras = getIntent().getExtras();
        String value1 = extras.getString("EXTRA_MESSAGE");

        String[] parts = value1.split("~");
        String [][] subparts = new String[parts.length][];

        int z=0,zz=0;
        for (int i=0;i<parts.length;i++){
            subparts[i] = parts[i].split("'");

            if(zz==subparts[z].length){
                z++;zz=0;
            }
            Group group = new Group(subparts[z][zz++]);
            for (int j = 0; j < subparts[i].length; j++) {
                group.children.add(subparts[i][j]);
            }
            groups.append(i, group);

        }


        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listViewx);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
                groups);
        listView.setAdapter(adapter);

    }


}