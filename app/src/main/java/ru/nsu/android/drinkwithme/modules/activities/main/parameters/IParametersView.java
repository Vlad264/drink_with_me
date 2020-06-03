package ru.nsu.android.drinkwithme.modules.activities.main.parameters;

import ru.nsu.android.drinkwithme.modules.base.IBaseView;

public interface IParametersView extends IBaseView<IParametersPresenter> {
    void showParameters(String name, int weight, int height, String gender);
}
