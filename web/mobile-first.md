---
title: "モバイルファーストで考えるコツ（max-width ではなく min-width を使うべし）"
date: "2016-07-31"
description: "モバイルファーストで Web サイトを作成するには、メディアクエリで max-width は使用せずに、min-width を使用するようにしましょう。"
---

画面が広いときと狭い時のレイアウトを切り替える
----

メディアクエリ (`@media`) を使用すると、画面幅に応じたレイアウトを行うことができます。
例えば、画面幅が広いとき（PC画面など）のみ `blockquote` 要素の左マージンを大きくしたい場合は、次のような 2 通りの方法が考えられます。
ここでは、`640px` を切り替えの基準としています。

#### 方法1. 広い画面をデフォルトのスタイルとする

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

#### 方法2. 狭い画面をデフォルトのスタイルとする

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

モバイルファーストで考えるのであれば、 **後者の `min-width` を使った指定方法をオススメ** します。
メディアクエリ (`@media`) を使ったスタイル定義によって、デフォルトのスタイル定義を上書きするわけですが、デフォルトのスタイルの方をモバイル用（画面が狭い）に定義しておくことで、最初からモバイルを意識した CSS を構築していくことができます。


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

例えば、スクリーン幅が 1500px ある場合は、`min-width: 600px` という条件も、`min-width: 1200px` という条件も満たすことに注意してください。
よって、`blockquote` 要素には次のようなスタイルが適用されます。

- `margin-left: 2em;` （画面サイズ大の定義）
- `background: blue;` （画面サイズ中の定義）

スタイル定義は後から定義されたものが優先的に使われるため、`margin-left` の設定は画面サイズ大のものが使われるというところがポイントです。

もし、画面サイズ中のときのみ（600px ～ 1199px) 適用するスタイルを定義したい場合は、次のように、`min-width` と `max-width` を組み合わせて条件を設定します。

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

通常の使い方でメディアクエリを使用すると、1 種類の要素のスタイル定義が散らばってしまって管理しにくくなってしまいます。
このような場合は、[Sass](/sass/) の mixin 機能を使用すると、スタイル定義をまとめて記述できるようになります。

次の例では、スクリーンサイズ中と大の場合を示す mixin 定義を行っています（ここでは幅の単位に [rem](font/rem-and-em.html) を使ってみました）。

#### sample.scss

```scss
// スクリーンサイズ中
@mixin screen-size-medium {
  @media (min-width: 40rem) { @content; }
}

// スクリーンサイズ大
@mixin screen-size-large {
  @media (min-width: 60rem) { @content; }
}
```

これらの mixin を使用すると、`blockquote` の 3 種類のレイアウトを下記のようにまとめて記述できます。

```scss
blockquote {
  margin-left: 0;
  @include screen-size-medium {
    margin-left: 1em;
  }
  @include screen-size-large {
    margin-left: 2em;
  }
}
```

