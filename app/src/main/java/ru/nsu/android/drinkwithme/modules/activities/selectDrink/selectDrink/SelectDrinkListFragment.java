package ru.nsu.android.drinkwithme.modules.activities.selectDrink.selectDrink;

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
import ru.nsu.android.drinkwithme.modules.activities.selectDrink.ISelectDrinkPresenter;

public class SelectDrinkListFragment extends Fragment implements ISelectDrinkListView {
    private ISelectDrinkPresenter presenter;

    private EditText drinkPercentEditText;
    private Button confirmButton;
    private RecyclerView drinkRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_drink, container, false);

        drinkPercentEditText = view.findViewById(R.id.percent_edit);
        confirmButton = view.findViewById(R.id.confirm_button);
        drinkRecyclerView = view.findViewById(R.id.drink_recycler_view);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                presenter.setDrink("", percent);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        drinkRecyclerView.setLayoutManager(manager);
        drinkRecyclerView.setAdapter(new SelectDrinkListAdapter(new LinkedList<Drink>(), presenter));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(ISelectDrinkPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showDrinkList(List<Drink> drinks) {
        drinkRecyclerView.setAdapter(new SelectDrinkListAdapter(drinks, presenter));
    }
}
