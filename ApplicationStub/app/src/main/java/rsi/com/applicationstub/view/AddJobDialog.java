package rsi.com.applicationstub.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import butterknife.Bind;
import butterknife.ButterKnife;
import rsi.com.applicationstub.BaseDialogFragment;
import rsi.com.applicationstub.service.GoogleApiFragment;
import rsi.com.applicationstub.viewadapters.PlaceArrayAdapter;
import rsi.com.applicationstub.R;
import rsi.com.applicationstub.domain.Job;
import rsi.com.applicationstub.event.AddJobEvent;

public class AddJobDialog extends BaseDialogFragment {


    @Bind(R.id.autoCompleteLocation)
    AutoCompleteTextView mAutoCompleteLocation;
    @Bind(R.id.position)
    EditText mPositionText;

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    PlaceArrayAdapter mAdapter;

    GoogleApiFragment mClientFragment;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_job, null);
        ButterKnife.bind(this, view);
        mAdapter = new PlaceArrayAdapter(getContext(), android.R.layout.simple_list_item_1, BOUNDS_MOUNTAIN_VIEW, null);
        mAutoCompleteLocation.setAdapter(mAdapter);

        mClientFragment = (GoogleApiFragment) getChildFragmentManager().findFragmentByTag(GoogleApiFragment.FRAGMENT_TAG);
        if (mClientFragment == null) {
            mClientFragment = new GoogleApiFragment();
            getChildFragmentManager().beginTransaction().add(mClientFragment, GoogleApiFragment.FRAGMENT_TAG).commit();
        }

        builder.setTitle(R.string.dialog_add_job_title)
                .setPositiveButton(R.string.dialog_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Job job = new Job(mPositionText.getText().toString(), mAutoCompleteLocation.getText().toString());
                        mEventBus.post(new AddJobEvent(job));
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, null)
                .setView(view);

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.setGoogleApiClient(mClientFragment.getClient());
    }
}
