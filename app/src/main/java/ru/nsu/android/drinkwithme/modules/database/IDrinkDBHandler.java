package ru.nsu.android.drinkwithme.modules.database;

import java.util.List;

import ru.nsu.android.drinkwithme.model.Drink;

public interface IDrinkDBHandler {
    void add(Drink drink);
    void update(long id, Drink drink);
    long has(Drink drink);
    Drink get(long id);
    List<Drink> getAll();
    void delete(long id);
    void deleteAll();
}
