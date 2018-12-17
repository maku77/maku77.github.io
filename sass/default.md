---
title: "変数のデフォルト値を設定する (!default)"
date: "2018-12-17"
---

下記のように、変数に値を設定するときに末尾に **`!default`** を付けると、その変数がまだ定義されていないとき（あるいは `null` が格納されているとき）にのみ代入が行われます。

#### 入力 (SCSS)

~~~ scss
$content: "First";
$content: "Second" !default;
$new-content: "Third" !default;

main {
  content: $content;
  new-content: $new-content;
}
~~~

#### 出力 (CSS)

~~~ css
main {
  content: "First";
  new-content: "Third"; }
~~~

この仕組みは、上書き可能な設定を持つ Sass ライブラリを作成するときに便利です。

