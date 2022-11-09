---
title: "Windows のバッチファイルの実行を途中で終了する (exit /b)"
url: "p/66q7m2j/"
date: "2015-02-09"
tags: ["Windows", "バッチファイル"]
aliases: /windows/exit-batch.html
---

バッチファイルの実行を途中で終了したい場合は、次のコマンドを実行します。

```bat
EXIT /B
```

バッチファイルの中で単純に `exit` コマンドを実行すると、バッチファイルを実行していたコマンドプロンプト（コマンドシェル）ごと終了してしまいますが、__`/B`__ オプション付きで実行すると、__コマンドプロンプト自体は終了せずに、バッチファイルの実行だけを終了__ してくれます。

{{< code lang="bat" title="sample.cmd" >}}
@echo off
echo AAA
exit /b
echo BBB
{{< /code >}}

例えば、上記の `sample.cmd` を実行すると、`echo BBB` は実行せずに終了します。

{{< code lang="console" title="実行結果" >}}
C:\> sample
AAA
{{< /code >}}

