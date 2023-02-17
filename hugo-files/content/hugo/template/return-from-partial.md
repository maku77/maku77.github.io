---
title: "Hugo のパーシャルテンプレートから値を返す（関数化）(return)"
url: "p/4anjern/"
date: "2020-04-06"
tags: ["Hugo"]
aliases: /hugo/template/return-from-partial.html
---

パーシャルテンプレートを関数として使用する
----

[Hugo v.0.55](https://github.com/gohugoio/hugo/issues/5783) 以降のパーシャルテンプレートでは、テキストを出力する代わりに、値を __`return`__ できるようになっています（参考: [Returning a value from a Partial ｜ Hugo](https://gohugo.io/templates/partials/#returning-a-value-from-a-partial)）。

例えば、次の `my-add-100` パーシャルテンプレートは、引数で受け取った値に 100 を足した値を返します。

{{< code title="layouts/partials/functions/my-add-100.html" >}}
{{ $ret := add 100 . }}
{{ return $ret }}
{{< /code >}}

呼び出し側のテンプレートからは、次のように関数のように使用できます。

{{< code lang="html" title="layouts/_default/single.html（抜粋）" >}}
{{ $ret := partial "functions/my-add-100" 50 }}

計算結果: <b>{{ $ret }}</b>
{{< /code >}}

レンダリング結果は次のようになります。

```html
計算結果: <b>150</b>
```


パーシャルテンプレートからリストを返す
----

パーシャルテンプレートの `return` では様々な型のオブジェクトを返すことができます。
例えば次の `get-colors` パーシャルテンプレートでは、__`slice`__ で作成したリストを返しています。

{{< code title="layouts/partials/functions/get-colors.html" >}}
{{ $ret := slice "Blue" "Red" "Yellow" }}
{{ $ret = $ret | append "Black" "White" }}
{{ return $ret }}
{{< /code >}}

呼び出し元のテンプレートファイルでは、`get-colors` が返したリストを `range` などでループ処理します。

{{< code lang="html" title="layouts/_default/single.html" >}}
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

