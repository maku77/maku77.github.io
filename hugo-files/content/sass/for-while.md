---
title: "Sassメモ: SCSS 内でのループ処理 (@for, @while)"
url: "p/ka8a3uc/"
date: "2018-12-30"
tags: ["sass"]
aliases: ["/sass/for-while.html"]
---

Sass スクリプトで **`@for`** ディレクティブや **`@while`** ディレクティブを使用すると、数値によるループ処理を行うことができます。
主に、シンプルな数値のインクリメント（あるいはデクリメント）によるループには `@for` ディレクティブを使用し、より複雑なループ処理が必要な場合には `@while` ディレクティブを使用します。


`@for` ディレクティブ
----

`@for` ディレクティブは下記のいずれかの構文で使用します。

```
@for 変数 from 開始値 through 終了値 {
  処理（スタイル定義など）
}

@for 変数 from 開始値 to 終了値 {
  処理（スタイル定義など）
}
```

いずれも `変数` の値が `開始値` から `終了値` に向かってインクリメント（あるいはデクリメント）されながらループ処理されますが、`through` を使った場合に `終了値` を含む値まで処理されるのに対し、`to` を使った場合は `終了値` に達する手前の値までしか処理されません。
下記の例を見ると、その違いが分かると思います。

{{< code lang="scss" title="入力 (SCSS)" >}}
@for $i from 1 through 3 {
  .foo-#{$i} { width: 10px * $i; }
}

@for $i from 1 to 3 {
  .bar-#{$i} { width: 10px * $i; }
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
.foo-1 { width: 10px; }
.foo-2 { width: 20px; }
.foo-3 { width: 30px; }

.bar-1 { width: 10px; }
.bar-2 { width: 20px; }
{{< /code >}}

`開始値` よりも `終了値` の方が小さい場合は、`変数` の値は 1 つずつデクリメントされていきます。

{{< code lang="scss" title="入力 (SCSS)" >}}
@for $i from 3 through 1 {
  .item-#{$i} { width: 10px * $i; }
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
.item-3 { width: 30px; }
.item-2 { width: 20px; }
.item-1 { width: 10px; }
{{< /code >}}


`@while` ディレクティブ
----

`@while` ディレクティブを使用すると、ある条件に一致している間処理を続けるループ処理を記述することができます。
`@while` ディレクティブの構文は次の通りです。

```
@while 条件式 {
  処理（スタイル定義など）
}
```

`@while` ディレクティブを使用してループ処理を行う場合は、無限ループにならないように、`条件式` で比較する変数の値をループ処理の中で更新してやる必要があります。
下記の例では、変数 `$height` の値を、ループ処理内で 10 ずつ増加させています。

{{< code lang="scss" title="入力 (SCSS)" >}}
$height: 10;
@while $height < 50 {
  .space-#{$height} {
    margin-top: $height + px;
  }
  $height: $height + 10;
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
.space-10 {
  margin-top: 10px;
}
.space-20 {
  margin-top: 20px;
}
.space-30 {
  margin-top: 30px;
}
.space-40 {
  margin-top: 40px;
}
{{< /code >}}


リスト変数やマップ変数の要素のループ処理
----

リスト変数やマップ変数に格納された要素を 1 つずつ取り出しながらループ処理したい場合は、**`@each`** ディレクティブを使用することができます。
詳しくは、下記の記事を参照してください。

* [SCSS でリスト変数を使用する](/p/gm47at8/)
* [SCSS でマップ変数を使用する](/p/rm99r8j/)

