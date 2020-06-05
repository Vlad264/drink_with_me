package ru.nsu.android.drinkwithme.modules.useCases;

import java.util.List;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.model.DrinkLiter;
import ru.nsu.android.drinkwithme.modules.database.history.IHistoryDBHandler;

public class GetHistory extends UseCase<GetHistory.RequestValues, GetHistory.ResponseValues> {
    private IHistoryDBHandler dbHandler;

    public GetHistory(IHistoryDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        getUseCaseCallback().onSuccess(new ResponseValues(dbHandler.getAll(), dbHandler.getAllStates()));
    }

    public static final class RequestValues implements UseCase.RequestValues {
    }

    public static final class ResponseValues implements UseCase.ResponseValues {
        List<List<DrinkLiter>> history;
        List<Integer> states;

        public ResponseValues(List<List<DrinkLiter>> history, List<Integer> states) {
            this.history = history;
            this.states = states;
        }

        public List<List<DrinkLiter>> getHistory() {
            return history;
        }

        public List<Integer> getStates() {
            return states;
        }
    }
}
