---
title: "Hugo テンプレート内で変数を扱う"
url: "p/y39gzkc/"
date: "2017-12-15"
tags: ["Hugo"]
description: "Hugo テンプレート内で変数を定義するときは、$ で始まる変数名を付けます。"
aliases: /hugo/template/variable.html
---

Hugo テンプレート内で変数を定義するときは、変数名のプレフィックスとして __`$`__ を付けます。

変数定義の基本
----

Hugo テンプレート内で変数を定義するには、下記のような構文を使用します。
`=` ではなくて、__`:=`__ を使用することに注意してください。

```go-html-template
{{ $変数名 := 初期値 }}
```

上記の構文から分かるように、独自の変数を定義する場合は、変数名のプレフィックスとして __`$`__ を付けます。

{{% note %}}
より正確に言うと、上記のように `$` で始まるようにアクションを記述すると、それが変数定義のためのアクションだと見なされるということです。
{{% /note %}}

次の例では、数値変数 `$x` と、文字列変数 `$y` を定義し、それらの値を出力しています。
変数定義のアクションを実行しただけでは、その変数の値は出力されないので、変数出力のためのアクションを続けて記述しています（下の例では __`{{ $x }}`__ や __`{{ $y }}`__ という部分）。

{{< code lang="go-html-template" title="テンプレート内での記述例" >}}
{{ $x := 100 }}
<p>{{ $x }}</p>

{{ $y := "Hello" }}
<p>{{ $y }}</p>
{{< /code >}}

{{< code lang="html" title="出力結果" >}}
<p>100</p>
<p>Hello</p>
{{< /code >}}


処理結果を変数に格納する
----

ある値をパイプで関数に渡して、処理を行った結果を変数に格納する、といったこともできます。
次の例では、文字列 `maku` を __`printf`__ 関数に渡し、その結果をさらに `printf` 関数に渡しています。
変数 `$x` には、その最終的な結果が格納されます。

{{< code lang="go-html-template" title="テンプレート内での記述例" >}}
{{ $x := "maku" | printf "Hello %s." | printf "%s What's up?" }}
<p>{{ $x }}</p>
{{< /code >}}

{{< code lang="html" title="出力結果" >}}
<p>Hello maku. What's up?</p>
{{< /code >}}

上記では、文字列 `maku` をパイプで `printf` 関数に渡していますが、次のように `printf` 関数のパラメータとして指定することもできます。

```go-html-template
{{ $x := printf "%s What's up?" (printf "Hello %s." "maku") }}
<p>{{ $x }}</p>
```

どちらの方法でも結果は同じですが、上記のように 1 つの値を連続して変換していくようなケースでは、パイプを使って記述したほうが読みやすいように感じます。

{{% note %}}
パイプ (__`|`__) を使用して処理を繋げると、パイプの前に記述した処理結果が、後ろの関数の __最後のパラメータ__ として渡されます。
{{% /note %}}


range アクションでの変数の使用
----

__`range`__ アクションによって配列をループ処理する場合は、各要素の値を変数に取り出しながら処理することができます。

```go-html-template
<h3>ページ一覧</h3>
<ul>
  {{ range $page := .Site.AllPages }}
    <li><a href="{{ $page.RelPermalink }}">{{ $page.Title }}</a>
  {{ end }}
</ul>
```

ただし、上記のように要素の値のみを取り出す場合は、デフォルトのドット (`.`) のコンテキストを置き換えて下記のようにループ処理する方が簡潔に記述できます。

```go-html-template
<h3>ページ一覧</h3>
<ul>
  {{ range .Site.AllPages }}
    <li><a href="{{ .RelPermalink }}">{{ .Title }}</a>
  {{ end }}
</ul>
```
次のように、2 つの変数を用意することで、インデックス（0 始まり）を取得しながらループ処理することもできます。

```go-html-template
<h3>ページ一覧</h3>
<ul>
  {{ range $index, $page := .Site.AllPages }}
  <li><a href="{{ $page.RelPermalink }}">{{ printf "%d: %s" (add $index 1) $page.Title }}</a>
  {{ end }}
</ul>
```

`<ul>` じゃなくて `<ol>` で表示すればいいじゃん、というツッコミはなしで（＾＾；


with アクションでの変数の使用
----

__`with`__ アクションで変数スコープを制限する場合にも、変数を使用することができます。

```go-html-template
{{ with $x := "maku" }}
  Hello {{ $x }}
{{ end }}
```

上記のようにすると、`with ～ end` のブロックの外からは、変数 `$x` は参照できなくなります。
