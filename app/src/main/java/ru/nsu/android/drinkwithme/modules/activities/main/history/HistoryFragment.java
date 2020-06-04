package ru.nsu.android.drinkwithme.modules.activities.main.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import ru.nsu.android.drinkwithme.R;
import ru.nsu.android.drinkwithme.model.DrinkLiter;

public class HistoryFragment extends Fragment implements IHistoryView {
    private IHistoryPresenter presenter;

    private RecyclerView historyRecyclerView;
    private Button nextGroupButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyRecyclerView = view.findViewById(R.id.history_recycler_view);
        nextGroupButton = view.findViewById(R.id.next_group_button);

        nextGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.nextGroup();
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        historyRecyclerView.setLayoutManager(manager);
        historyRecyclerView.setAdapter(new HistoryAdapter(getContext(), new LinkedList<List<DrinkLiter>>()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(IHistoryPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showHistory(List<List<DrinkLiter>> history) {
        historyRecyclerView.setAdapter(new HistoryAdapter(getContext(), history));
    }

    @Override
    public void showNextGroupError() {
        Toast.makeText(getContext(),
                getResources().getString(R.string.next_group_error),
                Toast.LENGTH_SHORT)
                .show();
    }
}
