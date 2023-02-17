---
title: "Hugo テンプレート内で if や with で分岐処理する"
url: "p/ewoqwrk/"
date: "2017-12-11"
tags: ["Hugo"]
description: "Hugo テンプレートの中で、if アクションや with アクションを使用することで、分岐処理を行うことができます。"
aliases: /hugo/layout/grammer/if.html
---

Hugo テンプレートの中で、__`if`__ アクションや __`with`__ アクションを使用することで、分岐処理を行うことができます。

if による分岐処理
----

### if の構文

Hugo のテンプレート内で分岐処理を行うには、下記のように `if` ~ `end` を使用します。
必要に応じて、`else` や `else if` を挟むことができます。

```
{{ if ● }} ... {{ end }}
{{ if ● }} ... {{ else }} ... {{ end }}
{{ if ● }} ... {{ else if ▲ }} ... {{ end }}
{{ if ● }} ... {{ else if ▲ }} ... {{ else }} ... {{ end }}
```

__`{{`__ と __`}}`__ で囲まれた部分は、Go のテンプレートでは __アクション__ と呼ばれている部分で、基本的に 1 つのアクションだけを記述することができます（関数をパイプで結んだりすることはできる）。
`if` による条件式を記述する部分と、`end` でブロックを閉じる部分は、上記のように分けて記述する必要があるため、若干まわりくどくなってしまうケースがあるかもしれませんが、そこは我慢するしかありません。

`if` の条件式（上記の ● の部分）において「偽」とみなされる値は、__空値 (empty value)__ です。
空値となるのは下記のような値です。

* `false`
* `0` とみなされる数値（`0.0` も含む）
* サイズ 0 の配列、スライス、マップ
* 空文字列 (`""`)
* `nil` ポインタ

{{< code title="真とみなされる例（T と表示される）" >}}
{{ if true  }} T {{ else }} F {{ end }}
{{ if "ABC" }} T {{ else }} F {{ end }}
{{ if 1     }} T {{ else }} F {{ end }}
{{ if -1    }} T {{ else }} F {{ end }}
{{< /code >}}

{{< code title="偽とみなされる例（F と表示される）" >}}
{{ if false }} T {{ else }} F {{ end }}
{{ if ""    }} T {{ else }} F {{ end }}
{{ if 0     }} T {{ else }} F {{ end }}
{{ if 0.0   }} T {{ else }} F {{ end }}
{{< /code >}}

条件式の部分で、値の比較を行いたい場合は、下記のような __`eq`__ や __`ne`__ といった二値関数 (boolean functions) を使用します。
比較する 2 つの値は、後ろに続けて記述することに注意してください。

| 記述方法 | いつ真とみなすか？ | リファレンス |
| ---- | ---- | ---- |
| __`eq ARG1 ARG2`__ | ARG1 ＝ ARG2 のとき | [eq 関数](https://gohugo.io/functions/eq/) |
| __`ne ARG1 ARG2`__ | ARG1 ≠ ARG2 のとき | [ne 関数](https://gohugo.io/functions/ne/) |
| __`lt ARG1 ARG2`__ | ARG1 ＜ ARG2 のとき | [lt 関数](https://gohugo.io/functions/lt/) |
| __`le ARG1 ARG2`__ | ARG1 ≦ ARG2 のとき | [le 関数](https://gohugo.io/functions/le/) |
| __`gt ARG1 ARG2`__ | ARG1 ＞ ARG2 のとき | [gt 関数](https://gohugo.io/functions/gt/) |
| __`ge ARG1 ARG2`__ | ARG1 ≧ ARG2 のとき | [ge 関数](https://gohugo.io/functions/ge/) |

### if の使用例

次の例では、数値変数の値が 100 より大きいかを、`gt` 関数を使用して調べています。

{{< code title="変数 $x の値が 100 より大きいか調べる" >}}
{{ $x := 200 }}
{{ if gt $x 100 }}
  x is greater than 100
{{ end }}
{{< /code >}}

__文字列変数の比較__ にも `eq`（等しい）や `ne`（等しくない）を使用することができます。

{{< code title="変数 $s の値が hello かどうか調べる" >}}
{{ $s := "hello" }}
{{ if eq $s "hello" }}
  s is hello
{{ end }}
{{< /code >}}


with による分岐処理
----

### with の構文

`if` アクションの代わりに、__`with`__ アクションを使用して分岐処理を記述すると、ブロックの中のコンテキスト（ドット __`.`__）を、条件式で評価した値に置き換えることができます。

```
{{ with ● }} ... {{ end }}
{{ with ● }} ... {{ else }} ... {{ end }}
```

使い方は、例を見たほうが分かりやすいかもしれません。
次の例では、`with` アクションの条件式で `Maku` という文字列が指定されているため、ブロックの中でドット (`.`) を参照することで、`Maku` という文字列を取得することができます。

{{< code title="テンプレート内で with アクションを利用する" >}}
{{ with "Maku" }}
  {{ printf "I am %s" . }}
{{ end }}
{{< /code >}}

{{< code title="実行結果" >}}
I am Maku
{{< /code >}}

通常、ドット (`.`) はトップレベルのコンテキスト（Hugo のレイアウトファイルの中では通常 `Page` オブジェクト）を表しますが、上記のように `with` アクションを使用することで、コンテキストを切り替えて、ブロック内の記述をシンプルにすることができます。

逆に、`with` ブロックでコンテキストを切り替えた後に、トップレベルのコンテキストを明示的に参照したい場合は、次のように __`$`__ プレフィックスを指定して参照します。

{{< code title="with ブロックの中からトップレベルコンテキストを参照する" >}}
{{ with "Maku" }}
  {{ printf "Hello, %s. Page title is %s" . $.Title }}
{{ end }}
{{< /code >}}

{{< code title="実行結果" >}}
Hello, Maku. Page title is SAMPLE_TITLE.
{{< /code >}}

### with の使用例

下記のサンプルコードは、Web サイトの設定ファイル (`config.toml`) に、サイトの説明文や作者が設定されている場合に、それを meta 要素として出力する例です。

{{< code lang="html" title="テンプレート内での記述例" >}}
{{ with .Site.Params.author -}}
  <meta name="author" content="{{ . }}">
{{- end }}
{{ with .Site.Params.description -}}
  <meta name="description" content="{{ . }}">
{{- end }}
{{< /code >}}

{{< code lang="toml" title="設定ファイルの記述例 (config.toml)" >}}
title = "My New Hugo Site"

[params]
  author = "Maku"
  description = "This is my first Hugo site. It's very cool."
{{< /code >}}

{{< code lang="html" title="出力結果" >}}
<meta name="author" content="Maku">
<meta name="description" content="This is my first Hugo site. It&#39;s very cool.">
{{< /code >}}

クォーテーションマークが、出力箇所のコンテキストに応じて、自動的にエスケープ処理されてますね。Cool!

