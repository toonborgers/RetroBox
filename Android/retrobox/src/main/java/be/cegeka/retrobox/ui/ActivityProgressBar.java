package be.cegeka.retrobox.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import be.cegeka.retrobox.R;

public class ActivityProgressBar extends View {
    private float progress;
    private int barColor;

    public ActivityProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        progress = 0f;
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawRoundedRect(canvas, getWidth(), getHeight(), getResources().getColor(R.color.activity_gray_text));
        drawRoundedRect(canvas, progressToWidth(), getHeight(), barColor);
    }

    private float progressToWidth() {
        float factor = progress / 100f;
        return getWidth() * factor;
    }

    private void drawRoundedRect(Canvas canvas, float right, float bottom, int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        RectF baseRect = new RectF(0, 0, right, bottom);
        float radius = getHeight() / 2f;
        canvas.drawRoundRect(baseRect, radius, radius, paint);
    }
}
