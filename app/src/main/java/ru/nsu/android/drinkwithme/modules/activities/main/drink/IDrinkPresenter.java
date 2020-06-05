package ru.nsu.android.drinkwithme.modules.activities.main.drink;

import java.util.List;

import ru.nsu.android.drinkwithme.model.DrinkLiter;
import ru.nsu.android.drinkwithme.modules.base.IBasePresenter;

public interface IDrinkPresenter extends IBasePresenter {
    void saveDrink(String name, int percent, double liter);
    void setState(int state);
    void predict(String name, int percent, double liter);
    double calculateAlcoholPercent(List<DrinkLiter> history, int weight, String gender);
}
