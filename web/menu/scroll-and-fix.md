---
title: "ある位置までスクロールしたら固定されるメニューを作成する"
date: "2018-05-15"
---

ページをスクロールしたときに、メニュー（サイドバー）のスクロールを画面上端あたりまでに制限する方法には、CSS の sticky position を使う方法や、JavaScript を使用する方法があります。


position: sticky を使ってサイドバーのスクロールを抑制する方法
----

下記のデモページをスクロールすると、最初はメニュー（サイドバー部分）も一緒にスクロールしますが、ある位置を超えるとメニュー位置が固定されます。
このやり方は、ページ上部のヘッダに大きなアイキャッチ画像などを入れているサイトなどでたまに見かけます。

#### デモ（<a target="_blank" href="scroll-and-fix-demo1.html">別ウィンドウで開く</a>）

<iframe class="maku-htmlDemo" src="scroll-and-fix-demo1.html"></iframe>

CSS の [Sticky positioning](https://developer.mozilla.org/ja/docs/web/css/position#Sticky_positioning) に対応したブラウザであれば、次のようにして要素のスクロールを簡単に制限することができます。

~~~ css
.yourElement {
    position: -webkit-sticky;  /* for Safari */
    position: sticky;
    top: 30px;  /* 上端の閾値 */
}
~~~

`position: sticky;` を指定すると、デフォルトでは `position: relative;` と同様な振る舞いをしますが、その要素が画面上の指定した位置（上記の例では上端から 30 px）に来ると、要素の位置が固定されます。
この振る舞いは、親要素の表示範囲内で効いてくるため、画面下端のフッター領域が表示されるタイミングでは、メニュー領域はちゃんと上にスクロールされて消えていきます（うまくできています）。

下記のコードは、上記のデモページを構成している HTML と CSS の抜粋です。

#### HTML 抜粋

~~~ html
<div id="headerArea"></div>
<div id="container">
  <div id="mainArea">
    本文<br>本文<br>本文<br>本文<br>本文 ...
  </div>
  <div id="sidebarArea">
    <div id="sidebar">
      メニュー<br>メニュー<br>メニュー<br>メニュー ...
    </div>
  </div>
</div>
<div id="footerArea"></div>
~~~

#### CSS 抜粋

~~~ css
/* ヘッダー部分 */
#headerArea {
  height: 100px;
  background: gray;
}

/* フッター部分 */
#footerArea {
  height: 300px;
  background: gray;
}

/* 本文領域とサイドバー領域を含むコンテナ */
#container {
  /* フレキシブルボックスレイアウトによる段組 */
  display: flex;
  display: -webkit-flex;
  display: -webkit-box;
  display: -moz-box;
}

/* 左側の本文領域 */
#mainArea {
  background: lightgray;

  /* 画面幅が広い時はこの要素の横幅を拡張する */
  flex-grow: 1;
  -webkit-flex-grow: 1;
  -webkit-box-flex: 1;
  -moz-box-flex: 1;
}

/* 右側のサイドバー領域 */
#sidebarArea {
  width: 100px;
  background: yellow;
}

/* サイドバー内のメニュー ★ここがポイント */
#sidebar {
  background: magenta;

  /* Sticky positioning の設定 */
  position: -webkit-sticky;  /* for Safari */
  position: sticky;
  top: 30px;  /* 上端の閾値 */
}
~~~


<div class="note">
Sticky positioning は、主にアルファベット順や五十音順のリストの見出しに使用されます（<a target="_blank" href="https://developer.mozilla.org/ja/docs/web/css/position#Sticky_positioning">こちらの例</a>を見るとわかりやすいです）。
サイドバーなどのメニュー全体の上端を固定するような用途で使用すると、メニューが長くなった場合に、メニューの下の方の項目を表示するためにたくさんスクロールしなければならなくなってしまうので注意してください。
</div>



JavaScript を使ってサイドバーのスクロールを抑制する方法
----

#### デモ（<a target="_blank" href="scroll-and-fix-demo2.html">別ウィンドウで開く</a>）

<iframe class="maku-htmlDemo" src="scroll-and-fix-demo2.html"></iframe>

JavaScript でスクロールイベントをハンドルして、メニュー要素の位置をうまく制御するという方法もあります。
この例では、メニューが指定した位置までスクロールしたら、`position: fixed` プロパティを設定して、位置を固定するようにしています。

#### HTML 抜粋

~~~ html
<div id="headerArea"></div>
<div id="container">
  <div id="mainArea">
    本文<br>本文<br>本文<br>本文<br>本文 ...
  </div>
  <div id="sidebarArea">
    <div id="sidebar">
      メニュー<br>メニュー<br>メニュー<br>メニュー ...
    </div>
  </div>
</div>
<div id="footerArea"></div>
~~~

#### CSS 抜粋

~~~ css
/* ヘッダー部分 */
#headerArea {
  height: 100px;
  background: gray;
}

/* フッター部分 */
#footerArea {
  height: 300px;
  background: gray;
}

/* floating レイアウトの解除用 */
#container::after {
  display: block;
  content: "";
  clear: both;
}

/* 左側の本文領域 */
#mainArea {
  float: left;
  width: calc(100% - 150px);
  background: lightgray;
}

/* 右側のサイドバー領域 */
#sidebarArea {
  float: left;
}

/* サイドバー内のメニュー */
#sidebar {
  /*
   * position: fixed; にした瞬間に、親要素 (#sidebarArea) から
   * 切り離されるので、親要素ではなく、この要素に width プロパティを
   * 指定しておく必要がある。
   */
  width: 150px;
  background: magenta;
}

/* ある位置までスクロールしたらサイドバーの位置指定を fixed にする */
.sidebar-fixed {
  position: fixed;
  top: 30px;
}
~~~

#### JavaScript 抜粋（jQuery を使用）

~~~ javascript
$(function() {
  var MARGIN_TOP = 30;  // どの位置で固定するか（閾値）
  var sidebar = $('#sidebar');
  var sidebar_top = sidebar.offset().top;  // サイドバーの初期位置

  $(window).scroll(function() {
    if ($(window).scrollTop() + MARGIN_TOP > sidebar_top) {
      sidebar.addClass('sidebar-fixed');
    } else {
      sidebar.removeClass('sidebar-fixed');
    }
  });
});
~~~

ただし、JavaScript でスクロールイベントをハンドルするやり方は、パフォーマンスの観点などからあまりお勧めはできません。
要素下端の処理も複雑なので（上記の例ではちゃんと処理していません）、できるだけ CSS の Sticky positioning を使う方法を採用した方がよいでしょう。

