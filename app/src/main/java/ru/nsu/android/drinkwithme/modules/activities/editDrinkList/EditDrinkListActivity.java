package ru.nsu.android.drinkwithme.modules.activities.editDrinkList;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.modules.base.SingleFragmentActivity;
import ru.nsu.android.drinkwithme.modules.database.drink.DrinkDBHandler;

public class EditDrinkListActivity extends SingleFragmentActivity {
    private EditDrinkListFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        fragment = new EditDrinkListFragment();
        fragment.setPresenter(new EditDrinkListPresenter(fragment,
                UseCaseHandler.getInstance(),
                new DrinkDBHandler(this)));
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFirstFragment() {
        return fragment;
    }
}
