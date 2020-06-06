package ru.nsu.android.drinkwithme.modules.useCases;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.modules.database.history.IHistoryDBHandler;

public class GetState extends UseCase<GetState.RequestValues, GetState.ResponseValues> {
    private IHistoryDBHandler dbHandler;

    public GetState(IHistoryDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        getUseCaseCallback().onSuccess(new ResponseValues(dbHandler.getLastState()));
    }

    public static final class RequestValues implements UseCase.RequestValues {
    }

    public static final class ResponseValues implements UseCase.ResponseValues {
        int state;

        public ResponseValues(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
