package ru.nsu.android.drinkwithme.modules.useCases;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.model.DrinkLiter;
import ru.nsu.android.drinkwithme.modules.database.history.IHistoryDBHandler;

public class AddHistory extends UseCase<AddHistory.RequestValues, AddHistory.ResponseValues> {
    private IHistoryDBHandler dbHandler;

    public AddHistory(IHistoryDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        dbHandler.add(new DrinkLiter(requestValues.getName(), requestValues.getPercent(), requestValues.getLiter()));
        getUseCaseCallback().onSuccess(new ResponseValues());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String name;
        private int percent;
        private double liter;

        public RequestValues(String name, int percent, double liter) {
            this.name = name;
            this.percent = percent;
            this.liter = liter;
        }

        public String getName() {
            return name;
        }

        public int getPercent() {
            return percent;
        }

        public double getLiter() {
            return liter;
        }
    }

    public static final class ResponseValues implements UseCase.ResponseValues {

    }
}
