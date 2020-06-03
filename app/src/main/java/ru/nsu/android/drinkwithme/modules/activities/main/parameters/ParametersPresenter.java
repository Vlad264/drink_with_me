package ru.nsu.android.drinkwithme.modules.activities.main.parameters;

import ru.nsu.android.drinkwithme.common.useCaseEngine.IUseCaseCallback;
import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCaseHandler;
import ru.nsu.android.drinkwithme.modules.database.parameters.IParametersDBHandler;
import ru.nsu.android.drinkwithme.modules.useCases.GetParameters;
import ru.nsu.android.drinkwithme.modules.useCases.UpdateParameters;

public class ParametersPresenter implements IParametersPresenter {
    private IParametersView view;
    private UseCaseHandler handler;
    private IParametersDBHandler dbHandler;

    public ParametersPresenter(IParametersView view, UseCaseHandler handler, IParametersDBHandler dbHandler) {
        this.view = view;
        this.handler = handler;
        this.dbHandler = dbHandler;
    }

    @Override
    public void start() {
        GetParameters getParameters = new GetParameters(dbHandler);
        GetParameters.RequestValues requestValues = new GetParameters.RequestValues();
        handler.execute(getParameters, requestValues, new IUseCaseCallback<GetParameters.ResponseValues>() {
            @Override
            public void onSuccess(GetParameters.ResponseValues response) {
                view.showParameters(response.getName(), response.getWeight(), response.getHeight(), response.getGender());
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void saveParameters(String name, int weight, int height, String gender) {
        UpdateParameters updateParameters = new UpdateParameters(dbHandler);
        UpdateParameters.RequestValues requestValues = new UpdateParameters.RequestValues(name, weight, height, gender);
        handler.execute(updateParameters, requestValues, new IUseCaseCallback<UpdateParameters.ResponseValues>() {
            @Override
            public void onSuccess(UpdateParameters.ResponseValues response) {
                view.showSuccess();
            }

            @Override
            public void onError() {

            }
        });
    }
}
