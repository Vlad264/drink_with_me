package ru.nsu.android.drinkwithme.common.useCaseEngine;

public interface IUseCaseCallback<T> {
    void onSuccess(T response);
    void onError();
}
