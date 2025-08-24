---
title: "Androidメモ: Canvas への描画時にアンチエイリアスを有効にする (Paint#setAntiAlias)"
url: "p/8wsrauw/"
date: "2014-08-11"
tags: ["android"]
aliases: ["/android/ui/antialias-on-canvas.html"]
---

Android の `Canvas` への描画時に使用する `Paint` オブジェクトで、**`setAntiAlias(true)`** することで、アンチエイリアスを有効にした描画を行うことができます。

```java
public class MyCustomView extends View {
    private final Paint mBackgroundPaint = new Paint();

    // ...

    private void init() {
        mBackgroundPaint.setColor(Color.BLUE);
        mBackgroundPaint.setAntiAlias(true);  // ★アンチエイリアス有効化
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = canvas.getWidth();
        int h = canvas.getHeight();
        int radius = w < h ? w/2 : h/2;
        canvas.drawCircle(w / 2, h / 2, radius, mBackgroundPaint);  // アンチエイリアス有効にした円を描画
    }
}
```

