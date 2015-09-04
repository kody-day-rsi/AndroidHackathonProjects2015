package rsi.com.applicationstub.view;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import rsi.com.applicationstub.BaseDialogFragment;
import rsi.com.applicationstub.R;
import rsi.com.applicationstub.event.SearchJobEvent;

public class DatePickerFragment extends BaseDialogFragment {

    public Date date;

    @Bind(R.id.datePicker)
    DatePicker datePicker;
    @Bind(R.id.timePicker)
    TimePicker timePicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_date_picker, null);
        ButterKnife.bind(this, view);

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int dayOfMonth = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth();
                        int year = datePicker.getYear();
                        int hour, minutes;

                        if(Build.VERSION.SDK_INT < 23) {
                            hour = timePicker.getCurrentHour();
                            minutes = timePicker.getCurrentMinute();
                        } else {
                            hour = timePicker.getHour();
                            minutes = timePicker.getMinute();
                        }

                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, dayOfMonth, hour, minutes, 0);
                        date = cal.getTime();
                        mEventBus.post(new SearchJobEvent(date));
                    }
                })
                .create();

        // Create a new instance of DatePickerDialog and return it
        return dialog;
    }

}
