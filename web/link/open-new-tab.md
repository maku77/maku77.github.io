---
title: "外部リンクをクリックしたときに必ず新しいタブで開く (target=_blank)"
date: "2017-10-12"
description: "自分のウェブサイト内から外部サイトの URL をクリックしたときに、必ず新しいタブでページを開くようにする方法です。"
---

HTML の `a` 要素によるリンクをクリックしたときに、新しいタブでページを開くには、属性 `target="_blank"` を指定します。

~~~ html
<a target="_blank" href="http://example.com/">外部サイトへのリンク</a>
~~~

HTML を直接記述する方法であれば、このような記述をすればよいのですが、最近主流になっている静的サイトジェネレータでは Markdown 記法などが用いられることが多く、各リンクに上記のような属性を付加するのが難しくなっています。
例えば、

~~~ md
[Markdown フォーマットによるリンクの例](http://example.com/)
~~~

という Markdown 記述は、下記のような単純なリンクに置き換えられます。

~~~ html
<a href="http://example.com/">Markdown フォーマットによるリンクの例</a>
~~~

このような `target="_blank"` 属性の指定のないリンクをクリックした場合に、強制的に新しいタブでページを開くようにするには、下記のような JavaScript を使用します。

#### jQuery の場合

~~~ javascript
$(function() {
  // 外部リンクを必ず新しいタブで開く
  // （rel=noopener を指定することにより、必ず別プロセスで開く）
  $('a[href^=http]').attr('target', '_blank').attr('rel', 'noopener');
});
~~~

#### Pure JavaScript の場合

~~~ javascript
document.addEventListener('DOMContentLoaded', function() {
  const links = document.querySelectorAll('a[href^=http]');
  for (let i = 0; i < links.length; ++i) {
    // 外部リンクを必ず新しいタブで開く
    // （rel=noopener を指定することにより、必ず別プロセスで開く）
    links[i].setAttribute('target', '_blank');
    links[i].setAttribute('rel', 'noopener');
  }
});
~~~

`rel="noopener"` という属性を追加することによって、新しく開かれたタブが、リンク元のタブと同じプロセスで動くのを防ぐことができます。
これを入れておかないと、他のサイトの動作が重かったときに、自分のサイトにまで影響が及んでしまう可能性があります。

<div class="note">
ここでは「外部リンク」の定義を、URL が <code>http</code> で始まっているものと定義しています。
URL が <code>http</code> で始まるリンクはすべて新しいタブで開くようになることに注意してください。
</div>

上記の例では、外部リンクの `a` 要素に `target=_blank` 属性を追加していますが、下記のように、リンクをクリックしたときの振る舞いを定義してしまう方法もあります。

~~~ javascript
$(function() {
  $('a[href^=http]').click(function() {
    window.open(this.href, "_blank");
    return false;
  });
});
~~~

