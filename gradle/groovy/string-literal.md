---
title: "Groovy で文字列リテラルを扱う"
date: "2015-07-08"
---

文字列リテラルの基本
----
Groovy の文字列リテラルは、シングルクォート (`'`) あるいは、ダブルクォート (`"`) で囲んで表現します。
ダブルクォートで囲んだ文字列リテラルは、`$` で始まる変数を展開する機能を持っています。
このような変数を含む文字列を **GStrings (Groovy Strings)** と呼びます。

#### sample.groovy
```groovy
def name = 'Britney'

println 'Hello $name'
println "Hello $name"
```

#### 実行結果
```
Hello $name
Hello Britney
```


文字列の中の式
----

`${式}` という構文を使用すると、文字列の中で任意の式を実行することができます。

#### sample.groovy
```groovy
def name = 'mickey'
println "Hello ${name.capitalize()}"
```

#### 実行結果
```sh
Hello Mickey
```

ヒアドキュメント
----
3 連続のクォーテーションマークで文字列を囲むことによって、複数行にまたがる文字列リテラル（ヒアドキュメント）を定義することができます。
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

