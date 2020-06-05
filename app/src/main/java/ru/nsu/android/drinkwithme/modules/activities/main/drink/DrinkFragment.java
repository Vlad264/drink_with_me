package ru.nsu.android.drinkwithme.modules.activities.main.drink;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import ru.nsu.android.drinkwithme.R;
import ru.nsu.android.drinkwithme.modules.activities.condition.ConditionCheckActivity;
import ru.nsu.android.drinkwithme.modules.activities.selectDrink.SelectDrinkActivity;

public class DrinkFragment extends Fragment implements IDrinkView {
    private IDrinkPresenter presenter;

    private TextView percentText;
    private TextView descriptionText;
    private TextView currentStateTitle;
    private TextView currentStateText;
    private TextView predictTitle;
    private TextView predictText;
    private Button drinkButton;
    private Button predictButton;
    private Button testButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink, container, false);

        percentText = view.findViewById(R.id.percent_text);
        descriptionText = view.findViewById(R.id.description_text);
        currentStateTitle = view.findViewById(R.id.current_state_title);
        currentStateText = view.findViewById(R.id.current_state_text);
        predictTitle = view.findViewById(R.id.predict_title);
        predictText = view.findViewById(R.id.predict_text);
        drinkButton = view.findViewById(R.id.drink_button);
        predictButton = view.findViewById(R.id.predict_button);
        testButton = view.findViewById(R.id.test_button);

        drinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectDrinkActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectDrinkActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConditionCheckActivity.class);
                startActivityForResult(intent, 3);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null) {
            return;
        }
        if (requestCode <= 2) {
            String name = data.getStringExtra("NAME");
            int percent = data.getIntExtra("PERCENT", 0);
            double liter = data.getDoubleExtra("LITER", 0);
            if (requestCode == 1) {
                presenter.saveDrink(name, percent, liter);
                return;
            }
            presenter.predict(name, percent, liter);
        } else {
            int state = data.getIntExtra("RESULT", 0);
            presenter.setState(state);
        }

    }

    @Override
    public void setPresenter(IDrinkPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPercent(double percent) {
        percentText.setText(String.format(Locale.ROOT, "%s %.2f", getString(R.string.alcohol_percent), percent));
    }

    @Override
    public void showDescription(String text) {
        descriptionText.setText(text);
    }

    @Override
    public void showCurrentState(String text) {
        currentStateTitle.setVisibility(View.VISIBLE);
        currentStateText.setVisibility(View.VISIBLE);
        currentStateText.setText(text);
    }

    @Override
    public void showPredict(String text) {
        predictTitle.setVisibility(View.VISIBLE);
        predictText.setVisibility(View.VISIBLE);
        predictText.setText(text);
    }
}
