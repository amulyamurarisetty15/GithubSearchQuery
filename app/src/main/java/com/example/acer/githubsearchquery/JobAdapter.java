package com.example.acer.githubsearchquery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewholder> {
    Context context;
    ArrayList<JobModel> jobModelArrayList;

    public JobAdapter(JobActivity jobActivity, ArrayList<JobModel> jobModels) {
        this.context=jobActivity;
        this.jobModelArrayList=jobModels;
    }

    @NonNull
    @Override
    public JobAdapter.JobViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v=LayoutInflater.from(context).inflate(R.layout.job_design,viewGroup,false);
        return new JobViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdapter.JobViewholder jobViewholder, int i) {

        JobModel model=jobModelArrayList.get(i);
        jobViewholder.jo_title.setText(model.getJtitle());
        jobViewholder.jo_loc.setText(model.getJlocation());
        jobViewholder.jo_url.setText(model.getJurl());


    }

    @Override
    public int getItemCount() {
        return jobModelArrayList.size();
    }

    public class JobViewholder extends RecyclerView.ViewHolder {
        TextView jo_title,jo_loc,jo_url;
        public JobViewholder(@NonNull View itemView) {
            super(itemView);
            jo_title=itemView.findViewById(R.id.jobtitle);
            jo_loc=itemView.findViewById(R.id.joblocation);
            jo_url=itemView.findViewById(R.id.joburl);

        }
    }
}
