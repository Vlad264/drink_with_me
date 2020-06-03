package ru.nsu.android.drinkwithme.modules.activities.main.parameters;

import ru.nsu.android.drinkwithme.modules.base.IBasePresenter;

public interface IParametersPresenter extends IBasePresenter {
    void saveParameters(String name, int weight, int height, String gender);
}
