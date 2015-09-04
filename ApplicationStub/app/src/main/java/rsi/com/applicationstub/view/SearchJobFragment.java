package rsi.com.applicationstub.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rsi.com.applicationstub.BaseFragment;
import rsi.com.applicationstub.R;

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
}
