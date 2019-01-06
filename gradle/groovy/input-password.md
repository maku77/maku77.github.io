---
title: "Groovy でパスワードなどをユーザに入力させる"
date: "2016-10-13"
---

キーボードからのパスワード入力
----

Groovy を使用してユーザにパスワードを入力させるには、`java.io.Console` オブジェクトの `readPassword` メソッドを使用します。

#### sample.groovy

```groovy
def password = System.console().readPassword('Password? ')
println password
```

#### 実行結果

```
$ groovy sample.groovy
Password? （キーボードから abcabc と入力）
abcabc
```

