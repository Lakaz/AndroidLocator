package com.mobiwarez.laki.reactivelocation.observables;

import android.content.Context;
import android.support.annotation.NonNull;

//import com.google.android.gms.location.ActivityRecognition;

import java.util.concurrent.TimeUnit;

/**
 * Created by Laki on 02/12/2017.
 */

public class RxLocation {

    final Context ctx;
    //private final ActivityRecognition activityRecognition = new ActivityRecognition(this);
    private final FusedLocation fusedLocation = new FusedLocation(this);
    //private final Geocoding geocoding;
    //private final Geofencing geofencing = new Geofencing(this);
    //private final LocationSettings locationSettings = new LocationSettings(this);
    Long timeoutTime = null;
    TimeUnit timeoutUnit = null;


    public RxLocation(@NonNull Context ctx) {
        this.ctx = ctx.getApplicationContext();
        //this.geocoding = new Geocoding(ctx.getApplicationContext());
    }


    /* Set a default timeout for all requests to the Location APIs made in the lib.
 * When a timeout occurs, onError() is called with a StatusException.
 */
    public void setDefaultTimeout(long time, @NonNull TimeUnit timeUnit) {
        if (timeUnit != null) {
            timeoutTime = time;
            timeoutUnit = timeUnit;
        } else {
            throw new IllegalArgumentException("timeUnit parameter must not be null");
        }
    }

    /* Reset the default timeout.
     */
    public void resetDefaultTimeout() {
        timeoutTime = null;
        timeoutUnit = null;
    }


    //public ActivityRecognition activity() {
      //  return activityRecognition;
    //}

    //public Geocoding geocoding() {
    //    return geocoding;
    //}

    //public Geofencing geofencing() {
      //  return geofencing;
    //}

    public FusedLocation location() {
        return fusedLocation;
    }

    //public LocationSettings settings() {
        //return locationSettings;
    //}


}
