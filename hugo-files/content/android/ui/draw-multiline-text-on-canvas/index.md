---
title: "Androidメモ: Canvas に複数行のテキストを描画する"
url: "p/j7yysr5/"
date: "2014-07-28"
tags: ["android"]
aliases: ["/android/ui/draw-multiline-text-on-canvas.html"]
---

Android の `SurfaceView` にテキストを描画するには、`Canvas#drawText()` を使用しますが、このメソッドでは一行のテキストのみ描画できます。
特定の幅でテキストを折り返して Canvas に描画するには、折り返し計算と複数行テキストの描画処理を自分で実装する必要があります。

{{< image src="img-001.png" >}}

- [TextDrawUtil.java](./TextDrawUtil.java)

上記のユーティリティクラスを使用すると、特定の幅、高さに収まるように改行しながらテキストを描画できます。

```java
// 座標 (0, 0) に、幅 80、高さ 100 に収まるようにテキスト描画
TextDrawUtil.drawMultilineText(canvas, text, 0, 0, 80, 100, mPaint);
```

同じテキストを繰り返し描画する場合は、描画内容を `Bitmap` インスタンスに保持して、`Canvas#drawBitmap()` で描画する方が効率的です。
上記のユーティリティには、テキストを描画した `Bitmap` インスタンスを生成するメソッドも用意しています。

```java
Bitmap bitmap = TextDrawUtil.createTextBitmap(text, 80, 100, mPaint);

// 作ったビットマップは以下のようにして描画
canvas.drawBitmap(bitmap, 0, 0, null);
```

以下は、全体的な構成のサンプルです。

{{< code lang="java" title="MyView.java" >}}
public class MyView extends View {
    // ...
    private final Paint mPaintRect = new Paint() {
        {
            setColor(Color.BLUE);
        }
    };

    private final Paint mPaintText = new Paint() {
        {
            setColor(Color.WHITE);
            setTextSize(24);
            setAntiAlias(true);
        }
    };

    private final RectF mRect = new RectF(50, 50, 130, 155);

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw a background of the text.
        canvas.drawRect(mRect, mPaintRect);

        // Draw a multiline text.
        final String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        TextDrawUtil.drawMultilineText(canvas, text, mRect, mPaintText);
    }
}
{{< /code >}}

