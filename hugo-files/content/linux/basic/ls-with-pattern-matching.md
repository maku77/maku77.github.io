---
title: "Linuxメモ: ls コマンドでパターンに一致するファイルだけを列挙する"
url: "p/vtkjxha/"
date: "2005-05-22"
tags: ["linux"]
aliases: /linux/basic/ls-with-pattern-matching.html
---

`ls` コマンドでファイルを列挙するときに、Linux シェルのワイルドカードやグロブパターンを利用すると、特定のパターンにマッチするファイルだけを列挙できます。
**`?`** は任意の 1 文字、**`*`** は 0 文字以上の任意の文字列にマッチします。

{{< code lang="console" title="例: 3文字以上のファイルを列挙する" >}}
$ ls ???*
{{< /code >}}

{{< code lang="console" title="例: 01, 02, 03 のいずれかの文字列を含むファイルを列挙する" >}}
$ ls *{01,02,03}*   # 方法1
$ ls *0[1-3]*       # 方法2
{{< /code >}}

