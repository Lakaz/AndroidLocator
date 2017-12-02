package com.mobiwarez.laki.reactivelocation.observables.Exceptions

import java.lang.RuntimeException

/**
 * Created by Laki on 02/12/2017.
 */

class GoogleApiConnectionSuspendedException(val errorCause: Int) : RuntimeException()
