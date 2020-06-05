package ru.nsu.android.drinkwithme.modules.useCases;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.modules.database.history.IHistoryDBHandler;

public class AddState extends UseCase<AddState.RequestValues, AddState.ResponseValues> {
    private IHistoryDBHandler dbHandler;

    public AddState(IHistoryDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        dbHandler.setState(requestValues.getState());
        getUseCaseCallback().onSuccess(new ResponseValues());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private int state;

        public RequestValues(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }

    public static final class ResponseValues implements UseCase.ResponseValues {

    }
}
