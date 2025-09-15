---
title: "Sassメモ: SCSS で変数を使用する ($var-name, !default)"
url: "p/vd4ym66/"
date: "2018-12-17"
tags: ["sass"]
aliases:
  - "/sass/var.html"
  - "/sass/default.html"
---

変数定義の基本
----

SCSS ファイル内で変数を定義するには、下記のような構文を使用します。

```
$変数名: 初期値;
```

下記はカラー値を変数として定義する簡単な例です。

{{< code lang="scss" title="入力 (SCSS)" >}}
$main-color: #333;

body {
  color: $main-color;
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
body {
  color: #333;
}
{{< /code >}}

変数の値をそのままプロパティの値として使用する場合は、上記のように **`$変数名`** の形で参照できます。
何らかのキーワードの一部（セレクタ名の一部など）や、コメントの中で変数の値を参照する場合は、インターポレーションの形式 (**`#{$変数名}`**) で参照する必要があります。
インターポレーションで文字列変数の値を参照すると、引用符は削除されて展開されます。

{{< code lang="scss" title="入力 (SCSS)" >}}
$prefix: ".maku";

#{$prefix}-shout {
  font-size: larger;
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
.maku-shout {
  font-size: larger;
}
{{< /code >}}


変数のスコープ（ローカル変数）
----

入れ子になったルール定義の中で変数を定義すると、その変数はそのブロック内でのみ参照可能なローカル変数となります。
グローバル変数と同じ名前のローカル変数を定義した場合、ローカル変数が優先されます。

{{< code lang="scss" title="入力 (SCSS)" >}}
$color: black;  // グローバル変数の定義

body {
  color: $color;  // グローバル変数の参照
}

main {
  $color: red;    // ローカル変数の定義
  color: $color;  // ローカル変数の参照
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
body {
  color: black;
}

main {
  color: red;
}
{{< /code >}}


変数名のハイフンとアンダースコアは同一視される
----

歴史的な理由により、SCSS の中で定義した変数名に含まれるハイフン (`-`) と、アンダースコア (`_`) は同一のものとして扱われます。
例えば、`$main-color` という名前で定義した変数は、`$main_color` という名前でも参照できます。

```scss
$main-color: #333;

body {
  color: $main_color;
}
```

このルールは、[Mixin](/p/awmebxk/) の名前や関数の名前にも同様に適用されます。


変数のデフォルト値を設定する (!default)
----

下記のように、変数に値を設定するときに末尾に **`!default`** を付けると、その変数がまだ定義されていないとき（あるいは `null` が格納されているとき）にのみ代入が行われます。

{{< code lang="scss" title="入力 (SCSS)" >}}
$content: "First";
$content: "Second" !default;
$new-content: "Third" !default;

main {
  content: $content;
  new-content: $new-content;
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
main {
  content: "First";
  new-content: "Third";
}
{{< /code >}}

この仕組みは、上書き可能な設定を持つ Sass ライブラリを作成するときに便利です。


変数に値を代入する
----

定義した変数に別の値を代入するには、変数の定義と同じ `$変数名: 値;` という構文を使用します。
次の例では、[`@while` ループ](/p/ka8a3uc/)のブロックの中で、`$i` 変数の値を 1 ずつデクリメントしています。

{{< code lang="scss" title="入力 (SCSS)" >}}
$i: 1;

@while $i <= 5 {
  h#{$i} {
    font-size: 1.6rem - (0.1rem * $i);
  }
  $i: $i + 1;
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
h1 {
  font-size: 1.5rem;
}
h2 {
  font-size: 1.4rem;
}
h3 {
  font-size: 1.3rem;
}
h4 {
  font-size: 1.2rem;
}
h5 {
  font-size: 1.1rem;
}
{{< /code >}}


変数のデータタイプ
----

Sass で扱える変数のデータタイプは、下記の8種類です。

- **`number`** ... 数値。単位なし (`13`, `0.3`) と単位あり (`5em`, `10px`) のものがある。
- **`string`** ... 文字列。引用符で囲まれたもの (`"foo"`, `"こんにちは"`) と囲まれていないもの (`sans-serif`, `bar`) がある。
- **`color`** ... 色を表す値 (`red`, `#0cf2f9`, `rgba(255, 255, 0, 0.5)`)
- **`boolean`** ... 真偽値 (`true`, `false`)
- **`null`** ... `null`
- **`list`** ... スペースやカンマで区切られた値のリスト (`1em 2em 1em 0.5em`, `Helvetica, Arial, sans-serif`)
- **`map`** ... キー＆バリューのリスト。全体を括弧で囲んで定義する (`(key1:value1, key2:value2)`)
- **`function`** ... `get-function("関数名")` で返される関数への参照。`call($func, $args...)` で呼び出せる

Sass の `type-of` 関数を使用すると、指定した変数のデータタイプを確認することができます。

{{< code lang="scss" title="入力 (SCSS)" >}}
$color: red;

#message {
  content: "red is " + type-of($color);
}
{{< /code >}}

{{< code lang="css" title="出力 (CSS)" >}}
#message {
  content: "red is color";
}
{{< /code >}}

