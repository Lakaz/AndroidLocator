package com.mobiwarez.laki.reactivelocation.observables;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.mobiwarez.laki.reactivelocation.observables.Exceptions.GoogleApiConnectionException;
import com.mobiwarez.laki.reactivelocation.observables.Exceptions.GoogleApiConnectionSuspendedException;

import java.util.concurrent.TimeUnit;

import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by Laki on 02/12/2017.
 */

abstract class RxLocationSingleOnSubscribe<T> extends RxLocationBaseOnSubscribe<T> implements SingleOnSubscribe<T> {

    protected RxLocationSingleOnSubscribe(@NonNull RxLocation rxLocation, Long timeout, TimeUnit timeUnit) {
        super(rxLocation, timeout, timeUnit);
    }

    protected RxLocationSingleOnSubscribe(@NonNull Context ctx, @NonNull Api<? extends Api.ApiOptions.NotRequiredOptions>[] services, Scope[] scopes) {
        super(ctx, services, scopes);
    }

    @Override
    public final void subscribe(SingleEmitter<T> emitter) throws Exception {
        final GoogleApiClient apiClient = createApiClient(new ApiClientConnectionCallbacks(emitter));

        try {
            apiClient.connect();
        } catch (Throwable ex) {
            emitter.onError(ex);
        }

        emitter.setCancellable(() -> {
            if (apiClient.isConnected()) {
                onUnsubscribed(apiClient);
            }

            apiClient.disconnect();
        });
    }

    protected abstract void onGoogleApiClientReady(GoogleApiClient apiClient, SingleEmitter<T> emitter);

    protected class ApiClientConnectionCallbacks extends RxLocationBaseOnSubscribe.ApiClientConnectionCallbacks {

        final protected SingleEmitter<T> emitter;

        private GoogleApiClient apiClient;

        private ApiClientConnectionCallbacks(SingleEmitter<T> emitter) {
            this.emitter = emitter;
        }

        @Override
        public void onConnected(Bundle bundle) {
            try {
                onGoogleApiClientReady(apiClient, emitter);
            } catch (Throwable ex) {
                emitter.onError(ex);
            }
        }

        @Override
        public void onConnectionSuspended(int cause) {
            emitter.onError(new GoogleApiConnectionSuspendedException(cause));
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            emitter.onError(new GoogleApiConnectionException("Error connecting to GoogleApiClient.", connectionResult));
        }

        public void setClient(GoogleApiClient client) {
            this.apiClient = client;
        }
    }

}
