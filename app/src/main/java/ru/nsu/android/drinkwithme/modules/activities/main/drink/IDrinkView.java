package ru.nsu.android.drinkwithme.modules.activities.main.drink;

import ru.nsu.android.drinkwithme.modules.base.IBaseView;

public interface IDrinkView extends IBaseView<IDrinkPresenter> {
    void showPercent(double percent);
    void showDescription(String text);
    void showCurrentState(String text);
    void showPredict(String text);
}
