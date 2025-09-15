---
title: "Sassメモ: 文字列変数の引用符の展開ルール"
url: "p/by8fhor/"
date: "2018-12-17"
tags: ["sass"]
aliases: ["/sass/string.html"]
---

引用符で囲まれた文字列、囲まれていない文字列
----

Sass の文字列変数には、引用符で囲まれた文字列と、囲まれていない文字列の 2 種類があります。
文字列変数を **`$変数`** の形で参照すると、変数定義時の引用符のありなしがそのまま反映されて展開されます。

{{< code lang="scss" title="入力 (SCSS)" >}}
$family1: "Lucida Grande";
$family2: 'Lucida Sans';
$family3: sans-serif;

main {
  font-family: $family1, $family2, $family3;
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
main {
  font-family: "Lucida Grande", "Lucida Sans", sans-serif;
}
{{< /code >}}


インターポレーション
----

セレクタ名やコメント、文字列の中で文字列変数を参照する場合は、**`#{$変数}`** のようなインターポレーション形式で参照する必要があります。

{{< code lang="scss" title="入力 (SCSS)" >}}
$name: "maku";

/* これはコメントです by #{$name} */
.note-#{$name}  {
  content: "I am #{$name}"
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
@charset "UTF-8";
/* これはコメントです by maku */
.note-maku {
  content: "I am maku";
}
{{< /code >}}

入力ファイルのエンコーディング形式を判別して、`@charset` ディレクティブまで自動で挿入してくれるみたいですね。

インターポレーション (`#{}`) で文字列変数を参照すると、変数定義時に付いていた引用符が削除されるという効果があります（後述の `unquote` 関数を使う方法もあります）。

{{< code lang="scss" title="入力 (SCSS)" >}}
$family: "sans-serif";

main {
  font-family: $family;
  font-family: #{$family};
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
main {
  font-family: "sans-serif";
  font-family: sans-serif;
}
{{< /code >}}


引用符を外す関数 (unquote)
----

Sass の **`unquote`** 関数を使用すると、明示的に文字列の引用符 (`"`) を取り除くことができます。

{{< code lang="scss" title="入力 (SCSS)" >}}
$family: "sans-serif";

main {
  font-family: unquote($family);
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
main {
  font-family: sans-serif;
}
{{< /code >}}


文字列を結合する
----

文字列は **`+`** 演算子を使って結合することができます。

```scss
main {
  font-family: "Lucida " + "Grande";  //=> "Lucida Grande"
  font-family: sans- + serif;         //=> sans-serif
}
```

引用符で囲まれた文字列と囲まれていない文字列を結合した場合、`+` の左側が引用符で囲まれているかどうかで結合結果が引用符で囲まれるかどうかが決まります。

```scss
main {
  font-family: "Lucida " + Grande;  //=> "Lucida Grande"
  font-family: sans- + "serif";     //=> sans-serif
}
```

ただし、文字列以外の値と、引用符で囲まれた文字列の結合結果は、必ず引用符で囲まれた文字列になります。

```scss
main {
  content: red + " ocean";  //=> "red ocean" （color型との結合）
  content: 10 + " miles";   //=> "10 miles" （number型との結合）
}
```

