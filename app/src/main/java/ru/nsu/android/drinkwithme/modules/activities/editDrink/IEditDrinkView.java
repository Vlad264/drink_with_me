package ru.nsu.android.drinkwithme.modules.activities.editDrink;

import ru.nsu.android.drinkwithme.modules.base.IBaseView;

public interface IEditDrinkView extends IBaseView<IEditDrinkPresenter> {
    void showDrinkInfo(String name, int percent);
    void finish();
}
