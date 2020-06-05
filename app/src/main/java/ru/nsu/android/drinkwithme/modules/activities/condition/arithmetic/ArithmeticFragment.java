package ru.nsu.android.drinkwithme.modules.activities.condition.arithmetic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.nsu.android.drinkwithme.R;

public class ArithmeticFragment extends Fragment implements IArithmeticView {
    private IArithmeticPresenter presenter;

    private TextView arithmeticTextView;
    private EditText answerEditText;
    private Button answerButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_arithmetic, container, false);

        arithmeticTextView = view.findViewById(R.id.arithmetic_text);
        answerEditText = view.findViewById(R.id.answer_edit);
        answerButton = view.findViewById(R.id.answer_button);

        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.answer_empty_error),
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                int answer = Integer.parseInt(answerEditText.getText().toString());
                presenter.checkAnswer(answer);
                answerEditText.setText("");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stop();
    }

    @Override
    public void setPresenter(IArithmeticPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setArithmetic(String arithmetic) {
        arithmeticTextView.setText(arithmetic);
    }

    @Override
    public void showSuccess() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),
                        getResources().getString(R.string.success),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public void showFailure() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),
                        getResources().getString(R.string.failure),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public void showTime(final long time) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), Long.toString(time), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
