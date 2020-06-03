package ru.nsu.android.drinkwithme.modules.useCases;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.modules.database.parameters.IParametersDBHandler;

public class UpdateParameters extends UseCase<UpdateParameters.RequestValues, UpdateParameters.ResponseValues> {
    private IParametersDBHandler dbHandler;

    public UpdateParameters(IParametersDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        dbHandler.setName(requestValues.getName());
        dbHandler.setWeight(requestValues.getWeight());
        dbHandler.setHeight(requestValues.getHeight());
        dbHandler.setGender(requestValues.getGender());
        getUseCaseCallback().onSuccess(new ResponseValues());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String name;
        private int weight;
        private int height;
        private String gender;

        public RequestValues(String name, int weight, int height, String gender) {
            this.name = name;
            this.weight = weight;
            this.height = height;
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public int getWeight() {
            return weight;
        }

        public int getHeight() {
            return height;
        }

        public String getGender() {
            return gender;
        }
    }

    public static final class ResponseValues implements UseCase.ResponseValues {

    }
}
