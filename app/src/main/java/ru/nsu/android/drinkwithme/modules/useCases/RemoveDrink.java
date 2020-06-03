package ru.nsu.android.drinkwithme.modules.useCases;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.modules.database.drink.IDrinkDBHandler;

public class RemoveDrink extends UseCase<RemoveDrink.RequestValues, RemoveDrink.ResponseValues> {
    private IDrinkDBHandler dbHandler;

    public RemoveDrink(IDrinkDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        dbHandler.delete(requestValues.getId());
        getUseCaseCallback().onSuccess(new ResponseValues());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private long id;

        public RequestValues(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }
    }

    public static final class ResponseValues implements UseCase.ResponseValues {

    }
}
