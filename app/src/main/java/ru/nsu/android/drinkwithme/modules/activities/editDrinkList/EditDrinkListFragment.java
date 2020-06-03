package ru.nsu.android.drinkwithme.modules.activities.editDrinkList;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import ru.nsu.android.drinkwithme.R;
import ru.nsu.android.drinkwithme.model.Drink;

public class EditDrinkListFragment extends Fragment implements IEditDrinkListView {
    private IEditDrinkListPresenter presenter;

    private EditText drinkNameEditText;
    private EditText drinkPercentEditText;
    private Button addButton;
    private RecyclerView drinkRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_drink_list, container, false);

        drinkNameEditText = view.findViewById(R.id.drink_name_edit);
        drinkPercentEditText = view.findViewById(R.id.drink_percent_edit);
        addButton = view.findViewById(R.id.add_drink_button);
        drinkRecyclerView = view.findViewById(R.id.drink_recycler_view);

        addButton.setOnClickListener(new View.OnClickListener() {
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
                presenter.addDrink(drinkNameEditText.getText().toString(), percent);
                drinkNameEditText.setText("");
                drinkPercentEditText.setText("");
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        drinkRecyclerView.setLayoutManager(manager);
        drinkRecyclerView.setAdapter(new EditDrinkListAdapter(getContext(), new LinkedList<Drink>(), presenter));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(IEditDrinkListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showDrinkList(List<Drink> drinks) {
        drinkRecyclerView.setAdapter(new EditDrinkListAdapter(getContext(), drinks, presenter));
    }
}
