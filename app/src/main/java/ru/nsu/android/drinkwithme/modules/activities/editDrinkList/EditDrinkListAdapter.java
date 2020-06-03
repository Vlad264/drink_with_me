package ru.nsu.android.drinkwithme.modules.activities.editDrinkList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nsu.android.drinkwithme.R;
import ru.nsu.android.drinkwithme.model.Drink;

public class EditDrinkListAdapter extends RecyclerView.Adapter<EditDrinkListAdapter.DrinkHolder> {
    private Context context;
    private List<Drink> drinks;
    private IEditDrinkListPresenter presenter;

    public EditDrinkListAdapter(Context context, List<Drink> drinks, IEditDrinkListPresenter presenter) {
        this.context = context;
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
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.removeDrink(drink.getId());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    public static final class DrinkHolder extends RecyclerView.ViewHolder {
        public TextView info;
        public ImageButton removeButton;

        public DrinkHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.drink_info);
            removeButton = itemView.findViewById(R.id.drink_remove_button);
            removeButton.setVisibility(View.VISIBLE);
        }
    }
}
