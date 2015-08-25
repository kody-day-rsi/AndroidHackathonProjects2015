package rsi.com.applicationstub.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rsi.com.applicationstub.R;
import rsi.com.applicationstub.domain.Job;

public class JobListViewAdapter extends RecyclerView.Adapter<JobListViewAdapter.JobViewHolder> {

    private List<Job> jobs;

    public JobListViewAdapter(List<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.job_list_item, viewGroup, false);
        JobViewHolder viewHolder = new JobViewHolder(view, jobs.get(i));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(JobViewHolder jobViewHolder, int i) {
        Job job = jobs.get(i);
        jobViewHolder.position.setText(job.getPosition());
        jobViewHolder.location.setText(job.getLocation());
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class JobViewHolder extends RecyclerView.ViewHolder {
        TextView position, location;

        public JobViewHolder(View itemView, Job job) {
            super(itemView);

            position = (TextView) itemView.findViewById(R.id.position);
            location = (TextView) itemView.findViewById(R.id.location);

            if (job != null) {
                position.setText(job.getPosition());
                location.setText(job.getLocation());
            }
        }
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }


}
