package ru.nsu.android.drinkwithme.modules.activities.editDrinkList;

import ru.nsu.android.drinkwithme.common.useCaseEngine.IUseCaseCallback;
import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.modules.database.drink.IDrinkDBHandler;
import ru.nsu.android.drinkwithme.modules.useCases.AddDrink;
import ru.nsu.android.drinkwithme.modules.useCases.GetAllDrinks;
import ru.nsu.android.drinkwithme.modules.useCases.RemoveDrink;

public class EditDrinkListPresenter implements IEditDrinkListPresenter {
    private IEditDrinkListView view;
    private UseCaseHandler handler;
    private IDrinkDBHandler dbHandler;

    public EditDrinkListPresenter(IEditDrinkListView view, UseCaseHandler handler, IDrinkDBHandler dbHandler) {
        this.view = view;
        this.handler = handler;
        this.dbHandler = dbHandler;
    }

    @Override
    public void start() {
        GetAllDrinks getAllDrinks = new GetAllDrinks(dbHandler);
        GetAllDrinks.RequestValues requestValues = new GetAllDrinks.RequestValues();
        handler.execute(getAllDrinks, requestValues, new IUseCaseCallback<GetAllDrinks.ResponseValues>() {
            @Override
            public void onSuccess(GetAllDrinks.ResponseValues response) {
                view.showDrinkList(response.getDrinks());
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void addDrink(String name, int percent) {
        AddDrink addDrink = new AddDrink(dbHandler);
        AddDrink.RequestValues requestValues = new AddDrink.RequestValues(name, percent);
        handler.execute(addDrink, requestValues, new IUseCaseCallback<AddDrink.ResponseValues>() {
            @Override
            public void onSuccess(AddDrink.ResponseValues response) {
                start();
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void removeDrink(long id) {
        RemoveDrink addDrink = new RemoveDrink(dbHandler);
        RemoveDrink.RequestValues requestValues = new RemoveDrink.RequestValues(id);
        handler.execute(addDrink, requestValues, new IUseCaseCallback<RemoveDrink.ResponseValues>() {
            @Override
            public void onSuccess(RemoveDrink.ResponseValues response) {
                start();
            }

            @Override
            public void onError() {

            }
        });
    }
}
