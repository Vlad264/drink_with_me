package ru.nsu.android.drinkwithme.modules.activities.selectDrink;

import ru.nsu.android.drinkwithme.modules.base.IBasePresenter;

public interface ISelectDrinkPresenter extends IBasePresenter {
    void setDrink(String name, int percent);
    void setLiter(double liter);
}
