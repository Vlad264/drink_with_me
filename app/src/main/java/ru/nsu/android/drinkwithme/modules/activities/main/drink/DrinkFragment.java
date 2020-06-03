package ru.nsu.android.drinkwithme.modules.activities.main.drink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.nsu.android.drinkwithme.R;

public class DrinkFragment extends Fragment implements IDrinkView {
    private IDrinkPresenter presenter;

    private TextView percentTextView;
    private TextView statusTextView;
    private Button drinkButton;
    private Button predictButton;
    private Button testButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink, container, false);

        percentTextView = view.findViewById(R.id.percent_text);
        statusTextView = view.findViewById(R.id.status_text);
        drinkButton = view.findViewById(R.id.drink_button);
        predictButton = view.findViewById(R.id.predict_button);
        testButton = view.findViewById(R.id.test_button);

        drinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(IDrinkPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPercent(String text) {
        percentTextView.setText(text);
    }

    @Override
    public void showStatus(String text) {
        statusTextView.setText(text);
    }
}
