---
title: HTML5 Canvas によるテキストの描画サイズを調べる
date: "2015-04-08"
---

HTML5 Canvas に描画したテキストのサイズは、`CanvasRenderingContext2D.measureText()` を使用して調べることができます。
`measureText()` の戻り値は `TextMetrics` オブジェクトになっており、実際の描画領域のサイズを取得することができます。

- [CanvasRenderingContext2D.measureText() - Web APIs｜MDN](https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/measureText)
- [TextMetrics - Web APIs｜MDN](https://developer.mozilla.org/en-US/docs/Web/API/TextMetrics)

得られた `TextMetrics` オブジェクトの `width` プロパティを参照することで、実際の描画幅 (px) を調べることができます。
下記の例では、テキスト「あいうえお」を実際に描画した時の横幅を調べて、その幅で矩形を表示しています。

#### 実行結果

![measure-text.png](measure-text.png)

#### sample.html

~~~ html
<canvas id="my-canvas" width="200" height="100"></canvas>

<script>
var canvas = document.getElementById('my-canvas');
var ctx = canvas.getContext('2d'); // CanvasRenderingContext2D

// フォントの設定
ctx.font = '20pt Arial';

// 描画サイズ (TextMetrics) の取得
var metrics = ctx.measureText('あいうえお');

// 取得した横幅で Rectangle と Text を描画
ctx.fillStyle = 'rgba(255, 0, 0, 0.25)';
ctx.fillRect(20, 50, metrics.width, -24);
ctx.fillStyle = 'rgb(255, 0, 0)';
ctx.fillText('あいうえお', 20, 50);
</script>
~~~

