---
title: "Hugo のテンプレートやショートコードでランダムな文字列を生成する"
url: "p/9qexh2q/"
date: "2021-02-22"
tags: ["Hugo"]
aliases: /hugo/misc/random.html
---

次の __`random`__ ショートコードは、1 〜 9 の数値をランダムに並び替えた 9 桁の数値文字列を生成します。

{{< code lang="html" title="layouts/shortcodes/random.html" >}}
{{ $random1 := delimit (seq 9 | shuffle) "" }}
{{ $random2 := delimit (shuffle (split "123456789" "" )) "" }}

<p>random1 = {{ $random1 }}</p>
<p>random2 = {{ $random2 }}</p>
{{< /code >}}

{{< code lang="yaml" title="content/page1.md（使用例）" >}}
---
title: "ページタイトル"
---

{{</* random */>}}
{{</ code >}}

{{< code lang="html" title="レンダリング結果" >}}
<p>random1 = 816257394</p>
<p>random2 = 234976851</p>
{{< /code >}}

上記の例では、2 種類の生成方法を示していますが、どちらも次のような手順でランダムな文字列を生成しています。

1. 1 〜 9 の連番からなる配列を生成（`seq 9` あるいは `split "123456789" ""` の部分）
2. [shuffle 関数](https://gohugo.io/functions/shuffle/) で配列要素をシャッフル
3. [delimit 関数](https://gohugo.io/functions/delimit/) で配列要素を 1 つの文字列に結合

配列要素を結合する関数に `delimit` という名前が付けられていますが、多くの言語では `join` とか `concatenate` という名前が一般的ですね。
この関数名にはちょっと戸惑うかも（＾＾；

