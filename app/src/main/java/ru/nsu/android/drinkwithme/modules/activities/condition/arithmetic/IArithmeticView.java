package ru.nsu.android.drinkwithme.modules.activities.condition.arithmetic;

import ru.nsu.android.drinkwithme.modules.base.IBaseView;

public interface IArithmeticView extends IBaseView<IArithmeticPresenter> {
    void setArithmetic(String arithmetic);
    void showSuccess();
    void showFailure();
    void showTime(long time);
}
