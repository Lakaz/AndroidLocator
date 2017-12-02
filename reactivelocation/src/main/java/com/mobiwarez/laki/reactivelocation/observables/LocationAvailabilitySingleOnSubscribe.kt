package com.mobiwarez.laki.reactivelocation.observables

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationServices

import io.reactivex.SingleEmitter

/**
 * Created by Laki on 02/12/2017.
 */

internal class LocationAvailabilitySingleOnSubscribe(rxLocation: RxLocation) : RxLocationSingleOnSubscribe<Boolean>(rxLocation, null, null) {

    override fun onGoogleApiClientReady(apiClient: GoogleApiClient, emitter: SingleEmitter<Boolean>) {

        val locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(apiClient)

        if (locationAvailability != null) {
            emitter.onSuccess(locationAvailability.isLocationAvailable)
        } else {
            emitter.onSuccess(false)
        }
    }

}
