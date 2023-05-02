---
title: "Hugo のパーシャルテンプレートから値を返す（関数化）(return)"
url: "p/4anjern/"
date: "2020-04-06"
lastmod: "2023-05-03"
tags: ["Hugo"]
aliases: /hugo/template/return-from-partial.html
changes:
  - 2023-05-03: 複数の値の渡し方と、インラインパーシャルテンプレートの説明
---

パーシャルテンプレートを関数として使用する
----

[Hugo v.0.55](https://github.com/gohugoio/hugo/issues/5783) 以降のパーシャルテンプレートでは、テキストを出力する代わりに、値を __`return`__ できるようになっています（参考: [Returning a value from a Partial ｜ Hugo](https://gohugo.io/templates/partials/#returning-a-value-from-a-partial)）。
この機能をうまく使うと、テンプレート内の共通処理をくくり出して、コードをスッキリさせることができます（通常のプログラミング言語の関数と同じイメージ）。

例えば、次の `my-add-100` パーシャルテンプレートは、引数で受け取った値に 100 を足した値を返します。
パーシャルテンプレートの呼び出し時に渡された引数は、コンテキスト (__`.`__) として参照できます。

{{< code lang="go-html-template" title="layouts/partials/functions/my-add-100.html" >}}
{{ $ret := add 100 . }}
{{ return $ret }}
{{< /code >}}

ここでは、関数風に呼び出せるパーシャルテンプレートであることを示すために、`functions` というディレクトリ以下に HTML ファイルを保存していますが、このあたりは自由です。
呼び出し側のテンプレートからは、次のように関数のように呼び出して、戻り値を受け取ることができます。

{{< code lang="go-html-template" title="layouts/_default/single.html（抜粋）" >}}
{{ $ret := partial "functions/my-add-100" 50 }}

戻り値: <b>{{ $ret }}</b>
{{< /code >}}

レンダリング結果は次のようになります。

```html
戻り値: <b>150</b>
```


パーシャルテンプレートに複数の値（引数）を渡す
----

パーシャルテンプレートに複数の値を渡すには、辞書オブジェクト（キー＆バリュー）の形で値を渡します。
次のパーシャンテンプレート (`my-add`) は、2 つの数値を受け取り、それらを足し合わせた結果を返します。
ここでは、辞書のキー名として `val1` と `val2` を使用することを想定していますが、キー名は何でも構いません。
下記のように、パーシャルテンプレートの先頭で辞書キーを参照して変数に代入するようにしておくと、このパーシャルテンプレートがどのような辞書キーを期待しているのかが分かりやすくなります。

{{< code lang="go-html-template" title="layouts/partials/functions/my-add.html" >}}
{{ $v1 := .val1 }}
{{ $v2 := .val2 }}
{{ return (add $v1 $v2) }}
{{< /code >}}

このパーシャルテンプレートを呼び出すには、次のように __`dict`__ 関数を使って辞書オブジェクトを作成して引数として渡します。
ここでは、`val1` に 100、`val2` に 200 という値を入れて渡しているので、結果として 300 という値が返ってきます。

{{< code lang="go-html-template" title="テンプレートからの呼び出し例" >}}
{{- $ret := partial "functions/my-add" (dict "val1" 100 "val2" 200) }}
戻り値: <b>{{ $ret }}</b>
{{< /code >}}

{{< code lang="html" title="レンダリング結果" >}}
戻り値: <b>300</b>
{{< /code >}}

{{% note title="引数は 1 つしか渡せない" %}}
`partial` 関数の呼び出し時に指定した第 2 引数は、パーシャルテンプレート側のコンテキスト (`.`) として参照できるようになるのですが、次のように複数の値を単純に並べて渡すことはできません。

{{< code lang="go-html-template" title="間違った呼び出し方" >}}
{{ $ret := partial "functions/my-add" 100 200 }}
{{< /code >}}

なぜなら、第 3 引数の値 (200) は、パーシャルテンプレート呼び出しのキャッシュ用のキーとして使われるという仕様になっているからです。
詳しくは、公式サイトの [partialCached 関数](https://gohugo.io/functions/partialcached/) の説明を参照してください。
{{% /note %}}


パーシャルテンプレートからリストを返す
----

パーシャルテンプレートの `return` では、様々な型のオブジェクトを返すことができます。
例えば次の `get-colors` パーシャルテンプレートでは、__`slice`__ で作成したリストを返しています。

{{< code lang="go-html-template" title="layouts/partials/functions/get-colors.html" >}}
{{ $ret := slice "Blue" "Red" "Yellow" }}
{{ $ret = $ret | append "Black" "White" }}
{{ return $ret }}
{{< /code >}}

呼び出し元のテンプレートファイルでは、`get-colors` が返したリストを `range` などでループ処理します。

{{< code lang="go-html-template" title="layouts/_default/single.html" >}}
<ul>
  {{- range partial "functions/get-colors" . }}
    <li>{{ . }}
  {{- end }}
</ul>
{{< /code >}}

{{< code lang="html" title="実行結果" >}}
<ul>
  <li>Blue
  <li>Red
  <li>Yellow
  <li>Black
  <li>White
</ul>
{{< /code >}}


インラインパーシャルテンプレート
----

パーシャルテンプレートは、通常 `layouts/partils` ディレクトリ以下に HTML ファイルとして作成しますが、テンプレート（ショートコードやレイアウトファイル）の中で、__`define`__ 関数を使って、インラインに定義してしまうこともできます（参考: [Inline Partials](https://gohugo.io/templates/partials/#inline-partials)）。
特定のショートコード内でしか使わないような関数を定義したいときに利用できるかもしれません。

{{< code lang="go-html-template" title="layouts/shortcodes/my-shortcode.html" >}}
{{/* インラインパーシャルを定義する （partials プレフィックスを付ける） */}}
{{ define "partials/functions/inline-add" }}
  {{ $v1 := .val1 }}
  {{ $v2 := .val2 }}
  {{ return (add $v1 $v2) }}
{{ end }}

{{/* 上記は同じテンプレート内から呼び出せる （partials プレフィックスは省略できる） */}}
{{- $ret := partial "functions/inline-add" (dict "val1" 100 "val2" 200) }}
戻り値 = {{ $ret }}
{{< /code >}}

`define` 関数で、インラインパーシャルテンプレートを定義する時は、プレフィックスとして __`partials/`__ を付けることに注意してください。
これにより、`partial` 関数による呼び出しが可能になります。
`partials/` プレフィックスを付けずに定義した場合は、通常の部分テンプレートとして `template` 関数で呼び出すことができるようになるのですが、その方法だと戻り値を返すことができません（戻り値を返すことができるのは、パーシャルテンプレートを `partial` 関数で呼び出したときのみです）。

パーシャルテンプレートの名前空間はグローバルに共有されるため、名前のコンフリクトには注意してください。
同じ名前のテンプレートが定義されている場合は、Hugo ビルド時に次のようなエラーになります。

```
multiple definition of template "partials/functions/inline-add"
```

