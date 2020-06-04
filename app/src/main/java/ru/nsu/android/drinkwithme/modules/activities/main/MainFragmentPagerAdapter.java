package ru.nsu.android.drinkwithme.modules.activities.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.modules.activities.main.drink.DrinkFragment;
import ru.nsu.android.drinkwithme.modules.activities.main.drink.DrinkPresenter;
import ru.nsu.android.drinkwithme.modules.activities.main.history.HistoryFragment;
import ru.nsu.android.drinkwithme.modules.activities.main.history.HistoryPresenter;
import ru.nsu.android.drinkwithme.modules.activities.main.parameters.ParametersFragment;
import ru.nsu.android.drinkwithme.modules.activities.main.parameters.ParametersPresenter;
import ru.nsu.android.drinkwithme.modules.database.history.IHistoryDBHandler;
import ru.nsu.android.drinkwithme.modules.database.parameters.IParametersDBHandler;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private DrinkFragment drinkFragment;
    private HistoryFragment historyFragment;
    private ParametersFragment parametersFragment;

    public MainFragmentPagerAdapter(@NonNull FragmentManager fm, UseCaseHandler handler, IHistoryDBHandler historyDBHandler, IParametersDBHandler parametersDBHandler) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);

        drinkFragment = new DrinkFragment();
        drinkFragment.setPresenter(new DrinkPresenter(drinkFragment, handler, historyDBHandler));

        historyFragment = new HistoryFragment();
        historyFragment.setPresenter(new HistoryPresenter(historyFragment, handler, historyDBHandler));

        parametersFragment = new ParametersFragment();
        parametersFragment.setPresenter(new ParametersPresenter(parametersFragment, handler, parametersDBHandler));
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position % 3) {
            case 0: {
                return drinkFragment;
            }
            case 1: {
                return historyFragment;
            }
            case 2: {
                return parametersFragment;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
