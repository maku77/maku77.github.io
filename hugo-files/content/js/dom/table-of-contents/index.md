---
title: "JavaScriptメモ: ページ内のヘッダ要素 (h2/h3/h4) から自動的にメニュー (TOC) を生成する"
url: "p/pzkgih9/"
date: "2017-08-24"
lastmod: "2019-03-31"
tags: ["javascript"]
aliases: [/js/dom/table-of-contents.html]
---

一階層のみのメニュー
----

HTML 内に配置したヘッダー要素（`h2` 要素）の表示テキストから、自動的にページ内メニュー (TOC: Table of Contents) を作成するサンプルです。

#### デモ1（{{< file src="table-of-contents-demo1.html" caption="別ウィンドウで開く" >}}）
<iframe class="xHtmlDemo" src="table-of-contents-demo1.html"></iframe>

例えば、下記のように 3 つの `h2` 要素（タイトル１、タイトル２、タイトル３）を含んだ HTML があるとして、それぞれの位置へジャンプするためのメニューを自動で追加します。

```html
<div id="menuStub">メニュー挿入位置</div>

<main>
  <h2>タイトル１</h2>
  <p>
    文<br>文<br>文<br>文<br>文<br>文<br>文<br>文<br>文<br>文<br>文<br>文
  </p>
  <h2>タイトル２</h2>
  <p>
    文<br>文<br>文<br>文<br>文<br>文<br>文<br>文<br>文<br>文<br>文<br>文
  </p>
  <h2>タイトル３</h2>
  <p>
    文<br>文<br>文<br>文<br>文<br>文<br>文<br>文<br>文<br>文<br>文<br>文
  </p>
</main>
```

下記の JavaScript コードでは、HTML コンテンツの `h2` 要素を回収して、その位置にジャンプするためのメニュー項目を `li` 要素として生成しています。
メニューを構成する `ul` 要素は、もともとの HTML にプレースホルダとして配置しておいた `<div id="menuStub">` に挿入するようにしています（記述を簡単にするため jQuery を使用しています）。

```js
$(function() {
  /*
   * 指定されたヘッダ要素（例: 'h2'）のタイトル名をもとに
   * メニュー要素 (ul) を生成する。
   *
   * - searchParent: この要素以下のヘッダ要素を抽出する（例: '#content'）
   * - headerTagNames: 抽出するヘッダ要素名（例: 'h2'）
   */
  function createMenu(searchParent, headerTagNames) {
    var $ul = $('<ul />');  // メニュー項目の親要素
    var sectionNumber = 1;  // ジャンプ先名称のサフィックス

    $(searchParent).find(headerTagNames).each(function (index, elem) {
      var $e = $(elem);
      // ジャンプ先となる a 要素を追加する（name 属性も自動生成）
      var name = 'section' + (sectionNumber++);
      $e.prepend('<a name="' + name + '"></a>');

      // メニュー項目となる li 要素を追加する
      $ul.append('<li><a href="#' + name + '">' + $e.text() + '</a>');
    });

    return $ul;
  }

  // main 要素以下の h2 ヘッダを抽出して、メニュー (ul 要素) を自動生成し、
  // その ul 要素を #menuStub へと挿入する。
  var menuElem = createMenu('main', 'h2');
  $('#menuStub').append(menuElem);
});
```

上記の JavaScript コードを実行すると、下記のようなメニューが動的に生成されます。

```html
<div id="menuStub">メニュー挿入位置
  <ul>
    <li><a href="#section1">タイトル１</a></li>
    <li><a href="#section2">タイトル２</a></li>
    <li><a href="#section3">タイトル３</a></li>
  </ul>
</div>
```


二階層以上のメニュー
----

上記の例では、ページ内の `h2` 要素から目次を生成する方法でしたが、下位の `h3` 要素、`h4` 要素なども検索対象として、複数階層の目次を生成することもできます。

下記のデモでは、`h2`、`h3` 要素を抽出して目次を自動生成しています。

#### デモ2（{{< file src="table-of-contents-demo2.html" caption="別ウィンドウで開く" >}}）

<iframe class="xHtmlDemo" src="table-of-contents-demo2.html"></iframe>



ここで使用している HTML は、下記のように `h2` 要素と `h3` 要素を含んだものになっています。

```html
<div id="menuStub">メニュー挿入位置</div>

<main>
  <h2>タイトル1</h2>
    <h3>タイトル1-1</h3>
    <p>文<br>文<br>文<br>文<br>文</p>
    <h3>タイトル1-2</h3>
    <p>文<br>文<br>文<br>文<br>文</p>
    <h3>タイトル1-3</h3>
    <p>文<br>文<br>文<br>文<br>文</p>

  <h2>タイトル2</h2>
    <h3>タイトル2-1</h3>
    <p>文<br>文<br>文<br>文<br>文</p>
    <h3>タイトル2-2</h3>
    <p>文<br>文<br>文<br>文<br>文</p>
    <h3>タイトル2-3</h3>
    <p>文<br>文<br>文<br>文<br>文</p>
</main>
```

JavaScript のコードの方はあまり変更する必要はなく、基本的にはセレクターに使用する文字列を `h2, h3` のようにカンマ区切りのタグ名に変更するだけです。
ここでは、jQuery を用いていますが、Vanilla JS（プレーンな JS）でも、`document.querySelectorAll('h2, h3')` のように複数のタグ名で検索できます。

```js
$(function() {
  /*
   * 指定されたヘッダ要素（例: 'h2, h3, h4'）のタイトル名をもとに
   * メニュー要素 (ul) を生成する。
   *
   * - searchParent: この要素以下のヘッダ要素を抽出する（例: '#content'）
   * - headerTagNames: 抽出するヘッダ要素名（例: 'h2, h3, h4'）
   */
  function createMenu(searchParent, headerTagNames) {
    var $ul = $('<ul />');  // メニュー項目の親要素
    var sectionNumber = 1;  // ジャンプ先名称のサフィックス

    $(searchParent).find(headerTagNames).each(function (index, elem) {
      var $e = $(elem);
      // ジャンプ先となる a 要素を追加する（name 属性も自動生成）
      var name = 'section' + (sectionNumber++);
      $e.prepend('<a name="' + name + '"></a>');

      // メニュー項目となる li 要素を追加する
      var level = $e.get(0).tagName.charAt(1);  // 'h2' などの後ろの数値部分
      $ul.append('<li class="level-' + level + '"><a href="#' + name + '">' + $e.text() + '</a>');
    });

    return $ul;
  }

  // main 要素以下の h2, h3 ヘッダを抽出して、メニュー (ul 要素) を自動生成し、
  // その ul 要素を #menu へと挿入する。
  var menuElem = createMenu('main', 'h2, h3');
  $('#menuStub').append(menuElem);
});
```

このコードにより、下記のような目次が自動生成されます。

```html
<div id="menuStub">メニュー挿入位置
  <ul>
    <li class="level-2"><a href="#section1">タイトル1</a></li>
    <li class="level-3"><a href="#section2">タイトル1-1</a></li>
    <li class="level-3"><a href="#section3">タイトル1-2</a></li>
    <li class="level-3"><a href="#section4">タイトル1-3</a></li>
    <li class="level-2"><a href="#section5">タイトル2</a></li>
    <li class="level-3"><a href="#section6">タイトル2-1</a></li>
    <li class="level-3"><a href="#section7">タイトル2-2</a></li>
    <li class="level-3"><a href="#section8">タイトル2-3</a></li>
  </ul>
</div>
```

おまけとして、各 `li` 要素がどのレベルのヘッダから生成されたものか区別できるように、`h2` や `h3` 要素の後ろの数値を基に、`level-2` や `level-3` というクラスを付加するようにしています。
このクラス情報を使用すれば、CSS でヘッダレベルに応じた装飾を行うことができます。
