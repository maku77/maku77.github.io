---
title: "Hugo テンプレートで数値によるループ処理を行う (range, seq)"
url: "p/8vm6xqm/"
date: "2017-12-22"
tags: ["Hugo"]
description: "Hugo のテンプレート内では、Go 言語のような for を使用した数値ループは記述できません。代わりに range を使用します。"
aliases: /hugo/template/loop.html
---

Hugo のテンプレート内では、Go 言語のような `for` を使用した数値ループは記述できません。
代わりに `range` を使用します。

次の例では、[seq 関数](https://gohugo.io/functions/seq/) を使用して 1 から 5 の数値シーケンスを作成し、それらを `range` を使ってループ処理しています。

{{< code lang="html" title="テンプレート内での記述例" >}}
<ul>
  {{ range $val := seq 5 }}
    <li>{{ $val }}
  {{ end }}
</ul>
{{< /code >}}

{{< code lang="html" title="出力結果" >}}
<ul>
    <li>1
    <li>2
    <li>3
    <li>4
    <li>5
</ul>
{{< /code >}}

戻り値を 2 つの変数で受け取れば、0 始まりのインデックスも同時に得ることができます。

{{< code lang="html" title="テンプレート内での記述例" >}}
<ul>
  {{ range $index, $val := seq 5 }}
    <li>{{ $index }} : {{ $val }}
  {{ end }}
</ul>
{{< /code >}}

{{< code lang="html" title="出力結果" >}}
<ul>
    <li>0 : 1
    <li>1 : 2
    <li>2 : 3
    <li>3 : 4
    <li>4 : 5
</ul>
{{< /code >}}

