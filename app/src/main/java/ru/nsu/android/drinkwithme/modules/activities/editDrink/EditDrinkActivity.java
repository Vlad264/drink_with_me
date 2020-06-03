package ru.nsu.android.drinkwithme.modules.activities.editDrink;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.modules.base.SingleFragmentActivity;
import ru.nsu.android.drinkwithme.modules.database.drink.DrinkDBHandler;

public class EditDrinkActivity extends SingleFragmentActivity {
    private EditDrinkFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        long id = getIntent().getLongExtra("ID", 0);
        fragment = new EditDrinkFragment();
        fragment.setPresenter(new EditDrinkPresenter(fragment,
                UseCaseHandler.getInstance(),
                new DrinkDBHandler(this),
                id));
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFirstFragment() {
        return fragment;
    }
}
