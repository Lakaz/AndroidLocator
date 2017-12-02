package com.mobiwarez.laki.reactivelocation.observables;

import android.support.annotation.NonNull;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.mobiwarez.laki.reactivelocation.observables.Exceptions.StatusException;

import io.reactivex.SingleEmitter;
import io.reactivex.functions.Function;

/**
 * Created by Laki on 02/12/2017.
 */

class SingleResultCallBack<T extends Result, R> implements ResultCallback<T> {

    private static final Function ID_FUNC = input -> input;

    private final SingleEmitter<R> emitter;
    private final Function<T, R> mapper;

    private SingleResultCallBack(@NonNull SingleEmitter<R> emitter, @NonNull Function<T, R> mapper) {
        this.emitter = emitter;
        this.mapper = mapper;
    }

    static <T extends Result, R> ResultCallback<T> get(@NonNull SingleEmitter<R> emitter, @NonNull Function<T, R> mapper) {
        return new SingleResultCallBack<>(emitter, mapper);
    }

    static <T extends Result> ResultCallback<T> get(@NonNull SingleEmitter<T> emitter) {
        //noinspection unchecked
        return new SingleResultCallBack<>(emitter, ID_FUNC);
    }

    @Override
    public void onResult(@NonNull T result) {
        if (!result.getStatus().isSuccess()) {
            emitter.onError(new StatusException(result));
        } else {
            try {
                emitter.onSuccess(mapper.apply(result));
            } catch (Exception e) {
                emitter.onError(e);
            }
        }
    }

}
