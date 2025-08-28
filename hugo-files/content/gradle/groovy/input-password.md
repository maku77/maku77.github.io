---
title: "Groovy でパスワードなどをユーザに入力させる (readPassword)"
url: "p/yxha7mn/"
date: "2016-10-13"
tags: ["gradle, groovy"]
aliases: ["/gradle/groovy/input-password.html"]
---

キーボードからのパスワード入力
----

Groovy を使用してユーザにパスワードを入力させるには、**`java.io.Console`** オブジェクトの **`readPassword`** メソッドを使用します。

{{< code lang="groovy" title="sample.groovy" >}}
def password = System.console().readPassword('Password? ')
println password
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ groovy sample.groovy
Password? （キーボードから abcabc と入力）
abcabc
{{< /code >}}

