package ru.nsu.android.drinkwithme.modules.useCases;

import java.util.Random;

import ru.nsu.android.drinkwithme.common.useCaseEngine.UseCase;
import ru.nsu.android.drinkwithme.modules.activities.condition.dot.IDotView;

public class DrawCircle extends UseCase<DrawCircle.RequestValues, DrawCircle.ResponseValues> {
    private boolean run = true;
    private boolean modify = true;
    private boolean checked = false;

    private int radius;
    private int width;
    private int height;
    private int number;

    private int curX;
    private int curY;
    private int checkX;
    private int checkY;

    private int curIteration = 0;
    private long startTime;

    public DrawCircle(int number) {
        this.number = number;
    }

    public void checkTouch(int x, int y) {
        checkX = x;
        checkY = y;
        checked = true;
    }

    public void stop() {
        run = false;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        radius = requestValues.getView().getRadius();
        width = requestValues.getView().getWidth();
        height = requestValues.getView().getHeight();
        Random random = new Random();
        long[] times = new long[number];
        while (run) {
            if (modify) {
                modify = false;
                curX = random.nextInt(width - 2 * radius) + radius;
                curY = random.nextInt(height - 2 * radius) + radius;
                startTime = System.currentTimeMillis();
                requestValues.getView().drawCircle(curX, curY);
            }
            if (checked && ((curX - checkX) * (curX - checkX) + (curY - checkY) * (curY - checkY) <= radius * radius)) {
                times[curIteration] = System.currentTimeMillis() - startTime;
                modify = true;
                checked = false;
                curIteration++;
                if (curIteration == number) {
                    stop();
                }
            }
        }
        getUseCaseCallback().onSuccess(new ResponseValues(times));
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private IDotView view;

        public RequestValues(IDotView view) {
            this.view = view;
        }

        public IDotView getView() {
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
