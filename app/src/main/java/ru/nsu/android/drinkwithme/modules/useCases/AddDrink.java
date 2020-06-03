package ru.nsu.android.drinkwithme.modules.useCases;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.model.Drink;
import ru.nsu.android.drinkwithme.modules.database.drink.IDrinkDBHandler;

public class AddDrink extends UseCase<AddDrink.RequestValues, AddDrink.ResponseValues> {
    private IDrinkDBHandler dbHandler;

    public AddDrink(IDrinkDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        dbHandler.add(new Drink(requestValues.getName(), requestValues.getPercent()));
        getUseCaseCallback().onSuccess(new ResponseValues());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String name;
        private int percent;

        public RequestValues(String name, int percent) {
            this.name = name;
            this.percent = percent;
        }

        public String getName() {
            return name;
        }

        public int getPercent() {
            return percent;
        }
    }

    public static final class ResponseValues implements UseCase.ResponseValues {

    }
}
