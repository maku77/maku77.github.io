---
title: "ページ内の h2 要素から自動的にメニュー (TOC) を生成する"
date: "2017-08-24"
---

HTML 内に配置した h2 要素のテキストから、自動的にメニュー (TOC: Table of Contents) を作成するサンプルです。

例えば、下記のように３つの h2 要素（タイトル１、タイトル２、タイトル３）を含んだ HTML があるとして、それぞれの位置へジャンプするためのメニューを自動で追加します。

~~~ html
<div id="menu">メニュー挿入位置</div>

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
~~~

下記の JavaScript コードでは、HTML コンテンツの h2 要素を回収して、その位置にジャンプするためのメニュー項目を li 要素として生成しています。
メニュー項目となる ul、li 要素は、もともとの HTML にプレースホルダとして配置しておいた `<div id="menu">` に挿入するようにしています（記述を簡単にするため jQuery を使用しています）。

~~~ js
$(function() {
  /*
   * 指定されたヘッダ要素（例: 'h2'）のタイトル名をもとに
   * メニュー要素 (ul) を生成する。
   */
  function createMenu(targetTagName) {
    var $ul = $('<ul class="menu" />');  // メニュー項目の親要素
    var sectionNumber = 1;  // ジャンプ先名称のサフィックス
    $(targetTagName).each(function (index, elem) {
      var $e = $(elem);
      // ジャンプ先となる a 要素を追加する（name 属性も自動生成）
      var name = 'section' + (sectionNumber++);
      $e.prepend('<a name="' + name + '"></a>');
      // メニュー項目を追加
      $ul.append('<li><a href="#' + name + '">' + $e.text() + '</a>');
    });
    return $ul;
  }

  // メニュー要素 (ul) を生成し、メニュー挿入位置へ挿入
  $('#menu').append(createMenu('h2'));
});
~~~

上記の JavaScript コードを実行すると、下記のようなメニューが動的に生成されます。

~~~ html
<div id="menu">メニュー挿入位置
  <ul class="menu">
    <li><a href="#section1">タイトル１</a></li>
    <li><a href="#section2">タイトル２</a></li>
    <li><a href="#section3">タイトル３</a></li>
  </ul>
</div>
~~~

