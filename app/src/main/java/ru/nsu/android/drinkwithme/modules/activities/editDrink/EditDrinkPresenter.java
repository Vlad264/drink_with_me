package ru.nsu.android.drinkwithme.modules.activities.editDrink;

import ru.nsu.android.drinkwithme.common.useCaseEngine.IUseCaseCallback;
import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.modules.database.drink.IDrinkDBHandler;
import ru.nsu.android.drinkwithme.modules.useCases.GetDrink;
import ru.nsu.android.drinkwithme.modules.useCases.RemoveDrink;
import ru.nsu.android.drinkwithme.modules.useCases.UpdateDrink;

public class EditDrinkPresenter implements IEditDrinkPresenter {
    private IEditDrinkView view;
    private UseCaseHandler handler;
    private IDrinkDBHandler dbHandler;
    private long id;

    public EditDrinkPresenter(IEditDrinkView view, UseCaseHandler handler, IDrinkDBHandler dbHandler, long id) {
        this.view = view;
        this.handler = handler;
        this.dbHandler = dbHandler;
        this.id = id;
    }

    @Override
    public void start() {
        GetDrink getDrink = new GetDrink(dbHandler);
        final GetDrink.RequestValues requestValues = new GetDrink.RequestValues(id);
        handler.execute(getDrink, requestValues, new IUseCaseCallback<GetDrink.ResponseValues>() {
            @Override
            public void onSuccess(GetDrink.ResponseValues response) {
                view.showDrinkInfo(response.getDrink().getName(), response.getDrink().getPercent());
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void saveDrinkInfo(String name, int percent) {
        UpdateDrink updateDrink = new UpdateDrink(dbHandler);
        UpdateDrink.RequestValues requestValues = new UpdateDrink.RequestValues(id, name, percent);
        handler.execute(updateDrink, requestValues, new IUseCaseCallback<UpdateDrink.ResponseValues>() {
            @Override
            public void onSuccess(UpdateDrink.ResponseValues response) {
                view.finish();
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void remove() {
        RemoveDrink addDrink = new RemoveDrink(dbHandler);
        RemoveDrink.RequestValues requestValues = new RemoveDrink.RequestValues(id);
        handler.execute(addDrink, requestValues, new IUseCaseCallback<RemoveDrink.ResponseValues>() {
            @Override
            public void onSuccess(RemoveDrink.ResponseValues response) {
                view.finish();
            }

            @Override
            public void onError() {

            }
        });
    }
}
