package ru.nsu.android.drinkwithme.modules.activities.main.drink;

import ru.nsu.android.drinkwithme.modules.base.IBaseView;

public interface IDrinkView extends IBaseView<IDrinkPresenter> {
    void showPercent(String text);
    void showStatus(String text);
}
