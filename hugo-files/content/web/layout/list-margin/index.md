---
title: "HTML のリスト要素 (ul, ol, li) のマージン指定方法を理解する"
url: "p/sw5kgj2/"
date: "2018-04-23"
tags: ["CSS"]
description: "ul や ol によるマージン、インデント設定は若干わかりにくいので、ここではいろいろなパターンの表示を確認しながら理解を深めます。"
aliases: /web/layout/list-margin.html
---

HTML でリストを表示するには `ul` や `ol` 要素を使いますが、これらのマージン、インデント設定は若干わかりにくいので、ここではいろいろなパターンの表示例を確認しながら理解を深めます。

ul 要素と li 要素の padding-left
----

{{< image w="450" src="img-001.png" title="ul 要素と li 要素の padding プロパティの意味" >}}

この図は、`ul` 要素（あるいは `ol` 要素）の `padding-left` の値と、`li` 要素の `padding-left` の値がどの領域のサイズとして反映されるかを示しています。

特に気をつけなければいけないのは、`ul` 要素の `padding-left` 値は、`li` 要素の描画領域の左端までのサイズになるというところです。
言い換えると、リストマーカー（`・`など）の右端までのサイズです。
つまり、__`ul` 要素の `padding-left` の値は、リストマーカーまで含んだサイズ__ であることを意識して設定する必要があります。
Chrome では `ul` 要素の `padding-left` の規定値は 40px になっているようです。

一方で、__`li` 要素の `padding-left` の値は、リストマーカーの右端から、テキストの左端までのサイズ__ を表します。
こちらの値は 0 になっていても、特におかしな表示にはなりません。


ul のインデント設定は padding-left で行うのが基本
----

下記は、__`ul` 要素の `padding-left`__ プロパティだけを設定した場合の表示デモです（その他の `margin` 設定や `padding` 設定は 0 です）。
入れ子になった `ul` 要素も、それっぽくインデントされていることがわかります。
これが、`ul` 要素や `ol` 要素に対してインデントサイズを指定するときの基本的な方法です。

### 表示デモ 1

<center>
  <iframe width="550" height="370" src="./demo1.html"></iframe>
  <div>（{{< file src="demo1.html" caption="別ページで開く" >}}）</div>
</center>

表示デモの中の外側の矩形は、`ul` 要素を配置する親要素の描画領域を示しています。
`ul` 要素の描画領域はピンク背景、`li` 要素の描画領域は下線を引くことで、それぞれの要素の位置関係が分かるようにしています。
ここでは `ul { padding-left: 3em; }` と指定していますが、このサイズは、上記の表示では、ピンク領域の左端 ～ テキストの下線が始まるところまでのサイズとして反映されています。

{{% note %}}
サイズの単位として `em` を使うようにすれば、`ul` 要素内のフォントサイズを基準にして指定することができます。
{{% /note %}}

### 表示デモ 2

<center>
  <iframe width="550" height="370" src="./demo2.html"></iframe>
  <div>（{{< file src="demo2.html" caption="別ページで開く" >}}）</div>
</center>

`ul` 要素の `padding-left` を 0 にしてしまうと、上記のように入れ子要素のインデントがなくなり、さらに、リストマーカーを表示する余裕もないので、親要素をはみ出してリストマーカーが表示されてしまいます。
`ul` 要素の `padding-left` には、余裕を持ったサイズを指定しなければいけないということです。

### 表示デモ 3

<center>
  <iframe width="550" height="370" src="./demo3.html"></iframe>
  <div>（{{< file src="demo3.html" caption="別ページで開く" >}}）</div>
</center>

上記は、`padding-left` の代わりに `margin-left` を設定した場合の表示デモですが、この方法でもうまくインデントされているかのように見えます。
ただし、`margin` プロパティの値は、他の要素と並べて配置した時に、それらの `margin` と相殺される性質を持っています。
思わぬ表示位置のずれを防ぐためにも、__`ul`/`ol` 要素のインデントサイズ指定には `padding-left` だけを使う__ ことをおススメします。

ちなみに `padding-left` と `margin-left` を両方設定すると、次のようにインデント幅がものすごく大きくなったように見えます。

### 表示デモ 4

<center>
  <iframe width="550" height="370" src="./demo4.html"></iframe>
  <div>（{{< file src="demo4.html" caption="別ページで開く" >}}）</div>
</center>


li の padding-left は 0 で OK
----

__`li` 要素の `padding-left`__ プロパティでは、リストマーカーの右端からテキストの先頭までの距離を設定します。
次の例では、各 `li` 要素の `padding-left` の値を少しずつ増やしながら表示しています。

### 表示デモ 5

<center>
  <iframe width="550" height="190" src="./demo5.html"></iframe>
  <div>（{{< file src="demo5.html" caption="別ページで開く" >}}）</div>
</center>

入れ子になった `ul` 要素は `li` 要素の子要素として配置されるため、`li` 要素の `padding-left` を大きくすると、その下の `ul` 要素自体のインデント幅が大きくなったかのように見えます。

### 表示デモ 6

<center>
  <iframe width="550" height="335" src="./demo6.html"></iframe>
  <div>（{{< file src="demo6.html" caption="別ページで開く" >}}）</div>
</center>

{{% note %}}
間違えがちですが、`ul` 要素の直下に `ul` を配置することはできません。
`ul` 要素の直下には、必ず `li` 要素を配置する必要があります。
{{% /note %}}


入れ子になった（2 階層目以降の）ul のインデントだけ小さくする
----

1 階層目の `ul` のインデントだけ小さくしたい場合や、2 階層目以降の `ul` のインデントだけ小さくしたい場合は、例えば以下のように設定すればよいでしょう。

```css
ul, ol {
  padding-left: 3em;
}

li > ul, li > ol {
  /* 2 階層目以降はインデントを小さく */
  padding-left: 1em;
}
```

`ul` 要素の親要素が `li` であることを示すセレクタ (`li > ul`) を使用することで、2 階層目以降の `ul` 要素に対するスタイル設定を行うことができます。

### 表示デモ 7

<center>
  <iframe width="550" height="335" src="./demo7.html"></iframe>
  <div>（{{< file src="demo7.html" caption="別ページで開く" >}}）</div>
</center>


画面幅が狭い時にインデントを小さくする（レスポンシブ対応）
----

リストのインデントを大きくしすぎると、ブラウザの画面サイズが小さいとき（スマホで表示した場合など）に、表示できる文字数が少なくなってしまいます。
このような問題を避けるためには、メディアクエリを使用して、画面サイズに応じてインデントサイズを変更するようにします。

{{< code lang="css" title="CSS の例" >}}
ul, ol {
  padding-left: 2em;
}

li > ul, li > ol {
  padding-left: 1em;
}

/* スクリーンサイズが大きいときはインデントを大きくする */
@media (min-width: 35rem) {
  ul, ol {
    padding-left: 3em;
  }

  li > ul, li > ol {
    padding-left: 2em;
  }
}
{{< /code >}}

次の例では、ブラウザの画面サイズが小さいときに、リストのインデント幅も小さくなるように設定しています（フレームの右下をドラッグしたり、<kbd>Ctrl-+</kbd> でフォントサイズを変更したりすると、インデントサイズが変化することを確認できます）。

### 表示デモ 8

<center>
  <iframe width="100%" height="300" src="./demo8.html" style="resize: both; overflow: auto;"></iframe>
  <div>（{{< file src="demo8.html" caption="別ページで開く" >}}）</div>
</center>


まとめ
----

* `ul`/`ol` 要素を入れ子にしたときのインデントサイズは `ul`/`ol` 要素の __`padding-left`__ で設定する。
* 2 階層目以降の `ul`/`ol` 要素のインデントサイズだけ変更したい場合は __`li > ul`__ というセレクタを使用する。
* `margin-left` は使用しない。
* `padding-left` の値のサイズとしては __`em`__ を使用するのがおすすめ。

