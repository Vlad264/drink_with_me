package ru.nsu.android.drinkwithme.modules.useCases;

import java.util.List;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.model.Drink;
import ru.nsu.android.drinkwithme.modules.database.drink.IDrinkDBHandler;

public class GetAllDrinks extends UseCase<GetAllDrinks.RequestValues, GetAllDrinks.ResponseValues> {
    private IDrinkDBHandler dbHandler;

    public GetAllDrinks(IDrinkDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        getUseCaseCallback().onSuccess(new ResponseValues(dbHandler.getAll()));
    }

    public static final class RequestValues implements UseCase.RequestValues {

    }

    public static final class ResponseValues implements UseCase.ResponseValues {
        private List<Drink> drinks;

        public ResponseValues(List<Drink> drinks) {
            this.drinks = drinks;
        }

        public List<Drink> getDrinks() {
            return drinks;
        }
    }
}
