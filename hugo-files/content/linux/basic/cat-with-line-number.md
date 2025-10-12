---
title: "Linuxメモ: 行番号付きでテキストファイルの内容を出力する (cat -n, cat -b)"
url: "p/sybn8yf/"
date: "2005-04-21"
lastmod: "2025-10-12"
tags: ["linux"]
aliases: /linux/basic/cat-with-line-number.html
changes:
  - 2025-10-12: -n オプションの説明を追加
---

`cat` コマンドには行番号を付加して出力する **`-n`** オプションが用意されています。

{{< code lang="console" title="行番号付きで出力" >}}
$ cat -b input.txt
{{< /code >}}

`-n` オプションの代わりに **`-b`** オプションを使用すると、空行を除いた行に対して行番号が付加されます。

{{< code lang="console" title="空行以外の行にだけ行番号を付ける" >}}
$ cat -b input.txt
{{< /code >}}

