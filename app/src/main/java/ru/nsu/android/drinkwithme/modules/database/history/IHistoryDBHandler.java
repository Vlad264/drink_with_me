package ru.nsu.android.drinkwithme.modules.database.history;

import java.util.List;

import ru.nsu.android.drinkwithme.model.DrinkLiter;

public interface IHistoryDBHandler {
    void add(DrinkLiter drink);
    boolean nextGroup();
    List<DrinkLiter> getGroup(long id);
    List<List<DrinkLiter>> getAll();
    void deleteGroup(long id);
    void deleteAll();
}
