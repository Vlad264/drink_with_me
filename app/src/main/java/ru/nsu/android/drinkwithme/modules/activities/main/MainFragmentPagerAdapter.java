package ru.nsu.android.drinkwithme.modules.activities.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ru.nsu.android.drinkwithme.modules.activities.main.drink.DrinkFragment;
import ru.nsu.android.drinkwithme.modules.activities.main.drink.DrinkPresenter;
import ru.nsu.android.drinkwithme.modules.activities.main.parameters.ParametersFragment;
import ru.nsu.android.drinkwithme.modules.activities.main.parameters.ParametersPresenter;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private DrinkFragment drinkFragment;
    private ParametersFragment parametersFragment;

    public MainFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);

        drinkFragment = new DrinkFragment();
        drinkFragment.setPresenter(new DrinkPresenter());

        parametersFragment = new ParametersFragment();
        parametersFragment.setPresenter(new ParametersPresenter());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position % 2) {
            case 0: {
                return drinkFragment;
            }
            case 1: {
                return parametersFragment;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
