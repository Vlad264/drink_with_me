package ru.nsu.android.drinkwithme.modules.activities.condition;

import android.app.Activity;
import android.content.Intent;

import ru.nsu.android.drinkwithme.R;

public class ConditionCheckPresenter implements IConditionCheckPresenter {
    private ConditionCheckActivity view;
    private long[] dotTimes;
    private long[] arithmeticTimes;
    private int dotsNumber;
    private int arithmeticNumber;

    public ConditionCheckPresenter(ConditionCheckActivity view) {
        this.view = view;
        dotsNumber = Integer.parseInt(view.getString(R.string.dot_test_number));
        arithmeticNumber = Integer.parseInt(view.getString(R.string.arithmetic_test_number));
    }

    @Override
    public void start() {

    }

    @Override
    public void setDotsResult(long[] times) {
        dotTimes = times;
        view.startArithmetic();
    }

    @Override
    public void setArithmeticResult(long[] times) {
        arithmeticTimes = times;
        Intent intent = new Intent();
        intent.putExtra("RESULT", calculate());
        view.setResult(Activity.RESULT_OK, intent);
        view.finish();
    }

    private int calculate() {
        return Math.round((calculateDot() + calculateArithmetic()) / (dotsNumber + arithmeticNumber));
    }

    private float calculateDot() {
        float count = 0;
        for (int i = 0; i < dotsNumber; ++i) {
            long time = dotTimes[i];
            if (time < 1000) {
                count += 0;
            } else if (time < 2000) {
                count += 1;
            } else if (time < 3000) {
                count += 2;
            } else {
                count += 3;
            }
        }
        return count;
    }

    private float calculateArithmetic() {
        float count = 0;
        for (int i = 0; i < arithmeticNumber; ++i) {
            long time = arithmeticTimes[i];
            if (time < 8000) {
                count += 0;
            } else if (time < 16000) {
                count += 1;
            } else if (time < 24000) {
                count += 2;
            } else {
                count += 3;
            }
        }
        return count;
    }
}
