package ru.nsu.android.drinkwithme.modules.useCases;

import java.util.List;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.model.DrinkLiter;
import ru.nsu.android.drinkwithme.modules.database.history.IHistoryDBHandler;
import ru.nsu.android.drinkwithme.modules.database.parameters.IParametersDBHandler;

public class GetHistoryAndParameters extends UseCase<GetHistoryAndParameters.RequestValues, GetHistoryAndParameters.ResponseValues> {
    private IHistoryDBHandler historyDBHandler;
    private IParametersDBHandler parametersDBHandler;

    public GetHistoryAndParameters(IHistoryDBHandler historyDBHandler, IParametersDBHandler parametersDBHandler) {
        this.historyDBHandler = historyDBHandler;
        this.parametersDBHandler = parametersDBHandler;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        List<DrinkLiter> history = historyDBHandler.getLastGroup();
        String name = parametersDBHandler.getName();
        int weight = parametersDBHandler.getWeight();
        int height = parametersDBHandler.getHeight();
        String gender = parametersDBHandler.getGender();
        getUseCaseCallback().onSuccess(new ResponseValues(history, name, weight, height, gender));
    }

    public static final class RequestValues implements UseCase.RequestValues {
    }

    public static final class ResponseValues implements UseCase.ResponseValues {
        List<DrinkLiter> history;
        private String name;
        private int weight;
        private int height;
        private String gender;

        public ResponseValues(List<DrinkLiter> history, String name, int weight, int height, String gender) {
            this.history = history;
            this.name = name;
            this.weight = weight;
            this.height = height;
            this.gender = gender;
        }

        public List<DrinkLiter> getHistory() {
            return history;
        }

        public String getName() {
            return name;
        }

        public int getWeight() {
            return weight;
        }

        public int getHeight() {
            return height;
        }

        public String getGender() {
            return gender;
        }
    }
}
