package ru.nsu.android.drinkwithme.modules.activities.selectDrink.selectLiter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.nsu.android.drinkwithme.R;
import ru.nsu.android.drinkwithme.modules.activities.selectDrink.ISelectDrinkPresenter;
import ru.nsu.android.drinkwithme.modules.activities.selectDrink.ISelectDrinkView;

public class SelectLiterFragment extends Fragment implements ISelectDrinkView {
    private ISelectDrinkPresenter presenter;

    private EditText literEditText;
    private Button literButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_liter, container, false);

        literEditText = view.findViewById(R.id.liter_edit);
        literButton = view.findViewById(R.id.liter_button);

        literButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (literEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.empty_drink_liter),
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                double liter = Double.parseDouble(literEditText.getText().toString());
                if (liter > 20.0) {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.out_of_range_drink_liter),
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                presenter.setLiter(liter);
            }
        });

        return view;
    }

    @Override
    public void setPresenter(ISelectDrinkPresenter presenter) {
        this.presenter = presenter;
    }
}
