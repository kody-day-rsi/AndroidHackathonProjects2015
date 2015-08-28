package rsi.com.applicationstub;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rsi.com.applicationstub.domain.Job;
import rsi.com.applicationstub.view.AddJobDialog;
import rsi.com.applicationstub.view.JobListViewAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String JOB_LIST_KEY = "JOBS";

    private TestService mService;
    private Callback<List<Job>> mServiceCallback;
    private Callback<List<Job>> mRefreshCallback;

    private JobListViewAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        initializeService();
        mAdapter = new JobListViewAdapter();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        FloatingActionButton addJobButton = (FloatingActionButton) findViewById(R.id.addJobButton);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.jobListView);

        mServiceCallback = new Callback<List<Job>>() {
            @Override
            public void success(List<Job> jobs, Response response) {
                mAdapter.setJobs(jobs);
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };

        mRefreshCallback = new Callback<List<Job>>() {
            @Override
            public void success(List<Job> jobs, Response response) {
                mAdapter.setJobs(jobs);
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mService.getJobs(mRefreshCallback);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int state) {
                Log.i(TAG, "Listener triggered");
                if (state == RecyclerView.SCROLL_STATE_IDLE) {
                    mSwipeRefreshLayout.setEnabled(
                            ((LinearLayoutManager) recyclerView.getLayoutManager())
                                    .findFirstCompletelyVisibleItemPosition() == 0
                    );
                }
            }
        });

        addJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddJobDialog.newInstance(
                        new AddJobDialog.AddJobDialogListener() {
                            @Override
                            public void onDialogConfirm(Job job) {
                                mService.createJob(job, mServiceCallback);
                            }
                        }
                ).show(getSupportFragmentManager(), "addJob");
            }
        });

        if (state != null) {
            ArrayList<Job> jobs = state.getParcelableArrayList(JOB_LIST_KEY);
            mAdapter.setJobs(jobs);
        } else {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mService.getJobs(mRefreshCallback);
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        state.putParcelableArrayList(JOB_LIST_KEY, new ArrayList<>(mAdapter.getJobs()));
    }

    private void initializeService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getResources().getString(R.string.endpoint))
                .build();
        mService = restAdapter.create(TestService.class);
    }
}
