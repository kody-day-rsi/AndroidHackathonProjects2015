package rsi.com.applicationstub.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import rsi.com.applicationstub.BaseDialogFragment;
import rsi.com.applicationstub.R;
import rsi.com.applicationstub.event.ChangeJobSortEvent;
import rsi.com.applicationstub.viewadapters.JobListViewAdapter;

public class SortJobListDialog extends BaseDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_change_sort)
                .setItems(R.array.dialog_sort_jobs_choices, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mEventBus.post(new ChangeJobSortEvent(JobListViewAdapter.POSITION_COMPARATOR));
                                break;
                            case 1:
                                mEventBus.post(new ChangeJobSortEvent(JobListViewAdapter.LOCATION_COMPARATOR));
                                break;
                            case 2:
                                mEventBus.post(new ChangeJobSortEvent(JobListViewAdapter.DATE_COMPARATOR));
                                break;
                        }
                    }
                });

        return builder.create();
    }
}
