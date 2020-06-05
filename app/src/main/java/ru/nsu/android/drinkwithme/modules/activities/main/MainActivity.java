package ru.nsu.android.drinkwithme.modules.activities.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentPagerAdapter;

import ru.nsu.android.drinkwithme.modules.base.SwapFragmentActivity;
import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.modules.database.history.HistoryDBHandler;
import ru.nsu.android.drinkwithme.modules.database.history.IHistoryDBHandler;
import ru.nsu.android.drinkwithme.modules.database.parameters.IParametersDBHandler;
import ru.nsu.android.drinkwithme.modules.database.parameters.ParametersDBHandler;

public class MainActivity extends SwapFragmentActivity {

    private UseCaseHandler handler;
    private IHistoryDBHandler historyDBHandler;
    private IParametersDBHandler parametersDBHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        handler = UseCaseHandler.getInstance();
        historyDBHandler = new HistoryDBHandler(this);
        parametersDBHandler = new ParametersDBHandler(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected FragmentPagerAdapter createFragmentPagerAdapter() {
        return new MainFragmentPagerAdapter(getSupportFragmentManager(), this, handler, historyDBHandler, parametersDBHandler);
    }
}
