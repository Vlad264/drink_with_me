package ru.nsu.android.drinkwithme.modules.database.history;

import java.util.List;

import ru.nsu.android.drinkwithme.model.DrinkLiter;

public interface IHistoryDBHandler {
    void add(DrinkLiter drink);
    boolean nextGroup();
    void setState(int state);
    List<DrinkLiter> getGroup(long id);
    List<DrinkLiter> getLastGroup();
    List<List<DrinkLiter>> getAll();
    int getState(long id);
    int getLastState();
    List<Integer> getAllStates();
    void deleteGroup(long id);
    void deleteAll();
}
