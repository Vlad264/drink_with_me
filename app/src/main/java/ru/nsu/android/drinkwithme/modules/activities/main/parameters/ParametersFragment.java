package ru.nsu.android.drinkwithme.modules.activities.main.parameters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.nsu.android.drinkwithme.R;

public class ParametersFragment extends Fragment implements IParametersView {
    private IParametersPresenter presenter;

    private EditText nameEditText;
    private EditText weightEditText;
    private EditText heightEditText;
    private Spinner genderSpinner;
    private Button saveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parameters, container, false);

        nameEditText = view.findViewById(R.id.name_edit);
        weightEditText = view.findViewById(R.id.weight_edit);
        heightEditText = view.findViewById(R.id.height_edit);
        genderSpinner = view.findViewById(R.id.gender_spinner);
        saveButton = view.findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveParameters(nameEditText.getText().toString(),
                        Integer.parseInt(weightEditText.getText().toString()),
                        Integer.parseInt(heightEditText.getText().toString()),
                        genderSpinner.getSelectedItem().toString());
            }
        });

        return view;
    }

    @Override
    public void setPresenter(IParametersPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showParameters(String name, int weight, int height, String gender) {
        nameEditText.setText(name);
        weightEditText.setText(weight);
        heightEditText.setText(height);
        if (gender.equals("M")) {
            genderSpinner.setSelection(0);
        } else {
            genderSpinner.setSelection(1);
        }

    }
}
