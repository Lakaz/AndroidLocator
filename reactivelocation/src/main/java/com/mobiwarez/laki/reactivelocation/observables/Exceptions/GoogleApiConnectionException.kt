package com.mobiwarez.laki.reactivelocation.observables.Exceptions

import com.google.android.gms.common.ConnectionResult
import java.lang.RuntimeException

/**
 * Created by Laki on 02/12/2017.
 */

class GoogleApiConnectionException(detailMessage: String, val connectionResult: ConnectionResult?) : RuntimeException(detailMessage) {

    fun wasResolutionUnsuccessful(): Boolean {
        return connectionResult?.hasResolution() ?: false
    }

}
