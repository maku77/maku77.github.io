---
title: "Web ブラウザのページ表示位置を調べる（スクロール位置を取得する）"
date: "2018-05-19"
---

ウィンドウ（表示領域）上端の座標を取得する
----

Web ブラウザで今表示されている部分（ビューポート）の位置、つまり現在のページのスクロール量を調べるには、以下のようにします。

### プレーンな JavaScript の例 (Vanilla JavaScript)

~~~ javascript
window.addEventListener('scroll', function(event) {
  console.log(window.pageYOffset);  // 縦方向のスクロール量
  console.log(window.pageXOffset);  // 横方向のスクロール量
});
~~~

ここでは、スクロール時のイベントをハンドルして、座標表示を更新しています。

<div class="note">
<code>pageYOffset</code> の代わりに新しい <code>scrollY</code> プロパティを使用することもできます。
新しいブラウザのほとんどは <code>scrollY</code> プロパティもサポートしているので、<code>scrollY</code> を使ってもよいでしょう。
互換性を重要視する jQuery などの実装では、<code>pageYOffset</code> を使用しているようです。
</div>

#### デモ（<a target="_blank" href="scroll-pos-demo.html">別ウィンドウで開く</a>）

<iframe class="maku-htmlDemo" src="scroll-pos-demo.html"></iframe>

上記のデモでは、ページをスクロールすると、スクロール後の表示位置がウィンドウ左上に表示されます。


### jQuery を使った例

jQuery を使った場合も、ほぼ同じように記述できますが、プレーンな JavaScript でももともと簡単なので、jQuery を使用する意味はほとんどありません（パフォーマンスが悪くなるだけ）。

~~~ javascript
$(function () {
  var $wnd = $(window);
  $wnd.scroll(function() {
    console.log($wnd.scrollTop());   // 縦方向のスクロール量
    console.log($wnd.scrollLeft());  // 横方向のスクロール量
  });
});
~~~


ウィンドウ下端の座標を取得する
----

`window.pageYOffset` (`scrollY`) や `window.pageXOffset` (`scrollX`) は、表示領域左上の座標しか取得できませんが、`window.innerHeight` や `window.innerWidth` を使ってウィンドウサイズを取得すれば、表示領域右下の座標を計算することができます。

~~~ javascript
window.addEventListener('scroll', function(event) {
  var right = window.pageXOffset + window.innerWidth;
  var bottom = window.pageYOffset + window.innerHeight;
  console.log(right + ', ' + bottom);
});
~~~

<div class="note">
<code>window.innerWidth</code> には垂直方向のスクロールバーを含んだウィンドウ幅が格納されています。
jQuery の <code>$(window).width()</code> を使用すると、スクロールバーのサイズを除いたウィンドウ幅を取得することができます。
</div>

