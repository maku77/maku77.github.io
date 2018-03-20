---
title: "全要素に統一された上下マージンを設定する（ふくろうセレクタ）"
date: "2018-01-08"
description: "owl セレクタ（ふくろうセレクタ）を利用すると、各要素の上下のマージンをまとめて設定することができます。"
---

HTML の各要素の上下のマージンを、それぞれ `margin-top` や `margin-bottom` で設定していると、ちょっとした調整が全体のレイアウトの崩れにつながります。
Owl セレクタ（ふくろうセレクタ）と呼ばれている `* + *` という CSS セレクタを利用すると、全要素のマージンをまとめて設定することができるので、サイト全体で統一のとれたマージン設定が可能です。

`* + *` という指定は、ユニバーサルセレクタ (`*`) と隣接セレクタ (`+`) の組み合わせから成っており、要するに、連続する２番目以降の要素をすべて選択するという意味になります。
`* + *` によって選択された要素の上マージン (`margin-top`) を設定してやることで、要素間のマージンを一気に設定できることになります。

下記の例では、`main` 要素直下に配置された、すべての要素間に `1rem` のマージンを設定しています（つまり、そのページのフォントサイズで１行分のマージン）。

~~~ css
* {
  margin: 0;  /* ブラウザ依存のマージンはリセット */
}

html {
  font-size: 100%;  /* ユーザの指定した表示サイズを尊重する */
  line-height: 1.5;
}

main > * + * {
  margin-top: 1rem;  /* サイト全体で上下マージンを統一 */
}

main > * + h2,
main > * + h3,
main > * + h4,
main > * + h5,
main > * + h6 {
  margin-top: 1.5em;  /* ヘッダ要素の上マージンは広めに取る */
}
~~~

ただし、`h2` や `h3` などのヘッダの上マージンまで同じサイズになってしまうと、セクションごとのまとまりが分かりにくくなってしまいます。
そこで、ヘッダに関しては特別に `1.5em` のマージンを設定しています。
このマージンには、単位として `rem` ではなく、`em` を使用していることに注意してください。
`em` を使用することで、ヘッダ自身のフォントサイズを基準として、1.5 行分のマージンが設定されることになります。
つまり、`h2` 要素のマージンの方が、`h3` 要素のマージンよりも大きくなるということです（`h2` のフォントサイズの方が大きければですが）。

- 参考: [Axiomatic CSS and Lobotomized Owls](https://alistapart.com/article/axiomatic-css-and-lobotomized-owls)
