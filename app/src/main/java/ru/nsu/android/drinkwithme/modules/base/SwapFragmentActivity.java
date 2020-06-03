package ru.nsu.android.drinkwithme.modules.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.pixelcan.inkpageindicator.InkPageIndicator;

import ru.nsu.android.drinkwithme.R;

public abstract class SwapFragmentActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private InkPageIndicator indicator;

    protected abstract FragmentPagerAdapter createFragmentPagerAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_fragment);

        viewPager = findViewById(R.id.swap_view_pager);
        indicator = findViewById(R.id.swap_page_indicator);

        viewPager.setAdapter(createFragmentPagerAdapter());
        indicator.setViewPager(viewPager);
    }
}
