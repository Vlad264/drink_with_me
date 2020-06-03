package ru.nsu.android.drinkwithme.modules.activities.selectDrink;

import android.app.Activity;
import android.content.Intent;

import ru.nsu.android.drinkwithme.common.useCaseEngine.IUseCaseCallback;
import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.modules.activities.selectDrink.selectDrink.ISelectDrinkListView;
import ru.nsu.android.drinkwithme.modules.database.drink.IDrinkDBHandler;
import ru.nsu.android.drinkwithme.modules.useCases.GetAllDrinks;

public class SelectDrinkPresenter implements ISelectDrinkPresenter {
    private ISelectDrinkListView view;
    private UseCaseHandler handler;
    private IDrinkDBHandler dbHandler;
    private SelectDrinkActivity activity;

    private String drinkName;
    private int drinkPercent;

    public SelectDrinkPresenter(ISelectDrinkListView view, UseCaseHandler handler, IDrinkDBHandler dbHandler, SelectDrinkActivity activity) {
        this.view = view;
        this.handler = handler;
        this.dbHandler = dbHandler;
        this.activity = activity;
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
    public void setDrink(String name, int percent) {
        drinkName = name;
        drinkPercent = percent;
        activity.changeFragment();
    }

    @Override
    public void setLiter(double liter) {
        Intent intent = new Intent();
        intent.putExtra("NAME", drinkName);
        intent.putExtra("PERCENT", drinkPercent);
        intent.putExtra("LITER", liter);
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
    }
}
