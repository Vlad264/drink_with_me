package ru.nsu.android.drinkwithme.common.useCaseEngine;

class UseCaseCallbackWrapper<T extends UseCase.ResponseValues> implements IUseCaseCallback<T> {

    private IUseCaseCallback<T> callback;
    private UseCaseHandler useCaseHandler;

    UseCaseCallbackWrapper(IUseCaseCallback<T> callback, UseCaseHandler useCaseHandler) {
        this.callback = callback;
        this.useCaseHandler = useCaseHandler;
    }

    @Override
    public void onSuccess(T response) {
        useCaseHandler.notifyResponse(response, callback);
    }

    @Override
    public void onError() {
        useCaseHandler.notifyError(callback);
    }
}
