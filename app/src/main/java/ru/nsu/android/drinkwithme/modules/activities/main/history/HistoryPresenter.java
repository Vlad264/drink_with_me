package ru.nsu.android.drinkwithme.modules.activities.main.history;

import ru.nsu.android.drinkwithme.common.useCaseEngine.IUseCaseCallback;
import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.modules.activities.main.drink.IDrinkPresenter;
import ru.nsu.android.drinkwithme.modules.database.history.IHistoryDBHandler;
import ru.nsu.android.drinkwithme.modules.useCases.GetHistory;
import ru.nsu.android.drinkwithme.modules.useCases.NextGroupHistory;

public class HistoryPresenter implements IHistoryPresenter {
    private IHistoryView view;
    private UseCaseHandler handler;
    private IHistoryDBHandler dbHandler;
    private IDrinkPresenter drinkPresenter;

    public HistoryPresenter(IHistoryView view, UseCaseHandler handler, IHistoryDBHandler dbHandler, IDrinkPresenter drinkPresenter) {
        this.view = view;
        this.handler = handler;
        this.dbHandler = dbHandler;
        this.drinkPresenter = drinkPresenter;
    }

    @Override
    public void start() {
        GetHistory getHistory = new GetHistory(dbHandler);
        GetHistory.RequestValues requestValues = new GetHistory.RequestValues();
        handler.execute(getHistory, requestValues, new IUseCaseCallback<GetHistory.ResponseValues>() {
            @Override
            public void onSuccess(GetHistory.ResponseValues response) {
                view.showHistory(response.getHistory(), response.getStates());
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void nextGroup() {
        NextGroupHistory nextGroupHistory = new NextGroupHistory(dbHandler);
        NextGroupHistory.RequestValues requestValues = new NextGroupHistory.RequestValues();
        handler.execute(nextGroupHistory, requestValues, new IUseCaseCallback<NextGroupHistory.ResponseValues>() {
            @Override
            public void onSuccess(NextGroupHistory.ResponseValues response) {
                start();
                drinkPresenter.start();
            }

            @Override
            public void onError() {
                view.showNextGroupError();
            }
        });
    }
}
