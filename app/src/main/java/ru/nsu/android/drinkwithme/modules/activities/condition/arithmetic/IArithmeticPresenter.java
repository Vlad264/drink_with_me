package ru.nsu.android.drinkwithme.modules.activities.condition.arithmetic;

import ru.nsu.android.drinkwithme.modules.base.IBasePresenter;

public interface IArithmeticPresenter extends IBasePresenter {
    void checkAnswer(int answer);
    void stop();
}
