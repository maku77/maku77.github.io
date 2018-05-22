---
title: "ある位置までスクロールしたら固定されるメニューを作成する"
date: "2018-05-15"
---

ページをスクロールしたときに、メニュー（サイドバー）のスクロールを画面上端あたりまでに制限する方法には、CSS の sticky position を使う方法や、JavaScript を使用する方法があります。


position: sticky を使ってサイドバーのスクロールを抑制する方法
----

下記のデモページをスクロールすると、最初はメニュー（サイドバー部分）も一緒にスクロールしますが、ある位置を超えるとメニュー位置が固定されます。
このやり方は、ページ上部のヘッダに大きなアイキャッチ画像などを入れているサイトなどでたまに見かけます。

#### デモ（<a target="_blank" href="scroll-and-fix-demo1-1.html">別ウィンドウで開く</a>）

<iframe class="xHtmlDemo" src="scroll-and-fix-demo1-1.html"></iframe>

CSS の [Sticky positioning](https://developer.mozilla.org/ja/docs/web/css/position#Sticky_positioning) に対応したブラウザであれば、次のようにして要素のスクロールを簡単に制限することができます。

~~~ css
.yourElement {
    position: -webkit-sticky;  /* for Safari */
    position: sticky;
    top: 0px;  /* 画面上端オフセット */
}
~~~

`position: sticky;` を指定すると、デフォルトでは `position: relative;` と同様な振る舞いをしますが、その要素が画面上の指定した位置（上記の例では画面上端から 0px）に来ると、要素の位置が固定されます。
この振る舞いは、親要素の表示範囲内で効いてくるため、画面下端のフッター領域が表示されるタイミングでは、メニュー領域はちゃんと上にスクロールされて消えていきます（うまくできています）。

下記のコードは、上記のデモページを構成している HTML と CSS の抜粋です。

#### HTML 抜粋

~~~ html
<div id="headerArea"></div>
<div id="container">
  <div id="main">
    <div class="sample">本文</div>
    <div class="sample">本文</div>
    <div class="sample">本文</div>
    ...
  </div>
  <div id="sidebar">
    <div class="sticky">
      <div class="sample">メニュー</div>
      <div class="sample">メニュー</div>
      <div class="sample">メニュー</div>
      ...
    </div>
  </div>
</div>
<div id="footerArea"></div>
~~~

#### CSS 抜粋

~~~ css
/* ヘッダー／フッター部分 */
#headerArea, #footerArea {
  height: 100px;
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
#main {
  background: dodgerblue;
  padding: 10px;

  /* 画面幅が広い時はこの要素の横幅を拡張する */
  flex-grow: 1;
  -webkit-flex-grow: 1;
  -webkit-box-flex: 1;
  -moz-box-flex: 1;
}

/* 右側のサイドバー領域 */
#sidebar {
  background: purple;
  padding: 10px;
  width: 100px;
}

/* Sticky ポジションで固定する部分 */
.sticky {
  background: magenta;
  position: -webkit-sticky;
  position: sticky;
  top: 0;
}
~~~

Sticky positioning は、主にアルファベット順や五十音順のリストの見出しに使用されます（<a target="_blank" href="https://developer.mozilla.org/ja/docs/web/css/position#Sticky_positioning">こちらの例</a>を見るとわかりやすいです）。
サイドバーなどの上端を固定するような用途で使用すると、サイドバーが長くなった場合に、なかなかスクロールが始まらないという問題が発生します。
**Sticky positioning によりサイドバー全体を画面上端に固定する場合は、短いサイドバーに限る**ようにしましょう。


### サイドバーの後半部分だけを固定する

長いサイドバー全体を Sticky positioning で画面上端に固定すると、サイドバーの末尾が見えなくなってしまいます（ページの末尾までスクロールしないと見えなくなる）。
このような場合は、**サイドバーの下の方の要素だけを Sticky positioning で固定**してやると、そこそこうまい感じで見えるようになります（どの程度の要素を固定するかは、想定する画面の高さによります）。

#### デモ（<a target="_blank" href="scroll-and-fix-demo1-2.html">別ウィンドウで開く</a>）

<iframe class="xHtmlDemo" src="scroll-and-fix-demo1-2.html"></iframe>

#### HTML 抜粋

~~~ html
<div id="container">
  ...
  <div id="sidebar">
    <div class="sample">メニュー</div>
    <div class="sample">メニュー</div>
    <div class="sample">メニュー</div>
    <div class="sample">メニュー</div>
    <div class="sticky">
      <div class="sample">メニュー</div>
      <div class="sample">メニュー</div>
      <div class="sample">メニュー</div>
      <div class="sample">メニュー</div>
    </div>
  </div>
</div>
~~~


### ウィンドウに収まる範囲でサイドバーの要素を固定する

次はもう少し頑張った例です。
JavaScript を利用して、ウィンドウの高さに収まる範囲内で、サイドバーの末尾の要素を固定 (`position: sticky`) しています。

#### デモ（<a target="_blank" href="scroll-and-fix-demo1-3.html">別ウィンドウで開く</a>）

<iframe class="xHtmlDemo" src="scroll-and-fix-demo1-3.html"></iframe>

HTML ファイルでは、サイドバー要素の下に、固定しない部分の要素を格納するための (`#sidebar_notFixed`) と、固定する部分の要素を格納するための (`#sidebar_fixed`) を新たに追加しています。
さらに、固定する候補となる要素には、カスタム属性の `data-sticky` を付加するようにしています。

#### HTML 抜粋

~~~ html
<div id="container">
  ...
  <div id="sidebar">
    <div id="sidebar_notFixed">
      <div class="sample" data-sticky>メニュー1</div>
      <div class="sample" data-sticky>メニュー2</div>
      <div class="sample" data-sticky>メニュー3</div>
      <div class="sample" data-sticky>メニュー4</div>
      <div class="sample" data-sticky>メニュー5</div>
      <div class="sample" data-sticky>メニュー6</div>
      <div class="sample" data-sticky>メニュー7</div>
      <div class="sample" data-sticky>メニュー8</div>
    </div>
    <div id="sidebar_fixed"></div>
  </div>
</div>
~~~

#### CSS 抜粋

~~~
#sidebar_fixed {
  background: magenta;
  position: -webkit-sticky;
  position: sticky;
  top: 0;
}
~~~

JavaScript では、サイドバー内の要素を末尾から見ていき、どの範囲までを Sticky positioning の対象とできるかを計算します。
対象となった要素は、`position: sticky` 設定された `div` 要素に配置換えします。

#### JavaScript 抜粋

~~~ javascript
$(function() {
  $(window).on('resize', handleResize);
  handleResize();

  function handleResize() {
    var $sidebarNotFixed = $('#sidebar_notFixed');
    var $sidebarFixed = $('#sidebar_fixed');
    var $elems = $('#sidebar').find('[data-sticky]');
    var winHeight = $(window).height();

    // data-sticky 属性を持つ要素を後ろから見ていき、
    // ウィンドウ内に収まる要素にフラグを立てる
    //（data-sticky 属性の値を true にする）。
    // $(...get().reverse()) は要素を逆順に処理するイディオム。
    var sum = 0;
    $($elems.get().reverse()).each(function() {
      sum += $(this).outerHeight(true);
      $(this).data('sticky', sum < winHeight);
    });

    // サイドバー内の要素を #sidebar_notFixed と、
    // #sidebar-fixed の子要素として振り分ける。
    // 順番がおかしくならないようにループを分ける。
    $elems.each(function() {
      if ($(this).data('sticky')) {
        $sidebarFixed.append($(this));
      } else {
        $sidebarNotFixed.append($(this));
      }
    });
  }
});
~~~

### ここまでのまとめ

Sticky positioning を利用して、サイドバー内の要素を固定する方法を見てきました。
サイドバーが長くなる場合でも、JavaScript を組み合わせて使用することで、適切な範囲の要素だけを固定することができました。
**下方向のスクロールに関しては、ほとんどのケースはこれでうまく制御できる**でしょう。

ただし、Sticky positioning を使用して長いサイドバーを末尾部分で固定すると、ページを上方向にスクロールするときに、なかなかサイドバーのスクロールが始まらないという問題があります（空白が表示されているよりは全然よいのですが）。

ページの末尾から上方向にスクロールするときに、サイドバーをうまいことスクロールできるようにするには、次で説明するような JavaScript を使用した場合分け処理が必要になります。


全てのパターンでサイドバーのスクロールをうまく制御する
----

本文領域の高さ (main) やサイドバーの高さ (sidebar)、ブラウザの表示領域（window）の高さの組み合わせは、次のように 6 パターンに分類できます。

1. window の高さ ＞ main の高さ ＞ sidebar の高さ
2. window の高さ ＞ sidebar の高さ ＞ main の高さ
3. main の高さ ＞ window の高さ ＞ sidebar の高さ
4. sidebar の高さ ＞ window の高さ ＞ main の高さ
5. main の高さ ＞ sidebar の高さ ＞ window の高さ
6. sidebar の高さ ＞ main の高さ ＞ window の高さ

ページをスクロールさせたときに、できるだけ本文領域やサイドバー末尾の空白を表示しないようにするには、それぞれのパターンで異なる処理を行う必要があります。
以下、順番に詳しく見ていきます。

### 本文領域とサイドバーがウィンドウ内にすべて収まる場合

![scroll-and-fix1.svg](scroll-and-fix1.svg){: .center }

ウィンドウ内にすべてのコンテンツ（本文とサイドバー）が表示できる場合は、特にサイドバーの位置調整などを行う必要はありません。
デフォルトのポジション指定である `position: static` のままで大丈夫です。
ただし、フッターのサイズが大きい場合は、main と sidebar いずれか短い方に、`position: sticky` を指定しておくのが望ましいでしょう。

### 本文領域、あるいはサイドバーのどちらかがウィンドウ内に収まる場合

![scroll-and-fix2.svg](scroll-and-fix2.svg){: .center }

本文、あるいは、サイドバーがウィンドウ内にすべて表示できるサイズの場合、サイズの小さい方の上端を `position: sticky` で固定します。
こうしておけば、ページをスクロールしている最中に、小さい方のコンテンツはすべて表示しつづけることができます。


### サイドバーが本文より小さいが、ウィンドウに収まらない場合

![scroll-and-fix3.svg](scroll-and-fix3.svg){: .center }

サイドバーが、ウィンドウ内に収まらない場合は、少し複雑な処理が必要になります。
必要に応じて、下方向にスクロールしているときはウィンドウ下端にサイドバーを配置し、上方向にスクロールしているときはウィンドウ上端にサイドバーを配置するようにします。
こうすれば、スクロール時に必ずサイドバーをスクロールさせることができ、しかも、無駄な空白領域が表示されてしまうのを防ぐことができます。

### 本文がサイドバーより小さいが、画面内に収まらない場合

![scroll-and-fix4.svg](scroll-and-fix4.svg){: .center }

ひとつ前のパターンとほぼ同様ですが、本文がサイドバーのサイズより小さいパターンです。
必要に応じて、スクロール時に本文領域の位置を調整します。

本文領域まで位置制御しなくてもよい（サイドバーだけ位置制御できればよい）と考えるのであれば、本文領域は単純に `position: static` で表示しておけばよいでしょう。

![scroll-and-fix5.svg](scroll-and-fix5.svg){: .center }

他のパターンでは、本文領域は `position: static` の代わりに `position: sticky` を指定しておいても問題ありませんが、この場合だけは必ず `position: static` を指定しないといけないことに注意してください。
そうしないと、スクロール時に本文領域の上端部分しか表示されなくなってしまいます。

### デモ（<a target="_blank" href="scroll-and-fix-demo2.html">別ウィンドウで開く</a>）

<iframe class="xHtmlDemo" src="scroll-and-fix-demo2.html"></iframe>


### 実装

ここでは、単純化のため、本文領域 (main) に関しては位置制御を行わず (`position: static`)、サイドバーのみ位置制御を行うようにしています。

#### HTML 抜粋

~~~ html
<div id="headerArea"></div>
<div id="container">
  <div id="main">
    <div class="sample">本文</div>
    <div class="sample">本文</div>
    <div class="sample">本文</div>
    ...
  </div>
  <div id="sidebar">
    <div id="sidebar-notFixed">
      <div class="sample" data-sticky>メニュー1</div>
      <div class="sample" data-sticky>メニュー2</div>
      <div class="sample" data-sticky>メニュー3</div>
      ...
    </div>
    <div id="sidebar-fixed"></div>
  </div>
</div>
<div id="footerArea"></div>
~~~

#### CSS 抜粋

~~~ css
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
  position: relative;
  background: dodgerblue;

  /* 画面幅が広い時はこの要素の横幅を拡張する */
  flex-grow: 1;
  -webkit-flex-grow: 1;
  -webkit-box-flex: 1;
  -moz-box-flex: 1;
}

#main {
  position: static;
  padding: 10px;
}

/* 右側のサイドバー領域 */
#sidebarArea {
  position: relative;
  width: 100px;
  background: magenta;
}

/* サイドバーの実体 */
#sidebar {
  padding: 10px;
  width: 100%;
  /* デフォルトで Sticky positioning */
  position: -webkit-sticky;
  position: sticky;
  top: 0;
}
~~~

#### JavaScript 抜粋

~~~ javascript
$(function () {
  var $window = $(window);
  var $container = $('#container');
  var $main = $('#main');
  var $sidebar = $('#sidebar');
  var windowHeight, containerHeight, sidebarHeight;

  $window.on('resize', init);
  init();

  // 高さ情報の更新、イベントハンドラの登録・解除など
  function init() {
    var mainHeight = $main.outerHeight(true);
    windowHeight = $window.height();
    containerHeight = $container.outerHeight(true);
    sidebarHeight = $sidebar.outerHeight(true);
    $window.off('scroll', adjustSidebar); // スクロールハンドラを一旦解除

    if ((mainHeight > sidebarHeight) && (sidebarHeight > windowHeight)) {
      $window.on('scroll', adjustSidebar);  // 動的なサイドバー制御
      adjustSidebar();  // すぐに一度位置調整
    } else {
      posSticky();
    }
  }

  function posSticky() {
    $sidebar.css({ 'position' : 'sticky', 'top' : 0 });
  }

  function posAbsolute(offset) {
    $sidebar.css({ 'position' : 'absolute', 'top' : offset });
  }

  // サイドバーの位置を調整する処理
  function adjustSidebar() {
    var windowTop = window.pageYOffset;
    var windowBottom = windowTop + windowHeight;
    var containerTop = $container.offset().top;
    var containerBottom = containerTop + containerHeight;
    var sidebarTop = $sidebar.offset().top;
    var sidebarBottom = sidebarTop + sidebarHeight;

    if (windowTop < containerTop) { // 上への行き過ぎ防止
      posAbsolute(0);
    } else if (windowBottom > containerBottom) { // 下への行き過ぎ防止
      posAbsolute(containerHeight - sidebarHeight);
    } else if (windowTop < sidebarTop) { // 上スクロール時の上端固定
      posAbsolute(windowTop - containerTop);
    } else if (windowBottom > sidebarBottom) { // 下スクロール時の下端固定
      posAbsolute(windowBottom - containerTop - sidebarHeight);
    }
    // それ以外は何もせず、自然にスクロールするのにまかせる
  }
});
~~~

