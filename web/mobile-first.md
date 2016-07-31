---
title: モバイルファーストで考えるコツ
created: 2016-07-31
---


メディアクエリは max-width よりも min-width を使用する
----

メディアクエリ (`@media`) を使用すると、画面幅に応じたレイアウトを行うことができます。

```css
/* デフォルトのスタイル */
.myComponent: ...;

/* 横幅 640px 以上の場合に適用（PC ブラウザなど）*/
@media (max-width: 640px) {
  .myComponent: ...;
}

/* 横幅 640px 以下の場合に適用（スマホなど）*/
@media (min-width: 640px) {
  .myComponent: ...;
}
```

メディアクエリを使ったスタイル定義によって、デフォルトのスタイル定義を上書きするわけですが、デフォルトのスタイルの方をモバイル用と位置付けることで、最初からモバイルを意識した CSS を構築していくことができます。

例えば、下記では `book` コンポーネントを定義していますが、デフォルトのレイアウトとして横幅いっぱい（`width: 100%;`) に表示するようにしています。
まず、モバイルデバイスでの表示にも対応できるように、十分な横幅を確保するということです。
一方で、PC などの横幅の広いブラウザで表示する場合は、２段組で表示できるように、メディアクエリ `@media (min-width: 640px)` を利用して表示方法を拡張しています。

#### html

```html
<div class="book">Title 1</div>
<div class="book">Title 2</div>
<div class="book">Title 3</div>
```

#### css

```css
/* normalize */
* {
  margin: 0;
}

/* モバイル用レイアウト */
.book {
  width: 100%;
  display: inline-block;
  background: #ccc;
}

/* PC 用レイアウト */
@media (min-width: 640px) {
  .book {
    width: 49%;
  }
}
```

