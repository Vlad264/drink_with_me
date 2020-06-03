package ru.nsu.android.drinkwithme.modules.activities.selectDrink.selectDrink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nsu.android.drinkwithme.R;
import ru.nsu.android.drinkwithme.model.Drink;
import ru.nsu.android.drinkwithme.modules.activities.selectDrink.ISelectDrinkPresenter;

public class SelectDrinkListAdapter extends RecyclerView.Adapter<SelectDrinkListAdapter.DrinkHolder> {
    private List<Drink> drinks;
    private ISelectDrinkPresenter presenter;

    public SelectDrinkListAdapter(List<Drink> drinks, ISelectDrinkPresenter presenter) {
        this.drinks = drinks;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public DrinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink, parent, false);
        return new DrinkHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkHolder holder, int position) {
        final Drink drink = drinks.get(position);
        String info = drink.getName() + " (" + drink.getPercent() + "%)";
        holder.info.setText(info);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setDrink(drink.getName(), drink.getPercent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    public static final class DrinkHolder extends RecyclerView.ViewHolder {
        public TextView info;

        public DrinkHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.drink_info);
        }
    }
}
