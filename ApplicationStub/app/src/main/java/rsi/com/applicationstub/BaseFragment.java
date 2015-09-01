package rsi.com.applicationstub;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.squareup.otto.Bus;

import javax.inject.Inject;

public class BaseFragment extends Fragment {

    @Inject
    Bus mEventBus;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).getObjectGraph().inject(this);
        mEventBus.register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mEventBus.unregister(this);
    }
}
