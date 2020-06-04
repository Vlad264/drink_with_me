package ru.nsu.android.drinkwithme.modules.activities.condition;

public class ConditionCheckPresenter implements IConditionCheckPresenter {
    private ConditionCheckActivity view;

    public ConditionCheckPresenter(ConditionCheckActivity view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void setDotsResult(long[] times) {
        view.startArithmetic();
    }

    @Override
    public void setArithmeticResult(long[] times) {
        view.finish();
    }
}
