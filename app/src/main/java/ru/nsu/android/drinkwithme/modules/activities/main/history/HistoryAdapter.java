package ru.nsu.android.drinkwithme.modules.activities.main.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nsu.android.drinkwithme.R;
import ru.nsu.android.drinkwithme.model.DrinkLiter;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    private Context context;
    private List<List<DrinkLiter>> history;

    public HistoryAdapter(Context context, List<List<DrinkLiter>> history) {
        this.context = context;
        this.history = history;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        List<DrinkLiter> currentHistory = history.get(history.size() - 1 - position);
        StringBuilder historyText = new StringBuilder();
        for (DrinkLiter drink : currentHistory) {
            historyText.insert(0," л.\n");
            historyText.insert(0, drink.getLiter());
            historyText.insert(0,"%) ");
            historyText.insert(0, drink.getPercent());
            historyText.insert(0," (");
            historyText.insert(0, drink.getName());
        }
        if (position == 0) {
            holder.historyTitle.setText(R.string.current_history);
            if (currentHistory.isEmpty()) {
                historyText.append(context.getResources().getString(R.string.empty_history));
            }
        }
        holder.historyText.setText(historyText.toString());
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public static final class HistoryHolder extends RecyclerView.ViewHolder {
        public TextView historyTitle;
        public TextView historyText;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            historyTitle = itemView.findViewById(R.id.history_title);
            historyText = itemView.findViewById(R.id.history_text);
            historyText.setSingleLine(false);
        }
    }
}
