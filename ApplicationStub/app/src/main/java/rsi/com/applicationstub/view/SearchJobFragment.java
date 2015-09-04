package rsi.com.applicationstub.view;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;
import java.util.logging.SimpleFormatter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rsi.com.applicationstub.BaseFragment;
import rsi.com.applicationstub.R;
import rsi.com.applicationstub.event.SearchJobEvent;

public class SearchJobFragment extends BaseFragment {

    @Bind(R.id.searchView)
    SearchView mSearchView;
    @Bind(R.id.searchCriteriaSpinner)
    Spinner mSearchCriteriaSpinner;

    @OnClick(R.id.dateButton)
    void showDatePickerDialog() {
        new DatePickerFragment().show(getChildFragmentManager(), "datePicker");

    }

    public SearchJobFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_job, container, false);
        ButterKnife.bind(this, view);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.search_criteria_choices, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSearchCriteriaSpinner.setAdapter(adapter);
        return view;
    }

    @Subscribe
    public void search(SearchJobEvent event) {
        Log.i("SEARCH", Integer.toString(event.criteria) + " " + SimpleDateFormat.getDateTimeInstance().format(event.date));

        getChildFragmentManager().beginTransaction().add(R.id.list_frame, JobListFragment.newInstance(event), "AAA").commit();
    }
}
