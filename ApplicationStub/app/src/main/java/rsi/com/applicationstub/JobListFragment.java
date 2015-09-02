package rsi.com.applicationstub;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rsi.com.applicationstub.domain.Job;
import rsi.com.applicationstub.event.AddJobEvent;
import rsi.com.applicationstub.event.ChangeJobSortEvent;
import rsi.com.applicationstub.event.FABEvent;
import rsi.com.applicationstub.event.GetJobListServiceEvent;
import rsi.com.applicationstub.view.AddJobDialog;
import rsi.com.applicationstub.view.JobListViewAdapter;

public class JobListFragment extends BaseFragment {

    @Inject
    JobService mService;

    @Bind(R.id.jobListView)
    RecyclerView mJobListView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private static final String JOB_LIST_KEY = "AAAAAAAAAAAAAAAAAAAAAAA";

    private JobListViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_job_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_sort:
                new SortJobListDialog().show(getChildFragmentManager(), "sortJob");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Subscribe
    public void onSortChange(ChangeJobSortEvent event) {
        mAdapter.changeComparator(event.comparator);
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });

        mAdapter = new JobListViewAdapter(JobListViewAdapter.DATE_COMPARATOR);
        mJobListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mJobListView.addItemDecoration(new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if(ItemTouchHelper.LEFT == direction) {

                }
            }
        }));
        mJobListView.setAdapter(mAdapter);

        mJobListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int state) {
                if (state == RecyclerView.SCROLL_STATE_IDLE) {
                    mSwipeRefreshLayout.setEnabled(
                            ((LinearLayoutManager) recyclerView.getLayoutManager())
                                    .findFirstCompletelyVisibleItemPosition() == 0
                    );
                }
            }
        });

        if (state != null) {
            ArrayList<Job> jobs = state.getParcelableArrayList(JOB_LIST_KEY);
            mAdapter.refreshJobs(jobs);
        } else {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    doRefresh();
                }
            });
        }
    }

    public void doRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mService.getJobs(new ServiceCallback());
    }

    @Subscribe
    public void onFABClicked(FABEvent event) {
        new AddJobDialog().show(getChildFragmentManager(), "addJob");
    }

    @Subscribe
    public void addJob(AddJobEvent event) {
        mSwipeRefreshLayout.setRefreshing(true);
        event.job.setTimestamp(new Date());
        mService.createJob(event.job, new ServiceCallback());
    }

    @Subscribe
    public void onServiceRefreshSuccess(GetJobListServiceEvent event) {
        if(event.isSuccessful) {
            mAdapter.refreshJobs(event.jobs);
        } else {
            Snackbar
                    .make(mSwipeRefreshLayout, R.string.snackbar_error, Snackbar.LENGTH_LONG)
                    .show();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private class ServiceCallback implements Callback<List<Job>> {

        @Override
        public void success(List<Job> jobs, Response response) {
            mEventBus.post(new GetJobListServiceEvent(true, jobs));
        }

        @Override
        public void failure(RetrofitError error) {
            mEventBus.post(new GetJobListServiceEvent(false, null));
        }
    }


}
