package com.crystal.module_base.base.http.retrofit;

import com.crystal.module_base.tools.observable.Observable;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;

public interface Config {
    <T> void parsingCallgetData(@NotNull Call<T> call, @NotNull Class cls, Observable observable);

    <T> void parsingCallgetData(@NotNull Call<T> call, @NotNull Class cls, Observable observable, ParseIntercept parseIntercept);
}
