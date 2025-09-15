---
title: "Sassメモ: SCSS 内での条件分岐処理 (@if-else, if関数)"
url: "p/rkk224q/"
date: "2018-12-30"
tags: ["sass"]
aliases: ["/sass/if-else.html"]
---

`@if` ディレクティブ
----

Sass スクリプトの中で **`@if`**、**`@else`**、**`@else if`** ディレクティブを使用することで、条件分岐処理を記述することができます。

下記の例では、変数 `$fruit` の値によってスタイルの出力を分岐させています。

{{< code lang="scss" title="入力 (SCSS)" >}}
$fruit: banana;

p {
  @if $fruit == apple {
    color: red;
  } @else if $fruit == banana {
    color: yellow;
  } @else {
    color: blue;
  }
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
p {
  color: yellow;
}
{{< /code >}}

比較演算子としては、`==`、`!=`、`>`、`>=`、`<`、`<=` などを使用することができます。
論理演算子としては、`and`、`or`、`not` などを使用することができます（`&&` や `||` などはありません）。

`@if` ディレクティブは、Mixin や関数に渡されたパラメータを使って分岐処理したいときによく使用されます。

{{< code lang="scss" title="入力 (SCSS)" >}}
@mixin highlight($inverted: false) {
  @if $inverted {
    color: white;
    background: red;
  } @else {
    color: red;
    background: white;
  }
}

strong {
  @include highlight();
}

em {
  @include highlight(true);
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
strong {
  color: red;
  background: white;
}

em {
  color: white;
  background: red;
}
{{< /code >}}


`if` 関数
----

`@if` ディレクティブに似たものに、`if` 関数があります。
`if` 関数は第1パラメータの値が真 (true) の場合に第2パラメータで指定した値を返し、偽 (false) の場合に第3パラメータで指定した値を返します。
Java や C 言語の三項演算子のように使用するものです。

```scss
if(true, 1px, 2px)   //=> 1px
if(false, 1px, 2px)  //=> 2px
```

第2パラメータ、第3パラメータの部分に式を指定した場合、それぞれの値が返されるケースでのみ、その式が評価されます。
下記の例では、この性質を利用することで 0 除算を避けています。

```scss
.foo {
  $denom: 0;
  margin: if($denom == 0, 0, 100px / $denom);
}
```

