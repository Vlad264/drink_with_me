package ru.nsu.android.drinkwithme.modules.base;

public interface IBaseView<T extends IBasePresenter> {
    void setPresenter(T presenter);
}
