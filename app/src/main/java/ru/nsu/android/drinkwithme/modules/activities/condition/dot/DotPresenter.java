package ru.nsu.android.drinkwithme.modules.activities.condition.dot;

import ru.nsu.android.drinkwithme.common.useCaseEngine.IUseCaseCallback;
import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.modules.activities.condition.IConditionCheckPresenter;
import ru.nsu.android.drinkwithme.modules.useCases.DrawCircle;

public class DotPresenter implements IDotPresenter {
    private IDotView view;
    private UseCaseHandler handler;
    private IConditionCheckPresenter mainPresenter;
    private DrawCircle drawCircle;

    public DotPresenter(IDotView view, UseCaseHandler handler, IConditionCheckPresenter mainPresenter, int number) {
        this.view = view;
        this.handler = handler;
        this.mainPresenter = mainPresenter;
        drawCircle = new DrawCircle(number);
    }

    @Override
    public void start() {
        DrawCircle.RequestValues requestValues = new DrawCircle.RequestValues(view);
        handler.execute(drawCircle, requestValues, new IUseCaseCallback<DrawCircle.ResponseValues>() {
            @Override
            public void onSuccess(DrawCircle.ResponseValues response) {
                mainPresenter.setDotsResult(response.getTimes());
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void checkTouch(int x, int y) {
        drawCircle.checkTouch(x, y);
    }

    @Override
    public void stop() {
        drawCircle.stop();
    }
}
