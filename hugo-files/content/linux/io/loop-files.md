---
title: "Linux シェルスクリプト: ディレクトリ内のファイルを順に処理する (for, while)"
url: "p/or3cmv6/"
date: "2011-02-06"
tags: ["Linux"]
aliases: /linux/io/loop-files.html
---

ワイルドカードを使用する方法
----

ワイルドカード (__`*`__) を使って、カレントディレクトリ内のファイルをループ処理することができます。

{{< code lang="bash" title="例: すべてのファイル（ドットファイルを除く）をループ処理" >}}
for x in *; do
  echo "$x"
done
{{< /code >}}

特定の拡張子を持つファイルのみを列挙するには次のようにします。

{{< code lang="bash" title="例: すべての PNG ファイルをループ処理" >}}
for x in *.png; do
  echo $x
done
{{< /code >}}

次のように一行で書くこともできます。

```bash
for x in *.png; do echo "$x"; done
```


find コマンドの結果を使用する方法（再帰的にファイル探索）
----

下記の例では、`find` コマンドによって見つかったファイルを `while` ループで順番に処理しています。
深い階層にあるファイルも再帰的に処理されます。

```bash
find . -type f | while read x; do
  echo "$x"
done
```

