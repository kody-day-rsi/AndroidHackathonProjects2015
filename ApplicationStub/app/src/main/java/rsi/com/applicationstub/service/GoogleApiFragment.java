package rsi.com.applicationstub.service;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import rsi.com.applicationstub.BaseFragment;

public class GoogleApiFragment extends BaseFragment {

    private GoogleApiClient client;

    public static final String FRAGMENT_TAG = "AAAAAAAAAGGGGGGGGGGGDDDDDDDDDD";
    private static final int GOOGLE_API_CLIENT_ID = 0;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        if(ConnectionResult.SUCCESS == GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext())){
            client = new GoogleApiClient.Builder(getContext())
                    .addApi(Places.GEO_DATA_API)
                    .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            // cry
                        }
                    })
                    .build();
        }
    }

    public GoogleApiClient getClient() {
        return client;
    }
}
