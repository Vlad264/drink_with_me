package ru.nsu.android.drinkwithme.common.useCaseEngine;

public class UseCaseHandler {

    private static UseCaseHandler useCaseHandler;
    private final IUseCaseScheduler useCaseScheduler;

    public static UseCaseHandler getInstance() {
        if (useCaseHandler == null) {
            synchronized (UseCaseHandler.class) {
                if (useCaseHandler == null) {
                    useCaseHandler = new UseCaseHandler(new UseCaseThreadPoolScheduler());
                }
            }
        }
        return useCaseHandler;
    }

    private UseCaseHandler(IUseCaseScheduler useCaseScheduler) {
        this.useCaseScheduler = useCaseScheduler;
    }

    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValues> void execute(
            final UseCase<T, R> useCase, T requestValues, IUseCaseCallback<R> callback) {
        useCase.setRequestValues(requestValues);
        useCase.setUseCaseCallback(new UseCaseCallbackWrapper<>(callback, this));

        useCaseScheduler.execute(new Runnable() {
            @Override
            public void run() {
                useCase.run();
            }
        });
    }

    <T extends UseCase.ResponseValues> void notifyResponse(T response, IUseCaseCallback<T> useCaseCallback) {
        useCaseScheduler.onResponse(response, useCaseCallback);
    }

    <T extends UseCase.ResponseValues> void notifyError(IUseCaseCallback<T> useCaseCallback) {
        useCaseScheduler.onError(useCaseCallback);
    }

}
