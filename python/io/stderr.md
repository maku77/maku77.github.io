---
title: "標準エラー出力へ出力する (sys.stderr)"
date: "2018-10-14"
---

Python で標準エラー出力へ出力するには、`print` の `file` パラメータに `sys.stderr` を指定します。

#### sys.stderr.write の使用例

~~~ python
import sys

# ...
if not config():
    print('Error: configuration failed', file=sys.stderr)
    sys.exit(1)
~~~

ちなみに、`file` パラメータで指定できるのは、`write` メソッドを持っているオブジェクトです。
`sys.stderr` オブジェクトも `write` メソッドを持っているので、上記のように指定することができます。


コラム: Python2 までの方法
----

Python2 までは下記のように標準エラー出力に出力することができました。

#### Python2 までの方法

~~~ python
import sys

print >> sys.stderr, 'エラーメッセージ'
~~~
