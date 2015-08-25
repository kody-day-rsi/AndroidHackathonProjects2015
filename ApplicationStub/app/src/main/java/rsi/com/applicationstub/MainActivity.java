package rsi.com.applicationstub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rsi.com.applicationstub.domain.Job;
import rsi.com.applicationstub.view.JobListViewAdapter;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private TestService mService;

    private JobListViewAdapter mAdapter;

    private static final String JOB_LIST_KEY = "JOBS";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeService();
        List<Job> jobs;
        if(savedInstanceState != null) {
            jobs = savedInstanceState.getParcelableArrayList(JOB_LIST_KEY);
        } else {
            jobs = new ArrayList<>();
        }
        mAdapter = new JobListViewAdapter(jobs);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.jobListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        Button addJobButton = (Button) findViewById(R.id.addJobButton);

        final Callback<List<Job>> createJobCallback = new Callback<List<Job>>() {
            @Override public void success(List<Job> jobs, Response response) {
                mAdapter.setJobs(jobs);
                mAdapter.notifyDataSetChanged();
            }

            @Override public void failure(RetrofitError error) {
                // Do nothing
            }
        };
        addJobButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Job job = new Job("Position " + Integer.toString((int) (Math.random() * 20)), "Enterprise Mill");
                mService.createJob(job, createJobCallback);
            }
        });

    }

    @Override public void onSaveInstanceState(Bundle state) {
        state.putParcelableArrayList(JOB_LIST_KEY, new ArrayList<>(mAdapter.getJobs()));
    }

    private void initializeService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://10.0.3.2:8080")
                .build();
        mService = restAdapter.create(TestService.class);
    }
}