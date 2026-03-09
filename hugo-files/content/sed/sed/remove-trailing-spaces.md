---
title: "sed/awkメモ: sed で行末のスペースを削除する"
url: "/p/2km5fqj/"
date: "2010-08-26"
tags: ["sed/awk"]
aliases: [/sed/sed/remove-trailing-spaces.html]
---

次のようにすると、テキストファイル `input.txt` の各行の行末にあるスペースを取り除くことができます。

```console
$ sed 's/\s*$//' input.txt
```

ちなみに、シェルスクリプトの中で、変数に格納したテキストの末尾のスペースを削除するには、例えば下記のようにします。

```bash
line='aaa bbb ccc  '
line=$(echo $line | sed 's/\s*$//')
```

