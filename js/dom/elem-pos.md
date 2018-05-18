---
title: "HTML 要素の位置を取得する"
date: "2018-05-18"
---

要素の位置を取得する API
----

ある HTML 要素の左上の座標を取得するには次のようにします。
ページの左上からの絶対座標や、親要素からの相対座標で取得することができます。

#### jQuery の例

~~~ javascript
var $e = $('#box');

// document（ページ左上）からの絶対座標
var x1 = $e.offset().top;
var y1 = $e.offset().left;

// 親要素の左上を (0,0) とした相対座標
var x2 = $e.position().top;
var y2 = $e.position().left;
~~~

次のデモでは、div 要素 (`#box`) の座標をリアルタイムに取得して表示しています。
これらの座標は、ページをスクロールしても変化しないところがポイントです（ただし、ズームを行ってレイアウトが崩れた場合などは、座標値が変化することがあります）。

#### デモ（<a target="_blank" href="elem-pos-demo.html">別ウィンドウで開く</a>）

<iframe class="maku-htmlDemo" src="elem-pos-demo.html"></iframe>


ウィンドウ左上を基準にした要素の位置を取得
----

ウィンドウの左上を原点 (0, 0) とみなした相対座標で要素の位置を取得するには次のようにします。

#### プレーンな JavaScript (Vanilla JavaScript) の例

~~~ javascript
var e = document.getElementById('box');

// スクリーン左上を (0, 0) とした相対座標
var rect = e.getBoundingClientRect();
console.log(rect.top);
console.log(rect.left);
~~~

#### jQuery を使った例（jQuery 用の独自の関数はない）

~~~ javascript
var $e = $('#box');

// スクリーン左上を (0, 0) とした相対座標
var rect = $e.get(0).getBoundingClientRect();
console.log(rect.top);
console.log(rect.left);
~~~

この座標値は、ウィンドウ内で表示されている要素の位置が変化すると同時に変化します。
要素が画面の上に隠れている場合は、`top` プロパティの値はマイナスになります。


スクロール量を取得する
----

ページをスクロールしたときのスクロール量は次のように取得することができます。
ここでは、スクロール時のイベントをハンドルして、スクロール量を表示するようにしています。

#### プレーンな JavaScript の例 (Vanilla JavaScript)

~~~ javascript
window.addEventListener('scroll', function(event) {
  console.log(window.scrollY);  // 縦方向のスクロール量
  console.log(window.scrollX);  // 横方向のスクロール量
});
~~~

#### jQuery を使った例

~~~ javascript
$(function () {
  var $wnd = $(window);
  $wnd.scroll(function() {
    console.log($wnd.scrollTop());   // 縦方向のスクロール量
    console.log($wnd.scrollLeft());  // 横方向のスクロール量
  });
});
~~~

