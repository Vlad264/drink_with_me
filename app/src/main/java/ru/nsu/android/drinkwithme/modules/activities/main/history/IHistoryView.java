package ru.nsu.android.drinkwithme.modules.activities.main.history;

import java.util.List;

import ru.nsu.android.drinkwithme.model.DrinkLiter;
import ru.nsu.android.drinkwithme.modules.base.IBaseView;

public interface IHistoryView extends IBaseView<IHistoryPresenter> {
    void showHistory(List<List<DrinkLiter>> history, List<Integer> states);
    void showNextGroupError();
}
