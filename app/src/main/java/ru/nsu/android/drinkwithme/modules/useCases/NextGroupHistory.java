package ru.nsu.android.drinkwithme.modules.useCases;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.modules.database.history.IHistoryDBHandler;

public class NextGroupHistory extends UseCase<NextGroupHistory.RequestValues, NextGroupHistory.ResponseValues> {
    private IHistoryDBHandler dbHandler;

    public NextGroupHistory(IHistoryDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        if (dbHandler.nextGroup()) {
            getUseCaseCallback().onSuccess(new ResponseValues());
        } else {
            getUseCaseCallback().onError();
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {
    }

    public static final class ResponseValues implements UseCase.ResponseValues {

    }
}
