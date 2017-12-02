package com.mobiwarez.laki.reactivelocation.observables.Exceptions

import com.google.android.gms.common.api.Result
import com.google.android.gms.common.api.Status
import java.lang.RuntimeException

/**
 * Created by Laki on 02/12/2017.
 */

class StatusException(val result: Result) : RuntimeException(result.status.toString()) {

    val status: Status
        get() = result.status

}
