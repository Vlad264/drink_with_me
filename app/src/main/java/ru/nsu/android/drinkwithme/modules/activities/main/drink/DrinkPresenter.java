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
import ru.nsu.android.drinkwithme.modules.useCases.GetPredict;
import ru.nsu.android.drinkwithme.modules.useCases.GetState;

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
        GetState getState = new GetState(historyDBHandler);
        GetState.RequestValues requestValues2 = new GetState.RequestValues();
        handler.execute(getState, requestValues2, new IUseCaseCallback<GetState.ResponseValues>() {
            @Override
            public void onSuccess(GetState.ResponseValues response) {
                String text = "";
                switch (response.getState()) {
                    case -1:
                        view.showCurrentState(null);
                        return;
                    case 0:
                        text = context.getString(R.string.state_0_name_app_text);
                        break;
                    case 1:
                        text = context.getString(R.string.state_1_name_app_text);
                        break;
                    case 2:
                        text = context.getString(R.string.state_2_name_app_text);
                        break;
                    case 3:
                        text = context.getString(R.string.state_3_name_app_text);
                        break;
                }
                view.showCurrentState(text + " " + context.getString(R.string.condition_app_text));
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void saveDrink(String name, int percent, double liter) {
        AddHistory addHistory = new AddHistory(historyDBHandler);
        AddHistory.RequestValues requestValues1 = new AddHistory.RequestValues(name, percent, liter);
        handler.execute(addHistory, requestValues1, new IUseCaseCallback<AddHistory.ResponseValues>() {
            @Override
            public void onSuccess(AddHistory.ResponseValues response) {
                start();
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
        GetPredict getPredict = new GetPredict(historyDBHandler, parametersDBHandler);
        GetPredict.RequestValues requestValues = new GetPredict.RequestValues(name, percent, liter);
        handler.execute(getPredict, requestValues, new IUseCaseCallback<GetPredict.ResponseValues>() {
            @Override
            public void onSuccess(GetPredict.ResponseValues response) {
                double bloodPercent = calculateAlcoholPercent(response.getCurrentHistory(), response.getWeight(), response.getGender());
                StringBuilder text = new StringBuilder();
                text.append(predictMainText(response.getCurrentHistory().get(response.getCurrentHistory().size() - 1), bloodPercent));
                text.append(predictDescription(response.getName(), bloodPercent));
                text.append(predictByHistory(response.getAllHistory(), response.getStates(), bloodPercent, response.getWeight(), response.getGender()));
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

    private String predictMainText(DrinkLiter lastDrink, double bloodPercent) {
        StringBuilder text = new StringBuilder();
        text.append(context.getString(R.string.if_drink_app_text));
        text.append(" ");
        if (!lastDrink.getName().isEmpty()) {
            text.append(lastDrink.getName());
            text.append(" ");
        }
        text.append("(");
        text.append(lastDrink.getPercent());
        text.append("%) ");
        text.append(lastDrink.getLiter());
        text.append(" л.\n");
        text.append(String.format(Locale.ROOT, "%s %.2f", context.getString(R.string.if_drink_alcohol_percent_app_text), bloodPercent));
        text.append("\n");
        return text.toString();
    }

    private String predictDescription(String name, double bloodPercent) {
        return name + textForPercent(bloodPercent) + "\n";
    }

    private String predictByHistory(List<List<DrinkLiter>> history, List<Integer> states, double bloodPercent, int weight, String gender) {
        for (int i = 0; i < history.size() - 1; ++i) {
            if (states.get(i) != -1) {
                double historyPercent = calculateAlcoholPercent(history.get(i), weight, gender);
                if ((bloodPercent >= historyPercent - 0.5) && (bloodPercent <= historyPercent + 0.5)) {
                    StringBuilder text = new StringBuilder();
                    text.append(context.getString(R.string.history_predict_app_text));
                    text.append(" ");
                    switch (states.get(i)) {
                        case 0:
                            text.append(context.getString(R.string.state_0_name_app_text));
                            break;
                        case 1:
                            text.append(context.getString(R.string.state_1_name_app_text));
                            break;
                        case 2:
                            text.append(context.getString(R.string.state_2_name_app_text));
                            break;
                        case 3:
                            text.append(context.getString(R.string.state_3_name_app_text));
                            break;
                    }
                    text.append(".");
                    return text.toString();
                }
            }
        }
        return context.getString(R.string.no_history_predict_app_text);
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
