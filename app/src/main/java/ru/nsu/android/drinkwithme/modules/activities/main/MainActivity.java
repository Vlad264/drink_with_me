package ru.nsu.android.drinkwithme.modules.activities.main;

import androidx.fragment.app.FragmentPagerAdapter;

import ru.nsu.android.drinkwithme.modules.base.SwapFragmentActivity;

public class MainActivity extends SwapFragmentActivity {
    @Override
    protected FragmentPagerAdapter createFragmentPagerAdapter() {
        return new MainFragmentPagerAdapter(getSupportFragmentManager());
    }
}
