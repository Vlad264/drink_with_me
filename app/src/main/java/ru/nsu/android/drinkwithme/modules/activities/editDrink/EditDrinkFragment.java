package ru.nsu.android.drinkwithme.modules.activities.editDrink;

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

import java.util.Locale;

import ru.nsu.android.drinkwithme.R;

public class EditDrinkFragment extends Fragment implements IEditDrinkView {
    private IEditDrinkPresenter presenter;

    private EditText drinkNameEditText;
    private EditText drinkPercentEditText;
    private Button saveButton;
    private Button backButton;
    private Button removeButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_drink, container, false);

        drinkNameEditText = view.findViewById(R.id.drink_name_edit);
        drinkPercentEditText = view.findViewById(R.id.drink_percent_edit);
        saveButton = view.findViewById(R.id.drink_save_button);
        backButton = view.findViewById(R.id.drink_back_button);
        removeButton = view.findViewById(R.id.drink_remove_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drinkNameEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.empty_drink_name),
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (drinkPercentEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.empty_drink_percent),
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                int percent = Integer.parseInt(drinkPercentEditText.getText().toString());
                if (percent > 100) {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.out_of_range_drink_percent),
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                presenter.saveDrinkInfo(drinkNameEditText.getText().toString(), percent);
                getActivity().finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.remove();
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
    public void setPresenter(IEditDrinkPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showDrinkInfo(String name, int percent) {
        drinkNameEditText.setText(name);
        drinkPercentEditText.setText(String.format(Locale.ROOT, "%d", percent));
    }

    @Override
    public void finish() {
        getActivity().finish();
    }
}
