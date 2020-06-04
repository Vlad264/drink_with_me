package ru.nsu.android.drinkwithme.modules.activities.condition;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.nsu.android.drinkwithme.R;
import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.modules.activities.condition.arithmetic.ArithmeticFragment;
import ru.nsu.android.drinkwithme.modules.activities.condition.arithmetic.ArithmeticPresenter;
import ru.nsu.android.drinkwithme.modules.activities.condition.dot.DotFragment;
import ru.nsu.android.drinkwithme.modules.activities.condition.dot.DotPresenter;
import ru.nsu.android.drinkwithme.modules.base.SingleFragmentActivity;

public class ConditionCheckActivity extends SingleFragmentActivity implements IConditionCheckView {
    private IConditionCheckPresenter presenter;
    private DotFragment dotFragment;
    private ArithmeticFragment arithmeticFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = new ConditionCheckPresenter(this);
        dotFragment = new DotFragment();
        dotFragment.setPresenter(
                new DotPresenter(dotFragment,
                        UseCaseHandler.getInstance(),
                        presenter,
                        Integer.parseInt(getString(R.string.dot_test_number))));
        arithmeticFragment = new ArithmeticFragment();
        arithmeticFragment.setPresenter(
                new ArithmeticPresenter(arithmeticFragment,
                        UseCaseHandler.getInstance(),
                        presenter,
                        Integer.parseInt(getString(R.string.arithmetic_test_number))));
        super.onCreate(savedInstanceState);
    }

    public void startArithmetic() {
        replaceFragment(arithmeticFragment);
    }

    @Override
    protected Fragment createFirstFragment() {
        return dotFragment;
    }

    @Override
    public void setPresenter(IConditionCheckPresenter presenter) {

    }
}
