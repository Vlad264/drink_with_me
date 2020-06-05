package ru.nsu.android.drinkwithme.modules.activities.main.drink;

import android.content.Context;

import java.util.List;
import java.util.Locale;

import ru.nsu.android.drinkwithme.R;
import ru.nsu.android.drinkwithme.common.useCaseEngine.IUseCaseCallback;
import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.model.DrinkLiter;
import ru.nsu.android.drinkwithme.modules.database.history.IHistoryDBHandler;
import ru.nsu.android.drinkwithme.modules.database.parameters.IParametersDBHandler;
import ru.nsu.android.drinkwithme.modules.useCases.AddHistory;
import ru.nsu.android.drinkwithme.modules.useCases.AddState;
import ru.nsu.android.drinkwithme.modules.useCases.GetHistoryAndParameters;

public class DrinkPresenter implements IDrinkPresenter {
    private Context context;
    private IDrinkView view;
    private UseCaseHandler handler;
    private IHistoryDBHandler historyDBHandler;
    private IParametersDBHandler parametersDBHandler;
    private String defaultGender;

    public DrinkPresenter(Context context, IDrinkView view, UseCaseHandler handler, IHistoryDBHandler historyDBHandler, IParametersDBHandler parametersDBHandler) {
        this.context = context;
        this.view = view;
        this.handler = handler;
        this.historyDBHandler = historyDBHandler;
        this.parametersDBHandler = parametersDBHandler;
        defaultGender = context.getString(R.string.default_gender);
    }

    @Override
    public void start() {
        GetHistoryAndParameters getHistoryAndParameters = new GetHistoryAndParameters(historyDBHandler, parametersDBHandler);
        GetHistoryAndParameters.RequestValues requestValues = new GetHistoryAndParameters.RequestValues();
        handler.execute(getHistoryAndParameters, requestValues, new IUseCaseCallback<GetHistoryAndParameters.ResponseValues>() {
            @Override
            public void onSuccess(GetHistoryAndParameters.ResponseValues response) {
                double percent = calculateAlcoholPercent(response.getHistory(), response.getWeight(), response.getGender());
                view.showPercent(percent);
                view.showDescription(response.getName() + textForPercent(percent));
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void saveDrink(String name, int percent, double liter) {
        AddHistory addHistory = new AddHistory(historyDBHandler);
        AddHistory.RequestValues requestValues = new AddHistory.RequestValues(name, percent, liter);
        handler.execute(addHistory, requestValues, new IUseCaseCallback<AddHistory.ResponseValues>() {
            @Override
            public void onSuccess(AddHistory.ResponseValues response) {

            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void setState(int state) {
        AddState addState = new AddState(historyDBHandler);
        AddState.RequestValues requestValues = new AddState.RequestValues(state);
        handler.execute(addState, requestValues, new IUseCaseCallback<AddState.ResponseValues>() {
            @Override
            public void onSuccess(AddState.ResponseValues response) {

            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void predict(final String name, final int percent, final double liter) {
        GetHistoryAndParameters getHistoryAndParameters = new GetHistoryAndParameters(historyDBHandler, parametersDBHandler);
        GetHistoryAndParameters.RequestValues requestValues = new GetHistoryAndParameters.RequestValues();
        handler.execute(getHistoryAndParameters, requestValues, new IUseCaseCallback<GetHistoryAndParameters.ResponseValues>() {
            @Override
            public void onSuccess(GetHistoryAndParameters.ResponseValues response) {
                List<DrinkLiter> history = response.getHistory();
                history.add(new DrinkLiter(name, percent, liter));
                double bloodPercent = calculateAlcoholPercent(response.getHistory(), response.getWeight(), response.getGender());
                StringBuilder text = new StringBuilder();
                text.append(context.getString(R.string.if_drink_app_text));
                text.append(" ");
                if (!name.isEmpty()) {
                    text.append(name);
                    text.append(" ");
                }
                text.append("(");
                text.append(percent);
                text.append("%) ");
                text.append(liter);
                text.append(" л.\n");
                text.append(String.format(Locale.ROOT, "%s %.2f", context.getString(R.string.if_drink_alcohol_percent_app_text), bloodPercent));
                text.append("\n");
                view.showPredict(text.toString());
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public double calculateAlcoholPercent(List<DrinkLiter> history, int weight, String gender) {
        double A = 0.0;
        for (DrinkLiter drink : history) {
            A += calculateAlcoholMass(drink);
        }
        double r;
        if (defaultGender.equals(gender)) {
            r = 0.7;
        } else {
            r = 0.6;
        }
        return A / (weight * r);
    }

    private double calculateAlcoholMass(DrinkLiter drink) {
        return (drink.getLiter() * 1000) * (drink.getPercent() * 0.01) * 0.8;
    }

    private String textForPercent(double percent) {
        if (percent <= 0.5) {
            return context.getString(R.string.percent_less_0_5_app_text);
        } else if (percent <= 1.0) {
            return context.getString(R.string.percent_less_1_0_app_text);
        } else if (percent <= 2.0) {
            return context.getString(R.string.percent_less_2_0_app_text);
        } else if (percent <= 3.0) {
            return context.getString(R.string.percent_less_3_0_app_text);
        } else if (percent <= 4.0) {
            return context.getString(R.string.percent_less_4_0_app_text);
        } else if (percent <= 5.0) {
            return context.getString(R.string.percent_less_5_0_app_text);
        }
        return context.getString(R.string.percent_more_5_0_app_text);
    }
}
