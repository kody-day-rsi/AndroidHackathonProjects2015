package rsi.com.applicationstub.viewadapters;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import rsi.com.applicationstub.R;
import rsi.com.applicationstub.domain.Job;

public class JobListViewAdapter extends RecyclerView.Adapter<JobListViewAdapter.JobViewHolder> {

    private SortedList<Job> jobs;

    private Comparator<Job> comparator;
    private SortedListCallback callback = new SortedListCallback();

    private SimpleDateFormat mFormat = new SimpleDateFormat("MMMM d, h:mm", Locale.getDefault());

    public JobListViewAdapter(Comparator<Job> comparator) {
        jobs = new SortedList<>(Job.class, callback);
        this.comparator = comparator;
    }

    public void changeComparator(Comparator<Job> newComparator) {
        if (comparator != newComparator) {
            comparator = newComparator;
            SortedList<Job> newList = new SortedList<>(Job.class, new SortedListCallback(), jobs.size());
            jobs.beginBatchedUpdates();
            newList.beginBatchedUpdates();
            int count = jobs.size();
            for (int i = 0; i < count; i++) {
                newList.add(jobs.removeItemAt(jobs.size() - 1));
            }
            jobs.endBatchedUpdates();
            jobs = newList;
            jobs.endBatchedUpdates();
        }
    }

    public static Comparator<Job> DATE_COMPARATOR = new Comparator<Job>() {
        @Override
        public int compare(Job lhs, Job rhs) {
            return lhs.getTimestamp().compareTo(rhs.getTimestamp());
        }
    };

    public static Comparator<Job> POSITION_COMPARATOR = new Comparator<Job>() {
        @Override
        public int compare(Job lhs, Job rhs) {
            String left = lhs.getPosition(), right = rhs.getPosition();
            return left.compareTo(right);
        }
    };

    public static Comparator<Job> LOCATION_COMPARATOR = new Comparator<Job>() {
        @Override
        public int compare(Job lhs, Job rhs) {
            String left = lhs.getLocation(), right = rhs.getLocation();
            return left.compareTo(right);
        }
    };

    private class SortedListCallback extends SortedList.Callback<Job> {

        @Override
        public int compare(Job job1, Job job2) {
            return comparator.compare(job1, job2);
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(Job oldJob, Job newJob) {
            // return whether the items' visual representations are the same or not.
            return oldJob.equals(newJob);
        }

        @Override
        public boolean areItemsTheSame(Job job1, Job job2) {
            return areContentsTheSame(job1, job2);
        }
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.job_list_item, viewGroup, false);
        return new JobViewHolder(view, jobs.get(i));
    }

    @Override
    public void onBindViewHolder(JobViewHolder jobViewHolder, int i) {
        Job job = jobs.get(i);
        jobViewHolder.position.setText(job.getPosition());
        jobViewHolder.location.setText(job.getLocation());
        jobViewHolder.date.setText(mFormat.format(job.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return jobs != null ? jobs.size() : 0;
    }

    public class JobViewHolder extends RecyclerView.ViewHolder {
        TextView position, location, date;

        public JobViewHolder(View itemView, Job job) {
            super(itemView);

            position = (TextView) itemView.findViewById(R.id.position);
            location = (TextView) itemView.findViewById(R.id.location);
            date = (TextView) itemView.findViewById(R.id.date);

            if (job != null) {
                position.setText(job.getPosition());
                location.setText(job.getLocation());
                date.setText(mFormat.format(job.getTimestamp()));
            }
        }
    }

    public void refreshJobs(List<Job> newJobList) {
        jobs.beginBatchedUpdates();
        ArrayList<Job> removedJobs = new ArrayList<>();
        for(int i = 0; i < jobs.size(); i++) {
            if(!newJobList.contains(jobs.get(i))){
                removedJobs.add(jobs.get(i));
            }
        }
        for(int i = 0; i < removedJobs.size(); i++) {
            jobs.remove(removedJobs.get(i));
        }
        jobs.addAll(newJobList);
        jobs.endBatchedUpdates();
    }
}
