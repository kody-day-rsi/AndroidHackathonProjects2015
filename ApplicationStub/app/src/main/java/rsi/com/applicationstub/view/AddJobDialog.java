package rsi.com.applicationstub.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import rsi.com.applicationstub.R;
import rsi.com.applicationstub.domain.Job;

public class AddJobDialog extends DialogFragment {

    private AddJobDialogListener mListener;

    private EditText mPositionText, mLocationText;

    public static AddJobDialog newInstance(AddJobDialogListener mListener) {
        AddJobDialog addJobDialog = new AddJobDialog();
        addJobDialog.mListener = mListener;
        return addJobDialog;
    }

    public interface AddJobDialogListener {
        void onDialogConfirm(Job job);
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_job, null);
        mPositionText = (EditText) view.findViewById(R.id.position);
        mLocationText = (EditText) view.findViewById(R.id.location);

        builder.setTitle(R.string.dialog_add_job_title)
                .setPositiveButton(R.string.dialog_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Job job = new Job(mPositionText.getText().toString(), mLocationText.getText().toString());
                        mListener.onDialogConfirm(job);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, null)
                .setView(view)
                ;

        return builder.create();
    }
}
