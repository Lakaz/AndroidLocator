package com.mobiwarez.laki.reactivelocation.observables;

import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.TimeUnit;

import io.reactivex.FlowableEmitter;

/**
 * Created by Laki on 02/12/2017.
 */

public class LocationUpdatesFlowableOnSubscribe extends RxLocationFlowableOnSubscribe<Location>{

    final LocationRequest locationRequest;
    final Looper looper;
    RxLocationListener locationListener;

    protected LocationUpdatesFlowableOnSubscribe(@NonNull RxLocation rxLocation, LocationRequest locationRequest, Looper looper, Long timeout, TimeUnit timeUnit) {
        super(rxLocation, timeout, timeUnit);
        this.locationRequest = locationRequest;
        this.looper = looper;
    }

    @Override
    protected void onGoogleApiClientReady(GoogleApiClient apiClient, FlowableEmitter<Location> emitter) {
        locationListener = new RxLocationListener(emitter);

        //noinspection MissingPermission
        setupLocationPendingResult(
                LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, locationRequest, locationListener, looper),
                new StatusErrorResultCallBack(emitter)
        );
    }

    @Override
    protected void onUnsubscribed(GoogleApiClient apiClient) {
        LocationServices.FusedLocationApi.removeLocationUpdates(apiClient, locationListener);
        locationListener.onUnsubscribed();
        locationListener = null;
    }

    static class RxLocationListener implements LocationListener {

        private FlowableEmitter<Location> emitter;

        RxLocationListener(FlowableEmitter<Location> emitter) {
            this.emitter = emitter;
        }

        void onUnsubscribed() {
            emitter = null;
        }

        @Override
        public void onLocationChanged(Location location) {
            if(emitter != null) { emitter.onNext(location); }
        }
    }

}
