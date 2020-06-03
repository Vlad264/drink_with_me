package ru.nsu.android.drinkwithme.modules.activities.selectDrink.selectDrink;

import java.util.List;

import ru.nsu.android.drinkwithme.model.Drink;
import ru.nsu.android.drinkwithme.modules.activities.selectDrink.ISelectDrinkView;

public interface ISelectDrinkListView extends ISelectDrinkView {
    void showDrinkList(List<Drink> drinks);
}
