package com.example.acer.githubsearchquery;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class JobActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<String> {

    RecyclerView job_activity_recyclerview;
    ProgressBar job_progress_bar;
    ArrayList<JobModel> jobModels;
   public static final int Loader_id=2;
    String job_url="https://jobs.github.com/positions.json?description=" ;
            String locationurl="&location=new+york";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        job_activity_recyclerview = findViewById(R.id.job_recycler_view);
        job_progress_bar = findViewById(R.id.progress_bar);
        jobModels = new ArrayList<>();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
          getLoaderManager().initLoader(Loader_id,null,this);
        }
        else {
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(JobActivity.this);
            alertdialog.setTitle("Alert message");
            alertdialog.setMessage("No internet");
            alertdialog.setCancelable(true);
            alertdialog.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertdialog.create();
            alert.show();
        }
        job_activity_recyclerview.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public android.content.Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new android.content.AsyncTaskLoader<String>(this) {
            @Override
            public String loadInBackground() {
                try {
                    String s=getIntent().getStringExtra("job");
                    URL url=new URL(job_url+s+locationurl);
                    HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    Scanner scanner=new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner.hasNext()){
                        return scanner.next();
                    }
                    else {
                        return null;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                jobModels.clear();
                job_progress_bar.setVisibility(View.VISIBLE);
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(android.content.Loader<String> loader, String s) {

        job_progress_bar.setVisibility(View.INVISIBLE);
        if (s!=null){
            try {
                JSONArray array=new JSONArray(s);
                if(array.length()!=0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String job_title = object.getString("title");
                        String job_location = object.getString("location");
                        String job_url = object.getString("how_to_apply");
                        jobModels.add(new JobModel(job_title, job_location, job_url));

                    }
                }

                else {
                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(JobActivity.this);
                    alertdialog.setTitle("Alert message");
                    alertdialog.setMessage("No data found");
                    alertdialog.setCancelable(true);
                    alertdialog.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = alertdialog.create();
                    alert.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            job_activity_recyclerview.setAdapter(new JobAdapter(this,jobModels));

        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<String> loader) {

    }
}
