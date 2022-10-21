package dev.scaraz.gateway.configuration.utils;

import org.jetbrains.annotations.Nullable;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class ResponseAdapterFactory extends CallAdapter.Factory {

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type type, Annotation[] annotations, Retrofit retrofit) {
        return null;
    }



}
