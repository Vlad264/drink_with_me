package ru.nsu.android.drinkwithme.modules.activities.condition;

import ru.nsu.android.drinkwithme.modules.base.IBasePresenter;

public interface IConditionCheckPresenter extends IBasePresenter {
    void setDotsResult(long[] times);
    void setArithmeticResult(long[] times);
}
