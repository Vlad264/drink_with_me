package ru.nsu.android.drinkwithme.modules.activities.main.parameters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import ru.nsu.android.drinkwithme.R;
import ru.nsu.android.drinkwithme.modules.activities.editDrinkList.EditDrinkListActivity;

public class ParametersFragment extends Fragment implements IParametersView {
    private IParametersPresenter presenter;

    private EditText nameEditText;
    private EditText weightEditText;
    private EditText heightEditText;
    private Spinner genderSpinner;
    private Button saveButton;
    private Button editDrinkListButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parameters, container, false);

        nameEditText = view.findViewById(R.id.name_edit);
        weightEditText = view.findViewById(R.id.weight_edit);
        heightEditText = view.findViewById(R.id.height_edit);
        genderSpinner = view.findViewById(R.id.gender_spinner);
        saveButton = view.findViewById(R.id.save_button);
        editDrinkListButton = view.findViewById(R.id.edit_drink_list_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.empty_name_error),
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (weightEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.empty_weight_error),
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (heightEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.empty_height_error),
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                int weight = Integer.parseInt(weightEditText.getText().toString());
                if ((weight < 20) || (weight > 200)) {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.out_of_range_weight_error),
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                int height = Integer.parseInt(heightEditText.getText().toString());
                if ((height < 50) || (height > 300)) {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.out_of_range_height_error),
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                presenter.saveParameters(nameEditText.getText().toString(),
                        weight,
                        height,
                        genderSpinner.getSelectedItem().toString());
            }
        });

        editDrinkListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditDrinkListActivity.class);
                startActivity(intent);
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
    public void setPresenter(IParametersPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showParameters(String name, int weight, int height, String gender) {
        nameEditText.setText(name);
        weightEditText.setText(String.format(Locale.ROOT,"%d", weight));
        heightEditText.setText(String.format(Locale.ROOT,"%d", height));
        if (gender.equals(genderSpinner.getAdapter().getItem(0).toString())) {
            genderSpinner.setSelection(0);
        } else {
            genderSpinner.setSelection(1);
        }

    }

    @Override
    public void showSuccess() {
        Toast.makeText(getContext(), getResources().getString(R.string.success_save), Toast.LENGTH_SHORT).show();
    }
}
