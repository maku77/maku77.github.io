---
title: "Groovy で文字列リテラルを扱う"
url: "p/v8m6rme/"
date: "2015-07-08"
tags: ["gradle, groovy"]
aliases: ["/gradle/groovy/string-literal.html"]
---

文字列リテラルと GStrings
----
Groovy の文字列リテラルは、シングルクォート (`'`) あるいは、ダブルクォート (`"`) で囲んで表現します。
ダブルクォートで囲んだ文字列リテラルは、**`$`** で始まる変数が展開されます。
このような変数を含む文字列を **GStrings (Groovy Strings)** と呼びます。

{{< code lang="groovy" title="sample.groovy" >}}
def name = 'Britney'

println 'Hello $name'
println "Hello $name"
{{< /code >}}

{{< code title="実行結果" >}}
Hello $name
Hello Britney
{{< /code >}}


文字列の中の式
----

**`${式}`** という構文を使用すると、文字列の中で任意の式を実行することができます。

{{< code lang="groovy" title="sample.groovy" >}}
def name = 'mickey'
println "Hello ${name.capitalize()}"
{{< /code >}}

{{< code title="実行結果" >}}
Hello Mickey
{{< /code >}}


ヒアドキュメント
----

3 連続のクォーテーションマーク（**`'''`** あるいは **`"""`**）で文字列を囲むことによって、複数行にまたがる文字列リテラル（ヒアドキュメント）を定義することができます。
通常の文字列リテラルと同様に、ダブルクォート (`"`) で囲んだ場合だけ、`$` で始まる変数が展開されます。

```groovy
def html = """<html>
  <head>
    <title>$title</title>
  <head>
  <body>
    $body
  </body>
</html>"""
```

