package ru.nsu.android.drinkwithme.modules.activities.condition.dot;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.nsu.android.drinkwithme.R;

public class DotFragment extends Fragment implements IDotView, SurfaceHolder.Callback {
    private IDotPresenter presenter;

    private SurfaceView dotSurfaceView;
    private SurfaceHolder holder;
    private Paint paint;

    private int width;
    private int height;
    private int radius;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_dot, container, false);

        dotSurfaceView = view.findViewById(R.id.dot_surface_view);
        holder = dotSurfaceView.getHolder();
        holder.addCallback(this);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    presenter.checkTouch((int) event.getX(), (int) event.getY());
                }
                return true;
            }
        });

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        radius = getResources().getDimensionPixelSize(R.dimen.circle_radius);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().post(new Runnable() {
            @Override
            public void run() {
                width = getView().getWidth();
                height = getView().getHeight();
                presenter.start();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stop();
    }

    @Override
    public void setPresenter(IDotPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void drawCircle(int x, int y) {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(x, y, radius, paint);
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}
