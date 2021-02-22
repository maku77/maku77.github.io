---
title: "CSS だけで開閉できるアコーディオンメニューを作成する"
date: "2018-01-19"
description: "チェックボックスの見た目を CSS でカスタマイズすると、開閉可能なメニューを HTML と CSS だけで実現することができます。"
---

開閉可能なシンプルなメニュー
----

### デモ（<a target="_blank" href="accordion-demo.html">別ウィンドウで開く</a>）

<iframe class="xHtmlDemo" src="accordion-demo.html"></iframe>


ここでは、開閉用のボタンとして `input` 要素を配置し、その下に、開閉されるサブメニューとして `ul` 要素を配置しています。
サブメニュー側の要素は何でも構わないのですが、メニューはリスト構造で作成することが多いので、多くのケースでは `ul` 要素として作成することになるでしょう。

~~~ html
<div class="menu">
  <label for="item1">項目１</label>
  <input type="checkbox" id="item1">
  <ul>
    <li>項目１－１
    <li>項目１－２
    <li>項目１－３
  </ul>

  <label for="item2">項目２</label>
  <input type="checkbox" id="item2">
  <ul>
    <li>項目２－１
    <li>項目２－２
    <li>項目２－３
  </ul>
</div>
~~~

ここでは、シンプルにするために１階層だけのサブメニューを作成していますが、サブメニュー内にさらに `input` 要素を含めるようにすれば、何階層のサブメニューでも作成することができます。

CSS では、サブメニューを初期状態で非表示にするために、下記のような設定を行います。

~~~ css
/* 閉じた状態のサブメニュー */
.menu ul {
    display: none;  /* デフォルトで非表示 */
    padding: 0;
}
~~~

そして、ここがポイントですが、親要素のチェックボックスがチェックされている場合 (`input:checked`)、その直後の `ul` 要素を表示されるように切り替えます。
直後の要素を指定するために、[隣接セレクタ (+) の記法](../selector/combinator.html)を使用しています。

~~~ css
/* 開いた状態のサブメニュー */
.menu input:checked + ul {
    display: block;  /* 表示する */
    padding: 1rem;
}
~~~

下記は、装飾設定などを含む全体の CSS コードです。

~~~ css
/* メニュー全体 */
.menu {
  width: 20rem;
}

/* チェックボックスは非表示にする（内部的な Off/On の機能だけ利用する） */
.menu input {
    display: none;
}

/* 開いた状態のサブメニュー */
.menu input:checked + ul {
    display: block;
}

/* 閉じた状態のサブメニュー */
.menu ul {
    display: none;
    /* 下記は開閉によらず共通の設定 */
    background: #eee;
    list-style: none;
    margin: 0;
    padding: 1rem;
}

/* 親項目の装飾 */
.menu label {
    display: block;
    margin: 0;
    padding: 0.5rem;
    background: #ddd;
    cursor: pointer;
}

.menu label:hover {
    background: #ccc;
}
~~~


サブメニューの開閉時にアニメーションさせる
----

上記の CSS に少し手を入れることで、サブメニューを開閉するときにアニメーションさせることができます。

### デモ（<a target="_blank" href="accordion-demo2.html">別ウィンドウで開く</a>）

<iframe class="xHtmlDemo" src="accordion-demo2.html"></iframe>


~~~ css
/* 閉じた状態のサブメニュー */
.menu ul {
    overflow: hidden;
    line-height: 0;
    padding: 0 1rem;
    transition: 0.2s;

    /* 下記は開閉によらず共通の設定 */
    background: #eee;
    list-style: none;
    margin: 0;
}

/* 開いた状態のサブメニュー */
.menu input:checked + ul {
    line-height: 1.5;
    padding: 1rem 1rem;
}
~~~

縦方向にぐい～んと伸ばすアニメーションを入れるには、サブメニューを閉じた状態で上下の `padding` や `line-height` を 0 に設定しておき、開いたときに適切なサイズに拡張するようにします。
アニメーションのスピードは、`transition` プロパティで調整することができます。

`display` プロパティを `none` から `block` に変更する方法ではアニメーションしないので注意してください。


サブメニューを開いた状態で表示する
----

最初からメニューが開かれている状態にしたいのであれば、チェックボックス要素のグローバル属性 `checked` を設定しておきます。

~~~ html
<input type="checkbox" checked>
~~~

