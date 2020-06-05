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
    private List<Integer> states;

    public HistoryAdapter(Context context, List<List<DrinkLiter>> history, List<Integer> states) {
        this.context = context;
        this.history = history;
        this.states = states;
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
            historyText.append(currentHistory.indexOf(drink) + 1);
            historyText.append(". ");
            if (!drink.getName().isEmpty()) {
                historyText.append(drink.getName());
                historyText.append(" ");
            }
            historyText.append("(");
            historyText.append(drink.getPercent());
            historyText.append("%) ");
            historyText.append(drink.getLiter());
            historyText.append(" л.\n");
        }
        if (position == 0) {
            holder.historyTitle.setText(R.string.current_history);
            if (currentHistory.isEmpty()) {
                historyText.append(context.getResources().getString(R.string.empty_history));
            }
        }
        holder.historyText.setText(historyText.toString());
        int state = states.get(states.size() - 1 - position);
        switch (state) {
            case -1:
                holder.stateText.setText(context.getString(R.string.no_state_app_text));
                break;
            case 0:
                holder.stateText.setText(context.getString(R.string.state_0_name_app_text));
                break;
            case 1:
                holder.stateText.setText(context.getString(R.string.state_1_name_app_text));
                break;
            case 2:
                holder.stateText.setText(context.getString(R.string.state_2_name_app_text));
                break;
            case 3:
                holder.stateText.setText(context.getString(R.string.state_3_name_app_text));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public static final class HistoryHolder extends RecyclerView.ViewHolder {
        public TextView historyTitle;
        public TextView historyText;
        public TextView stateText;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            historyTitle = itemView.findViewById(R.id.history_title);
            historyText = itemView.findViewById(R.id.history_text);
            historyText.setSingleLine(false);
            stateText = itemView.findViewById(R.id.test_result_text);
            stateText.setSingleLine(false);
        }
    }
}
