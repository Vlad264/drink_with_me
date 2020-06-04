package ru.nsu.android.drinkwithme.modules.useCases;

import java.util.Random;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.modules.activities.condition.arithmetic.IArithmeticView;
import ru.nsu.android.drinkwithme.modules.activities.condition.dot.IDotView;

public class CreateArithmetic extends UseCase<CreateArithmetic.RequestValues, CreateArithmetic.ResponseValues> {
    private boolean run = true;
    private boolean modify = true;
    private boolean checked = false;

    private int trueAnswer;
    private int answer;

    private int number;
    private int curIteration = 0;
    private long startTime;

    public CreateArithmetic(int number) {
        this.number = number;
    }

    public void checkAnswer(int answer) {
        this.answer = answer;
        checked = true;
    }

    public void stop() {
        run = false;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        Random random = new Random();
        long[] times = new long[number];
        while (run) {
            if (modify) {
                modify = false;
                String arithmetic = "";
                int op = random.nextInt(3);
                int a;
                int b;
                switch (op) {
                    case 0:
                        a = random.nextInt(50) + 1;
                        b = random.nextInt(50) + 1;
                        arithmetic = a + " + " + b;
                        trueAnswer = a + b;
                        break;
                    case 1:
                        a = random.nextInt(50) + 1;
                        b = random.nextInt(50) + 1;
                        arithmetic = a + " - " + b;
                        trueAnswer = a - b;
                        break;
                    case 2:
                        a = random.nextInt(10) + 1;
                        b = random.nextInt(10) + 1;
                        arithmetic = a + " * " + b;
                        trueAnswer = a * b;
                        break;
                }
                startTime = System.currentTimeMillis();
                requestValues.getView().setArithmetic(arithmetic);
            }
            if (checked) {
                checked = false;
                if (answer == trueAnswer) {
                    times[curIteration] = System.currentTimeMillis() - startTime;
                    requestValues.getView().showSuccess();
                    modify = true;
                    curIteration++;
                    if (curIteration == number) {
                        stop();
                    }
                } else {
                    requestValues.getView().showFailure();
                }
            }
        }
        getUseCaseCallback().onSuccess(new ResponseValues(times));
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private IArithmeticView view;

        public RequestValues(IArithmeticView view) {
            this.view = view;
        }

        public IArithmeticView getView() {
            return view;
        }
    }

    public static final class ResponseValues implements UseCase.ResponseValues {
        private long[] times;

        public ResponseValues(long[] times) {
            this.times = times;
        }

        public long[] getTimes() {
            return times;
        }
    }
}
