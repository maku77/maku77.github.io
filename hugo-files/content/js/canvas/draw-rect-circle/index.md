---
title: "JavaScriptメモ: HTML5 Canvas に矩形や円を描画する"
url: "p/pymfycy/"
date: "2012-01-20"
tags: ["javascript"]
aliases: [/js/canvas/draw-rect-circle.html]
---

{{< image src="img-001.png" >}}

下記のコードは、HTML5 の Canvas 要素に、矩形や円（あるいは半円）を描画するサンプルコードです。
Canvas 要素に対して描画を行うためには、`HTMLCanvasElement` の `getContext()` メソッドを使って、`CanvasRenderingContext2D` オブジェクトを取得します。

{{< code lang="html" title="draw-rect-circle-demo.html" >}}
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Canvas Sample</title>
  <style>
    canvas {
      background-color: #ffc;
      border: solid black 1px;
    }
  </style>
</head>
<body>
  <canvas id="my_canvas" width="200" height="150"></canvas>
  <script type="text/javascript" src="draw-rect-circle-demo.js"></script>
</body>
</html>
{{< /code >}}

{{< code lang="js" title="draw-rect-circle-demo.js" >}}
window.onload = function() {
    // Get a canvas context.
    var canvas = document.getElementById('my_canvas');  // HTMLCanvasElement
    var context = canvas.getContext('2d');  // CanvasRenderingContext2D

    // 赤色の矩形（外枠のみ）
    context.lineWidth = 5;         // 線の太さ
    context.strokeStyle = "#f00";  // 線の色
    context.strokeRect(10, 10, 100, 50);

    // 青色の矩形（塗りつぶし）
    context.fillStyle = "rgb(0,0,255)";  // 塗りつぶしの色
    context.fillRect(35, 35, 100, 50);

    // 半径 20px の円（外枠のみ）
    context.beginPath();
    context.arc(50, 120, 20, 0, Math.PI * 2, true);
    context.lineWidth = 3;          // 線の太さ
    context.strokeStyle = "brown";  // 線の色
    context.stroke();

    // 半径 40px の半円（塗りつぶし）
    context.beginPath();
    context.arc(150, 100, 40, Math.PI, Math.PI * 2, true);
    context.fillStyle = "#0f0";  // 塗りつぶしの色
    context.fill();
};
{{< /code >}}
