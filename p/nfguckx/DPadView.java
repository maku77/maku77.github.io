package io.github.maku77.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Software DPad.
 */
public class DPadView extends View {
    private final RectF mTouchAreaLeft = new RectF();
    private final RectF mTouchAreaUp = new RectF();
    private final RectF mTouchAreaRight = new RectF();
    private final RectF mTouchAreaDown = new RectF();
    private DPadListener mListener;

    private final Paint mCursorPaint = new Paint() {
        {
            setColor(Color.argb(127, 44, 44, 44));
            setAntiAlias(true);
        }
    };

    private final Paint mBackgroundPaint = new Paint() {;
        {
            setColor(Color.argb(127, 88, 88, 88));
            setAntiAlias(true);
        }
    };

    public DPadView(Context context) {
        super(context);
    }

    public DPadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DPadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDPadListener(DPadListener listener) {
        mListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        calcTouchArea(w, h);
        drawBackground(canvas, w, h);
        drawCursor(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mListener == null) {
            return false;
        }

        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            if (mTouchAreaLeft.contains(x, y)) {
                mListener.onCursor(new DPadEvent(DPadEvent.ACTION_DOWN, DPadEvent.DIR_LEFT));
            }
            if (mTouchAreaUp.contains(x, y)) {
                mListener.onCursor(new DPadEvent(DPadEvent.ACTION_DOWN, DPadEvent.DIR_UP));
            }
            if (mTouchAreaRight.contains(x, y)) {
                mListener.onCursor(new DPadEvent(DPadEvent.ACTION_DOWN, DPadEvent.DIR_RIGHT));
            }
            if (mTouchAreaDown.contains(x, y)) {
                mListener.onCursor(new DPadEvent(DPadEvent.ACTION_DOWN, DPadEvent.DIR_DOWN));
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            if (mTouchAreaLeft.contains(x, y)) {
                mListener.onCursor(new DPadEvent(DPadEvent.ACTION_UP, DPadEvent.DIR_LEFT));
            }
            if (mTouchAreaUp.contains(x, y)) {
                mListener.onCursor(new DPadEvent(DPadEvent.ACTION_UP, DPadEvent.DIR_UP));
            }
            if (mTouchAreaRight.contains(x, y)) {
                mListener.onCursor(new DPadEvent(DPadEvent.ACTION_UP, DPadEvent.DIR_RIGHT));
            }
            if (mTouchAreaDown.contains(x, y)) {
                mListener.onCursor(new DPadEvent(DPadEvent.ACTION_UP, DPadEvent.DIR_DOWN));
            }
        }

        return true;
    };

    private void calcTouchArea(int canvasW, int canvasH) {
        int min = canvasW < canvasH ? canvasW : canvasH;
        float side = min / 3.0F;
        mTouchAreaLeft.set(0, side, side, side*2);
        mTouchAreaUp.set(side, 0, side*2, side);
        mTouchAreaRight.set(side*2, side, side*3, side*2);
        mTouchAreaDown.set(side, side*2, side*2, side*3);
    }

    private void drawBackground(Canvas canvas, int canvasW, int canvasH) {
        float cx = canvasW / 2.0F;
        float cy = canvasH / 2.0F;
        float r = cx < cy ? cx : cy;
        canvas.drawCircle(r, r, r * 0.95F, mBackgroundPaint);
    }

    private void drawCursor(Canvas canvas) {
        Path path = new Path();
        final float OFFSET = mTouchAreaLeft.width() / 5;

        // Left arrow
        path.moveTo(mTouchAreaLeft.left + OFFSET, mTouchAreaLeft.top + (mTouchAreaLeft.height() / 2));
        path.lineTo(mTouchAreaLeft.right - OFFSET, mTouchAreaLeft.top);
        path.lineTo(mTouchAreaLeft.right - OFFSET, mTouchAreaLeft.bottom);

        // Up arrow
        path.moveTo(mTouchAreaUp.left + (mTouchAreaUp.width() / 2), mTouchAreaUp.top + OFFSET);
        path.lineTo(mTouchAreaUp.right, mTouchAreaUp.bottom - OFFSET);
        path.lineTo(mTouchAreaUp.left, mTouchAreaUp.bottom - OFFSET);

        // Right arrow
        path.moveTo(mTouchAreaRight.left + OFFSET, mTouchAreaRight.top);
        path.lineTo(mTouchAreaRight.right - OFFSET, mTouchAreaRight.top + (mTouchAreaRight.height() / 2));
        path.lineTo(mTouchAreaRight.left + OFFSET, mTouchAreaRight.bottom);

        // Down arrow
        path.moveTo(mTouchAreaDown.right, mTouchAreaDown.top + OFFSET);
        path.lineTo(mTouchAreaDown.left + (mTouchAreaDown.width() / 2), mTouchAreaDown.bottom - OFFSET);
        path.lineTo(mTouchAreaDown.left, mTouchAreaDown.top + OFFSET);

        canvas.drawPath(path, mCursorPaint);
    }
}
