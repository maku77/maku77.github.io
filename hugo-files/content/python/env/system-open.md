---
title: "Pythonメモ: 拡張子に関連付けられたアプリケーションでファイルを開く (os.system)"
url: "p/okhfyfx/"
date: "2009-01-05"
tags: ["python"]
aliases: ["/python/env/system-open.html"]
---

`os.system()` に任意のコマンドを渡すと、その OS のコマンドライン端末からコマンドを実行したのと同様の効果を得られます。
下記のようにすれば、.txt ファイルを関連付けられたアプリケーションで開くことができます。

{{< code lang="python" title="Windows の場合" >}}
import os
os.system('sample.txt')
{{< /code >}}

{{< code lang="python" title="Mac OSX の場合" >}}
import os
os.system('open sample.txt')
{{< /code >}}
