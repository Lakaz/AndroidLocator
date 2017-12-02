package com.mobiwarez.laki.reactivelocation.observables

import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.mobiwarez.laki.reactivelocation.observables.Exceptions.StatusException

import io.reactivex.FlowableEmitter

/**
 * Created by Laki on 02/12/2017.
 */

internal class StatusErrorResultCallBack(private val emitter: FlowableEmitter<*>) : ResultCallback<Status> {

    override fun onResult(status: Status) {
        if (!status.isSuccess) {
            emitter.onError(StatusException(status))
        }
    }

}
