package com.projects.tarea.ui.tree1;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.projects.tarea.R;
import com.projects.tarea.ui.resultTreeExpandable.ResultTreeExpandable;
import com.projects.tarea.ui.results.ResultOne;
import com.projects.tarea.ui.resultstree.ResultTree;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tree1Fragment extends Fragment {

    private Tree1ViewModel mViewModel;
    private EditText site;
    private EditText N;
    private EditText perpage;
    private Button button;
    private Button button1;
    private Button button2;
    private boolean tipo;
    private ProgressBar progressBar;
    private int globalN;
    private int counter;
    private  List<String> Visit;
    public static Tree1Fragment newInstance() {
        return new Tree1Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.tree1_fragment, container, false);

        site  = (EditText) root.findViewById(R.id.editTextTextTree);
        N  = (EditText) root.findViewById(R.id.editTextTextNT);
        perpage  = (EditText) root.findViewById(R.id.editTextTextPerPage);
        button = (Button) root.findViewById(R.id.button1);
        button1 = (Button) root.findViewById(R.id.button2);
        button2 = (Button) root.findViewById(R.id.button3);
        progressBar  = (ProgressBar) root.findViewById(R.id.progressBar3);
        site.setText("https://firebase.google.com/");
        Visit = new ArrayList<String>();
        counter = 0;


        //Action for only one page without depth
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (site.getText().toString().trim().equalsIgnoreCase("")) {
                    site.setError("This field can not be blank");
                }
                else if (perpage.getText().toString().trim().equalsIgnoreCase(""))
                    perpage.setError("This field can not be blank");
                else {

                    if (N.getText().toString().trim().equalsIgnoreCase("")) {
                        SetGlobalN(4000000);
                    }
                    else{
                        globalN = GetN();
                    }
                    Visit.clear();
                    SetTipo(true);
                    new Tree1Fragment.MyTask().execute(site.getText().toString());
                }

            }
        });

        //Action Deep search
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (site.getText().toString().trim().equalsIgnoreCase("")) {
                    site.setError("This field can not be blank");
                }
                else if (perpage.getText().toString().trim().equalsIgnoreCase(""))
                    perpage.setError("This field can not be blank");
                else {

                    if (N.getText().toString().trim().equalsIgnoreCase("")) {
                        SetGlobalN(4000000);
                    } else {
                        globalN = GetN();
                    }

                    //Clear the visited list
                    Visit.clear();
                    SetTipo(false);
                    new Tree1Fragment.MyTask().execute(site.getText().toString(), "1");
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DecreaseGlobalN(GetGlobalN());

            }
        });



        return root;



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(Tree1ViewModel.class);

    }


    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            SetBar(true);

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            String myString = params[0];
            String x =  "";
            String results = "";
            try {

                Thread.sleep(3000);

                x  =  myString+"'"+GetmyString(myString);
               results = x;
               String[] parts = x.split("'");
               if(parts.length>GetGlobalN())
               {
                   SetBar(false);
                   return results;
               }
               else{
                   String auxparts =  "";

                   //x+="$";
                   results = x+"~";
                   DecreaseGlobalN(parts.length);
                   int y=1;
                   while(GetGlobalN()>1){
                       auxparts = GetmyString(parts[y++]);
                       results += auxparts;
                       results += "~";
                       x  +=  auxparts;
                       parts = x.split("'");
                       DecreaseGlobalN(parts.length);
                   }
               }
                SetBar(false);

            } catch (InterruptedException e) {
                return results;
            }

            return results;
        }

        //Parser HTML to JAVA
        private String GetmyString(String myString) {


            String x="";
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);

                Document document = null;
                AddVisited(myString);
                try {

                    int w = 0;
                    document = Jsoup.connect(myString).get();
                    Elements links = document.select("a[href]");
                    UpdateCounter(links.size());
                    String aux1;
                    for (Element link : links) {
                        aux1= link.attr("abs:href");
                        if(!isVisited(aux1)){
                            x+=aux1;
                            x+="'";
                            w++;
                            AddVisited(aux1);
                        }
                        if(w>GetPerPage())break;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return x;
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Intent intent;
            if(GetTipo())
                intent= new Intent(getContext(), ResultTree.class);
            else
                intent= new Intent(getContext(), ResultTreeExpandable.class);

            intent.putExtra("EXTRA_MESSAGE", result);
            intent.putExtra("TOTAL", GetCounter());
            startActivity(intent);

        }
    }

    public  void SetBar(boolean x){
        if(x){progressBar.setVisibility(ProgressBar.VISIBLE);}
        else {progressBar.setVisibility(ProgressBar.INVISIBLE);}
    }
    public int GetN(){
        return Integer.parseInt(N.getText().toString());
    }
    public int GetPerPage(){
        return Integer.parseInt(perpage.getText().toString());
    }
    public int GetGlobalN(){
        return globalN;
    }
    public void  DecreaseGlobalN(int x){
        globalN-=x;
    }
    public void  SetGlobalN(int x){
        globalN=x;
    }
    public void AddVisited(String x){
        if(!isVisited(x))
        Visit.add(x);
    }
    public boolean isVisited(String x){return Visit.indexOf(x)!=-1;}
    public boolean GetTipo(){return tipo;}
    public void SetTipo(boolean x){tipo=x;}
    public void UpdateCounter(int x){counter+=x;}
    public int GetCounter(){return counter;}
}