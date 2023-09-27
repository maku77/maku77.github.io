---
title: "モバイルファーストな CSS を作成するコツ（max-width ではなく min-width を使うべし）"
url: "p/7vwoyht/"
date: "2016-07-31"
tags: ["CSS"]
description: "モバイルファーストで Web サイトを作成するには、メディアクエリで max-width は使用せずに、min-width を使用するようにしましょう。"
aliases: /web/mobile-first.html
---

モバイルファーストで Web サイトを作成するには、メディアクエリで `max-width` は使用せずに、__`min-width`__ を使用するようにしましょう。
そうすると、デフォルトのスタイルが小さい画面用のスタイルとして適用されるため、モバイル画面でどのように表示されるかを先に考えることができます。


画面が広いときと狭い時のレイアウトを切り替える
----

メディアクエリ (__`@media`__) を使用すると、画面幅に応じたレイアウトを行うことができます。
例えば、画面幅が広いとき（PC 画面など）のみ `blockquote` 要素の左マージンを大きくしたい場合は、次のような 2 通りの方法が考えられます。
ここでは、`640px` を切り替えの基準としています。

### 方法1. 広い画面をデフォルトのスタイルとする

```css
/* デフォルトのスタイル（画面が広いとき） */
blockquote {
  margin-left: 1em;
}

/* 639px 以下のスタイル（画面が狭いとき） */
@media (max-width: 639px) {
  blockquote {
    margin-left: 0;
  }
}
```

### 方法2. 狭い画面をデフォルトのスタイルとする

```css
/* デフォルトのスタイル（画面が狭いとき） */
blockquote {
  margin-left: 0;
}

/* 640px 以上のスタイル（画面が広いとき） */
@media (min-width: 640px) {
  blockquote {
    margin-left: 1em;
  }
}
```

モバイルファーストで考えるのであれば、__後者の `min-width` を使った指定方法をオススメ__ します。
メディアクエリ (`@media`) を使ったスタイル定義によって、デフォルトのスタイル定義を上書きするわけですが、デフォルトのスタイルの方を（画面が狭い）モバイルデバイス用に定義しておくことで、最初からモバイルデバイスでの表示を意識した CSS を構築していくことができます。


3 パターン以上の画面サイズに対応する場合
----

下記は、画面サイズを大・中・小の 3 パターンに分けてスタイル定義したい場合の例です。
ここでは、それぞれの横幅を次のように設定しています。

* 画面サイズ小: 599px 以下（デフォルト）
* 画面サイズ中: 600px 以上
* 画面サイズ大: 1200px 以上

```css
/* デフォルトスタイル（画面サイズ小） */
blockquote {
  margin-left: 0;
}

/* 600px 以上のスタイル（画面サイズ中） */
@media (min-width: 600px) {
  blockquote {
    margin-left: 1em;
    background: blue;
  }
}

/* 1200px 以上のスタイル（画面サイズ大） */
@media (min-width: 1200px) {
  blockquote {
    margin-left: 2em;
  }
}
```

メディアクエリによるスタイル指定は排他的ではないため、複数のスタイル指定が組み合わされる可能性があることに注意してください。
例えば、現在のスクリーン幅が 1500px の場合は、`min-width: 600px` という条件も、`min-width: 1200px` という条件も満たすため、画面サイズ「小」「中」「大」のすべてのスタイル指定が適用されます。
ただし、同じプロパティ名が指定されている場合は、より後方で定義されたスタイルによって上書きされます。
上記の例の場合は、`blockquote` 要素に次のようなスタイルが適用されることになります。

- `margin-left: 2em;` （画面サイズ大のスタイル定義が適用される）
- `background: blue;` （画面サイズ中のスタイル定義が適用される）

画面サイズ中 (600px ～ 1199px) のときのみ適用するスタイルを定義したい場合は、次のように、`min-width` と `max-width` を組み合わせて条件を設定します。

```css
/* 600px 以上 1199px 以下のスタイル（画面サイズ中） */
@media (min-width: 600px) and (max-width: 1199px) {
  blockquote {
    background: blue;
  }
}
```


（おまけ）Sass の mixin によるメディアクエリの簡略化
----

通常の使い方でメディアクエリを使用すると、各要素のスタイル定義が散らばってしまって管理しにくくなってしまいます。
このような場合は、[Sass](/sass/) の mixin 機能を使用すると、スタイル定義をまとめて記述できるようになります。

次の例では、スクリーンサイズ中と大の場合を示す mixin 定義を行っています（ここでは幅の単位に [rem](/p/hbh4dwr/) を使ってみました）。

{{< code lang="scss" title="sample.scss" >}}
// スクリーンサイズ中
@mixin screen-size-medium {
  @media (min-width: 40rem) { @content; }
}

// スクリーンサイズ大
@mixin screen-size-large {
  @media (min-width: 60rem) { @content; }
}
{{< /code >}}

これらの mixin を使用すると、`blockquote` の 3 種類のレイアウトを下記のようにまとめて記述できます。

{{< code lang="scss" >}}
blockquote {
  margin-left: 0;
  @include screen-size-medium {
    margin-left: 1em;
  }
  @include screen-size-large {
    margin-left: 2em;
  }
}
{{< /code >}}

