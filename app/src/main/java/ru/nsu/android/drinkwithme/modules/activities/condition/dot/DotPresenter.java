package ru.nsu.android.drinkwithme.modules.activities.condition.dot;

import ru.nsu.android.drinkwithme.common.useCaseEngine.IUseCaseCallback;
import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.modules.useCases.DrawCircle;

public class DotPresenter implements IDotPresenter {
    private IDotView view;
    private UseCaseHandler handler;
    private DrawCircle drawCircle;

    public DotPresenter(IDotView view, UseCaseHandler handler, int number) {
        this.view = view;
        this.handler = handler;
        drawCircle = new DrawCircle(number);
    }

    @Override
    public void start() {

        final DrawCircle.RequestValues requestValues = new DrawCircle.RequestValues(view);
        handler.execute(drawCircle, requestValues, new IUseCaseCallback<DrawCircle.ResponseValues>() {
            @Override
            public void onSuccess(DrawCircle.ResponseValues response) {

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
