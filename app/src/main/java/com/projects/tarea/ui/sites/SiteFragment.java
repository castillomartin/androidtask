package com.projects.tarea.ui.sites;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
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
import com.projects.tarea.ui.results.ResultOne;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;


public class SiteFragment extends Fragment {

    private SiteViewModel mViewModel;
    private EditText site;
    private Button button;
    private ImageView imageView;
    private  TextView textView;
    private  Bitmap bitmap;
    private  String title;
    private  ProgressBar progressBar2;


    public static SiteFragment newInstance() {
        return new SiteFragment();
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.site_fragment, container, false);

        site  = (EditText) root.findViewById(R.id.editTextTextSite);
        button = (Button) root.findViewById(R.id.button);
        imageView  = (ImageView) root.findViewById(R.id.image);
        progressBar2  = (ProgressBar) root.findViewById(R.id.progressBar2);
        site.setText("https://firebase.google.com/");

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new MyTask().execute(site.getText().toString());

            }
        });


        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SiteViewModel.class);
        // TODO: Use the ViewModel


    }

    public void Alerta(String s){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Confirmación");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }

        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        alertDialog.show();

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

            try {

                Thread.sleep(3000);

                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    Document document = null;

                    try {
                        document = Jsoup.connect(myString).get();
                        Elements links = document.select("a[href]");

                        for (Element link : links) {
                            x+=link.attr("abs:href");
                            x+="~";

                        }



                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                SetBar(false);

            } catch (InterruptedException e) {
                return x;
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

            Intent intent = new Intent(getContext(), ResultOne.class);

            intent.putExtra("EXTRA_MESSAGE", result);
            startActivity(intent);

        }
    }

    public  void SetBar(boolean x){
        if(x){progressBar2.setVisibility(ProgressBar.VISIBLE);}
        else {progressBar2.setVisibility(ProgressBar.INVISIBLE);}
    }




}