---
title: "Groovy で assert を使用する"
date: "2015-07-07"
---

Groovy では、デフォルトで `assert` が使用できるようになっています。

#### sample.groovy
```groovy
def num = 100
assert num.getClass() == java.lang.Integer

def str = 'Hello'
assert str.getClass() == java.lang.Integer
```

上記のスクリプトを実行すると、2 つ目の `assert` で fail して、下記のように詳細な情報が表示されます。

```sh
$ groovy sample.groovy

Assertion failed:

assert str.getClass() == java.lang.Integer
       |   |          |
       |   |          false
       |   class java.lang.String
       Hello

        at sample.run(sample.groovy:5)
```
