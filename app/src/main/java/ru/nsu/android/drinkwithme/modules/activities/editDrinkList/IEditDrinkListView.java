package ru.nsu.android.drinkwithme.modules.activities.editDrinkList;

import java.util.List;

import ru.nsu.android.drinkwithme.model.Drink;
import ru.nsu.android.drinkwithme.modules.base.IBaseView;

public interface IEditDrinkListView extends IBaseView<IEditDrinkListPresenter> {
    void showDrinkList(List<Drink> drinks);
}
