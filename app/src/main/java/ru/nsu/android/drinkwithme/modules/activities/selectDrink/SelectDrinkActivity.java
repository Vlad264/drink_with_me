package ru.nsu.android.drinkwithme.modules.activities.selectDrink;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.modules.activities.selectDrink.selectDrink.SelectDrinkListFragment;
import ru.nsu.android.drinkwithme.modules.activities.selectDrink.selectLiter.SelectLiterFragment;
import ru.nsu.android.drinkwithme.modules.base.SingleFragmentActivity;
import ru.nsu.android.drinkwithme.modules.database.drink.DrinkDBHandler;

public class SelectDrinkActivity extends SingleFragmentActivity {
    private ISelectDrinkPresenter presenter;
    private SelectDrinkListFragment listFragment;
    private SelectLiterFragment literFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        listFragment = new SelectDrinkListFragment();
        literFragment = new SelectLiterFragment();
        presenter = new SelectDrinkPresenter(listFragment,
                UseCaseHandler.getInstance(),
                new DrinkDBHandler(this),
                this);
        listFragment.setPresenter(presenter);
        literFragment.setPresenter(presenter);
        super.onCreate(savedInstanceState);
    }

    public void changeFragment() {
        replaceFragment(literFragment);
    }

    @Override
    protected Fragment createFirstFragment() {
        return listFragment;
    }
}
