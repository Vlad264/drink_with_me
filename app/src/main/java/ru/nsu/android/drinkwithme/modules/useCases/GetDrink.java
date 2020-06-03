package ru.nsu.android.drinkwithme.modules.useCases;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.model.Drink;
import ru.nsu.android.drinkwithme.modules.database.drink.IDrinkDBHandler;

public class GetDrink extends UseCase<GetDrink.RequestValues, GetDrink.ResponseValues> {
    private IDrinkDBHandler dbHandler;

    public GetDrink(IDrinkDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        getUseCaseCallback().onSuccess(new ResponseValues(dbHandler.get(requestValues.getId())));
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
        private Drink drink;

        public ResponseValues(Drink drink) {
            this.drink = drink;
        }

        public Drink getDrink() {
            return drink;
        }
    }
}
