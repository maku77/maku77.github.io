package io.github.maku77.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class TextDrawUtil {
    /**
     * Draw the multiple line text to the canvas.
     *
     * @param canvas Draw the text to this canvas.
     * @param text Text to be drawn.
     * @param x Left position to draw.
     * @param y Top position to draw.
     * @param width Maximum width of drawing area.
     * @param height Maximum height of drawing area.
     * @param paint Paint information to obtain font metrics.
     */
    public static void drawMultilineText(Canvas canvas, String text, float x, float y,
            float width, float height, Paint paint) {
        final float lineHeight = calcLineHeight(paint);
        final int len = text.length();
        float sumHeight = lineHeight;
        int index = 0;
        while (index < len && sumHeight <= height) {
            final int count = paint.breakText(text, index, len, true, width, null);
            if (count == 0) {
                // Just one character cannot be drawn.
                break;
            }
            canvas.drawText(text, index, index + count, x, y + sumHeight, paint);
            index += count;
            sumHeight += lineHeight;
        }
    }

    /**
     * @see drawMultilineText(Canvas, String, float, float, float, float, Paint)
     */
    public static void drawMultilineText(Canvas canvas, String text, RectF rect, Paint paint) {
        drawMultilineText(canvas, text, rect.left, rect.top, rect.width(), rect.height(), paint);
    }

    /**
     * @see drawMultilineText(Canvas, String, float, float, float, float, Paint)
     */
    public static void drawMultilineText(Canvas canvas, String text, Rect rect, Paint paint) {
        drawMultilineText(canvas, text, rect.left, rect.top, rect.width(), rect.height(), paint);
    }

    public static Bitmap createTextBitmap(String text, int width, int height, Paint paint) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        TextDrawUtil.drawMultilineText(canvas, text, 0, 0, width, height, paint);
        return bitmap;
    }

    private static float calcLineHeight(Paint paint) {
        // If you need more space, try paint.getFontMetrics(null).
        return paint.getTextSize();
    }
}
