package com.mobiwarez.laki.reactivelocation.observables;

import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.TimeUnit;

import io.reactivex.SingleEmitter;

/**
 * Created by Laki on 02/12/2017.
 */

class LocationFlushSingleOnSubscribe extends RxLocationSingleOnSubscribe<Status> {

    LocationFlushSingleOnSubscribe(@NonNull RxLocation rxLocation, Long timeout, TimeUnit timeUnit) {
        super(rxLocation, timeout, timeUnit);
    }

    @Override
    protected void onGoogleApiClientReady(GoogleApiClient apiClient, SingleEmitter<Status> emitter) {
        setupLocationPendingResult(
                LocationServices.FusedLocationApi.flushLocations(apiClient),
                SingleResultCallBack.get(emitter)
        );
    }

}
