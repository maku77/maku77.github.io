---
title: "Hugo テンプレート内で配列（スライス）変数を扱う (slice, index, range)"
url: "p/7bvjywy/"
date: "2017-12-15"
tags: ["Hugo"]
description: "Hugo テンプレートの中で slice 関数を使用すると、渡されたパラメータ群から新しいスライス（配列）を作成することができます。"
aliases: /hugo/template/array.html
---

Hugo テンプレートの中で slice 関数を使用すると、渡されたパラメータ群から新しいスライス（配列）を作成することができます。

配列（スライス）を定義する (slice)
----

Hugo テンプレートでは、新しくスライス（配列）を定義するための構文は用意されていないため、そのような場合はスライス（配列）を戻り値として返す [slice 関数](https://gohugo.io/functions/slice/) を使用する必要があります。
下記の例では、`slice` 関数に 3 つのパラメータを渡し、それらの要素からなるスライスを生成しています。

{{< code lang="go-html-template" title="テンプレート内での記述例" >}}
{{ $arr := slice "AAA" "BBB" "CCC" }}
{{ printf "%#v" $arr }}
{{< /code >}}

{{< code title="実行結果" >}}
[]interface {}{"AAA", "BBB", "CCC"}
{{< /code >}}

{{% note %}}
Go のテンプレート内で使用できる文法は、Go 言語の文法とは異なっています。
スライスを定義するときに、Go 言語と同じ構文で、`$arr := []string{"AAA", "BBB", "CCC"}` と記述することはできないことに注意してください。
{{% /note %}}


配列（スライス）の要素を参照する (index)
----

インデックス番号指定で配列（スライス）内の要素を参照するには、[index 関数](https://gohugo.io/functions/index-function/) を使用します。
この書き方は、慣れるまでは若干わかりにくいかもしれません。

{{< code lang="go-html-template" title="テンプレート内での記述例" >}}
{{ $arr := slice "AAA" "BBB" "CCC" }}
<ul>
  <li>{{ index $arr 0 }}
  <li>{{ index $arr 1 }}
  <li>{{ index $arr 2 }}
</ul>
{{< /code >}}

{{< code lang="html" title="実行結果" >}}
<ul>
  <li>AAA
  <li>BBB
  <li>CCC
</ul>
{{< /code >}}


配列（スライス）をループで処理する (range)
----

配列の全要素をループで処理したい場合は、__`range`__ を使って以下のように記述します。

{{< code lang="go-html-template" title="テンプレート内での記述例" >}}
{{ $arr := slice "AAA" "BBB" "CCC" }}
<ul>
  {{- range $arr }}
    <li>{{ . }}
  {{- end }}
</ul>
{{< /code >}}

{{< code lang="html" title="実行結果" >}}
<ul>
    <li>AAA
    <li>BBB
    <li>CCC
</ul>
{{< /code >}}

上記の例ではドットコンテキストを置き換えて、各要素をドット (__`.`__) で参照するようにしていますが、次のように変数名を付けてアクセスすることもできます。

{{< code lang="go-html-template" title="テンプレート内での記述例" >}}
{{ $arr := slice "AAA" "BBB" "CCC" }}
<ul>
  {{- range $val := $arr }}
    <li>{{ $val }}
  {{- end }}
</ul>
{{< /code >}}

{{% note %}}
`range` を使ってループ処理をするときに、ループ用の変数を明示的に割り当てても、結局ドットコンテキストの方は置き換えられてしまうようなので、この書き方はあまり意味がないかもしれません。
{{% /note %}}

`range` で配列をループ処理するときに、下記のように 2 つの変数を指定すると、配列のインデックス番号を取得しながらループ処理することができます。

{{< code lang="go-html-template" title="テンプレート内での記述例" >}}
{{ $arr := slice "AAA" "BBB" "CCC" }}
<ul>
  {{- range $index, $val := $arr }}
    <li>{{ $index }} : {{ $val }}
  {{- end }}
</ul>
{{< /code >}}

{{< code lang="html" title="実行結果" >}}
<ul>
    <li>0 : AAA
    <li>1 : BBB
    <li>2 : CCC
</ul>
{{< /code >}}

