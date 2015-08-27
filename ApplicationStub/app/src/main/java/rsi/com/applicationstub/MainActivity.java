package rsi.com.applicationstub;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rsi.com.applicationstub.domain.Job;
import rsi.com.applicationstub.view.AddJobDialog;
import rsi.com.applicationstub.view.JobListViewAdapter;

public class MainActivity extends AppCompatActivity implements AddJobDialog.AddJobDialogListener{

    private static String TAG = MainActivity.class.getSimpleName();

    private TestService mService;

    private JobListViewAdapter mAdapter;

    private static final String JOB_LIST_KEY = "JOBS";

    private EditText positionEditText, locationEditText;

    private Callback<List<Job>> mServiceCallback;

    private FrameLayout mProgessBar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setProgress();
        setContentView(R.layout.activity_main);
        initializeService();

        mProgessBar = (FrameLayout) findViewById(R.id.progressBar);
        positionEditText = (EditText) findViewById(R.id.position);
        locationEditText = (EditText) findViewById(R.id.location);
        FloatingActionButton addJobButton = (FloatingActionButton) findViewById(R.id.addJobButton);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.jobListView);

        mAdapter = new JobListViewAdapter();

        mServiceCallback = new Callback<List<Job>>() {
            @Override public void success(List<Job> jobs, Response response) {
                mAdapter.setJobs(jobs);
                mAdapter.notifyDataSetChanged();
                mProgessBar.setVisibility(View.INVISIBLE);
            }

            @Override public void failure(RetrofitError error) {
                // Do nothing
                Log.i(TAG, "It broke");
            }
        };

        if(savedInstanceState != null) {
            ArrayList<Job> jobs = savedInstanceState.getParcelableArrayList(JOB_LIST_KEY);
            mAdapter.setJobs(jobs);
        } else {
            mProgessBar.setVisibility(View.VISIBLE);
            mService.getJobs(mServiceCallback);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);


        addJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddJobDialog().show(getSupportFragmentManager(), "addJob");
            }
        });
    }

    @Override public void onSaveInstanceState(Bundle state) {
        state.putParcelableArrayList(JOB_LIST_KEY, new ArrayList<>(mAdapter.getJobs()));
    }

    private void initializeService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getResources().getString(R.string.endpoint))
                .build();
        mService = restAdapter.create(TestService.class);
    }

    @Override
    public void onDialogConfirm(Job job) {
        mProgessBar.setVisibility(View.VISIBLE);
        mService.createJob(job, mServiceCallback);
    }

    private PopupWindow addDim() {
        LayoutInflater inflater = getLayoutInflater();
        return null;
    }
}
