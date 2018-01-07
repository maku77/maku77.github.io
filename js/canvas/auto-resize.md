---
title: ウィンドウサイズに応じて HTML5 Canvas のサイズを変更する
date: "2012-01-20"
---

この例では、クライアントサイズの横幅に応じて HTML5 Canvas のサイズを設定しています。
Canvas のサイズは、横:縦=2:1 の比になるように縦サイズを調節しています。

- [デモページ](auto-resize-demo.html)

~~~ html
<DOCTYPE! html>
<html>
  <script>
  function fitCanvasSize() {
    // Canvas のサイズを変更
    var canvas = document.getElementById("my_canvas");
    canvas.width = document.body.clientWidth - 10;
    canvas.height = canvas.width / 2;

    // 矩形のサイズを決定
    var w = canvas.width - 30;
    var h = w / 2;

    // 塗りつぶし＆枠線
    var context = canvas.getContext("2d");
    context.fillStyle = "rgb(191, 255, 191)";
    context.fillRect(10, 10, w, h);
    context.strokeStyle = "darkgreen";
    context.strokeRect(10, 10, w, h);
  }

  window.onload = fitCanvasSize;
  window.onresize = fitCanvasSize;
  </script>

  <body>
    <canvas id="my_canvas" />
  </body>
</html>
~~~

