package ru.nsu.android.drinkwithme.modules.useCases;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.modules.database.parameters.IParametersDBHandler;

public class GetParameters extends UseCase<GetParameters.RequestValues, GetParameters.ResponseValues> {
    private IParametersDBHandler dbHandler;

    public GetParameters(IParametersDBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        String name = dbHandler.getName();
        int weight = dbHandler.getWeight();
        int height = dbHandler.getHeight();
        String gender = dbHandler.getGender();
        getUseCaseCallback().onSuccess(new ResponseValues(name, weight, height, gender));
    }

    public static final class RequestValues implements UseCase.RequestValues {

    }

    public static final class ResponseValues implements UseCase.ResponseValues {
        private String name;
        private int weight;
        private int height;
        private String gender;

        public ResponseValues(String name, int weight, int height, String gender) {
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
}
