---
title: "Python で標準エラー出力へ出力する (sys.stderr)"
url: "p/m3si4w6/"
date: "2018-10-14"
lastmod: "2023-06-07"
changes:
  - 2023-06-07: sys.exit でメッセージ出力までやってしまう方法を追加
tags: ["Python"]
aliases: /python/io/stderr.html
---

標準エラー出力への出力
----

Python の `print` の出力先を標準エラー出力 (STDERR) にするには、__`file`__ オプションで __`sys.stderr`__ を指定します。

{{< code lang="python" title="標準エラー出力への出力" >}}
import sys

# ...
if not is_valid_config():
    print("Error: invalid configuration", file=sys.stderr)
    sys.exit(1)
{{< /code >}}

ちなみに、`file` 引数には `write` メソッドを持っているオブジェクトを渡せるようになっています。
`sys.stderr` オブジェクトも `write` メソッドを持っているので、上記のように `file` 引数に渡すことができます。

上記の例では、標準エラーにメッセージ出力をして、さらに `sys.exit(1)` でプログラムをエラー終了させていますが、実はこの処理は、`sys.exit` 関数にエラーメッセージを渡すことで 1 行で記述できます。

{{< code lang="python" title="標準エラーへ出力してエラー終了する" >}}
sys.exit("Error: invalid configuration")
{{< /code >}}


コラム: Python2 までの方法
----

Python2 までは下記のように標準エラー出力に出力することができました。

{{< code lang="python" title="古い Python2 のやり方" >}}
import sys

print >> sys.stderr, 'エラーメッセージ'
{{< /code >}}

