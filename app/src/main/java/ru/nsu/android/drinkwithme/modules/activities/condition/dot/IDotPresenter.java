package ru.nsu.android.drinkwithme.modules.activities.condition.dot;

import ru.nsu.android.drinkwithme.modules.base.IBasePresenter;

public interface IDotPresenter extends IBasePresenter {
    void checkTouch(int x, int y);
    void stop();
}
