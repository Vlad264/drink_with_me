package ru.nsu.android.drinkwithme.common.useCaseEngine;

public abstract class UseCase<T extends UseCase.RequestValues, R extends UseCase.ResponseValues> {

    private T requestValues;
    private IUseCaseCallback<R> useCaseCallback;

    void run() {
        executeUseCase(requestValues);
    }

    protected abstract void executeUseCase(T requestValues);

    public T getRequestValues() {
        return requestValues;
    }

    void setRequestValues(T requestValues) {
        this.requestValues = requestValues;
    }

    public IUseCaseCallback<R> getUseCaseCallback() {
        return useCaseCallback;
    }

    void setUseCaseCallback(IUseCaseCallback<R> useCaseCallback) {
        this.useCaseCallback = useCaseCallback;
    }

    public interface RequestValues {}
    public interface ResponseValues {}
}
