package ru.nsu.android.drinkwithme.modules.useCases;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.model.Drink;
import ru.nsu.android.drinkwithme.modules.database.drink.IDrinkDBHandler;

public class UpdateDrink extends UseCase<UpdateDrink.RequestValues, UpdateDrink.ResponseValues> {
    private IDrinkDBHandler dbHandler;

    public UpdateDrink(IDrinkDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        dbHandler.update(requestValues.id, new Drink(requestValues.getName(), requestValues.getPercent()));
        getUseCaseCallback().onSuccess(new ResponseValues());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private long id;
        private String name;
        private int percent;

        public RequestValues(long id, String name, int percent) {
            this.id = id;
            this.name = name;
            this.percent = percent;
        }

        public long getId() {
            return id;
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
