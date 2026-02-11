---
title: "JavaScriptメモ: ウィンドウサイズに応じて HTML5 Canvas のサイズを変更する"
url: "p/iqkhk2x/"
date: "2012-01-20"
tags: ["javascript"]
aliases: [/js/canvas/auto-resize.html]
---

下記のサンプルでは、クライアントサイズの変更（ブラウザウィンドウのサイズ変更）に応じて HTML5 Canvas のサイズを設定しています。
Canvas のサイズをクライアントサイズほぼいっぱいに表示されるように調整し、その中に半分の大きさで矩形を描画しています。

<iframe class="xHtmlDemo" src="auto-resize-demo.html"></iframe>
{{< file src="auto-resize-demo.html" caption="デモページを開く (auto-resize-demo.html)" >}}

{{< code lang="html" title="sample.html" >}}
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="utf-8">
  <title>Canvas サイズの動的変更のデモページ</title>
  <style>
    body { margin: 0; padding: 0; }
  </style>
</head>
<body>
  <canvas id="my_canvas" />
  <script>
  window.onload = () => {
    const canvas = document.getElementById("my_canvas");
    const ctx = canvas.getContext("2d");

    function fitCanvasSize() {
      // Canvas のサイズをクライアントサイズに合わせる
      canvas.width = document.documentElement.clientWidth - 10;
      canvas.height = document.documentElement.clientHeight - 10;

      // Canvas 全体を塗りつぶし
      ctx.fillStyle = "rgb(191, 255, 191)";
      ctx.fillRect(0, 0, canvas.width, canvas.height);

      // Canvas サイズに合わせて矩形を描画
      const w = canvas.width / 2;
      const h = canvas.height / 2;
      ctx.fillStyle = "cyan";
      ctx.fillRect(10, 10, w, h);
    }

    fitCanvasSize();
    window.onresize = fitCanvasSize;
  }
  </script>
</body>
</html>
{{< /code >}}
