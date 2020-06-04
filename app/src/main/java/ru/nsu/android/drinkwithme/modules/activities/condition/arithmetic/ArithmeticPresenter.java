package ru.nsu.android.drinkwithme.modules.activities.condition.arithmetic;

import ru.nsu.android.drinkwithme.common.useCaseEngine.IUseCaseCallback;
import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.modules.activities.condition.IConditionCheckPresenter;
import ru.nsu.android.drinkwithme.modules.useCases.CreateArithmetic;

public class ArithmeticPresenter implements IArithmeticPresenter {
    private IArithmeticView view;
    private UseCaseHandler handler;
    private IConditionCheckPresenter mainPresenter;
    private CreateArithmetic createArithmetic;

    public ArithmeticPresenter(IArithmeticView view, UseCaseHandler handler, IConditionCheckPresenter mainPresenter, int number) {
        this.view = view;
        this.handler = handler;
        this.mainPresenter = mainPresenter;
        createArithmetic = new CreateArithmetic(number);
    }

    @Override
    public void start() {
        CreateArithmetic.RequestValues requestValues = new CreateArithmetic.RequestValues(view);
        handler.execute(createArithmetic, requestValues, new IUseCaseCallback<CreateArithmetic.ResponseValues>() {
            @Override
            public void onSuccess(CreateArithmetic.ResponseValues response) {
                mainPresenter.setArithmeticResult(response.getTimes());
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void checkAnswer(int answer) {
        createArithmetic.checkAnswer(answer);
    }

    @Override
    public void stop() {
        createArithmetic.stop();
    }
}
