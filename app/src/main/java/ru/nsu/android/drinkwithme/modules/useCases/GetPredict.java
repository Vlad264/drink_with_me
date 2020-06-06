package ru.nsu.android.drinkwithme.modules.useCases;

import java.util.List;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.model.DrinkLiter;
import ru.nsu.android.drinkwithme.modules.database.history.IHistoryDBHandler;
import ru.nsu.android.drinkwithme.modules.database.parameters.IParametersDBHandler;

public class GetPredict extends UseCase<GetPredict.RequestValues, GetPredict.ResponseValues> {
    private IHistoryDBHandler historyDBHandler;
    private IParametersDBHandler parametersDBHandler;

    public GetPredict(IHistoryDBHandler historyDBHandler, IParametersDBHandler parametersDBHandler) {
        this.historyDBHandler = historyDBHandler;
        this.parametersDBHandler = parametersDBHandler;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        List<List<DrinkLiter>> allHistory = historyDBHandler.getAll();
        List<Integer> states = historyDBHandler.getAllStates();
        allHistory.get(allHistory.size() - 1).add(new DrinkLiter(requestValues.getName(), requestValues.getPercent(), requestValues.getLiter()));
        List<DrinkLiter> currentHistory = allHistory.get(allHistory.size() - 1);
        String name = parametersDBHandler.getName();
        int weight = parametersDBHandler.getWeight();
        int height = parametersDBHandler.getHeight();
        String gender = parametersDBHandler.getGender();
        getUseCaseCallback().onSuccess(new ResponseValues(allHistory, states, currentHistory, name, weight, height, gender));
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
        List<List<DrinkLiter>> allHistory;
        List<Integer> states;
        List<DrinkLiter> currentHistory;
        private String name;
        private int weight;
        private int height;
        private String gender;

        public ResponseValues(List<List<DrinkLiter>> allHistory, List<Integer> states, List<DrinkLiter> currentHistory, String name, int weight, int height, String gender) {
            this.allHistory = allHistory;
            this.states = states;
            this.currentHistory = currentHistory;
            this.name = name;
            this.weight = weight;
            this.height = height;
            this.gender = gender;
        }

        public List<List<DrinkLiter>> getAllHistory() {
            return allHistory;
        }

        public List<Integer> getStates() {
            return states;
        }

        public List<DrinkLiter> getCurrentHistory() {
            return currentHistory;
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
