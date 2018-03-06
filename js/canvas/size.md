---
title: "HTML5 Canvas 要素のサイズ指定について"
date: "2018-03-06"
---

canvas 要素のサイズ指定方法には下記のようなものがあります。

* canvas 要素の width、height 属性
* JavaScript から見える canvas 要素の width、height プロパティ
* CSS による width、height プロパティ

キャンバスサイズ（描画バッファサイズ）の指定
----

JavaScript から Canvas API を使用するときに見えるキャンバスサイズ（描画バッファサイズ）は、HTML の canvas 要素の width、height 属性で指定したサイズになります。

~~~ html
<canvas id="canvas" width="200" height="150">
  このブラウザは HTML5 Canvas に対応していません。
</canvas>
~~~

これらの属性値を省略した場合の規定値は、幅 300 ピクセル、高さ 150 ピクセルとなっています。

このキャンバスサイズは、JavaScript の中から次のように設定することもできます。

~~~ javascript
var canvas = document.getElementById('canvas');
canvas.width = 400;
canvas.height = 300;
~~~

どちらの方法で指定するかは、そのウェブサイト（ウェブアプリ）の特性によって選択すればよいでしょう。


画面上の表示サイズの指定
----

CSS スタイルシートで canvas 要素の width、height 指定を行うと、表示上のサイズがそのサイズに拡大、あるいは縮小されて表示されます。

~~~ css
#canvas {
  width: 600px;
}
~~~

上記のように、width プロパティのみを指定すると、描画バッファサイズのアスペクト比を保つように縦のサイズも引き伸ばされて表示されます。
例えば、次のようなサイズ指定を行うと、

~~~ html
<canvas id="canvas" width="100" height="50" style="width:500px;"></canvas>
~~~

描画バッファサイズが 100x50 なのに比べ、実際の描画サイズが 500x250 となるので、描画される図形は5倍に拡大されて大分ぼやけて表示されることになります。

#### デモ（半径20の円を5倍に拡大して表示）

<canvas id="canvas" width="100" height="50" style="width:500px;">
  このブラウザは HTML5 Canvas に対応していません。
</canvas>
<script>
window.onload = function () {
  //var ctx = canvas.getContext('2d', { alpha: false });
  var canvas = document.getElementById('canvas');
  var ctx = canvas.getContext('2d');

  // 半径 20px の円を中央に描く
  ctx.arc(50, 25, 20, 0, Math.PI * 2, true);
  ctx.closePath();
  ctx.lineWidth = 3;          // 線の太さ
  ctx.strokeStyle = "brown";  // 線の色
  ctx.fillStyle = "red";      // 塗りつぶしの色
  ctx.fill();
  ctx.stroke();
}
</script>

