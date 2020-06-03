package ru.nsu.android.drinkwithme.modules.activities.editDrinkList;

import ru.nsu.android.drinkwithme.modules.base.IBasePresenter;

public interface IEditDrinkListPresenter extends IBasePresenter {
    void addDrink(String name, int percent);
    void removeDrink(long id);
}
