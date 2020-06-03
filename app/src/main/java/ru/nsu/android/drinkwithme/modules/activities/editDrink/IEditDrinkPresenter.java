package ru.nsu.android.drinkwithme.modules.activities.editDrink;

import ru.nsu.android.drinkwithme.modules.base.IBasePresenter;

public interface IEditDrinkPresenter extends IBasePresenter {
    void saveDrinkInfo(String name, int percent);
    void remove();
}
