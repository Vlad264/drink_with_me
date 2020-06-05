package ru.nsu.android.drinkwithme.modules.activities.condition.dot;

import ru.nsu.android.drinkwithme.modules.base.IBaseView;

public interface IDotView extends IBaseView<IDotPresenter> {
    void drawCircle(int x, int y);
    int getWidth();
    int getHeight();
    int getRadius();
    void showTime(long time);
}
